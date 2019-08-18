package andrepereira.com.br.vivychallenge.usecases.login

import andrepereira.com.br.vivychallenge.data.model.AuthResponse
import andrepereira.com.br.vivychallenge.data.service.ServiceGenerator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginRepository {

    private val loginService = ServiceGenerator.createAuthService()
//    private val dbHelper = DatabaseHelper().getUserDao()

    fun autheticate(username: String, password: String): Observable<AuthResponse> {
        return loginService.autheticate(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
