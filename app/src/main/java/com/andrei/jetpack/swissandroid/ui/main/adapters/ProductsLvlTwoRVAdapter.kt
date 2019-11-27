package com.andrei.jetpack.swissandroid.ui.main.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andrei.jetpack.swissandroid.R
import com.andrei.jetpack.swissandroid.databinding.ListItemProductBinding
import com.andrei.jetpack.swissandroid.databinding.ListItemProductLvlTwoBinding
import com.andrei.jetpack.swissandroid.persistence.entities.ProductLvlTwo
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.ProductItemLvlTwoViewModel


class ProductsLvlTwoRVAdapter :
    ListAdapter<ProductLvlTwo, ProductsLvlTwoRVAdapter.ViewHolder>(ProductLvlTwoDifCallback()) {
    companion object {
        val TAG = ProductsLvlTwoRVAdapter::class.simpleName
    }

    class ViewHolder(
        private val binding: ListItemProductLvlTwoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                binding.viewModel?.id?.let { id ->
                    navigateToDetails(id, view)
                }
            }
        }

        private fun navigateToDetails(productId: Int, view: View) {
//            val direction = DIRECTION
//                .ACTION(plantId)
//            view.findNavController().navigate(direction)
        }

        fun bind(product: ProductLvlTwo) {
            with(binding) {
                Log.d(TAG, "bind Bind this item: $product")
                viewModel = ProductItemLvlTwoViewModel(product)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_product_lvl_two, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class ProductLvlTwoDifCallback : DiffUtil.ItemCallback<ProductLvlTwo>() {

    override fun areItemsTheSame(
        oldItem: ProductLvlTwo,
        newItem: ProductLvlTwo
    ): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(
        oldItem: ProductLvlTwo,
        newItem: ProductLvlTwo
    ): Boolean {
        return oldItem.name == newItem.name
    }
}