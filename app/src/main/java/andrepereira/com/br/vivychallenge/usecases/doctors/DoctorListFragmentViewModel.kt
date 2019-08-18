package andrepereira.com.br.vivychallenge.usecases.doctors

import andrepereira.com.br.vivychallenge.data.model.Doctor
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class DoctorListFragmentViewModel: ViewModel() {

    val searchStatus = ObservableField<SearchStatus>(SearchStatus.NotStarted)

}

sealed class SearchStatus {
    object NotStarted: SearchStatus()
    object Searching: SearchStatus()
    data class SearchSuccess(val list: List<Doctor>) : SearchStatus()
    object NotFound: SearchStatus()
}