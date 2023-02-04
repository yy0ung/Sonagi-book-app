package young.com.sonagibook_app.notice

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.Repository
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostNoticeDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseNoticeContentDto

class NoticeEditViewModel(private val repository: Repository) :ViewModel() {
    private val _repositoriesGetNoticeContent = MutableLiveData<RetrofitResponseNoticeContentDto>()
    val repositories1 : MutableLiveData<RetrofitResponseNoticeContentDto>
        get() = _repositoriesGetNoticeContent

    fun getNoticeContent(nid : String, token : String){
        viewModelScope.launch {
            repository.getNoticeContent(nid, token).let { response ->
                if(response.isSuccessful){
                    Log.d(ContentValues.TAG, "getNoticeContent: ${response.body()}")
                    _repositoriesGetNoticeContent.postValue(response.body())
                }

            }
        }
    }

    fun putNoticeContent(nid: String, token: String, data : RetrofitPostNoticeDto){
        viewModelScope.launch {
            repository.putNoticeContent(nid, token, data).let {
                Log.d(TAG, "putNoticeContent: 수정 완료")
            }
        }
    }

}
