package com.chen.app.navigationtest.simple

import android.os.Bundle
import androidx.navigation.Navigation
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.listenClick
import kotlinx.android.synthetic.main.fragment_simple_second.*

/**
 * @author CE Chen
 */
class SecondFragment : BaseSimpleFragment() {

    companion object {
        private const val TAG = "SecondFragment"
    }

    override val contentLayoutId = R.layout.fragment_simple_second

    /*Bundle arguments = getArguments();
    String data = arguments.getString("KEY");
    Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();*/

    /*SecondFragmentArgs fragmentArgs = SecondFragmentArgs.fromBundle(getArguments());
    Log.v(TAG, "boolean data = " + fragmentArgs.getBooleanData());
    Log.v(TAG, "int data = " + fragmentArgs.getIntData());
    Log.v(TAG, "float data = " + fragmentArgs.getFloatData());
    Log.v(TAG, "string data = " + fragmentArgs.getStringData());*/

    override fun initAndObserve() {

        toolbar.run{
            center("SecondFragment")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        listenClick(btn_to_third_fragment) {
            when(it){
                btn_to_third_fragment ->{ //                Navigation.findNavController(getView()).navigateUp();
                    Navigation.findNavController(view!!)
                        .navigate(R.id.action_nav_second_frag_to_nav_third_frag)
                }
                else ->{
                }
            }
        }
    }
}