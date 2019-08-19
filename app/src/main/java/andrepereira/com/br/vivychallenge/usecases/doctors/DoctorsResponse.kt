package andrepereira.com.br.vivychallenge.usecases.doctors

import andrepereira.com.br.vivychallenge.data.model.Doctor

data class DoctorsResponse(val doctors: List<Doctor>?,
                           val lastKey: String?,
                           val errorMsg: String?) {
}