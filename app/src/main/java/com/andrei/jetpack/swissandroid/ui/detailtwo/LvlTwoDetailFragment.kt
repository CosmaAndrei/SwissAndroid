package com.andrei.jetpack.swissandroid.ui.detailtwo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.andrei.jetpack.swissandroid.databinding.FragmentLvlOneDetailsBinding
import com.andrei.jetpack.swissandroid.databinding.FragmentLvlTwoDetailsBinding
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LvlTwoDetailFragment : DaggerFragment() {
    private val args: LvlTwoDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentLvlTwoDetailsBinding

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var lvlTwoDetailsFreagmentViewModel: LvlTwoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lvlTwoDetailsFreagmentViewModel =
            ViewModelProviders.of(this, providerFactory).get(LvlTwoDetailViewModel::class.java)

        binding = FragmentLvlTwoDetailsBinding.inflate(inflater, container, false)

        subscribeUi(binding)

        return binding.root
    }

    private fun subscribeUi(binding: FragmentLvlTwoDetailsBinding) {
        lvlTwoDetailsFreagmentViewModel.getProductDetails(args.productId)
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