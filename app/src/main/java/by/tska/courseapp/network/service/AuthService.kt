package by.tska.courseapp.network.service

import by.tska.courseapp.dto.AuthToken
import by.tska.courseapp.dto.CurrentUser
import by.tska.courseapp.dto.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST("token/generate-token")
    fun attemptAuth(@Body user: CurrentUser): Call<AuthToken>

    @GET("token/current")
    fun getUser(): Call<User>
}