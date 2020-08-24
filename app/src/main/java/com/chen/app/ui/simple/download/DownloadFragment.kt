package com.chen.app.ui.simple.download

import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Launch
import com.alibaba.android.arouter.facade.enums.LaunchType
import com.alibaba.android.arouter.facade.enums.SwipeType
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.repos.DownloadModel
import com.chen.basemodule.extend.listenClick
import com.chen.basemodule.extend.toast
import com.chen.basemodule.extend.toastDebug
import com.chen.basemodule.extend.toastSuc
import kotlinx.android.synthetic.main.fragment_attachmen_download.*

@Launch(launchType = LaunchType.COVER, swipeType = SwipeType.DISABLE)
class DownloadFragment : BaseSimpleFragment() {

    override val viewModel by lazy {
        ViewModelProvider(activity!!).get(DownloadModel::class.java).apply { owner = activity!! }
    }

    override val contentLayoutId = R.layout.fragment_attachmen_download

    override fun initAndObserve() {

        toolbar.run {
            center("下载")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        listenClick(_download) {
            when (it) {
                _download -> {
                    viewModel.run {
                        requestData(
                            { downloadRepos.downloadFile("http://fintechcdn.mbcloud.com/sns/2020/08/55e6bc7b2b704dd487316234f40636fc.apk") },
//                            { downloadRepos.downloadFile("https://r1---sn-bvn0o-tpil.gvt1.com/edgedl/android/studio/install/4.0.1.0/android-studio-ide-193.6626763-mac.dmg?cms_redirect=yes&mh=OW&mip=202.104.136.68&mm=28&mn=sn-bvn0o-tpil&ms=nvh&mt=1597730168&mv=m&mvi=1&pl=20&shardbypass=yes") },
                            {
                                it.data?.run {

                                }
                                    it.toast()
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