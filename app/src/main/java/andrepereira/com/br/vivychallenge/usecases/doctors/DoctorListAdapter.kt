package andrepereira.com.br.vivychallenge.usecases.doctors

import andrepereira.com.br.vivychallenge.data.model.Doctor
import andrepereira.com.br.vivychallenge.databinding.FragmentDoctorListBinding
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DoctorListAdapter(val doctors: List<Doctor>): RecyclerView.Adapter<DoctorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class DoctorViewHolder(val itemView: FragmentDoctorListBinding): RecyclerView.ViewHolder(itemView.root){

}