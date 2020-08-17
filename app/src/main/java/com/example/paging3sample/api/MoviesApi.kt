package com.example.paging3sample.api


import com.example.paging3sample.Constants
import com.example.paging3sample.Movies
import retrofit2.http.GET
import retrofit2.http.Query


//interface MoviesApi {
//    @GET(Constants.POPULAR_MOVIES_URL)
//    fun getAllMovies(
//        @Query("api_key") apiKey: String?
//    ): LiveData<GenericApiResponse<Movies>>
//}

interface MoviesApi {
    @GET(Constants.POPULAR_MOVIES_URL)
    suspend fun getAllMovies(
        @Query("api_key") apiKey: String,
        @Query("page") pos: Int
    ): Movies


}

