package com.example.paging3sample


import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val movies: List<Movie>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)