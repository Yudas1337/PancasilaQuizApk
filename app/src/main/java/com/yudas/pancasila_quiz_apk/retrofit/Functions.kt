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
    @POST("rest_daftar.php")
    fun register(
        @Field("namaUser") namaUser: String,
        @Field("emailUser") emailUser: String,
        @Field("hpUser") hpUser: String,
        @Field("passUser") passUser: String): Call<Value>

//    @FormUrlEncoded
//    @POST("login.php")
//    fun login(
//        @Field("email") email: String,
//        @Field("password") password: String
//
//    ):Call<User>

}

