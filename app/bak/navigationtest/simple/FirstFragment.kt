package com.chen.app.navigationtest.simple

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.navigation.Navigation
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.listenClick
import com.chen.basemodule.extend.toastSuc
import kotlinx.android.synthetic.main.fragment_simple_first.*

/**
 * @author CE Chen
 */
class FirstFragment : BaseSimpleFragment() {

    override val contentLayoutId = R.layout.fragment_simple_first

    override fun initAndObserve() {

        toolbar.run{
            center("FirstFragment")
            left(R.mipmap.ic_back) { activity?.finish() }
        }
//        val fragmentArgs: FirstFragmentArgs = FirstFragmentArgs.fromBundle(arguments!!)
//        fragmentArgs.data.toastSuc()

        listenClick(btn_to_second_fragment) {
            when(it){
                btn_to_second_fragment ->{

                    //NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_nav_first_frag_to_nav_second_frag);
                    //Navigation.findNavController(getActivity(), R.id.btn_to_second_fragment).navigate(R.id.action_nav_first_frag_to_nav_second_frag);
                    //Navigation.findNavController(getView()).navigate(R.id.action_nav_first_frag_to_nav_second_frag);

                    //Bundle bundle = new Bundle();
                    //bundle.putString("KEY", "我是从 First 过来的");
                    //Navigation.findNavController(getView()).navigate(R.id.action_nav_first_frag_to_nav_second_frag, bundle);
                    //Navigation.findNavController(getView()).navigate(R.id.action_nav_first_frag_to_nav_second_frag, bundle);

                    //Navigation.findNavController(getView()).popBackStack();
                    //Navigation.findNavController(getView()).navigateUp();

//                Navigation.findNavController(getView()).navigate(R.id.action_nav_first_frag_to_nav_second_frag);

                    /*SecondFragmentArgs fragmentArgs = new SecondFragmentArgs
                            .Builder(true,
                            1,
                            1.1f,
                            "我是通过 argument 过来的")
                            .build();
                    Navigation.findNavController(getView())
                            .navigate(R.id.action_nav_first_frag_to_nav_second_frag, fragmentArgs.toBundle());*/
                    Navigation.findNavController(view!!)
                        .navigate(R.id.action_nav_first_frag_to_nav_second_frag)
                }
                else ->{
                }
            }
        }
    }

}