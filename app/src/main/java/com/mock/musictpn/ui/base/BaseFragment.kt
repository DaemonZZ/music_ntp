package com.mock.musictpn.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.mock.musictpn.views.LoadingDialog
import com.mock.musictpn.views.MessageDialog

abstract class BaseFragment<DB : ViewDataBinding, VM : ViewModel> : Fragment() {
    protected lateinit var mBinding: DB
    protected abstract val mViewModel: VM

    private lateinit var mLoadingDialog: LoadingDialog
    private lateinit var mContext: Context
    private lateinit var mMessageDialog: MessageDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoadingDialog = LoadingDialog(mContext)
        mMessageDialog = MessageDialog(mContext)
        setupViews()
        setupListeners()
        setupObservers()
    }

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    open fun showLoading(isShow: Boolean) {
        if (isShow) mLoadingDialog.show()
        else {
            if (mLoadingDialog.isShowing) {
                mLoadingDialog.dismiss()
            }
        }
    }

    open fun showError(message: String) {
        mMessageDialog.message = message
        if (!mMessageDialog.isShowing) {
            mMessageDialog.show()
        }
    }

    abstract fun setupViews()

    abstract fun setupListeners()

    abstract fun setupObservers()

}
