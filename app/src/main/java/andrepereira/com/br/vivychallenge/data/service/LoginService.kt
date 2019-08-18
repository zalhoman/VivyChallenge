package andrepereira.com.br.vivychallenge.data.service

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    @FormUrlEncoded
    @POST
    fun autheticate(@Field("username") username: String,
                    @Field("password") password: String): Observable<String>
}