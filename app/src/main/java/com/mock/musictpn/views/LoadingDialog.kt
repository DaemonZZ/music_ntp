package com.mock.musictpn.views

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.mock.musictpn.R

class LoadingDialog(private val ctx: Context) :
    Dialog(ctx, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_loading)
        val window = this.window
        val wlp = window?.attributes
        wlp?.let {
            it.gravity = Gravity.CENTER
            it.width = WindowManager.LayoutParams.MATCH_PARENT
            it.height = WindowManager.LayoutParams.MATCH_PARENT
            it.flags = it.flags and WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
        }

        window?.attributes = wlp
        window?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(ctx, R.color.colorBgDialog)))
        setCancelable(false)
    }
}