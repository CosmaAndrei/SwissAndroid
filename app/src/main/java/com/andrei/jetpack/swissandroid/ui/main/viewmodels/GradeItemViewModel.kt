package com.andrei.jetpack.swissandroid.ui.main.viewmodels


import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.persistence.entities.Grade

class GradeItemViewModel(grade: Grade) : ViewModel() {
    val id = grade.productId
    val itemName = grade.itemName
    val clientLvlOneCount = grade.clientCountLevelOne
    val clientLvlTwoCount = grade.clientCountLevelOne
    val grade = grade.grade
}