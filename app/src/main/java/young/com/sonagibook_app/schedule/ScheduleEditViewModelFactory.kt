package young.com.sonagibook_app.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import young.com.sonagibook_app.Repository

class ScheduleEditViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repository::class.java).newInstance(repository)

    }
}