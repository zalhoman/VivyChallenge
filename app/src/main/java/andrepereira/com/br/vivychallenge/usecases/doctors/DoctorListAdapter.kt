package andrepereira.com.br.vivychallenge.usecases.doctors

import andrepereira.com.br.vivychallenge.R
import andrepereira.com.br.vivychallenge.data.dao.DatabaseHelper
import andrepereira.com.br.vivychallenge.data.model.Doctor
import andrepereira.com.br.vivychallenge.data.service.interceptors.PicassoInterceptor
import andrepereira.com.br.vivychallenge.databinding.DoctorAdapterRowBinding
import andrepereira.com.br.vivychallenge.util.Constants
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient

class DoctorListAdapter(val doctors: MutableList<Doctor>): RecyclerView.Adapter<DoctorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val viewRoot = DataBindingUtil.inflate<DoctorAdapterRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.doctor_adapter_row,
            parent,
            false
        )

        return DoctorViewHolder(viewRoot)
    }

    override fun getItemCount(): Int {
        return doctors.size
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.bindData(doctors[position])
    }

    fun needUpdate(): Boolean {
        if (doctors.size == 0) {
            return true
        }
        return false
    }
}

class DoctorViewHolder(val row: DoctorAdapterRowBinding): RecyclerView.ViewHolder(row.root){

    private val userDao = Room.databaseBuilder(row.root.context,
        DatabaseHelper::class.java,
        "ChallengeDB")
        .allowMainThreadQueries().build().getUserDao()

    fun bindData(doctor: Doctor) {
        val loggedUser = userDao.findLoggedUser("androidChallenge@vivy.com")
        val client = OkHttpClient.Builder().addInterceptor(PicassoInterceptor(loggedUser.authToken)).build()

        row.doctorName.text = doctor.name
        row.doctorAddress.text = doctor.address
        val picasso = Picasso.Builder(row.root.context)
            .downloader(OkHttp3Downloader(client))
            .build()
        picasso.load(Constants.API_URL + "api/doctors/${doctor.id}/keys/profilepictures")
            .placeholder(R.mipmap.profile)
            .into(row.doctorPhoto)
    }

}