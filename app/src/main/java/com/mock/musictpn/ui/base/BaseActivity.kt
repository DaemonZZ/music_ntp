package com.mock.musictpn.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mock.musictpn.utils.UIHelper
import com.mock.musictpn.views.LoadingDialog
import com.mock.musictpn.views.MessageDialog
import android.view.WindowManager

import android.os.Build




abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    protected lateinit var mBinding: DB
    protected abstract val mViewModel: VM
    private lateinit var errorDialog: MessageDialog

    private lateinit var mLoadingDialog: LoadingDialog

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutRes())
        mLoadingDialog = LoadingDialog(this)
        errorDialog = MessageDialog(this)
        UIHelper.setupUI(mBinding.root,this)
        setupViews()
        setupListeners()
        setupObservers()
    }

    open fun showError(message: String) {
        errorDialog.message = message
        if (!errorDialog.isShowing) {
            errorDialog.show()
        }
    }

    open fun showLoading(isShow: Boolean) {
        if (isShow) {
            mLoadingDialog.show()
        } else {
            if (mLoadingDialog.isShowing) {
                mLoadingDialog.dismiss()
            }
        }
    }

    abstract fun setupViews()

    abstract fun setupListeners()

    abstract fun setupObservers()


}