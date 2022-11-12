package young.com.sonagibook_app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseScheduleContentDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseScheduleDto

class ScheduleContentViewModel(private val repository: Repository) : ViewModel() {
    private val _repositoriesGetScheduleContent = MutableLiveData<RetrofitResponseScheduleContentDto>()
    val repositories1 : MutableLiveData<RetrofitResponseScheduleContentDto>
        get() = _repositoriesGetScheduleContent

    fun getScheduleContent(eid : String, token : String){
        viewModelScope.launch {
            repository.getScheduleContent(eid, token).let { response ->
                if(response.isSuccessful){
                    _repositoriesGetScheduleContent.postValue(response.body())
                }
            }
        }
    }
}