package com.yudas.pancasila_quiz_apk.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Yudas Malabi on 10/08/2019.
 */

interface Functions {

    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("telepon") telepon: String,
        @Field("password") password: String): Call<Value>

//    @FormUrlEncoded
//    @POST("login.php")
//    fun login(
//        @Field("email") email: String,
//        @Field("password") password: String
//
//    ):Call<User>

}

