package young.com.sonagibook_app.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TokenDao {
    @Insert
    fun insert(token : Token)

    @Update
    fun update(token : Token)

    @Query("SELECT * FROM TokenTable")
    fun getAll() : Token

    @Query("DELETE FROM TokenTable")
    fun deleteAll()

}