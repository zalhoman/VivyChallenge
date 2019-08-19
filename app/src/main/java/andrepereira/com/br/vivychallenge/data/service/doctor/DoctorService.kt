package andrepereira.com.br.vivychallenge.data.service.doctor

import andrepereira.com.br.vivychallenge.usecases.doctors.DoctorsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DoctorService {

    @GET("api/users/me/doctors?lat=52.534709&lng=13.3976972")
    fun findDoctors(@Header("Authorization") bearerAccessToken: String) : Observable<DoctorsResponse>

    @GET("api/users/me/doctors?lat=52.534709&lng=13.3976972")
    fun findDoctor(@Header("Authorization") bearerAccessToken: String,
                   @Query("name") doctorName: String) : Observable<DoctorsResponse>
}