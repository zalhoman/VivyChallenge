package andrepereira.com.br.vivychallenge.usecases.login

import android.widget.Button
import androidx.databinding.BindingAdapter

@BindingAdapter("clickLogin")
fun clickLogin(view: Button, viewModel: LoginActivityViewModel) {
    view.setOnClickListener {
        viewModel.executeLogin()
    }
}