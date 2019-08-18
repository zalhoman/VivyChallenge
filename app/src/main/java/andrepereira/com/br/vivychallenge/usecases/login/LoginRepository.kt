package andrepereira.com.br.vivychallenge.usecases.login

import andrepereira.com.br.vivychallenge.data.service.LoginService

class LoginRepository {

    private lateinit var loginService :LoginService
    fun autheticate(username: String, password: String) {
        loginService.autheticate(username, password)
    }

}
