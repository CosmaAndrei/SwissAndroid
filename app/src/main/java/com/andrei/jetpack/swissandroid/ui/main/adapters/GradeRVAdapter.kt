package com.andrei.jetpack.swissandroid.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andrei.jetpack.swissandroid.R
import com.andrei.jetpack.swissandroid.databinding.ListItemGradeBinding
import com.andrei.jetpack.swissandroid.persistence.entities.Grade
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.GradeItemViewModel
import timber.log.Timber


class GradeRVAdapter : ListAdapter<Grade, GradeRVAdapter.ViewHolder>(GradeDifCallback()) {
    companion object {
        val TAG = GradeRVAdapter::class.simpleName
    }

    class ViewHolder(
        private val binding: ListItemGradeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(grade: Grade) {
            with(binding) {
                Timber.d(TAG, "bind Bind this item: $grade")
                viewModel = GradeItemViewModel(grade)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_grade, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class GradeDifCallback : DiffUtil.ItemCallback<Grade>() {

    override fun areItemsTheSame(
        oldItem: Grade,
        newItem: Grade
    ): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(
        oldItem: Grade,
        newItem: Grade
    ): Boolean {
        return oldItem.productId == newItem.productId
    }
}