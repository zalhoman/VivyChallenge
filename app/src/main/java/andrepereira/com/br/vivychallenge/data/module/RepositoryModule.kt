package andrepereira.com.br.vivychallenge.data.module

import andrepereira.com.br.vivychallenge.data.dao.DatabaseHelper
import andrepereira.com.br.vivychallenge.data.dao.UserDao
import andrepereira.com.br.vivychallenge.data.service.ServiceGenerator
import andrepereira.com.br.vivychallenge.data.service.doctor.DoctorService
import andrepereira.com.br.vivychallenge.data.service.login.LoginService
import android.content.Context
import android.location.LocationManager
import androidx.room.Room
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule(private val context: Context) {

    @Provides
    fun getUserDao(): UserDao {
        val db = Room.databaseBuilder(context, DatabaseHelper::class.java, "ChallengeDB").allowMainThreadQueries().build()
        return db.getUserDao()
    }

    @Provides
    fun getLoginService(): LoginService {
        return ServiceGenerator.createAuthService()
    }

    @Provides
    fun getDoctorService(): DoctorService {
        return ServiceGenerator.createService(DoctorService::class.java)
    }

    @Provides
    fun getLocationManager(): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}