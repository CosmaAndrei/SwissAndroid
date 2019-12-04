package com.andrei.jetpack.swissandroid.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.ui.main.repos.MainScopeProductGradeRepo
import javax.inject.Inject

class MainViewPagerViewModel @Inject constructor(
    private val repo: MainScopeProductGradeRepo
) : ViewModel() {
    suspend fun refreshData() {
        repo.refreshData()
    }
}