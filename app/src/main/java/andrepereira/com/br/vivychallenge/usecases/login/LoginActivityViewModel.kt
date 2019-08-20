package andrepereira.com.br.vivychallenge.usecases.login

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException

class LoginActivityViewModel : ViewModel() {

    val usernameText = ObservableField("")
    val passwordText = ObservableField("")
    val authStatus = ObservableField<AuthStatus>(AuthStatus.None)

    val loginRepository = LoginRepository()
    private val disposable = CompositeDisposable()

    fun executeLogin() {
        disposable.add(loginRepository.autheticate(usernameText.get()!!, passwordText.get()!!)
            .subscribe({
                it.accessToken?.let { token ->
                    authStatus.set(AuthStatus.Authenticated(usernameText.get()!!, passwordText.get()!!, token))
                }

                it.errorMsg?.let { error ->
                    authStatus.set(AuthStatus.AuthError(error))
                }
            }, {
                if (it is UnknownHostException) {
                    authStatus.set(AuthStatus.AuthError("Check you network connection and try again"))
                } else {
                    authStatus.set(AuthStatus.AuthError(it.message!!))
                }
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}

sealed class AuthStatus {
    object None : AuthStatus()
    object Loading : AuthStatus()
    data class Authenticated(val username: String, val password: String, val authToken: String) : AuthStatus()
    data class AuthError(val error: String) : AuthStatus()
}