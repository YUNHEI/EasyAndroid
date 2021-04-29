package com.chen.app.navigationtest

import androidx.navigation.Navigation
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.listenClick
import kotlinx.android.synthetic.main.fragment_main1.*

/**
 * @author CE Chen
 */
class MainFragment : BaseSimpleFragment() {

    override val contentLayoutId = R.layout.fragment_main1

    override fun initAndObserve() {

        listenClick(btn_simple, btn_bottom_navigation_view) {
            when (it) {
                btn_simple -> {
                    Navigation.findNavController(view!!)
                        .navigate(R.id.action_nav_main_to_nav_simple);
                }
                btn_bottom_navigation_view -> {
                    Navigation.findNavController(view!!)
                        .navigate(R.id.action_nav_main_to_nav_bottom_navigation_view);
                }
                else -> {
                }
            }
        }
    }
}