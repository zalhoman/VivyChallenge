package andrepereira.com.br.vivychallenge.usecases.login

import andrepereira.com.br.vivychallenge.data.service.ServiceGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginRepository {

    private val loginService = ServiceGenerator.createAuthService()
//    private val dbHelper = DatabaseManager().getUserDao()

    fun autheticate(username: String, password: String) {
        loginService.autheticate(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            }
    }

}
