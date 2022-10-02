package young.com.sonagibook_app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import young.com.sonagibook_app.retrofit.Dto.NoticeDto
import young.com.sonagibook_app.retrofit.dataDto.dataDtoNoticeContentDto

class NoticeItemAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_item_add)
        val btn = findViewById<Button>(R.id.noticeItemAddBtn)
        btn.setOnClickListener {
            aa()
        }



    }

    private fun aa(){
        var title = findViewById<EditText>(R.id.noticeItemAddTitleInput)


        val map = dataDtoNoticeContentDto(title.text.toString(),"aa")
        Toast.makeText(this,"$map",Toast.LENGTH_LONG).show()

    }
}