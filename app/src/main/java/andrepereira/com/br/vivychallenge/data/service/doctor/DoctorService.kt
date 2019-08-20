package andrepereira.com.br.vivychallenge.data.service.doctor

import andrepereira.com.br.vivychallenge.usecases.doctors.DoctorsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DoctorService {

    @GET("api/users/me/doctors")
    fun findDoctors(@Header("Authorization") bearerAccessToken: String,
                    @Query("lat") latitude: Double,
                    @Query("lng") longitude: Double): Observable<DoctorsResponse>

    @GET("api/users/me/doctors")
    fun findDoctor(
        @Header("Authorization") bearerAccessToken: String,
        @Query("search") doctorName: String,
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double
    ): Observable<DoctorsResponse>

    @GET("api/users/me/doctors")
    fun findDoctorNextPage(
        @Header("Authorization") bearerAccessToken: String,
        @Query("search") doctorName: String,
        @Query("lastKey") lastKey: String,
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double
    ): Observable<DoctorsResponse>
}