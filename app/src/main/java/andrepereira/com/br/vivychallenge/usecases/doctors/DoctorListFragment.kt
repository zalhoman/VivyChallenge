package andrepereira.com.br.vivychallenge.usecases.doctors

import andrepereira.com.br.vivychallenge.R
import andrepereira.com.br.vivychallenge.data.component.DaggerRepositoryComponent
import andrepereira.com.br.vivychallenge.data.module.RepositoryModule
import andrepereira.com.br.vivychallenge.databinding.FragmentDoctorListBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class DoctorListFragment: Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(DoctorListFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewRoot = DataBindingUtil.inflate<FragmentDoctorListBinding>(
            inflater,
            R.layout.fragment_doctor_list,
            container,
            false
        )

        viewRoot.viewModel = viewModel

        injectDependecies()

        return viewRoot.root
    }

    private fun injectDependecies() {
        val repositoryComponent = DaggerRepositoryComponent.builder()
            .repositoryModule(RepositoryModule(activity!!.baseContext))
            .build()

        repositoryComponent.inject(viewModel.doctoRepository)
    }
}