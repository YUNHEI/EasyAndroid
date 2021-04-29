package com.chen.baseextend.widget.image_view

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.databinding.FragmentImageViewerBinding
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.util.WindowsUtil
import java.util.*

/**
 *
 */
class ImageViewerFragment : BaseSimpleFragment() {

    override val binding by doBinding(FragmentImageViewerBinding::inflate)

    private var uris: MutableList<String>? = null

    override fun initAndObserve() {

        WindowsUtil.setDarkTheme(requireActivity(), true)

        val intent = requireActivity().intent

        uris = intent.getStringArrayListExtra("urls")

        if (uris == null) {
            val uri = intent.getStringExtra("url")

            if (uri != null) {
                uris = ArrayList()
                uris!!.add(uri)
            }

        }

        var position = intent.getIntExtra("position", 0)

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
                    Index.text = (position + 1).toString() + "/" + uris!!.size
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })

            Pager.currentItem = position

            Index.text = (++position).toString() + "/" + uris!!.size

            Save.setOnClickListener { mAdapter.fragment.save() }
        }
    }

    internal inner class ImagePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        lateinit var fragment: ImageItemFragment


        override fun setPrimaryItem(container: ViewGroup, position: Int, any: Any) {
            fragment = any as ImageItemFragment
            super.setPrimaryItem(container, position, any)
        }

        override fun getItem(position: Int): Fragment {
            return ImageItemFragment.newInstance(uris!![position])
        }

        override fun getCount(): Int {
            return uris!!.size
        }
    }
}
