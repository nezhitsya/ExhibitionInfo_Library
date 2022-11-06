package com.nezhitsya.example.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.nezhitsya.example.R

class CommonDialog(
    private val context: Context,
    private val msg: String,
    private val btnText: Pair<String, String>? = null,
    private val doneAction: (() -> Unit)? = null,
    private val cancelAction: (() -> Unit)? = null
) {

    private val dialog = Dialog(context)

    fun show() {
        val dialogBinding = DataBindingUtil.inflate<LayoutDialogCommonBinding>(
            LayoutInflater.from(context),
            R.layout.common_dialog_layout,
            null,
            false
        )
        dialog.setContentView(dialogBinding.root)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        dialogBinding.tvDlgContent.movementMethod = object : ScrollingMovementMethod(){}
        dialogBinding.tvDlgContent.text = msg
        btnText?.let {
            dialogBinding.btnDlgOk.text = it.first
            if (it.first.isEmpty()) {
                dialogBinding.btnDlgOk.visibility = View.GONE
            }
            dialogBinding.btnDlgCancel.text = it.second
            if (it.second.isEmpty()) {
                dialogBinding.btnDlgCancel.visibility = View.GONE
            }
        }
        dialogBinding.btnDlgOk.setOnClickListener {
            dialog.dismiss()
            doneAction?.run {
                this.invoke()
            }
        }
        dialogBinding.btnDlgCancel.setOnClickListener {
            dialog.dismiss()
            cancelAction?.run {
                this.invoke()
            }
        }

        dialog.show()
    }

}