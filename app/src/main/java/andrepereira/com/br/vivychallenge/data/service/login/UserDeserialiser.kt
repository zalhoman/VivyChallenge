package andrepereira.com.br.vivychallenge.data.service.login

import andrepereira.com.br.vivychallenge.data.model.User
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class UserDeserialiser : JsonDeserializer<User> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}