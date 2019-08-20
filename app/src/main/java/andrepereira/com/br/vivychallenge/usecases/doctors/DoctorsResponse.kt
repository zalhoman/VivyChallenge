package andrepereira.com.br.vivychallenge.usecases.doctors

import andrepereira.com.br.vivychallenge.data.model.Doctor

data class DoctorsResponse(val doctors: MutableList<Doctor>?,
                           val lastKey: String?,
                           var latitude: Double?,
                           var longitude: Double?,
                           val errorMsg: String?)