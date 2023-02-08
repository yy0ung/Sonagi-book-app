package young.com.sonagibook_app.schedule

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.R
import young.com.sonagibook_app.Repository
import young.com.sonagibook_app.databinding.ActivityScheduleContentBinding
import young.com.sonagibook_app.dialog.DeleteDialog
import young.com.sonagibook_app.notice.NoticeItemEditActivity
import young.com.sonagibook_app.room.TokenDatabase

class ScheduleContentActivity : AppCompatActivity() {
    lateinit var binding: ActivityScheduleContentBinding
    lateinit var viewModel: ScheduleContentViewModel
    lateinit var viewModelFactory : ScheduleViewModelFactory
    private val tokenDB by lazy { TokenDatabase.getInstance(applicationContext) }
    private lateinit var eid : String
    private lateinit var accessToken : String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = ScheduleViewModelFactory(Repository())
        viewModel = ViewModelProvider(this, viewModelFactory)[ScheduleContentViewModel::class.java]

        //toolbar
        setSupportActionBar(binding.scheduleContentToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.scheduleContentToolbar.setNavigationOnClickListener { onBackPressed() }

        eid = intent.getStringExtra("eid")!!
        CoroutineScope(Dispatchers.Main).launch {
            val token = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { tokenDB?.tokenDao()?.getAll() }
            accessToken = "Bearer ${token?.accessToken}"

            getScheduleContent(eid, accessToken)
            viewModel.repositories1.observe(this@ScheduleContentActivity){
                Log.d(TAG, "onCreate: 로그 $it")
                binding.scheduleContentTitle.text = it.data.title
                binding.scheduleContentPlace.text = it.data.place
                binding.scheduleContentDate.text = "${it.data.start.substring(0,4)}년 ${it.data.start.substring(5,7)}월 ${it.data.start.substring(8,10)}일"
                binding.scheduleContentContent.text = it.data.content
                it.data.type?.let { it1 -> selectColor(it1) }
            }
        }


        binding.scheduleContentEditBtn.setOnClickListener {
            val intent = Intent(this, ScheduleItemEditActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.toolbarMenuEdit -> {
                Toast.makeText(this,"수정", Toast.LENGTH_LONG).show()
                val intent = Intent(this@ScheduleContentActivity, ScheduleItemEditActivity::class.java)
                intent.putExtra("eid", eid)
                startActivity(intent)
                super.onOptionsItemSelected(item)
            }
            R.id.toolbarMenuDelete -> {
                Toast.makeText(this,"삭제", Toast.LENGTH_LONG).show()
                //dialog test
                val dialog = DeleteDialog(this)
                dialog.createDialog()
                dialog.setOnClickListener(object : DeleteDialog.ButtonOnClickListener{
                    override fun onClicked() {
                        viewModel.deleteScheduleItem(eid, accessToken)
                        Toast.makeText(this@ScheduleContentActivity, "삭제되었습니다", Toast.LENGTH_LONG).show()
                    }
                })

                super.onOptionsItemSelected(item)
            }else -> super.onOptionsItemSelected(item)
        }
    }

    private suspend fun getScheduleContent(eid : String, token : String){
        viewModel.getScheduleContent(eid, token)
    }
    
    private fun selectColor(type : Int){
        when(type){
                    0 -> {
                        binding.scheduleContentType.setBackgroundResource(R.drawable.schedule_prac)
                        binding.scheduleContentType.text = "연습"
                    }
                    1-> {
                        binding.scheduleContentType.setBackgroundResource(R.drawable.schedule_event)
                        binding.scheduleContentType.text = "행사"
                    }
                    2->{
                        binding.scheduleContentType.setBackgroundResource(R.drawable.schedule_show)
                        binding.scheduleContentType.text = "공연"
                    }
                    3->{
                        binding.scheduleContentType.setBackgroundResource(R.drawable.schedule_performance)
                        binding.scheduleContentType.text = "발표회"
                    }
                    4->{
                        binding.scheduleContentType.setBackgroundResource(R.drawable.schedule_etc)
                        binding.scheduleContentType.text = "기타"
                    }
                }
    }
}