package andrepereira.com.br.vivychallenge.data.model

data class Doctor(val id: String,
                  val name: String,
                  val photoId: String? = null,
                  val rating: Double? = null,
                  val latitude: Double,
                  val longitude: Double) {
}