package young.com.sonagibook_app.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import young.com.sonagibook_app.MainActivity

@Database(entities = [Token::class], version = 1)
abstract class TokenDatabase : RoomDatabase() {
    abstract fun tokenDao() : TokenDao

    companion object{
        private var INSTANCE : TokenDatabase? = null

        fun getInstance(context: MainActivity) : TokenDatabase?{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TokenDatabase::class.java,
                    "TokenTable"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}