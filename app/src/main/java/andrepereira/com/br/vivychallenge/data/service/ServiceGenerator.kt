package andrepereira.com.br.vivychallenge.data.service

import andrepereira.com.br.vivychallenge.data.model.User
import andrepereira.com.br.vivychallenge.data.service.interceptors.BasicAuthInterceptor
import andrepereira.com.br.vivychallenge.data.service.login.LoginService
import andrepereira.com.br.vivychallenge.data.service.login.UserDeserialiser
import andrepereira.com.br.vivychallenge.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceGenerator {
    companion object {
        fun <T> createService(serviceClass: Class<T>): T {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        .setLevel(HttpLoggingInterceptor.Level.HEADERS)
                        .setLevel(HttpLoggingInterceptor.Level.BASIC))
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(serviceClass)
        }

        fun createAuthService(): LoginService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(BasicAuthInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.AUTH_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(buildAuthGsonConverter()))
                .client(okHttpClient)
                .build()

            return retrofit.create(LoginService::class.java)
        }

        private fun buildAuthGsonConverter(): Gson {
            return GsonBuilder().registerTypeAdapter(User::class.java, UserDeserialiser()).create()
        }


    }
}