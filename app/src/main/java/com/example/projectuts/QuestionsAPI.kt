package com.example.projectuts

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface QuestionsAPI {
    @GET("getStudentss.php")
    suspend fun getQues(): Response<QuestionsListResponse>

    @GET("pilihSoal.php")
    suspend fun getSoal(
        @Query("id_kategori") id_kategori: Int
    ) : Response<QuestionsListResponse>


}

