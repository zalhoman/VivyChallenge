package andrepereira.com.br.vivychallenge.usecases.doctors

import andrepereira.com.br.vivychallenge.R
import android.Manifest
import android.content.pm.PackageManager
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
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
        is DoctorSearchStatus.Error -> view.visibility = View.VISIBLE
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
                view.adapter = DoctorListAdapter(doctorSearchStatus.list, view.context)
                view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            if (reachedEndOfList(doctorSearchStatus, (view.layoutManager as LinearLayoutManager).findLastVisibleItemPosition())) {
                                viewModel.nextPage()
                            }
                        }
                    }
                })
            }
        }
        is DoctorSearchStatus.NewSearch -> {
            view.visibility = View.VISIBLE
            view.adapter = null
        }
        is DoctorSearchStatus.NextPageSearch -> view.visibility = View.VISIBLE
        else -> {
            view.visibility = View.GONE
            if (view.adapter != null) {
                view.adapter = null
            }
        }
    }
}

@BindingAdapter("onSearchClick", "doctorListViewModel", requireAll = true)
fun searchClick(button: Button,  searchField: String, viewModel: DoctorListFragmentViewModel) {
    button.setOnClickListener {
        button.isEnabled = false
        viewModel.searchStatus.set(DoctorSearchStatus.NewSearch)
        if (ContextCompat.checkSelfPermission(button.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(button.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Snackbar.make(button,
                button.context.resources.getString(R.string.location_permission_not_granted),
                Snackbar.LENGTH_LONG)
                .show()
        } else {
            viewModel.searchDoctor(searchField)
        }
    }
}

@BindingAdapter("searchButtonEnabled")
fun onSearchButtonEnabled(button: Button, searchStatus: DoctorSearchStatus) {
    when(searchStatus) {
        is DoctorSearchStatus.NewSearch -> button.isEnabled = false
        else -> button.isEnabled = true
    }
}

@BindingAdapter("progressBar")
fun onProgressBar(view: ProgressBar, searchStatus: DoctorSearchStatus) {
    if (searchStatus is DoctorSearchStatus.NewSearch || searchStatus is DoctorSearchStatus.NextPageSearch) {
        view.isIndeterminate = true
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("searchError")
fun onSearchError(view: View, searchStatus: DoctorSearchStatus) {
    if (searchStatus is DoctorSearchStatus.Error) {
        Snackbar.make(view, searchStatus.errorMsg, Snackbar.LENGTH_LONG).show()
    }
}

private fun downMotionScroll(dy: Int) = dy > 0

private fun reachedEndOfList(doctorSearchStatus: DoctorSearchStatus.SearchSuccess, lastVisibleItem: Int) =
    (lastVisibleItem + 1 >= doctorSearchStatus.list.size)