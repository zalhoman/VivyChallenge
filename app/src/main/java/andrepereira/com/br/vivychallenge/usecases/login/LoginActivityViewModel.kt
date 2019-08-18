package andrepereira.com.br.vivychallenge.usecases.login

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class LoginActivityViewModel: ViewModel() {

    val usernameText = ObservableField("")
    val passwordText = ObservableField("")
    private val loginRepository = LoginRepository()

    fun executeLogin() {
        loginRepository.autheticate(usernameText.get()!!, passwordText.get()!!)
    }
}