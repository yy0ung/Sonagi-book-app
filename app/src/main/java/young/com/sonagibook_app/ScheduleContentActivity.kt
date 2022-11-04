package young.com.sonagibook_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import young.com.sonagibook_app.databinding.ActivityScheduleContentBinding

class ScheduleContentActivity : AppCompatActivity() {
    lateinit var binding: ActivityScheduleContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scheduleContentEditBtn.setOnClickListener {
            val intent = Intent(this, ScheduleItemEditActivity::class.java)
            startActivity(intent)
        }


    }
}