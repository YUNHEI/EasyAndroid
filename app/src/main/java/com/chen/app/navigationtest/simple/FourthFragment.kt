package com.chen.app.navigationtest.simple

import androidx.navigation.Navigation
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.listenClick
import kotlinx.android.synthetic.main.fragment_simple_fourth.*

/**
 * @author CE Chen
 */
class FourthFragment : BaseSimpleFragment() {

    override val contentLayoutId = R.layout.fragment_simple_fourth

    override fun initAndObserve() {

        toolbar.run{
            center("FourthFragment")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        listenClick(btn_back_first_fragment) {
            when(it){
                btn_back_first_fragment ->{
                    Navigation.findNavController(view!!)
                        .popBackStack(R.id.nav_simple_first_frag, false)
                }
                else ->{
                }
            }
        }
    }
}