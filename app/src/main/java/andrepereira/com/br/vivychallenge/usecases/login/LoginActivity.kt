package andrepereira.com.br.vivychallenge.usecases.login

import andrepereira.com.br.vivychallenge.R
import andrepereira.com.br.vivychallenge.data.component.DaggerRepositoryComponent
import andrepereira.com.br.vivychallenge.data.module.RepositoryModule
import andrepereira.com.br.vivychallenge.databinding.ActivityLoginBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

class LoginActivity : AppCompatActivity() {

    private val viewModel by lazy {
        return@lazy ViewModelProvider(this).get(LoginActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val viewRoot = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        viewRoot.viewModel = viewModel

        injectDependencies()
    }

    private fun injectDependencies() {
        val loginComponent = DaggerRepositoryComponent.builder()
            .repositoryModule(RepositoryModule(baseContext))
            .build()

        loginComponent.inject(viewModel.loginRepository)
    }
}
