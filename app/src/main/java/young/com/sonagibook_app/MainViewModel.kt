package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.retrofit.Dto.RetrofitGetResponseAllInfo
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseNoticeDto

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _repositoriesGetAccessToken = MutableLiveData<RetrofitGetResponseAllInfo>()
    val repositories1 : MutableLiveData<RetrofitGetResponseAllInfo>
            get() = _repositoriesGetAccessToken
    private val _repositoriesNoticeList = MutableLiveData<RetrofitResponseNoticeDto>()
    val repositories2 : MutableLiveData<RetrofitResponseNoticeDto>
        get() = _repositoriesNoticeList


    init {
        Log.d(TAG, "creat: viewModelMain")
    }

    val accessToken = ArrayList<String>()
    val userHomeDataModel = ArrayList<RetrofitGetResponseAllInfo>()

    fun getAccessToken(token : String){
        Log.d(TAG, "getAccessToken: getget")
        viewModelScope.launch {
            repository.getAccessToken(token).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "getAccessToken: ${response.body()}")
                    _repositoriesGetAccessToken.postValue(response.body())
                    response.body()?.let { userHomeDataModel.add(it) }
                }
            }
        }
    }

    fun getNoticeList(page : Int, token :String){
        viewModelScope.launch {
            repository.getNoticeList(page, token).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "getNoticeList: ${response.body()}")
                    _repositoriesNoticeList.postValue(response.body())
                }
            }
        }
    }
}