package by.tska.courseapp.network.controller

import by.tska.courseapp.R
import by.tska.courseapp.dto.Content
import by.tska.courseapp.network.Session
import by.tska.courseapp.network.interceptor.AuthHttpInterceptor
import by.tska.courseapp.network.service.ContentService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContentController(session: Session) {

    private var contentService: ContentService

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthHttpInterceptor(session))
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        contentService = retrofit.create(ContentService::class.java)
    }

    fun getAllProducts() = contentService.getAllProducts()

    fun saveProduct(content: Content) = contentService.saveProduct(content)

    fun deleteProduct(id: String) = contentService.deleteProduct(id)

    fun getProductById(id: String) = contentService.getProductById(id)

    fun getProductByItemName(name: String) = contentService.getProductByItemName(name)

    fun getProductByCompany(id: String) = contentService.getProductByCompanyId(id)

    companion object {
        const val BASE_URL = "http://localhost:8081/"
    }
}