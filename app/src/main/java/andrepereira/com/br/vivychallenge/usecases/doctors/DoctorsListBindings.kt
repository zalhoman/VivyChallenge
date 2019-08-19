package andrepereira.com.br.vivychallenge.usecases.doctors

import android.view.View
import android.widget.Button
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

@BindingAdapter("doctorsRecyclerView", "doctorListViewModel")
fun doctorsRecyclerView(view: RecyclerView, searchStatus: SearchStatus, viewModel: DoctorListFragmentViewModel) {
    val linearLayoutManager = LinearLayoutManager(view.context)
    view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (linearLayoutManager.findLastCompletelyVisibleItemPosition() - 2 >= linearLayoutManager.itemCount) {
                viewModel.nextPage()
            }
        }
    })

    when (searchStatus) {
        is SearchStatus.SearchSuccess -> {
            view.visibility = View.VISIBLE
            view.layoutManager = linearLayoutManager
            view.adapter?.let {
                view.adapter!!.notifyDataSetChanged()
            } ?: run {
                view.adapter = DoctorListAdapter(searchStatus.list)
            }
        }
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter("doctorListViewModel")
fun searchClick(button: Button, viewModel: DoctorListFragmentViewModel) {
    button.setOnClickListener {
        viewModel.searchDoctor(viewModel.searchField.get()!!)
    }
}