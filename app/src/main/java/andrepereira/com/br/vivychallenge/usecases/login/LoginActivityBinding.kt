package andrepereira.com.br.vivychallenge.usecases.login

import andrepereira.com.br.vivychallenge.MainActivity
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.databinding.BindingAdapter

@BindingAdapter("clickLogin")
fun clickLogin(view: Button, viewModel: LoginActivityViewModel) {
    view.setOnClickListener {
        viewModel.executeLogin()
    }
}

@BindingAdapter("navigateToDoctorList")
fun navigateToDoctorList(view: View, authStatus: AuthStatus) {
    if (authStatus is AuthStatus.Authenticated) {
        val intent = Intent(view.context, MainActivity::class.java)
        view.context.startActivity(intent)
    }
}