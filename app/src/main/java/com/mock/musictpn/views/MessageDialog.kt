package com.mock.musictpn.views

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.mock.musictpn.R
import com.mock.musictpn.databinding.DialogMessageBinding

class MessageDialog(private val ctx: Context) : Dialog(ctx, R.style.Theme_AppCompat_Dialog) {
    private lateinit var mBinding: DialogMessageBinding

    var message: String = ""
        set(value) {
            field = value
            mBinding.tvMessage.text = message
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogMessageBinding.inflate(LayoutInflater.from(ctx))
        mBinding.tvMessage.text = message
        mBinding.tvOk.setOnClickListener {
            dismiss()
        }
        val window = this.window
        val wlp = window?.attributes
        wlp?.let {
            it.gravity = Gravity.CENTER
            it.width = WindowManager.LayoutParams.MATCH_PARENT
            it.height = WindowManager.LayoutParams.MATCH_PARENT
            it.flags = it.flags and WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
        }

        window?.attributes = wlp
        getWindow()?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(ctx, R.color.colorBgDialog)))
        this.window?.attributes = wlp
        setCancelable(false)
    }

}