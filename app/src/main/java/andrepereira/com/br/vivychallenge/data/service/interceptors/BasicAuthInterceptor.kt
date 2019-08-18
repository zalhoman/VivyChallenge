package andrepereira.com.br.vivychallenge.data.service.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var original = chain.request()
        val builder = original.newBuilder()
            .header("Authorization", "Basic aXBob25lOmlwaG9uZXdpbGxub3RiZXRoZXJlYW55bW9yZQ==")
        original = builder.build()
        return chain.proceed(original)
    }
}