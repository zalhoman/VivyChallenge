package andrepereira.com.br.vivychallenge.usecases.login

import andrepereira.com.br.vivychallenge.data.dao.UserDao
import andrepereira.com.br.vivychallenge.data.model.AuthResponse
import andrepereira.com.br.vivychallenge.data.service.login.LoginService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginRepository {

    @Inject
    lateinit var loginService : LoginService

    @Inject
    lateinit var userDao: UserDao

    fun autheticate(username: String, password: String): Observable<AuthResponse> {
        return loginService.autheticate(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
