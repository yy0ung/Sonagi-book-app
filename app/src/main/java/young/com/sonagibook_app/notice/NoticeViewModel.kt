package young.com.sonagibook_app.notice

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.Repository
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostNoticeDto

class NoticeViewModel(private val repository: Repository) : ViewModel() {


    fun postNewNotice(token : String, noticeInfo : RetrofitPostNoticeDto){
        viewModelScope.launch {
            repository.postNewNotice(token, noticeInfo).let {
                Log.d(TAG, "postNewNotice: success")
            }
        }
    }
}