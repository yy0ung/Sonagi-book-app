package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import young.com.sonagibook_app.databinding.ActivityMainBinding
import young.com.sonagibook_app.retrofit.Dto.ScheduleResponseDto
import young.com.sonagibook_app.room.Token
import young.com.sonagibook_app.room.TokenDatabase
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {
    private val tokenDB by lazy { TokenDatabase.getInstance(this) }
    lateinit var binding : ActivityMainBinding
    var token : String = ""
    private lateinit var viewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    @RequiresApi(Build.VERSION_CODES.O)
    private var today = LocalDate.now()
    private lateinit var todayFormat : String

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: onRestart")
        //HomeFragment().refreshAdapter()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        

        mainViewModelFactory = MainViewModelFactory(Repository())
        viewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        


        CoroutineScope(Dispatchers.Main).launch {
            val job = CoroutineScope(Dispatchers.Main).launch {
                val token =
                    withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { getTokenDB() }
                val accessToken = "Bearer ${token?.accessToken}"
                getAccessToken(accessToken,token?.refreshToken.toString())
                updateDBWithNewToken(token)
                updateInfoWithNewToken()
            }

            job.join()
            Log.d(TAG, "onCreate: 끝남")
            //delay 필요한지 확인
            mainGetNotice()



            todayFormat = today.toString().substring(0,4)+"-"
            var nextFormat = (today.toString().substring(5,7).toLong()-1).toString()
            //new format "YYYY-MM" (check)
            getMonthSchedule(todayFormat+nextFormat)


            val todayBookFormat = todayFormat+today.toString().substring(8)
            //Log.d(TAG, "onCreate: 예약 포맷 $todayBookFormat")
            //new format "YYYY-MM-DD"
            getWeekBook("2023-01-02")
        }


        val fragment = supportFragmentManager.beginTransaction()
        fragment.add(R.id.homeMainFragment, HomeFragment()).commit()
        changeColorToBlue(binding.homeNav1)

        binding.homeNav1.setOnClickListener{
            changeColorToBlue(binding.homeNav1)
            changeColorToGray(binding.homeNav5, binding.homeNav4, binding.homeNav3,binding.homeNav2)
            supportFragmentManager.beginTransaction().replace(R.id.homeMainFragment, HomeFragment()).commit()
        }
        binding.homeNav2.setOnClickListener{
            changeColorToBlue(binding.homeNav2)
            changeColorToGray(binding.homeNav5, binding.homeNav4, binding.homeNav3,binding.homeNav1)
            supportFragmentManager.beginTransaction().replace(R.id.homeMainFragment, ScheduleFragment()).commit()
        }
        binding.homeNav3.setOnClickListener{
            changeColorToBlue(binding.homeNav3)
            changeColorToGray(binding.homeNav5, binding.homeNav4, binding.homeNav2,binding.homeNav1)
            supportFragmentManager.beginTransaction().replace(R.id.homeMainFragment, BookFragment()).commit()
        }
        binding.homeNav4.setOnClickListener{
            changeColorToBlue(binding.homeNav4)
            changeColorToGray(binding.homeNav5, binding.homeNav2, binding.homeNav3,binding.homeNav1)
            supportFragmentManager.beginTransaction().replace(R.id.homeMainFragment, ContactFragment()).commit()
        }
        binding.homeNav5.setOnClickListener{
            changeColorToBlue(binding.homeNav5)
            changeColorToGray(binding.homeNav2, binding.homeNav4, binding.homeNav3,binding.homeNav1)
            supportFragmentManager.beginTransaction().replace(R.id.homeMainFragment, MoreFragment()).commit()
        }






    }

    fun getMonthSchedule(date : String){
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.homeScheduleDataModel.clear()
            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { getTokenDB() }
            val accessToken = "Bearer ${token?.accessToken}"

            Log.d(TAG, "onCreate: 달력 날짜 $date")
            getScheduleList(accessToken, date)
            viewModel.repositories5.observe(this@MainActivity){
                Log.d(TAG, "onCreate: $it")
                Log.d(TAG, "onCreate: 사이즈 ${it.data.size}")

                for(i in 0..it.data.size-1){
                    Log.d(TAG, "onCreate: ${viewModel.homeScheduleDataModel}")
                    var date = it.data[i].start.substring(0,10)
                    Log.d(TAG, "onCreate: 날짜 : $date")
                    if(viewModel.homeScheduleDataModel[date]==null){
                        var temp = ArrayList<ScheduleResponseDto>()
                        temp.add(it.data[i])

                        viewModel.homeScheduleDataModel.put(date, temp)
                    }else{
                        //여기 날짜 더하는 문젠가?
                        viewModel.homeScheduleDataModel[date]!!.add(it.data[i])
                    }
                }
                //viewModel.homeScheduleDataModel.add(it)
            }
        }
    }

    private suspend fun getWeekBook(date : String){
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.bookDataModel1.clear()
            //2는 아직 손보기 전
            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { getTokenDB() }
            val accessToken = "Bearer ${token?.accessToken}"

            getBookList(date, accessToken)
            viewModel.repositories6.observe(this@MainActivity){
                Log.d(TAG, "onCreate: 추가한 모델 ${viewModel.bookDataModel1}")


                //테스트 필요

                //start format : "2022-11-27T17:00:00.000Z"
                for(i in 0 until it.data.size){
                    var rDate = it.data[i].start.substring(8,10).toInt()
                    //여기서 date는 첫날인 일요일
                    //애초에 bookDataModel을 두개로 나눠서 저장하고 (place에 따라),
                    var fDate = date.substring(6,8).toInt()
                    //왜 null일때만 체크?
                    if(viewModel.bookDataModel1[rDate-fDate]==null){
                        //얘를 hash map으로 바꿔서 end time, title, rid, writer 같이 저장해서 이후에 보여줄 때 쓰기
                        // hash map 아니어도 그냥 arrayList 도 가능은 함.
                        val temp = HashMap<String, kotlin.collections.ArrayList<String>>()
                        var info = kotlin.collections.ArrayList<String>()
                        //val time =it.data[i].start.substring(11,13)
                        //temp.add(time.toInt())
                        //start, end, title, rid 순서대로
                        info.add("9") //it.data[i].start.substring(11,13)
                        info.add("12") //it.data[i].end.substring(11,13)
                        info.add("title") //it.data[i].title
                        info.add("1") //it.data[i].rid
                        temp["9"] = info
                        viewModel.bookDataModel1.put(rDate-fDate, temp)
                    }
                }
                //viewModel.homeScheduleDataModel.add(it)
            }
        }
    }

    private suspend fun getAccessToken(token : String, refreshToken : String) {
        viewModel.getAccessToken(token, refreshToken)
        viewModel.repositories1.observe(this@MainActivity){
            Log.d(TAG, "new ///// onCreate: $it")
            viewModel.userHomeDataModel.add(it)
        }

    }
    private suspend fun getNoticeList(page : Int, token : String){
        viewModel.getNoticeList(page, token)
    }

    private fun changeColorToBlue(imgG : ImageView){
        imgG.setColorFilter(Color.parseColor("#6773F6"))
    }

    private fun changeColorToGray(g1:ImageView, g2:ImageView, g3:ImageView, g4:ImageView){
        g1.setColorFilter(Color.parseColor("#C8CBD0"))
        g2.setColorFilter(Color.parseColor("#C8CBD0"))
        g3.setColorFilter(Color.parseColor("#C8CBD0"))
        g4.setColorFilter(Color.parseColor("#C8CBD0"))

    }

    private suspend fun getTokenDB() : Token?{
        return tokenDB?.tokenDao()?.getAll()
    }
    private suspend fun updateTokenDB(token : Token){
        withContext(Dispatchers.IO){
            tokenDB?.tokenDao()?.update(token)
            Log.d(TAG, "updateTokenDB: 업데이트 성공")
        }
    }

    private suspend fun updateDBWithNewToken(tokenDB : Token?){
        viewModel.repositoriesToken.observe(this@MainActivity){it->
            Log.d(TAG, "onCreate: 새로 발급된 토큰 $it")
            CoroutineScope(Dispatchers.IO).launch { updateTokenDB(Token(it.toString(),tokenDB?.refreshToken.toString())) }
            Log.d(TAG, "onCreate: 순서 1")
        }
    }

    private suspend fun updateInfoWithNewToken(){
        viewModel.repositories1.observe(this@MainActivity){it->
            viewModel.userHomeDataModel.add(it)
            Log.d(TAG, "onCreate: 순서 2")
        }
    }

    private suspend fun mainGetNotice(){
        val accessToken = "Bearer ${withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { getTokenDB() }?.accessToken}"
        Log.d(TAG, "onCreate: 순서 3, token 채워진 상탠지 $accessToken")
        getNoticeList(1,accessToken)
        viewModel.repositories2.observe(this@MainActivity){
            viewModel.homeNoticeDataModel.add(it)
        }
    }

    private suspend fun getScheduleList(token: String, date : String){
        viewModel.getScheduleList(token, date)
    }

    private suspend fun getBookList(date : String, token : String){
        viewModel.getBookList(date, token)
    }

    private suspend fun getNextScheduleList(token: String, date: String){
        viewModel.getNextScheduleList(token, date)
    }

    private suspend fun getLastScheduleList(token: String, date: String){
        viewModel.getLastScheduleList(token, date)
    }
    





}