package young.com.sonagibook_app

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseNoticeContentDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseNoticeDto

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

}
