package by.tska.courseapp.network.service

import by.tska.courseapp.dto.CurrentUser
import by.tska.courseapp.dto.User
import by.tska.courseapp.dto.UserToRegister
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("/api/user")
    fun getAllUserProfiles(): Call<Array<User>>

    @POST("/api/user/signup")
    fun saveUserProfile(@Body userProfile: UserToRegister): Call<CurrentUser>

    @DELETE("/api/user/{userProfileId}")
    fun deleteUserProfile(@Path("userProfileId") userProfileId: String): Call<Void>

    @GET("/api/user/check/{email}")
    fun getUserProfileByEmail(@Path("email") email: String): Call<User>

    @GET("/api/user/id/{id}")
    fun getUserProfileById(@Path("id") id: String): Call<User>

    @GET("/api/user/role/")
    fun getUserProfileByRoleId(@Path("id") id: String): Call<Array<User>>

    @PUT("/api/user/block/id/{id}")
    fun blockUser(@Path("id") id: String): Call<Void>

    @PUT("/api/user/activate/id/{id}")
    fun activateUser(@Path("id") id: String): Call<Void>

    @PUT("/api/user/id/{id}/address/{address}")
    fun putAddress(@Path("id") id: String, @Path("address") address: String): Call<Void>

    @GET("/api/user/email/{email}/address/")
    fun getAddressByEmail(@Path("email") email: String): Call<User>
}