package com.example.projectuts

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiEndpoint {
    @GET("getKategoriList.php")
    fun ktg() : Call<KategoriItem>

    @GET("getKategoriRead.php")
    fun rad() : Call<ReadingItem>

    @GET("getUser.php")
    fun usr() : Call<UserItem>

    @GET("getAbsensi.php")
    fun abs() : Call<AbsensiItem>

    @FormUrlEncoded
    @POST("insertHasil.php")
    fun create(
        @Field("total") total : Int,
        @Field("hasil") hasil : Int
    ) : Call<HasilItem>

    @FormUrlEncoded
    @POST("postUser.php")
    fun update(
        @Field("id") id : Int,
        @Field("nama") nama : String,
        @Field("username") username : String,
        @Field("password") password : String,
        @Field("status") status : String
    ) : Call<UserItem>
}