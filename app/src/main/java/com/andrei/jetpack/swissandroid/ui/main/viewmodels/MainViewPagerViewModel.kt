package com.andrei.jetpack.swissandroid.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.ui.main.repos.GradesRepo
import javax.inject.Inject

class MainViewPagerViewModel @Inject constructor(
    private val gradesRepo: GradesRepo
) : ViewModel() {
    suspend fun refreshData() {
        gradesRepo.refreshData()
    }
}