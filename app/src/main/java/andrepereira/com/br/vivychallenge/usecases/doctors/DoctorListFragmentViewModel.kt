package andrepereira.com.br.vivychallenge.usecases.doctors

import andrepereira.com.br.vivychallenge.data.model.Doctor
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class DoctorListFragmentViewModel: ViewModel() {

    val doctoRepository = DoctorRepository()
    val searchStatus = ObservableField<DoctorSearchStatus>(DoctorSearchStatus.NotStarted)

    val searchField = ObservableField("")

    private val disposable = CompositeDisposable()

    private fun searchEmpty() {
        searchStatus.set(DoctorSearchStatus.Searching)
        disposable.add(
            doctoRepository.searchAllDoctors()
                .subscribe({doctorResponse ->
                    if (doctorResponse.errorMsg.isNullOrEmpty()) {
                        if (!doctorResponse.doctors.isNullOrEmpty()){
                            searchStatus.set(DoctorSearchStatus.SearchSuccess(doctorResponse.doctors,
                                doctorResponse.lastKey,
                                "",
                                doctorResponse.latitude!!,
                                doctorResponse.longitude!!))
                        } else {
                            searchStatus.set(DoctorSearchStatus.NotFound)
                        }
                    } else {
                        searchStatus.set(DoctorSearchStatus.Error(doctorResponse.errorMsg))
                    }
                }, {
                    searchStatus.set(DoctorSearchStatus.Error(it.message!!))
                })
        )
    }

    fun searchDoctor(doctorName: String) {
        clearDoctorsList()
        searchStatus.set(DoctorSearchStatus.Searching)
        if (doctorName.isEmpty()) {
            searchEmpty()
        } else {
            disposable.add(
                doctoRepository.searchDoctor(doctorName)
                    .subscribe({ doctorsResponse ->
                        if (doctorsResponse.errorMsg.isNullOrEmpty()) {
                            if (!doctorsResponse.doctors.isNullOrEmpty()) {
                                searchStatus.set(DoctorSearchStatus.SearchSuccess(doctorsResponse.doctors,
                                    doctorsResponse.lastKey,
                                    doctorName,
                                    doctorsResponse.latitude!!,
                                    doctorsResponse.longitude!!))
                            } else {
                                searchStatus.set(DoctorSearchStatus.NotFound)
                            }
                        } else {
                            searchStatus.set(DoctorSearchStatus.Error(doctorsResponse.errorMsg))
                        }
                    }, {
                        searchStatus.set(DoctorSearchStatus.Error(it.message!!))
                    })
            )
        }
    }

    fun nextPage() {
        if (searchStatus.get() is DoctorSearchStatus.SearchSuccess) {
            val searchSuccess = searchStatus.get() as DoctorSearchStatus.SearchSuccess
            if (searchSuccess.lastKey == null) {
                return
            }

            val tempDoctors = searchSuccess.list
            val lastSearch = searchSuccess.searchDoctor
            searchStatus.set(DoctorSearchStatus.Searching)

            disposable.add(doctoRepository.nextPage(lastSearch, searchSuccess.lastKey, searchSuccess.latitude, searchSuccess.longitude)
                .subscribe ({ doctorsResponse ->
                    doctorsResponse.doctors?.let { doctors ->
                        if (doctors.isNotEmpty()) {
                            tempDoctors.addAll(doctors)
                            searchStatus.set(DoctorSearchStatus.SearchSuccess(tempDoctors,
                                doctorsResponse.lastKey,
                                lastSearch,
                                searchSuccess.latitude,
                                searchSuccess.longitude))
                        } else {
                            searchStatus.set(DoctorSearchStatus.NotFound)
                        }
                    }
                }, {
                    searchStatus.set(DoctorSearchStatus.Error(it.message!!))
                }))
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun clearDoctorsList() {
        if (searchStatus.get()!! is DoctorSearchStatus.SearchSuccess) {
            (searchStatus.get() as DoctorSearchStatus.SearchSuccess).list.clear()
        }
    }

}

sealed class DoctorSearchStatus {
    object NotStarted: DoctorSearchStatus()
    object Searching: DoctorSearchStatus()
    data class SearchSuccess(val list: MutableList<Doctor>,
                             val lastKey: String?,
                             val searchDoctor: String,
                             val latitude: Double,
                             val longitude: Double) : DoctorSearchStatus()
    object NotFound: DoctorSearchStatus()
    data class Error(val errorMsg: String): DoctorSearchStatus()
}