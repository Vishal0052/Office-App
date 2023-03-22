package com.example.officeapp.repository

import android.util.Log
import com.example.officeapp.model.*
import com.example.officeapp.model.addMenu.AddMenuData
import com.example.officeapp.model.addMenu.AddMenuResponse
import com.example.officeapp.model.createOrder.CreateOrderData
import com.example.officeapp.model.createOrder.CreateOrderDataResponse
import com.example.officeapp.model.deleteOrder.DeleteOrderData
import com.example.officeapp.model.deleteOrder.DeleteOrderResponse
import com.example.officeapp.model.deleteUser.DeleteUserResponse
import com.example.officeapp.model.deleteUser.DeleteUserData
import com.example.officeapp.model.forgetPassword.ForgetPasswordData
import com.example.officeapp.model.forgetPassword.ForgetPasswordResponse
import com.example.officeapp.model.orderStatus.ChangeOrderStatusData
import com.example.officeapp.model.orderStatus.ChangeOrderStatusResponse
import com.example.officeapp.model.resetPassword.ResetPasswordData
import com.example.officeapp.model.resetPassword.ResetPasswordResponse
import com.example.officeapp.model.userData.UserDataRes
import com.example.officeapp.network.ApiService
import com.example.officeapp.utils.Resource
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class OfficeRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun loginUser(loginDataModel: LoginDataModel): Resource<LoginResponse> {
        return try {
            val loginResponse = apiService.login(loginDataModel)
            if (loginResponse.isSuccessful) {
                Log.e("loginResponse", "1")
                Resource.Success(loginResponse.body())
            } else {
                Log.e("loginResponse", "0")
                Resource.Error("${loginResponse.errorBody()?.string()}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }
    }

    suspend fun createUser(token: String, createUser: CreateUser): Resource<CreateUserResponse> {

        return try {
            val createUserResponse = apiService.createUser(token, createUser)
            //   Log.e("error","${createUserResponse.errorBody()?.string()}")
            if (createUserResponse.isSuccessful) {
                Resource.Success(createUserResponse.body())
            } else {
                Resource.Error("${createUserResponse.errorBody()?.string()}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }

    }

    suspend fun getUser(token: String, role: String): Resource<UserDataRes> {

        return try {

            val getUserResponse = apiService.getUsers(token, role)


            if (getUserResponse.isSuccessful) {
                Resource.Success(getUserResponse.body())
            } else {
                Log.e("error", "${getUserResponse.errorBody()?.string()}")
                Resource.Error("${getUserResponse.errorBody()?.string()}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }

    }

    suspend fun deleteUser(
        token: String,
        deleteUserData: DeleteUserData
    ): Resource<DeleteUserResponse> {
        return try {
            val deleteUserResponse = apiService.deleteUser(token, deleteUserData)
            Log.e("errorBody", "${deleteUserResponse.errorBody()?.string()}")
            if (deleteUserResponse.isSuccessful) {
                Resource.Success(deleteUserResponse.body())
            } else {
                Resource.Error("${deleteUserResponse.errorBody()?.string()}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }
    }

    suspend fun getMenuItems(token: String): Resource<GetMenuResponse> {
        return try {
            val getMenuItemResponse = apiService.getMenuItems(token)
            if (getMenuItemResponse.isSuccessful) {
                Resource.Success(getMenuItemResponse.body())
            } else {
                Resource.Error("${getMenuItemResponse.errorBody()?.toString()}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }
    }

    suspend fun getFilterOrder(token: String, status: String): Resource<GetAllOrderResponse> {
        return try {
            val getFilterOrderRes = apiService.getFilterOrders(token, status)
            if (getFilterOrderRes.isSuccessful) {
                Log.e("empty", getFilterOrderRes.body().toString())
                Resource.Success(getFilterOrderRes.body())

            } else {
                getFilterOrderRes.errorBody()?.toString()?.let { Log.e("empty", it) }
                Resource.Error("${getFilterOrderRes.errorBody()?.toString()}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }
    }

    suspend fun getAllOrder(token: String): Resource<GetAllOrderResponse> {
        return try {
            val getAllOrderRes = apiService.getAllOrders(token)
            if (getAllOrderRes.isSuccessful && getAllOrderRes.code() == 200) {
                Log.e("empty", getAllOrderRes.body().toString())
                Resource.Success(getAllOrderRes.body())
            } else {
//                getAllOrderRes.errorBody()?.toString()?.let { Log.e("empty", it) }
//                Resource.Error("${getAllOrderRes.errorBody()?.toString()}")
                Resource.code(getAllOrderRes.code())
            }

        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }
    }

    suspend fun getYourOrders(token: String): Resource<GetYourOrderResponse> {
        return try {
            val getYourOrderRes = apiService.getYourOrders(token)
            if (getYourOrderRes.isSuccessful) {
                Resource.Success(getYourOrderRes.body())

            } else {
//                Resource.Error("${getYourOrderRes.errorBody()?.toString()}")
                Resource.code(getYourOrderRes.code())
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }
    }

    suspend fun createOrders(
        token: String,
        createOrderData: CreateOrderData
    ): Resource<CreateOrderDataResponse> {
        return try {
            val createOrderResponse = apiService.createOrders(token, createOrderData)

            if (createOrderResponse.isSuccessful) {
                Resource.Success(createOrderResponse.body())
            } else {
                Resource.Error("${createOrderResponse.errorBody()?.string()}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }
    }

    suspend fun deleteOrder(
        token: String,
        deleteOrderData: DeleteOrderData
    ): Resource<DeleteOrderResponse> {
        return try {
            val deleteOrderResponse = apiService.deleteOrder(token, deleteOrderData)

            if (deleteOrderResponse.isSuccessful) {
                Resource.Success(deleteOrderResponse.body())
            } else {
                Resource.Error("${deleteOrderResponse.errorBody()?.string()}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }
    }

    suspend fun resetPassword(
        token: String,
        resetPasswordData: ResetPasswordData
    ): Resource<ResetPasswordResponse> {

        return try {
            val getResetResponse = apiService.resetPassword(token, resetPasswordData)
            if (getResetResponse.isSuccessful) {
                Resource.Success(getResetResponse.body())
            } else {
                val jsonObject = JSONObject(getResetResponse.errorBody()?.string())
                if (jsonObject.getString("message") != null) {
                    Log.e("update", "${jsonObject.getString("message")}")
                    Resource.Error("${jsonObject.getString("message")}")

                } else
                    Resource.Error("${jsonObject.getString("message")}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }

    }

    suspend fun changeOrdersStatus(
        token: String,
        changeOrderStatusData: ChangeOrderStatusData
    ): Resource<ChangeOrderStatusResponse> {

        return try {
            val getChangeResponse = apiService.changeOrdersStatus(token, changeOrderStatusData)
            if (getChangeResponse.isSuccessful) {
                Log.e("changeRes", getChangeResponse.body().toString())
                Resource.Success(getChangeResponse.body())
            } else {
                Resource.Error("${getChangeResponse.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage)
        }
    }

    suspend fun deleteMenuItem(
        token: String,
        deleteMenuData: DeleteMenuData
    ): Resource<DeleteMenuResponse> {
        return try {
            val deleteMenuResponse = apiService.deleteMenuItem(token, deleteMenuData)

            if (deleteMenuResponse.isSuccessful) {
                Resource.Success(deleteMenuResponse.body())
            } else {
                Resource.Error("${deleteMenuResponse.errorBody()?.string()}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }
    }

    suspend fun addMenu(token: String, addMenuData: AddMenuData): Resource<AddMenuResponse> {
        return try {
            val addMenuResponse = apiService.addMenu(token, addMenuData)

            if (addMenuResponse.isSuccessful) {
                Resource.Success(addMenuResponse.body())
            } else {
                Resource.Error("${addMenuResponse.errorBody()?.string()}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }
    }


    suspend fun resetUser(forgetPasswordData: ForgetPasswordData): Resource<ForgetPasswordResponse> {
        return try {
            val resetResponse = apiService.resetUser(forgetPasswordData)

            if (resetResponse.isSuccessful) {
                Log.e("reset", resetResponse.body().toString())
                Resource.Success(resetResponse.body())

            } else {
                resetResponse.errorBody()?.string()?.let { Log.e("reset", it) }
                Resource.Error("${resetResponse.errorBody()?.string()}")
            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage)
        }
    }
}