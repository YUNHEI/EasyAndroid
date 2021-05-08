package com.chen.app.ui.one

import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.databinding.FragmentOneBinding
import com.chen.app.ui.simple.toolbar.SimpleFirstFragment
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.listenClick

@Launch
class OneFragment :BaseSimpleFragment(){

    override val binding by doBinding(FragmentOneBinding::inflate)

    override fun initAndObserve() {

        listenClick(binding.oneContainer) {
            when(it){
                binding.oneContainer ->{
                    parentFragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.anim_x_100_to_0, 0, 0, R.animator.anim_x_0_to_100)
                        .add(R.id.one_container ,SimpleFirstFragment())
                        .addToBackStack(null)
                        .commit()
                }
                else ->{
                }
            }
        }

        binding.oneContainer.setOnLongClickListener {
            parentFragmentManager.popBackStack()
            true
        }

    }



}