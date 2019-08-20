package andrepereira.com.br.vivychallenge

import andrepereira.com.br.vivychallenge.usecases.doctors.DoctorListFragment
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                123)
        }


        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_host, DoctorListFragment())
            .commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            123 -> {
                if (grantResults.size < 2) {
                    //Nao vai rolar
                    Log.d("teste", "permissao nao ok")
                } else {
                    //yay
                    Log.d("teste", "permissao ok")
                }
            }
        }
    }
}
