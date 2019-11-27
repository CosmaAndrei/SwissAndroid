package com.andrei.jetpack.swissandroid.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.andrei.jetpack.swissandroid.R


class SplashScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_splash_screen, container, false)

    override fun onResume() {
        super.onResume()
        findNavController().navigate(
            SplashScreenFragmentDirections.actionSplashScreenFragmentToMainViewPagerFragment()
        )
    }
}