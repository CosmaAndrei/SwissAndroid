package com.andrei.jetpack.swissandroid.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andrei.jetpack.swissandroid.R
import com.andrei.jetpack.swissandroid.databinding.ListItemProductBinding
import com.andrei.jetpack.swissandroid.persistence.entities.Product
import com.andrei.jetpack.swissandroid.ui.main.MainViewPagerFragmentDirections
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.ProductItemLvlOneViewModel
import timber.log.Timber


class ProductsRVAdapter : ListAdapter<Product, ProductsRVAdapter.ViewHolder>(ProductDifCallback()) {
    companion object {
        val TAG = ProductsRVAdapter::class.simpleName
    }

    class ViewHolder(
        private val binding: ListItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                binding.viewModel?.let { it ->
                    navigateToDetails(it.id, it.name, view)
                }
            }
        }

        private fun navigateToDetails(productId: Int, title: String, view: View) {
            val direction =
                MainViewPagerFragmentDirections.actionMainViewPagerFragmentToLvlOneDetailFragment(
                    productId,
                    title
                )
            view.findNavController().navigate(direction)
        }

        fun bind(product: Product) {
            with(binding) {
                Timber.d(TAG, "bind Bind this item: $product")
                viewModel = ProductItemLvlOneViewModel(product)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_product, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class ProductDifCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem.name == newItem.name
    }
}