package andrepereira.com.br.vivychallenge

import andrepereira.com.br.vivychallenge.usecases.doctors.DoctorListFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_host, DoctorListFragment())
            .commit()
    }
}
