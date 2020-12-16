package by.tska.courseapp.network.service

import by.tska.courseapp.dto.Subscription
import retrofit2.Call
import retrofit2.http.*

interface SubscriptionService {

    @GET("/api/subscription/all")
    fun getAllSubcriptions() : Call<Array<Subscription>>

    @POST("/api/subscription/new")
    fun saveSubscription(@Body subscription: Subscription): Call<Subscription>

    @DELETE("/api/subscription/{id}")
    fun deleteSubscription(@Path("id") id: String): Call<Void>

    @GET("/api/subscription/wallet/{walletId}")
    fun getSubsByWallet(@Path("walletId") walletId: String): Call<Array<Subscription>>

    @GET("/api/subscription/id/{id}")
    fun getSubscriptionById(@Path("id") id: String): Call<Subscription>
}