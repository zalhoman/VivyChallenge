package andrepereira.com.br.vivychallenge.util

class Constants {
    companion object {

        private const val BASE_URL = "staging.vivy.com/"
        private const val HTTPS_PREFIX = "https://"
        private const val AUTH_PREFIX = "auth."
        private const val API_PREFIX = "api."

        val API_URL = HTTPS_PREFIX + API_PREFIX + BASE_URL
        val AUTH_URL = HTTPS_PREFIX + AUTH_PREFIX + BASE_URL
    }
}