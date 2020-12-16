package by.tska.courseapp.network.service

import by.tska.courseapp.dto.BillingAccount
import retrofit2.Call
import retrofit2.http.*

interface BillingAccountService {

    @GET("/api/billing/all")
    fun getAllWallets(): Call<Array<BillingAccount>>

    @GET("/api/billing/user/{userId}")
    fun getBillingAccountsByUserId(@Path("userId") userId: String): Call<Array<BillingAccount>>

    @POST("/api/billing/new")
    fun saveBillingAccount(@Body billingAccount: BillingAccount): Call<BillingAccount>

    @DELETE("/api/billing/{id}")
    fun deleteBillingAccount(@Path("id") id: String): Call<Void>

    @GET("/api/billing/wallet-id/{walletId}")
    fun getBillingAccountByWalletId(@Path("walletId") walletId: String) : Call<BillingAccount>

    @GET("/api/billing/id/{id}")
    fun getBillingAccountById(@Path("id") id: String): Call<BillingAccount>
}