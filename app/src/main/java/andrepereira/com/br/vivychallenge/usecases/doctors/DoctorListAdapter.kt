package andrepereira.com.br.vivychallenge.usecases.doctors

import andrepereira.com.br.vivychallenge.R
import andrepereira.com.br.vivychallenge.data.dao.DatabaseHelper
import andrepereira.com.br.vivychallenge.data.model.Doctor
import andrepereira.com.br.vivychallenge.data.model.User
import andrepereira.com.br.vivychallenge.data.service.interceptors.PicassoInterceptor
import andrepereira.com.br.vivychallenge.databinding.DoctorAdapterRowBinding
import andrepereira.com.br.vivychallenge.util.Constants
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient

class DoctorListAdapter(val doctors: MutableList<Doctor>, context: Context): RecyclerView.Adapter<DoctorViewHolder>() {

    private val picasso: Picasso
    private val client: OkHttpClient
    private val userDao = Room.databaseBuilder(context,
        DatabaseHelper::class.java,
        "ChallengeDB")
        .allowMainThreadQueries().build().getUserDao()
    private val loggedUser: User

    init {
        loggedUser = userDao.findLoggedUser(Constants.LOGGED_USERNAME)
        client = OkHttpClient.Builder().addInterceptor(PicassoInterceptor(loggedUser.authToken)).build()
        picasso = Picasso.Builder(context)
            .downloader(OkHttp3Downloader(client))
            .build()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val viewRoot = DataBindingUtil.inflate<DoctorAdapterRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.doctor_adapter_row,
            parent,
            false
        )

        return DoctorViewHolder(viewRoot, picasso)
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

class DoctorViewHolder(
    private val row: DoctorAdapterRowBinding,
    private val picasso: Picasso
): RecyclerView.ViewHolder(row.root){

    private val userDao = Room.databaseBuilder(row.root.context,
        DatabaseHelper::class.java,
        "ChallengeDB")
        .allowMainThreadQueries().build().getUserDao()
    private val loggedUser: User

    init {
        loggedUser = userDao.findLoggedUser(Constants.LOGGED_USERNAME)

    }

    fun bindData(doctor: Doctor) {

        row.doctorName.text = doctor.name
        row.doctorAddress.text = doctor.address
        picasso.load(Constants.API_URL + "api/doctors/${doctor.id}/keys/profilepictures")
            .placeholder(R.mipmap.profile)
            .into(row.doctorPhoto)
    }

}