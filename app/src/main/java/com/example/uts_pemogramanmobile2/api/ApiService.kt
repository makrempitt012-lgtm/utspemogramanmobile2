package com.example.uts_pemogramanmobile2.api

import com.example.uts_pemogramanmobile2.model.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    fun getProducts(
        @Query("limit") limit: Int = 25,
        @Query("select") select: String = "id,title,description,category,price,stock,availabilityStatus,tags,dimensions,reviews,rating,images,thumbnail"
    ): Call<ProductResponse>
}