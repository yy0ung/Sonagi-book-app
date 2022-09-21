package young.com.sonagibook_app.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TokenTable")
data class Token(
    val accessToken : String,
    @PrimaryKey val refreshToken : String
)
