package com.andrei.jetpack.swissandroid.ui.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.persistence.entities.Grade
import com.andrei.jetpack.swissandroid.resource.Resource
import com.andrei.jetpack.swissandroid.ui.main.repos.MainScopeProductGradeRepo
import timber.log.Timber
import javax.inject.Inject

class GradesViewModel @Inject constructor(
    private val repo: MainScopeProductGradeRepo
) : ViewModel() {
    suspend fun refreshData() {
        Timber.d("GFD Refresh data.")
        repo.refreshData()
    }

    val products: LiveData<Resource<List<Grade>>> = repo.getGradesAsLiveData()
}