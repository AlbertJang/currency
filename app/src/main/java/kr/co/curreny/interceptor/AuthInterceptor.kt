package kr.co.curreny.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        // DONT INCLUDE API KEYS IN YOUR SOURCE CODE
        val url = req.url.newBuilder().addQueryParameter("access_key", "e2df14eeb00a9cceb778b797118366ca").build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}