package young.com.sonagibook_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import young.com.sonagibook_app.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {
    private var _binding : FragmentMoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

}