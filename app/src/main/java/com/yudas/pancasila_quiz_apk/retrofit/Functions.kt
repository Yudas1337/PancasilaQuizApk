package com.yudas.pancasila_quiz_apk.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Yudas Malabi on 10/08/2019.
 */

interface Functions {

    @GET("rest_soal.php")
    fun tampilsoal(): Call<Value>

    @FormUrlEncoded
    @POST("rest_daftar.php")
    fun register(
        @Field("namaUser") namaUser: String,
        @Field("emailUser") emailUser: String,
        @Field("hpUser") hpUser: String,
        @Field("passUser") passUser: String): Call<Value>

    @FormUrlEncoded
    @POST("rest_rank.php")
    fun ranking(
        @Field("idUser") idUser: String,
        @Field("skor") skor: String): Call<Value>

    @FormUrlEncoded
    @POST("rest_login.php")
    fun login(
        @Field("emailUser") email: String,
        @Field("passUser") password: String

    ):Call<User>

    @GET("rest_soal.php")
    fun getSoal():Call<Value>

    @GET("rest_listrank.php")
    fun listrank():Call<Value>

    @FormUrlEncoded
    @POST("rest_searchrank.php")
    fun searchrank(
        @Field("search") search: String
    ):Call<Value>

    @FormUrlEncoded
    @POST("rest_user.php")
    fun getUser(
        @Field("idUser") idUser: Int

    ):Call<User>

    @FormUrlEncoded
    @POST("rest_edit_profil.php")
    fun editProfil(
        @Field("namaUser") namaUser: String,
        @Field("emailUser") emailUser: String,
        @Field("hpUser") hpUser: String,
        @Field("idUser") idUser: Int): Call<Value>

    @FormUrlEncoded
    @POST("rest_edit_password.php")
    fun editPassword(
        @Field("passUser") passUser: String,
        @Field("idUser") idUser: Int): Call<Value>
}

