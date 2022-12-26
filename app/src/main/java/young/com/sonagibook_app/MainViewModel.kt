package young.com.sonagibook_app

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.retrofit.Dto.*
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
    private val _repositoriesGetNextSchedule = MutableLiveData<RetrofitResponseScheduleDto>()
    val repositories51 : MutableLiveData<RetrofitResponseScheduleDto>
        get() = _repositoriesGetNextSchedule
    private val _repositoriesGetLastSchedule = MutableLiveData<RetrofitResponseScheduleDto>()
    val repositories52 : MutableLiveData<RetrofitResponseScheduleDto>
        get() = _repositoriesGetLastSchedule
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
    //arraylist -> hashmap
    var bookDataModel = HashMap<Int, ArrayList<Int>>()
    var getNewAccessToken = HashMap<String, String>()

    fun getAccessToken(token: String, refreshToken: String){
        viewModelScope.launch {
            repository.getAccessToken(token).let { response->
                if(response.isSuccessful){
                    //만료되지 않음
                    _repositoriesGetAccessToken.postValue(response.body())
                }else{
                    //만료됨
                    getNewToken(token,refreshToken)
                }
            }
        }
    }

    fun getNewToken(token: String, refreshToken: String){
        val map = HashMap<String, String>()
        map["refresh_token"] = refreshToken
        viewModelScope.launch {
            repository.postRefreshToken(token, map).let { response ->
                if(response.isSuccessful){
                    getNewAccessToken["accessToken"] = response.body()!!.data.accessToken
                    getAccessToken(response.body()!!.data.accessToken, refreshToken)
                    Log.d(TAG, "getNewToken: 재발급 후 다시 로그인 성공")
                }else{
                    Log.d(TAG, "getNewToken: 어플 종료 후 재시작")
                }
            }
        }
    }


//    fun getAccessToken(token : String, refreshToken : String) {
//        Log.d(TAG, "getAccessToken: getget")
//        viewModelScope.launch {
//            repository.getAccessToken(token).let { response ->
//                if (response.isSuccessful) {
//                    Log.d(TAG, "getAccessToken: ${response.body()}")
//                    _repositoriesGetAccessToken.postValue(response.body())
//                    //response.body()?.let { userHomeDataModel.add(it) }
//                } else {
//                    val map = HashMap<String, String>()
//                    map["refresh_token"] = refreshToken
////                    Log.d(TAG, "getAccessToken: viewModel 재발급")
////                    postRefreshToken(map)
//                    repository.postRefreshToken(token, map).let { response ->
//                        if (response.isSuccessful) {
//                            Log.d(TAG, "postRefreshToken: ${response.body()}")
//                            _repositoriesPostRefreshToken.postValue(response.body())
//                            //response.body()?.data?.let { getAccessToken(it.accessToken, refreshToken) }
//                            Log.d(TAG, "getAccessToken: 재발급 뷰모델")
//                        } else {
//                            Log.d(TAG, "무한루프 x")
//                        }
//                    }
//
//
//                }
//            }
//        }
//    }



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

    fun postRefreshToken(token: String, refreshToken : HashMap<String,String>){
        viewModelScope.launch {
            repository.postRefreshToken(token, refreshToken).let { response ->
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
    fun getNextScheduleList(token: String, date : String){
        viewModelScope.launch {
            repository.getSchedule(token, date).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "getNextScheduleList: ${response.body()}")
                    _repositoriesGetNextSchedule.postValue(response.body())
                }
            }
        }
    }

    fun getLastScheduleList(token: String, date : String){
        viewModelScope.launch {
            repository.getSchedule(token, date).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "getLastScheduleList: ${response.body()}")
                    _repositoriesGetLastSchedule.postValue(response.body())
                }
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

    fun postBook(token : String, data : RetrofitPostBookDto){
        viewModelScope.launch {
            repository.postBook(token, data).let {
                Log.d(ContentValues.TAG, "postBook: 예약 업로드 성공")
            }
        }
    }
}