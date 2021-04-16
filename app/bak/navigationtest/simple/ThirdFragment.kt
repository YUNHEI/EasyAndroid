package com.chen.app.navigationtest.simple

import androidx.navigation.Navigation
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.listenClick
import kotlinx.android.synthetic.main.fragment_simple_third.*

/**
 * @author CE Chen
 */
class ThirdFragment : BaseSimpleFragment() {

    override val contentLayoutId = R.layout.fragment_simple_third

    override fun initAndObserve() {

        toolbar.run{
            center("ThirdFragment")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        listenClick(btn_to_fourth_fragment) {
            when(it){
                btn_to_fourth_fragment ->{
                    Navigation.findNavController(view!!)
                        .navigate(R.id.action_nav_third_frag_to_nav_fourth_frag)
                }
                else ->{
                }
            }
        }
    }
}