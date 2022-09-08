package young.com.sonagibook_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager

class DesignTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_design_test)

        val fragment = supportFragmentManager.beginTransaction()
        fragment.add(R.id.fragment, HomeFragment()).commit()
    }
}