package andrepereira.com.br.vivychallenge.data.component

import andrepereira.com.br.vivychallenge.data.module.RepositoryModule
import andrepereira.com.br.vivychallenge.usecases.login.LoginRepository
import dagger.Component

@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {
    fun inject(loginRepository: LoginRepository)
}