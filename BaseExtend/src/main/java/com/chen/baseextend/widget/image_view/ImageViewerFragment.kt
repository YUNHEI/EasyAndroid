package com.chen.baseextend.widget.image_view

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.chen.baseextend.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.util.WindowsUtil
import kotlinx.android.synthetic.main.fragment_image_viewer.*
import java.util.*

/**
 *
 */
class ImageViewerFragment : BaseSimpleFragment() {

    override val contentLayoutId = R.layout.fragment_image_viewer

    private var uris: MutableList<String>? = null

    override fun initAndObserve() {

        WindowsUtil.setDarkTheme(activity!!, true)

        val intent = activity!!.intent

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

        pager.adapter = mAdapter

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                index.text = (position + 1).toString() + "/" + uris!!.size
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        pager.currentItem = position

        index.text = (++position).toString() + "/" + uris!!.size

        save.setOnClickListener { mAdapter.fragment.save() }
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
