package andrepereira.com.br.vivychallenge.data.dao

import andrepereira.com.br.vivychallenge.data.model.User
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User) : Long

    @Transaction
    @Query("select * from User where username = :username")
    fun findLoggedUser(username: String): User

    @Update
    fun update(user: User)
}