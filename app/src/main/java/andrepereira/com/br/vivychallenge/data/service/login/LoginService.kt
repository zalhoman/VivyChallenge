package andrepereira.com.br.vivychallenge.data.service.login

import andrepereira.com.br.vivychallenge.data.model.AuthResponse
import andrepereira.com.br.vivychallenge.data.model.User
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    @FormUrlEncoded
    @POST("oauth/token?grant_type=password")
    fun autheticate(@Field("username") username: String,
                    @Field("password") password: String): Observable<AuthResponse>
}