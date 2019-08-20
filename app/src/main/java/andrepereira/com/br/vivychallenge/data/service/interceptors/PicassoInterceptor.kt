package andrepereira.com.br.vivychallenge.data.service.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class PicassoInterceptor(val bearerToken: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val builder = original.newBuilder()
            .header("Authorization", "Bearer $bearerToken")
        original = builder.build()

        return chain.proceed(original)
    }
}