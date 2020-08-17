package com.example.paging3sample.api


import com.example.paging3sample.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {


    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }


    val apiService: MoviesApi by lazy {
        retrofitBuilder
            .build()
            .create(MoviesApi::class.java)
    }
}