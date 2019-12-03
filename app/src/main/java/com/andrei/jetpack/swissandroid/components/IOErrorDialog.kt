package com.andrei.jetpack.swissandroid.components

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.andrei.jetpack.swissandroid.R
import java.lang.ref.WeakReference
import java.util.*

class IOErrorDialog private constructor(
    listener: IIOErrorDialogListener,
    private val dialogId: UUID,
    private val title: String,
    private val message: String
) : DialogFragment(),
    DialogInterface.OnClickListener {

    private val listener: WeakReference<IIOErrorDialogListener> = WeakReference(listener)

    companion object {
        fun NETWORK(
            listener: IIOErrorDialogListener,
            dialogId: UUID,
            message: String
        ): IOErrorDialog =
            IOErrorDialog(listener, dialogId, "Network Error", message)

        fun DATABASE(
            listener: IIOErrorDialogListener,
            dialogId: UUID,
            message: String
        ): IOErrorDialog =
            IOErrorDialog(listener, dialogId, "Database Error", message)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(activity!!.baseContext).let {
            it.setTitle(title)
            it.setMessage(message)
            it.setPositiveButton(getString(R.string.io_error_dialog_positive), this)
            it.setNegativeButton(getString(R.string.io_error_dialog_negative), this)
        }.create()

    override fun onClick(dialog: DialogInterface, which: Int) {
        if (Dialog.BUTTON_POSITIVE == which) {
            listener.get()?.onPositiveAction(dialogId)
        } else {
            listener.get()?.onNegativeAction(dialogId)
        }
    }
}