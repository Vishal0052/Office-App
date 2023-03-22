package com.example.officeapp.network

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
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

     @GET("user/getalluser")
     suspend fun getUsers(@Header("authorization") token: String,@Query("role") role : String):Response<UserDataRes>

     @POST("user/login")
     suspend fun login(@Body loginDataModel: LoginDataModel) : Response<LoginResponse>

     @POST("user/createuser")
     suspend fun createUser(@Header("authorization") token:String , @Body createUser : CreateUser ) : Response<CreateUserResponse>

     @HTTP(method = "DELETE", path = "user/removeuser", hasBody = true)
     suspend fun deleteUser(@Header("authorization") token:String , @Body deleteUserData : DeleteUserData ) : Response<DeleteUserResponse>

      @GET("menu/getitems")
      suspend fun getMenuItems(@Header("authorization")token: String) : Response<GetMenuResponse>

      @GET("order/getallorders")
      suspend fun getFilterOrders(@Header("authorization") token: String,@Query("status") status : String) : Response<GetAllOrderResponse>

    @GET("order/getallorders")
    suspend fun getAllOrders(@Header("authorization") token: String) : Response<GetAllOrderResponse>

    @GET("order/getyourorder")
    suspend fun getYourOrders(@Header("authorization") token: String) : Response<GetYourOrderResponse>

    @HTTP(method = "DELETE", path = "order/delete", hasBody = true)
    suspend fun deleteOrder(@Header("authorization") token:String , @Body deleteOrderData: DeleteOrderData ) : Response<DeleteOrderResponse>


    @POST("order/create")
      suspend fun createOrders(@Header("authorization") token: String , @Body createOrderData: CreateOrderData) : Response<CreateOrderDataResponse>

      @PUT("user/resetpass")
      suspend fun resetPassword(@Header("authorization")token:String ,@Body resetPasswordData: ResetPasswordData) : Response<ResetPasswordResponse>

    @PUT("order/updatestatus")
    suspend fun changeOrdersStatus(@Header("authorization")token :String ,@Body changeOrderStatusData: ChangeOrderStatusData) : Response<ChangeOrderStatusResponse>

    @HTTP(method = "DELETE", path = "menu/deleteitem", hasBody = true)
    suspend fun deleteMenuItem(@Header("authorization") token:String , @Body deleteMenuData: DeleteMenuData ) : Response<DeleteMenuResponse>

    @POST("menu/additem")
    suspend fun addMenu(@Header("authorization") token: String , @Body addMenuData: AddMenuData ) : Response<AddMenuResponse>

    @POST("user/resetuser")
    suspend fun resetUser(@Body forgetPasswordData: ForgetPasswordData) : Response<ForgetPasswordResponse>

}