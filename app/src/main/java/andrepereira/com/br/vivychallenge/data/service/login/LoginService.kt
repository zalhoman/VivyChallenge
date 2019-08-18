package andrepereira.com.br.vivychallenge.data.service.login

import andrepereira.com.br.vivychallenge.usecases.login.AuthResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    @FormUrlEncoded
    @POST("oauth/token?grant_type=password")
    fun autheticate(@Field("username") username: String,
                    @Field("password") password: String): Observable<AuthResponse>
}