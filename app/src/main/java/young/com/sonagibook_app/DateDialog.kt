package young.com.sonagibook_app

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.NumberPicker
import android.widget.TextView
import java.util.*

class DateDialog(context: Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickListener: onDialogClickListener

    fun setOnClickListener(listener: onDialogClickListener){
        onClickListener = listener
    }
    fun showDialog(today : Calendar){
        dialog.setContentView(R.layout.dialog_start_date)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        dialog.show()
        val year = dialog.findViewById<NumberPicker>(R.id.dialogStartYear)
        val month = dialog.findViewById<NumberPicker>(R.id.dialogStartMonth)
        val date = dialog.findViewById<NumberPicker>(R.id.dialogStartDate)
        val doneBtn = dialog.findViewById<TextView>(R.id.dialogStartDateDone)
        year.minValue = 2022
        month.minValue = 1
        month.maxValue = 12
        date.minValue = 1
        date.maxValue = 31

        doneBtn.setOnClickListener {
            onClickListener.onClicked(year.toString()+month.toString()+date.toString())
            dialog.dismiss()
        }
    }

    interface onDialogClickListener{
        fun onClicked(date : String)
    }

}