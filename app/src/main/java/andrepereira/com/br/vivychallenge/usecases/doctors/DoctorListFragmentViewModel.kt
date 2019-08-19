package andrepereira.com.br.vivychallenge.usecases.doctors

import andrepereira.com.br.vivychallenge.data.model.Doctor
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class DoctorListFragmentViewModel: ViewModel() {

    val doctoRepository = DoctorRepository()
    val searchStatus = ObservableField<SearchStatus>(SearchStatus.NotStarted)

    val searchField = ObservableField("")

    private val disposable = CompositeDisposable()

    fun searchEmpty() {
        disposable.add(doctoRepository.searchAllDoctors()
            .subscribe {
                searchStatus.set(SearchStatus.SearchSuccess(it.doctors!!, it.lastKey, ""))
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun searchDoctor(doctorName: String) {
        if (doctorName.isEmpty()) {
            searchEmpty()
        } else {
            doctoRepository.searchDoctor(doctorName)
        }
    }

    fun nextPage() {
        Log.d("test", "Nova pagina solicitada")
    }

}

sealed class SearchStatus {
    object NotStarted: SearchStatus()
    object Searching: SearchStatus()
    data class SearchSuccess(val list: List<Doctor>, val lastKey: String?, val searchDoctor: String) : SearchStatus()
    object NotFound: SearchStatus()
}