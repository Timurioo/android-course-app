package by.tska.courseapp.network.controller

import by.tska.courseapp.R
import by.tska.courseapp.dto.User
import by.tska.courseapp.dto.UserToRegister
import by.tska.courseapp.network.Session
import by.tska.courseapp.network.interceptor.AuthHttpInterceptor
import by.tska.courseapp.network.service.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserController(session: Session) {

    private var userService: UserService

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthHttpInterceptor(session))
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        userService = retrofit.create(UserService::class.java)
    }

    fun getAllUsers() = userService.getAllUserProfiles()

    fun saveUser(user: UserToRegister) = userService.saveUserProfile(user)

    fun deleteUser(userId: String) = userService.deleteUserProfile(userId)

    fun getUserByEmail(email: String) = userService.getUserProfileByEmail(email)

    fun getUserById(userId: String) = userService.getUserProfileById(userId)

    fun getUserByRoleId(roleId: String) = userService.getUserProfileByRoleId(roleId)

    fun blockUser(id: String) = userService.blockUser(id)

    fun activateUser(id: String) = userService.activateUser(id)

    fun putAddress(id: String, address: String) = userService.putAddress(id, address)

    fun getAddressByEmail(email: String) = userService.getAddressByEmail(email)

    companion object {
        const val BASE_URL = "http://localhost:8081/"
    }
}