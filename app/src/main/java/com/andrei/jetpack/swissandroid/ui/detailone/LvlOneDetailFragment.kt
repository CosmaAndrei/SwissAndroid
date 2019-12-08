package com.andrei.jetpack.swissandroid.ui.detailone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.andrei.jetpack.swissandroid.databinding.FragmentLvlOneBinding
import com.andrei.jetpack.swissandroid.databinding.FragmentLvlOneDetailsBinding
import com.andrei.jetpack.swissandroid.ui.main.LevelOneFragment
import com.andrei.jetpack.swissandroid.ui.main.adapters.ProductsRVAdapter
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.LvlOneProductsViewModel
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class LvlOneDetailFragment : DaggerFragment() {
    private val args: LvlOneDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentLvlOneDetailsBinding

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var lvlOneDetailFragmentViewModel: LvlOneDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        lvlOneDetailFragmentViewModel =
            ViewModelProviders.of(this, providerFactory).get(LvlOneDetailViewModel::class.java)

        binding = FragmentLvlOneDetailsBinding.inflate(inflater, container, false)

        subscribeUi(binding)

        return binding.root
    }

    private fun subscribeUi(binding: FragmentLvlOneDetailsBinding) {
        lvlOneDetailFragmentViewModel.getProductDetails(args.productId)
            .observe(viewLifecycleOwner) { result ->
                with(binding) {
                    productName.text = result.name
                    productAlias.text = result.alias
                    productReleaseDate.text = result.releaseDate
                    productClients.text = result.clients.toString()
                }
            }
    }
}