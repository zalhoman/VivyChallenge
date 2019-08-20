package andrepereira.com.br.vivychallenge.usecases.doctors

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

@BindingAdapter("notStartedTextField")
fun notStartedTextField(view: TextView, doctorSearchStatus: DoctorSearchStatus) {
    when (doctorSearchStatus) {
        is DoctorSearchStatus.NotStarted -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter("notFoundTextField")
fun notFoundTextField(view: TextView, doctorSearchStatus: DoctorSearchStatus) {
    when (doctorSearchStatus) {
        is DoctorSearchStatus.NotFound -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter("doctorsRecyclerView", "doctorListViewModel")
fun doctorsRecyclerView(view: RecyclerView, doctorSearchStatus: DoctorSearchStatus, viewModel: DoctorListFragmentViewModel) {

    when (doctorSearchStatus) {
        is DoctorSearchStatus.SearchSuccess -> {
            view.visibility = View.VISIBLE
            if (view.adapter != null
                && !(view.adapter!! as DoctorListAdapter).needUpdate()) {
                    view.adapter!!.notifyDataSetChanged()
            } else {
                view.adapter = DoctorListAdapter(doctorSearchStatus.list)
                view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        if (reachedEndOfList(doctorSearchStatus,
                                (view.layoutManager as LinearLayoutManager).findLastVisibleItemPosition())
                            && downMotionScroll(dy)) {
                            Log.d("nextPage", "nextPage")
                            viewModel.nextPage()
                        }
                    }
                })
            }
        }
        is DoctorSearchStatus.Searching -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter("onSearchClick", "doctorListViewModel", requireAll = true)
fun searchClick(button: Button,  searchField: String, viewModel: DoctorListFragmentViewModel) {
    button.setOnClickListener {
        if (ContextCompat.checkSelfPermission(button.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(button.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Snackbar.make(button, "Permission not granted", Snackbar.LENGTH_LONG).show()
        } else {
            viewModel.searchDoctor(searchField)
        }
    }
}

private fun downMotionScroll(dy: Int) = dy > 0

private fun reachedEndOfList(doctorSearchStatus: DoctorSearchStatus.SearchSuccess, lastVisibleItem: Int) =
    (lastVisibleItem + 2 >= doctorSearchStatus.list.size)