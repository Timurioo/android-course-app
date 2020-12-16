package by.tska.courseapp.network.controller

import by.tska.courseapp.R
import by.tska.courseapp.dto.BillingAccount
import by.tska.courseapp.network.Session
import by.tska.courseapp.network.interceptor.AuthHttpInterceptor
import by.tska.courseapp.network.service.BillingAccountService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BillingAccountController(session: Session) {

    private var billingAccountService: BillingAccountService

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthHttpInterceptor(session))
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        billingAccountService = retrofit.create(BillingAccountService::class.java)
    }

    fun getAllWallets() = billingAccountService.getAllWallets()

    fun getBillingAccountByUserId(userId: String) = billingAccountService.getBillingAccountsByUserId(userId)

    fun saveBillingAccount(billingAccount: BillingAccount) = billingAccountService.saveBillingAccount(billingAccount)

    fun deleteBillingAccount(billingAccountId: String) = billingAccountService.deleteBillingAccount(billingAccountId)

    fun getBillingAccountByWalletId(walletId: String) = billingAccountService.getBillingAccountByWalletId(walletId)

    fun getBillingAccountById(billingAccountId: String) = billingAccountService.getBillingAccountById(billingAccountId)

    companion object {
        const val BASE_URL = "http://localhost:8081/"
    }
}