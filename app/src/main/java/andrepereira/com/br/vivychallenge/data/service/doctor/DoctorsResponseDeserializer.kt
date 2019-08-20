package andrepereira.com.br.vivychallenge.data.service.doctor

import andrepereira.com.br.vivychallenge.data.model.Doctor
import andrepereira.com.br.vivychallenge.usecases.doctors.DoctorsResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class DoctorsResponseDeserializer: JsonDeserializer<DoctorsResponse> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): DoctorsResponse {

        val doctors = mutableListOf<Doctor>()
        val doctorsJsonArray = json!!.asJsonObject["doctors"].asJsonArray
        doctorsJsonArray.forEach {
            val doctorJsonObject = it.asJsonObject
            var photoId: String? = null
            if (!doctorJsonObject["photoId"].isJsonNull) {
                photoId = doctorJsonObject["photoId"].asString
            }
            val doctor = Doctor(
                id = doctorJsonObject["id"].asString,
                name = doctorJsonObject["name"].asString,
                photoId = photoId,
                address = doctorJsonObject["address"].asString
            )
            doctors.add(doctor)
        }

        val jsonObject = json!!.asJsonObject
        var lastKey: String? = null
        if (!jsonObject["lastKey"].isJsonNull) {
            lastKey = jsonObject["lastKey"].asString
        }
        val error = jsonObject["error"]?.let {
            return@let it.asString
        }

        return DoctorsResponse(doctors, lastKey, null, null, error)
    }
}