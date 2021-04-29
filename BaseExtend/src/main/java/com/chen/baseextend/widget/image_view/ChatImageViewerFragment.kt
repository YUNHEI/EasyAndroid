package com.chen.baseextend.widget.image_view

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.jpush.im.android.api.model.Message
import com.alibaba.android.arouter.facade.annotation.Launch
import com.alibaba.android.arouter.facade.enums.LaunchType
import com.alibaba.android.arouter.facade.enums.SwipeType
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.databinding.FragmentImageViewerBinding
import com.chen.basemodule.extend.argString
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.util.WindowsUtil

/**
 *
 */
@Launch(launchType = LaunchType.FULLSCREEN, swipeType = SwipeType.DISABLE)
class ChatImageViewerFragment : BaseSimpleFragment() {

    override val binding by doBinding(FragmentImageViewerBinding::inflate)

    val messages: MutableList<Message> by lazy {
        Message.fromJsonToCollection(argString("messages")).toMutableList()
    }

    val message: Message by lazy { Message.fromJson(argString("message")) }

    override fun initAndObserve() {

        WindowsUtil.setDarkTheme(requireActivity(), true)

        var position = 0

        for (i in 0 until messages.size) {
            if (messages[i].id == message.id) {
                position = i
                break
            }
        }

        val mAdapter = ImagePagerAdapter(childFragmentManager)

        binding.run {

            Pager.adapter = mAdapter

            Pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    Index.text = "${(position + 1)}/${messages.size}"
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })

            Pager.currentItem = position

            Index.text = "${(position + 1)}/${messages.size}"

            Save.setOnClickListener { mAdapter.fragment.save() }
        }
    }

    internal inner class ImagePagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        lateinit var fragment: ChatImageItemFragment


        override fun setPrimaryItem(container: ViewGroup, position: Int, any: Any) {
            fragment = any as ChatImageItemFragment
            super.setPrimaryItem(container, position, any)
        }

        override fun getItem(position: Int): Fragment {
            return ChatImageItemFragment.newInstance(messages[position])
        }

        override fun getCount(): Int {
            return messages.size
        }
    }
}
