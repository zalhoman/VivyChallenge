package andrepereira.com.br.vivychallenge.usecases.doctors

import andrepereira.com.br.vivychallenge.data.dao.UserDao
import andrepereira.com.br.vivychallenge.data.service.doctor.DoctorService
import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationManager
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

    @Inject
    lateinit var locationManager: LocationManager

    fun searchAllDoctors(): Observable<DoctorsResponse> {

        val authToken = userDao.findLoggedUser("androidChallenge@vivy.com").authToken
        val location = getLocation()
        if (location != null) {
            return doctorService
                .findDoctors("Bearer $authToken", location.latitude,location.longitude)
                .flatMap {
                    it.latitude = location.latitude
                    it.longitude = location.longitude
                    return@flatMap Observable.just(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
        throw RuntimeException("could not get any location")
    }

    fun searchDoctor(doctorName: String): Observable<DoctorsResponse> {
        val authToken = userDao.findLoggedUser("androidChallenge@vivy.com").authToken
        val location = getLocation()
        if (location != null) {
            return doctorService
                .findDoctor("Bearer $authToken", doctorName, location.latitude, location.longitude)
                .flatMap {
                    it.latitude = location.latitude
                    it.longitude = location.longitude
                    return@flatMap Observable.just(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
        throw RuntimeException("could not get any location")
    }

    fun nextPage(doctorName: String, lastKey: String, latitude: Double, longitude: Double): Observable<DoctorsResponse> {
        val authToken = userDao.findLoggedUser("androidChallenge@vivy.com").authToken
        return doctorService.findDoctorNextPage("Bearer $authToken", doctorName, lastKey, latitude, longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    @SuppressLint("MissingPermission")
    fun getLocation(): Location? {
        val isGPSEnabled = locationManager
            .isProviderEnabled(LocationManager.GPS_PROVIDER)
        // getting network status
        val isNetworkEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isGPSEnabled) {
            Log.d("Getting location", "Location found")
            val location = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                return location
            }
        }

        if (isNetworkEnabled) {
            val location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                return location
            }
        }
        return null
    }
}