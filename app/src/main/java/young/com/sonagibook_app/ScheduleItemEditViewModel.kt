package young.com.sonagibook_app

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostScheduleDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseScheduleContentDto

class ScheduleItemEditViewModel(private val repository: Repository) : ViewModel() {
    private val _repositoriesGetScheduleContent = MutableLiveData<RetrofitResponseScheduleContentDto>()
    val repositories1 : MutableLiveData<RetrofitResponseScheduleContentDto>
        get() = _repositoriesGetScheduleContent

    fun getScheduleContent(eid : String, token : String){
        viewModelScope.launch {
            repository.getScheduleContent(eid, token).let { response ->
                if(response.isSuccessful){
                    Log.d(ContentValues.TAG, "getScheduleContent: ${response.body()}")
                    _repositoriesGetScheduleContent.postValue(response.body())
                }

            }
        }
    }

    fun putScheduleContent(eid: String, token: String, data : RetrofitPostScheduleDto){
        viewModelScope.launch {
            repository.putScheduleContent(eid, token, data).let {
                Log.d(ContentValues.TAG, "putScheduleContent: 수정 완료")
            }
        }
    }
}