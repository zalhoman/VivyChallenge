package andrepereira.com.br.vivychallenge.data.service.doctor

import andrepereira.com.br.vivychallenge.data.model.Doctor
import io.reactivex.Observable
import retrofit2.http.GET

interface DoctorService {

    @GET("")
    fun findDoctors() : Observable<List<Doctor>>
}