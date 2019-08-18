package andrepereira.com.br.vivychallenge.data.dao

import andrepereira.com.br.vivychallenge.data.model.User
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(User::class)], version = 1, exportSchema = false)
abstract class DatabaseHelper: RoomDatabase() {
    abstract fun getUserDao(): UserDao
}