package young.com.sonagibook_app

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseNoticeDto

class NoticeListViewModel(private val repository: Repository) : ViewModel() {
    private val _repositoriesNoticeList = MutableLiveData<RetrofitResponseNoticeDto>()
    val repositories1 : MutableLiveData<RetrofitResponseNoticeDto>
        get() = _repositoriesNoticeList

    //val noticeListModel = ArrayList<RetrofitResponseNoticeDto>()


    fun getNoticeList(page : Int, token :String){
        viewModelScope.launch {
            repository.getNoticeList(page, token).let { response ->
                if(response.isSuccessful){
                    Log.d(ContentValues.TAG, "getNoticeList: ${response.body()}")
                    _repositoriesNoticeList.postValue(response.body())
                }
            }
        }
    }
}