package young.com.sonagibook_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import young.com.sonagibook_app.room.RoomRepository

class TokenViewModelFactory (private val repository: RoomRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RoomRepository::class.java).newInstance(repository)

    }
}