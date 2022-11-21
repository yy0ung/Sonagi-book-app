package young.com.sonagibook_app

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostBookDto

class BookAddViewModel(private val repository: Repository):ViewModel() {
    fun postBook(token : String, data : RetrofitPostBookDto){
        viewModelScope.launch {
            repository.postBook(token, data).let {
                Log.d(ContentValues.TAG, "postBook: 예약 업로드 성공")
            }
        }
    }
}