package young.com.sonagibook_app

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoticeContentViewModel(private val repository: Repository) : ViewModel() {
    fun deleteNoticeItem(nid : String, token : String){
        viewModelScope.launch {
            repository.deleteNoticeItem(nid, token).let {
                Log.d(ContentValues.TAG, "deleteNoticeItem: 삭제 성공")
            }
        }
    }
}