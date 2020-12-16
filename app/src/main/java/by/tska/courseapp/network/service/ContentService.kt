package by.tska.courseapp.network.service

import by.tska.courseapp.dto.Content
import retrofit2.http.*

interface ContentService {

    @GET("api/content/all")
    fun getAllProducts(): retrofit2.Call<Array<Content>>

    @POST("api/content")
    fun saveProduct(@Body content: Content): retrofit2.Call<Content>

    @DELETE("api/content/{id}")
    fun deleteProduct(@Path("id") id: String): retrofit2.Call<Void>

    @GET("api/content/id/{id}")
    fun getProductById(@Path("id") id: String): retrofit2.Call<Content>

    @GET("api/content/{name}")
    fun getProductByItemName(@Path("name") name: String): retrofit2.Call<Content>

    @GET("api/content/company/{id}")
    fun getProductByCompanyId(@Path("id") id: String): retrofit2.Call<Array<Content>>
}