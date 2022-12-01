package young.com.sonagibook_app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.retrofit.Dto.BookDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostBookDto
import young.com.sonagibook_app.room.TokenDatabase
import java.text.DecimalFormat
import java.util.*
import kotlin.math.log

class BookAddDialog : BottomSheetDialogFragment() {
    private val tokenDB by lazy { TokenDatabase.getInstance(requireContext()) }
    private lateinit var viewModel: MainViewModel
    private val format = DecimalFormat("00")
    private val cal = Calendar.getInstance()
    private lateinit var data : RetrofitPostBookDto
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_add, container, false)
        val bookSendBtn : Button = view.findViewById(R.id.bookAddSendBtn)
        val bookAddDate = view.findViewById<TextView>(R.id.bookAddDate)
        val startTime = view.findViewById<TextView>(R.id.bookAddTimeStart)
        val endTime = view.findViewById<TextView>(R.id.bookAddTimeEnd)

        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(Repository()))[MainViewModel::class.java]

        
        bookAddDate.setOnClickListener { datePicker(bookAddDate) }
        startTime.setOnClickListener { startTimePicker(startTime) }
        endTime.setOnClickListener { endTimePicker(endTime) }


        bookSendBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val token =
                    withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { tokenDB?.tokenDao()?.getAll() }
                val accessToken = "Bearer ${token?.accessToken}"
                addTitle(bookAddDate, startTime, endTime)
                Log.d(TAG, "onCreateView: 333333 $data")

                
            }
        }
        


        return view
    }

    private fun datePicker(text : TextView){
        val data = DatePickerDialog.OnDateSetListener{ view, year, month, date ->
            val m = format.format(month+1)
            val d = format.format(date)
            text.text = "${year}년 ${m}월 ${d}일"
        }
        DatePickerDialog(requireContext(), data, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun startTimePicker(text : TextView){
        val data2 = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
            val h1 = format.format(hourOfDay)
            val m1 = format.format(minute)
            if(h1.toInt()<=12) {
                text.text = "오전 ${h1}:${m1}"
            }else{
                text.text = "오후 ${format.format(h1.toInt()-12)}:${m1}"
            }
        }
        TimePickerDialog(requireContext(), data2, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

    }

    private fun endTimePicker(text : TextView){
        val data2 = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
            val h2 = format.format(hourOfDay)
            val m2 = format.format(minute)
            if(h2.toInt()<=12) {
                text.text = "오전 ${h2}:${m2}"
            }else{
                text.text = "오후 ${format.format(h2.toInt()-12)}:${m2}"
            }
        }
        TimePickerDialog(requireContext(), data2, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

    }

    private fun addTitle(date : TextView, startTime : TextView, endTime : TextView){
        var titleInput : EditText? = view?.findViewById(R.id.bookAddTitleInput)
        val date : String = getSS(date, 0,4)+getSS(date, 6,8)+getSS(date,10,12)
        val startTime :String = getSS(startTime,3,5) + startTime.text.toString().substring(6)
        val endTime :String = getSS(endTime,3,5) + endTime.text.toString().substring(6)
        data= RetrofitPostBookDto(BookDto(titleInput?.text.toString(), 0,
            date+startTime, date+endTime))
        

    }

    private fun getSS(text : TextView, start : Int, end : Int) : String{
        return text.text.toString().substring(start, end)
    }


}