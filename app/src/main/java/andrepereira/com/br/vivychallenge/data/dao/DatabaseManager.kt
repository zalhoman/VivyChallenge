package andrepereira.com.br.vivychallenge.data.dao

import android.content.Context
import androidx.room.Room

class DatabaseManager(private val context: Context) {

    private val db = Room.databaseBuilder(context, DatabaseHelper::class.java, "VivyChallenge").allowMainThreadQueries().build()

    fun provideDb(): DatabaseHelper {
        return db
    }

}