package young.com.sonagibook_app.room


class RoomRepository(private val dao : TokenDao) {

    suspend fun insert(token : Token){
        dao.insert(token)
    }

    suspend fun update(token: Token){
        dao.update(token)
    }

    suspend fun getAll() : Token{
        return dao.getAll()
    }


}