package andrepereira.com.br.vivychallenge.data.service.login

import andrepereira.com.br.vivychallenge.data.model.AuthResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class AuthDeserializer : JsonDeserializer<AuthResponse> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): AuthResponse {
        val jsonObject = json!!.asJsonObject
        val accessToken = jsonObject["access_token"]?.let {
            return@let it.asString
        }
        val error = jsonObject["error"]?.let {
            return@let it.asString
        }
        return AuthResponse(accessToken, error)
    }
}