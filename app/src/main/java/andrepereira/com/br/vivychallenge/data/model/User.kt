package andrepereira.com.br.vivychallenge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(@PrimaryKey val username: String,
                val password: String,
                val authToken: String) : Serializable