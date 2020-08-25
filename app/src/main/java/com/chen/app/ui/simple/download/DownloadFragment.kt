package com.chen.app.ui.simple.download

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Launch
import com.alibaba.android.arouter.facade.enums.LaunchType
import com.alibaba.android.arouter.facade.enums.SwipeType
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.repos.DownloadModel
import com.chen.baseextend.repos.DownloadRepos
import com.chen.basemodule.constant.LiveBusKey
import com.chen.basemodule.event_bus.BaseProgressEvent
import com.chen.basemodule.extend.listenClick
import com.chen.basemodule.extend.toast
import com.chen.basemodule.extend.toastDebug
import com.chen.basemodule.extend.toastSuc
import com.chen.basemodule.util.FileUtil
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.fragment_attachmen_download.*

@Launch(launchType = LaunchType.COVER, swipeType = SwipeType.DISABLE)
class DownloadFragment : BaseSimpleFragment() {

    override val viewModel by lazy {
        ViewModelProvider(activity!!).get(DownloadModel::class.java).apply { owner = activity!! }
    }

    override val contentLayoutId = R.layout.fragment_attachmen_download

    private val url by lazy { "https://r1---sn-bvn0o-tpil.gvt1.com/edgedl/android/studio/install/4.0.1.0/android-studio-ide-193.6626763-mac.dmg?cms_redirect=yes&mh=OW&mip=202.104.136.68&mm=28&mn=sn-bvn0o-tpil&ms=nvh&mt=1597730168&mv=m&mvi=1&pl=20&shardbypass=yes" }
//    val url by lazy { "http://fintechcdn.mbcloud.com/sns/2020/08/55e6bc7b2b704dd487316234f40636fc.apk" }

    val fileName by lazy { url.substringAfterLast("/") }

    private val progressOb by lazy { LiveEventBus.get(LiveBusKey.EVENT_PROGRESS, BaseProgressEvent::class.java)!! }

    override fun initAndObserve() {

        toolbar.run {
            center("下载")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        progressOb.observe(this, Observer {
            if (it.target.firstOrNull() == fileName) {
                it.obj.run {
                    _progress.progressPercent = currentSize.times(100f).div(totalSize)
                    _size.text = "${FileUtil.getFileSize(currentSize)}/${FileUtil.getFileSize(totalSize)}"
                }
            }
        })

        listenClick(_download) {
            when (it) {
                _download -> {

                    val path = DownloadRepos.getFileByUrl(url)
                    if (!path.isNullOrEmpty()) {
                        "已下载".toastSuc()
                        return@listenClick
                    }

                    _download.visibility = View.GONE
                    _progress.visibility = View.VISIBLE

                    viewModel.run {
                        requestData(
                                { downloadRepos.downloadFile(url) },
                                {
                                    it.data?.run {

                                    }
                                    it.toast()
                                },
                                preHandle = {
                                    _download.visibility = View.VISIBLE
                                    _progress.visibility = View.GONE
                                }
                        )
                    }
                }
                else -> {
                }
            }
        }
    }

}