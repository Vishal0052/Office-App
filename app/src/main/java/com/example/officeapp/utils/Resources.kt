package com.example.officeapp.utils

sealed class Resource<T:Any> {

    class loading<T:Any>() :Resource<T>()

     class Success<T:Any>(val data: T?):Resource<T>()

     class Error<T:Any>(val message: String?) :Resource<T>()

    class code<T:Any>(val code : Int?) : Resource<T>()

}