package andrepereira.com.br.vivychallenge.data.dao

import andrepereira.com.br.vivychallenge.data.model.User
import androidx.room.*
import io.reactivex.Single

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User) : String

    @Transaction
    @Query("select * from User where username = :username")
    fun find(username: String): Single<User>

    @Update
    fun update(user: User)
}