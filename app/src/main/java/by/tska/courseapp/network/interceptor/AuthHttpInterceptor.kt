package by.tska.courseapp.network.interceptor

import by.tska.courseapp.network.Session
import okhttp3.Interceptor
import okhttp3.Response

class AuthHttpInterceptor(var session: Session): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(TOKEN_HEADER_KEY, "Bearer ${session.jwtToken}")
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        const val TOKEN_HEADER_KEY = "Authorization"
    }
}