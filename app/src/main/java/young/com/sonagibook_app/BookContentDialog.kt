package young.com.sonagibook_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import young.com.sonagibook_app.room.TokenDatabase

class BookContentDialog : BottomSheetDialogFragment() {
    private val tokenDB by lazy { TokenDatabase.getInstance(requireContext()) }
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_content, container, false)
        return view
    }
}