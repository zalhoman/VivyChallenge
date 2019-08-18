package andrepereira.com.br.vivychallenge.usecases.doctors

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("notStartedTextField")
fun notStartedTextField(view: TextView, searchStatus: SearchStatus) {
    when (searchStatus) {
        is SearchStatus.NotStarted -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter("notFoundTextField")
fun notFoundTextField(view: TextView, searchStatus: SearchStatus) {
    when (searchStatus) {
        is SearchStatus.NotFound -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter("doctorsRecyclerView")
fun doctorsRecyclerView(view: RecyclerView, searchStatus: SearchStatus) {
    when (searchStatus) {
        is SearchStatus.SearchSuccess -> {
            view.visibility = View.VISIBLE
            view.layoutManager = LinearLayoutManager(view.context)
            view.adapter?.let {
                view.adapter!!.notifyDataSetChanged()
            } ?: run {
                view.adapter = DoctorListAdapter(searchStatus.list)
            }
        }
        else -> view.visibility = View.GONE
    }
}