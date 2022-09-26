package young.com.sonagibook_app.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TokenDao {
    @Insert
    fun insert(token : Token)

    @Update
    fun update(vararg token : Token)

    @Query("SELECT * FROM TokenTable")
    fun getAll() : Token

    @Delete
    fun deleteAll(token : Token)
}