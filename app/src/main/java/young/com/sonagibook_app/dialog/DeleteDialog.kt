package young.com.sonagibook_app.dialog

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import young.com.sonagibook_app.R


class DeleteDialog(context : Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickListener: ButtonOnClickListener

    fun createDialog(){
        dialog.setContentView(R.layout.fragment_delete_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(true)

        val cancelBtn = dialog.findViewById<Button>(R.id.deleteDialogCancelBtn)
        val deleteBtn = dialog.findViewById<Button>(R.id.deleteDialogDelBtn)

        cancelBtn.setOnClickListener {
            //onClickListener.onClicked()
            dialog.dismiss()
        }

        deleteBtn.setOnClickListener {
            onClickListener.onClicked()
            dialog.dismiss()
        }

        dialog.show()
    }

    interface ButtonOnClickListener{
        fun onClicked()
    }

    fun setOnClickListener(listener: ButtonOnClickListener){
        onClickListener = listener
    }


}