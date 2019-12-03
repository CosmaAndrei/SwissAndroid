package com.andrei.jetpack.swissandroid.components

import java.util.*

interface IIOErrorDialogListener {
    fun onPositiveAction(dialogId: UUID)
    fun onNegativeAction(dialogId: UUID)
}