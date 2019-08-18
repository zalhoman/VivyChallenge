package andrepereira.com.br.vivychallenge.usecases.login

import andrepereira.com.br.vivychallenge.R
import andrepereira.com.br.vivychallenge.databinding.ActivityLoginBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val viewRoot = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        viewRoot.viewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)
    }
}
