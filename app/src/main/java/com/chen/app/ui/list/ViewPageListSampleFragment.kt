package com.chen.app.ui.list

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.VideoView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Launch
import com.alibaba.android.arouter.facade.enums.LaunchType
import com.chen.app.R
import com.chen.baseextend.base.fragment.SingleListFragment
import com.chen.baseextend.bean.WeatherBean
import com.chen.basemodule.mlist.BaseItemViewHolder
import com.chen.basemodule.mlist.layoutmanager.OnViewPagerListener
import com.chen.basemodule.mlist.layoutmanager.ViewPagerLayoutManager
import com.chen.basemodule.network.base.BaseRequest
import com.chen.basemodule.network.base.BaseResponse
import com.chen.basemodule.util.WindowsUtil
import kotlinx.android.synthetic.main.item_video.view.*

@Launch(launchType = LaunchType.FULLSCREEN)
class ViewPageListSampleFragment : SingleListFragment<WeatherBean>() {

    private val videos = arrayOf(R.raw.video_1, R.raw.video_2)

    override fun initAndObserve() {
        //禁用分页
        disableLoadPageData()

        mRefresh.isEnableRefresh = false

        WindowsUtil.setDarkTheme(activity!!, true)


        lManager.setOnViewPagerListener(object : OnViewPagerListener {
            override fun onInitComplete() {
                playVideo(0)
            }

            override fun onPageRelease(isNext: Boolean, position: Int) {
                releaseVideo(if (isNext) 0 else 1)
            }

            override fun onPageSelected(position: Int, isBottom: Boolean) {
                playVideo(0)
            }
        })
    }

    override val lManager: ViewPagerLayoutManager by lazy { ViewPagerLayoutManager(context!!, RecyclerView.VERTICAL) }

    //item 点击事件
    override fun initClickListener() {
        listenItemClick { _, _, data, _, _, _ ->
            "点击了 - ${data!!.week}"
        }
    }

    //网络请求
    override fun loadData(refresh: Boolean, lastItem: WeatherBean?): LiveData<BaseResponse<MutableList<WeatherBean>>>? {
        return viewModel.run {
            requestData(
                    { weatherService.getWeekWeather(BaseRequest()).apply { status = 200 } }
            )
        }
    }

    //item 样式
    override val itemLayoutId = R.layout.item_video

    //item 数据绑定
    override fun bindItemData(viewHolder: BaseItemViewHolder, data: WeatherBean, position: Int, realP: Int) {
        viewHolder.itemView.run {
            _video.setVideoURI(Uri.parse("android.resource://${context!!.packageName}/${videos[position % 2]}"))
        }
    }


    private fun playVideo(position: Int) {
        val itemView: View = mRecycler.getChildAt(0)
        val mediaPlayer = arrayOfNulls<MediaPlayer>(1)
        itemView._video.start()
        itemView._video.setOnInfoListener { mp, what, extra ->
            mediaPlayer[0] = mp
            mp.isLooping = true
            false
        }
        itemView._video.setOnPreparedListener { }
    }

    private fun releaseVideo(index: Int) {
        val itemView: View = mRecycler.getChildAt(index)
        itemView._video.stopPlayback()
    }
}