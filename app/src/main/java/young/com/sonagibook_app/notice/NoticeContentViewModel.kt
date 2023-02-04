package young.com.sonagibook_app.notice

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.Repository
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostNoticeLikeDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseNoticeContentDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseNoticeLikeDto

class NoticeContentViewModel(private val repository: Repository) : ViewModel() {
    private val _repositoriesGetNoticeContent = MutableLiveData<RetrofitResponseNoticeContentDto>()
    val repositories1 : MutableLiveData<RetrofitResponseNoticeContentDto>
        get() = _repositoriesGetNoticeContent

    private val _repositoriesPostNoticeLike = MutableLiveData<RetrofitResponseNoticeLikeDto>()
    val repositories2 : MutableLiveData<RetrofitResponseNoticeLikeDto>
        get() = _repositoriesPostNoticeLike

    private val _repositoriesNoticeCancelLike = MutableLiveData<RetrofitResponseNoticeLikeDto>()
    val repositories3 : MutableLiveData<RetrofitResponseNoticeLikeDto>
        get() = _repositoriesNoticeCancelLike


    fun getNoticeContent(nid : String, token : String){
        viewModelScope.launch {
            repository.getNoticeContent(nid, token).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "getNoticeContent: ${response.body()}")
                    _repositoriesGetNoticeContent.postValue(response.body())
                }

            }
        }
    }

    fun postNoticeLike(token: String, nid: RetrofitPostNoticeLikeDto){
        viewModelScope.launch {
            repository.postNoticeLike(token, nid).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "postNoticeLike: ${response.body()}")
                    _repositoriesPostNoticeLike.postValue(response.body())
                }
            }
        }
    }

    fun postNoticeCancelLike(token: String, nid: RetrofitPostNoticeLikeDto){
        viewModelScope.launch {
            repository.postNoticeCancelLike(token, nid).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "postNoticeCancelLike: ${response.body()}")
                    _repositoriesNoticeCancelLike.postValue(response.body())
                }
            }
        }
    }



    fun deleteNoticeItem(nid : String, token : String){
        viewModelScope.launch {
            repository.deleteNoticeItem(nid, token).let {
                Log.d(ContentValues.TAG, "deleteNoticeItem: 삭제 성공")
            }
        }
    }
}