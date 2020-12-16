package by.tska.courseapp.network.controller

import by.tska.courseapp.R
import by.tska.courseapp.dto.CurrentUser
import by.tska.courseapp.dto.User
import by.tska.courseapp.network.Session
import by.tska.courseapp.network.interceptor.AuthHttpInterceptor
import by.tska.courseapp.network.service.AuthService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthController(session: Session) {
    private var authService: AuthService

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthHttpInterceptor(session))
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authService = retrofit.create(AuthService::class.java)
    }

    fun attemptAuth(user: CurrentUser) = authService.attemptAuth(user)

    fun getCurrentUser() = authService.getUser()

    companion object {
        const val BASE_URL = "http://localhost:8081/"
    }
}