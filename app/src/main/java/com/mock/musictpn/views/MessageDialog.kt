package com.mock.musictpn.views

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.mock.musictpn.R
import com.mock.musictpn.databinding.DialogMessageBinding

class MessageDialog(ctx: Context) : Dialog(ctx, R.style.Theme_AppCompat_Dialog) {
    private var mBinding: DialogMessageBinding? = null

    var message: String = ""
        set(value) {
            field = value
            mBinding?.tvMessage?.text = message
        }

    override fun onStart() {
        super.onStart()
        val window = this.window
        val wlp = window?.attributes
        wlp?.let {
            it.gravity = Gravity.CENTER
            it.width = WindowManager.LayoutParams.MATCH_PARENT
            it.height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 90)
        window?.attributes = wlp
        getWindow()?.setBackgroundDrawable(inset)
        this.window?.attributes = wlp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogMessageBinding.inflate(LayoutInflater.from(context))
        setContentView(mBinding!!.root)
        mBinding!!.tvMessage.text = message
        Log.d("NganHV", "onCreate: $message")
        mBinding!!.tvOk.setOnClickListener {
            dismiss()
        }
        setCancelable(false)
    }

}