package com.example.newsfeed.newsfeed.data.remote

import com.example.newsfeed.core.Api
import com.example.newsfeed.newsfeed.data.Item
import retrofit2.http.*

object ItemApi {
    private const val URL = "http://192.168.56.1:3000/"

    interface Service {
        @GET("/api/item")
        suspend fun find(): List<Item>

        @GET("/api/item/{id}")
        suspend fun read(@Path("id") itemId: String): Item;

        @Headers("Content-Type: application/json")
        @POST("/api/item")
        suspend fun create(@Body item: Item): Item

        @Headers("Content-Type: application/json")
        @PUT("/api/item/{id}")
        suspend fun update(@Path("id") itemId: String, @Body item: Item): Item
    }

    val service: Service = Api.retrofit.create(Service::class.java)
}