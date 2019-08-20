package andrepereira.com.br.vivychallenge.usecases.login

import andrepereira.com.br.vivychallenge.LoginActivity
import andrepereira.com.br.vivychallenge.MainActivity
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar

@BindingAdapter("clickLogin")
fun clickLogin(view: Button, viewModel: LoginActivityViewModel) {
    view.setOnClickListener {
        viewModel.executeLogin()
    }
}

@BindingAdapter("navigateToDoctorList", "finishActivity", requireAll = true)
fun navigateToDoctorList(view: View, authStatus: AuthStatus, activity: LoginActivity) {
    if (authStatus is AuthStatus.Authenticated) {
        val intent = Intent(view.context, MainActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
}

@BindingAdapter("loginError")
fun onLoginError(view: View, authStatus: AuthStatus) {
    if (authStatus is AuthStatus.AuthError) {
        Snackbar.make(view, authStatus.error, Snackbar.LENGTH_LONG).show()
    }
}