package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.retrofit.Dto.*
import young.com.sonagibook_app.room.Token
import young.com.sonagibook_app.room.TokenDatabase

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _repositoriesGetAccessToken = MutableLiveData<RetrofitGetResponseAllInfo>()
    val repositories1 : MutableLiveData<RetrofitGetResponseAllInfo>
            get() = _repositoriesGetAccessToken
    private val _repositoriesNoticeList = MutableLiveData<RetrofitResponseNoticeDto>()
    val repositories2 : MutableLiveData<RetrofitResponseNoticeDto>
        get() = _repositoriesNoticeList
    private val _repositoriesPostRefreshToken = MutableLiveData<RetrofitResponseRefreshTokenDto>()
    val repositories3 : MutableLiveData<RetrofitResponseRefreshTokenDto>
        get() = _repositoriesPostRefreshToken
    private val _repositoriesGetNewAccessToken = MutableLiveData<RetrofitGetResponseAllInfo>()
    val repositories4 : MutableLiveData<RetrofitGetResponseAllInfo>
        get() = _repositoriesGetNewAccessToken
    private val _repositoriesGetSchedule = MutableLiveData<RetrofitResponseScheduleDto>()
    val repositories5 : MutableLiveData<RetrofitResponseScheduleDto>
        get() = _repositoriesGetSchedule
    private val _repositoriesGetBook = MutableLiveData<RetrofitResponseBookDto>()
    val repositories6 : MutableLiveData<RetrofitResponseBookDto>
        get() = _repositoriesGetBook


    private var _newAccessToken : String? = null
    val newAccessToken : String? get() = _newAccessToken


    init {
        Log.d(TAG, "create: viewModelMain")
        _newAccessToken = null
        
    }

    
    var userHomeDataModel = ArrayList<RetrofitGetResponseAllInfo>()
    var homeNoticeDataModel = ArrayList<RetrofitResponseNoticeDto>()
    var homeScheduleDataModel = HashMap<String, ArrayList<ScheduleResponseDto>>()

    fun getAccessToken(token : String, refreshToken : String){
        Log.d(TAG, "getAccessToken: getget")
        viewModelScope.launch {
            repository.getAccessToken(token).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "getAccessToken: ${response.body()}")
                    _repositoriesGetAccessToken.postValue(response.body())
                    //response.body()?.let { userHomeDataModel.add(it) }
                }else{
                    val map = HashMap<String, String>()
                    map["refresh_token"] = refreshToken
//                    Log.d(TAG, "getAccessToken: viewModel 재발급")
//                    postRefreshToken(map)
                    repository.postRefreshToken(map).let { response ->
                        if(response.isSuccessful){
                            Log.d(TAG, "postRefreshToken: ${response.body()}")
                            _repositoriesPostRefreshToken.postValue(response.body())
                            //response.body()?.data?.let { getAccessToken(it.accessToken, refreshToken) }
                            Log.d(TAG, "getAccessToken: 재발급 뷰모델")
                        }else{
                            Log.d(TAG, "무한루프 x")
                        }
                    }


                }
            }
        }
    }
    fun getWithNewAccessToken(token : String){
        Log.d(TAG, "getAccessToken: 새로운 fun")
        viewModelScope.launch {
            repository.getAccessToken(token).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "getNewAccessToken: ${response.body()}")
                    _repositoriesGetNewAccessToken.postValue(response.body())
                }else{
                    Log.d(TAG, "getWithNewAccessToken: 불러오기 실패")
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

    fun postRefreshToken(refreshToken : HashMap<String,String>){
        viewModelScope.launch {
            repository.postRefreshToken(refreshToken).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "postRefreshToken: ${response.body()}")
                    _repositoriesPostRefreshToken.postValue(response.body())
                    response.body()?.data?.let { getAccessToken(it.accessToken, refreshToken["refreshToken"].toString()) }

                }
            }
        }
    }

    fun getScheduleList(token: String, date : String){
        viewModelScope.launch {
            repository.getSchedule(token, date).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "getScheduleList: ${response.body()}")
                    _repositoriesGetSchedule.postValue(response.body())
                }
            }
        }
    }

    fun postBook(token : String, data : RetrofitPostBookDto){
        viewModelScope.launch {
            repository.postBook(token, data).let {
                Log.d(TAG, "postBook: 예약 업로드 성공")
            }
        }
    }

    fun getBookList(date : String, token : String){
        viewModelScope.launch {
            repository.getBookList(date, token).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "getBookList: ${response.body()}")
                    _repositoriesGetBook.postValue(response.body())
                }
            }
        }
    }
}