package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.room.RoomRepository
import young.com.sonagibook_app.room.Token

class TokenViewModel(private val repository: RoomRepository) : ViewModel() {
    private val _repositoriesGetAll = MutableLiveData<Token>()
    val repositories1 : MutableLiveData<Token>
        get() = _repositoriesGetAll


    fun insert(token : Token){
        Log.d(TAG, "insert: viewModel insert")
        viewModelScope.launch {
            repository.insert(token)
        }
    }

    fun update(token : Token){
        Log.d(TAG, "update: viewModel update")
        viewModelScope.launch {
            repository.update(token)
        }
    }

    fun getAll(){
        Log.d(TAG, "getAll: ViewModel getAll")
        viewModelScope.launch {
            _repositoriesGetAll.postValue(repository.getAll())
            Log.d(TAG, "getAll: postValue")
        }
    }




}