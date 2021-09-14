package com.mock.musictpn.ui.activity

import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.mock.musictpn.R
import com.mock.musictpn.databinding.ActivityMain2Binding
import com.mock.musictpn.ui.base.BaseActivity
import com.mock.musictpn.ui.fragment.player.PlayerFragment
import com.mock.musictpn.ui.fragment.player.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity2 : BaseActivity<ActivityMain2Binding,PlayerViewModel>() {

    override val mViewModel: PlayerViewModel by viewModels()


    override fun getLayoutRes(): Int {
        return R.layout.activity_main2
    }

    override fun setupViews() {

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.frame,PlayerFragment())
        }

    }

    override fun setupListeners() {
    }

    override fun setupObservers() {
    }
}