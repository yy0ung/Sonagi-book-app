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
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.*
import young.com.sonagibook_app.databinding.ActivityMainBinding
import young.com.sonagibook_app.retrofit.Dto.ScheduleDto
import young.com.sonagibook_app.retrofit.Dto.ScheduleResponseDto
import young.com.sonagibook_app.room.Token
import young.com.sonagibook_app.room.TokenDatabase
import java.text.DecimalFormat
import java.text.Format
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    private val tokenDB by lazy { TokenDatabase.getInstance(this) }
    lateinit var binding : ActivityMainBinding
    var token : String = ""
    private lateinit var viewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    @RequiresApi(Build.VERSION_CODES.O)
    private var today = LocalDate.now()
    private lateinit var todayFormat : String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        

        mainViewModelFactory = MainViewModelFactory(Repository())
        viewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        


        CoroutineScope(Dispatchers.Main).launch {
            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { getTokenDB() }
            val accessToken = "Bearer ${token?.accessToken}"
            Log.d(TAG, "onCreate: @@@@@@@$accessToken")
            getAccessToken(accessToken,token?.refreshToken.toString())

            viewModel.repositories3.observe(this@MainActivity){it->
                Log.d(TAG, "onCreate: Room db 업데이트")
                CoroutineScope(Dispatchers.IO).launch {
                    //locking 필요한지?
                    updateTokenDB(Token(it.data.accessToken.toString(),token?.refreshToken.toString()))
                    Log.d(TAG, "onCreate: 재발급중")
                    delay(3000)
                    getAccessToken(it.data.accessToken.toString(),token?.refreshToken.toString())
                    Log.d(TAG, "onCreate: 재발급 완료")
                }
            }

            viewModel.repositories1.observe(this@MainActivity){it->
                Log.d(TAG, "만료 여부 상관 없음")
                viewModel.userHomeDataModel.add(it)
            }
            //Log.d(TAG, "@@@Activity onCreate: $accessToken")

            //main 에서 받아야함
        }

        CoroutineScope(Dispatchers.Main).launch {
            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { getTokenDB() }
            val accessToken = "Bearer ${token?.accessToken}"
            getNoticeList(1,accessToken)
            viewModel.repositories2.observe(this@MainActivity){
                Log.d(TAG, "onCreate: $it")
                viewModel.homeNoticeDataModel.add(it)
            }
            todayFormat = today.toString().substring(0,4)+today.toString().substring(5,7)
            getMonthSchedule(todayFormat)

        }

//        viewModel.accessToken.add(accessToken)

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
        val format = DecimalFormat("00")
        val cal = Calendar.getInstance()
        cal.set(date.substring(0,4).toInt(), date.substring(4).toInt(), 1)
        val nextYear = format.format(cal.get(Calendar.YEAR))
        val nextMonth = format.format(cal.get(Calendar.MONTH)+1)
        val lastYear = format.format(cal.get(Calendar.YEAR))
        val lastMonth = format.format(cal.get(Calendar.MONTH)-1)
        val nextDate = "$nextYear$nextMonth"
        val lastDate = "$lastYear$lastMonth"

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.homeScheduleDataModel.clear()
            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { getTokenDB() }
            val accessToken = "Bearer ${token?.accessToken}"

            Log.d(TAG, "onCreate: 달력 날짜 $date")
            getScheduleList(accessToken, date)
            viewModel.repositories5.observe(this@MainActivity){it ->
                Log.d(TAG, "getMonthSchedule: 순서1")

                for(i in 0..it.data.size-1){
                    Log.d(TAG, "onCreate: ${viewModel.homeScheduleDataModel}")
                    var date = it.data[i].start.substring(0,10)
                    Log.d(TAG, "onCreate: 날짜 : $date")
                    if(viewModel.homeScheduleDataModel[date]==null){
                        var temp = ArrayList<ScheduleResponseDto>()
                        temp.add(it.data[i])

                        viewModel.homeScheduleDataModel.put(date, temp)
                    }else{
                        viewModel.homeScheduleDataModel[date]?.add(it.data[i])
                    }
                }
                CoroutineScope(Dispatchers.Main).launch {
                    getNextScheduleList(accessToken, nextDate)
                    Log.d(TAG, "getMonthSchedule: 순서2")
                    viewModel.repositories51.observe(this@MainActivity){it2->
                        for(i in 0..it2.data.size-1){
                            var date = it2.data[i].start.substring(0,10)
                            Log.d(TAG, "onCreate: 날짜 : $date")
                            if(viewModel.homeScheduleDataModel[date]==null){
                                var temp = ArrayList<ScheduleResponseDto>()
                                temp.add(it.data[i])

                                viewModel.homeScheduleDataModel.put(date, temp)
                            }else{
                                viewModel.homeScheduleDataModel[date]?.add(it.data[i])
                            }
                        }

                    }
                    getLastScheduleList(accessToken, lastDate)
                    viewModel.repositories51.observe(this@MainActivity){it3->
                        for(i in 0..it3.data.size-1){
                            var date = it3.data[i].start.substring(0,10)
                            Log.d(TAG, "onCreate: 날짜 : $date")
                            if(viewModel.homeScheduleDataModel[date]==null){
                                var temp = ArrayList<ScheduleResponseDto>()
                                temp.add(it.data[i])

                                viewModel.homeScheduleDataModel.put(date, temp)
                            }else{
                                viewModel.homeScheduleDataModel[date]?.add(it.data[i])
                            }
                        }

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

//        viewModel.getWithNewAccessToken(token.accessToken)
//        viewModel.repositories4.observe(this@MainActivity){
//            Log.d(TAG, "자동으로 만료 token 업데이트")
//            viewModel.userHomeDataModel.add(it)
//        }
        CoroutineScope(Dispatchers.IO).launch {
            tokenDB?.tokenDao()?.update(token)
            Log.d(TAG, "updateTokenDB: 업데이트 성공")
        }


    }

    private suspend fun getScheduleList(token: String, date : String){
        viewModel.getScheduleList(token, date)
    }

    private suspend fun getNextScheduleList(token: String, date: String){
        viewModel.getNextScheduleList(token, date)
    }

    private suspend fun getLastScheduleList(token: String, date: String){
        viewModel.getLastScheduleList(token, date)
    }
    





}