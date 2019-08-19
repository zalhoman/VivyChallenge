package andrepereira.com.br.vivychallenge.usecases.doctors

import andrepereira.com.br.vivychallenge.data.dao.UserDao
import andrepereira.com.br.vivychallenge.data.service.doctor.DoctorService
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DoctorRepository {

    @Inject
    lateinit var doctorService: DoctorService

    @Inject
    lateinit var userDao: UserDao

    fun searchAllDoctors(): Observable<DoctorsResponse> {
        return userDao.findLoggedUser("androidChallenge@vivy.com")
            .subscribeOn(Schedulers.io())
            .doOnError {
                Log.e("error", it.message)
            }
            .flatMap {
                return@flatMap doctorService.findDoctors("Bearer "+ it[0].authToken)
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchDoctor(doctorName: String): Observable<DoctorsResponse> {
        return userDao.findLoggedUser("androidChallenge@vivy.com")
            .subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap doctorService.findDoctor("Bearer "+ it[0].authToken, doctorName)
            }
            .observeOn(AndroidSchedulers.mainThread())
    }
}