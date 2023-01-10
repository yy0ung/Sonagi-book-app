package young.com.sonagibook_app.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import young.com.sonagibook_app.R

class DeleteDialog(nid : String ) : DialogFragment() {
    private var dInterface : DeleteConfirmInterface? = null
    private lateinit var nid : String
//https://angangmoddi.tistory.com/261 see again
    init {
        this.nid = nid

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_delete_dialog, container, false)
        val cancelBtn = view.findViewById<Button>(R.id.deleteDialogCancelBtn)
        val deleteBtn = view.findViewById<Button>(R.id.deleteDialogDelBtn)
        //배경 투명
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        cancelBtn.setOnClickListener { dismiss() }
        deleteBtn.setOnClickListener {
            this.dInterface?.onYesButtonClick(nid)
            dismiss()
        }
        return view
    }

}
interface DeleteConfirmInterface {
    fun onYesButtonClick(nid : String)
}