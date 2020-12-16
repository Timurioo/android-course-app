package by.tska.courseapp.network.controller

import by.tska.courseapp.R
import by.tska.courseapp.dto.Subscription
import by.tska.courseapp.network.Session
import by.tska.courseapp.network.interceptor.AuthHttpInterceptor
import by.tska.courseapp.network.service.SubscriptionService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SubscriptionController(session: Session) {
    private var subscriptionService: SubscriptionService

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthHttpInterceptor(session))
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        subscriptionService = retrofit.create(SubscriptionService::class.java)
    }

    fun getAllSubscriptions() = subscriptionService.getAllSubcriptions()

    fun saveSubscription(subscription: Subscription) = subscriptionService.saveSubscription(subscription)

    fun deleteSubscription(id: String) = subscriptionService.deleteSubscription(id)

    fun getSubscriptionsByWalletId(walletId: String) = subscriptionService.getSubsByWallet(walletId)

    fun getSubscriptionById(subscriptionId: String) = subscriptionService.getSubscriptionById(subscriptionId)

    companion object {
        const val BASE_URL = "http://localhost:8081/"
    }
}