package young.com.sonagibook_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import young.com.sonagibook_app.databinding.ActivityNoticeListBinding

class NoticeListActivity : AppCompatActivity() {
    lateinit var binding : ActivityNoticeListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token : String = intent.getStringExtra("accessToken")!!

        binding.noticeListAddBtn.setOnClickListener {
            val intent = Intent(this,NoticeAddActivity::class.java)
            Toast.makeText(this,token,Toast.LENGTH_LONG).show()
            intent.putExtra("accessToken",token)
            startActivity(intent)
        }
    }
}