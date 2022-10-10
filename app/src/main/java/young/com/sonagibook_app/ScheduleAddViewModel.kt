package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostScheduleDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseScheduleDto

class ScheduleAddViewModel(private val repository: Repository) : ViewModel() {
    private val _repositoriesPostSchedule = MutableLiveData<RetrofitResponseScheduleDto>()
    val repositories1 : MutableLiveData<RetrofitResponseScheduleDto>
        get() = _repositoriesPostSchedule



    fun postSchedule(token : String, data : RetrofitPostScheduleDto){
        viewModelScope.launch {
            repository.postSchedule(token, data).let {response ->
                Log.d(TAG, "postSchedule: 성공")
//                if(response.isSuccessful){
//                    Log.d(TAG, "postSchedule: ${response.body()}")
//                    _repositoriesPostSchedule.postValue(response.body())
//                }

            }

        }
    }

}