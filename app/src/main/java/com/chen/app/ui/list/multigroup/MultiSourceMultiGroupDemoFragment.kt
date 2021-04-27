package com.chen.app.ui.list.multigroup

import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.app.databinding.LayoutSmartStrategyHeaderBinding
import com.chen.app.ui.delegate.BannerHeaderDelegate
import com.chen.app.ui.delegate.MenuHeaderDelegate
import com.chen.app.ui.list.multigroup.delegate.*
import com.chen.baseextend.base.MultiSourceGroupBean
import com.chen.baseextend.base.fragment.MultiGroupMultiSourceListFragment
import com.chen.baseextend.bean.AdvertBean
import com.chen.baseextend.bean.menu.HomeMenuBean
import com.chen.baseextend.bean.news.NewsBean
import com.chen.baseextend.ui.WebActivity
import com.chen.basemodule.basem.BaseBean
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.mlist.BaseItemViewDelegate
import com.chen.basemodule.mlist.BaseItemViewHolder
import com.chen.basemodule.mlist.bean.DataWrapBean
import com.chen.basemodule.mlist.bean.GroupWrapBean
import com.chen.basemodule.mlist.bean.ItemWrapBean
import com.chen.basemodule.network.base.BaseResponse
import kotlinx.android.synthetic.main.layout_smart_strategy_header.view.*
import kotlinx.android.synthetic.main.layout_strategy_banner_header.view.*
import kotlinx.android.synthetic.main.layout_header_text_marquee.view.*
import kotlin.reflect.KClass

/**
 *  Created by 86152 on 2021-01-19
 **/
@Launch
class MultiSourceMultiGroupDemoFragment : MultiGroupMultiSourceListFragment() {

    //轮播图
    private val bannerDelegate by lazy { BannerHeaderDelegate(requireContext(), "400:266") }

    //菜单列表
    private val menuDelegate by lazy { MenuHeaderDelegate(requireContext()) }

    //跑马灯
    private val textBannerDelegate by lazy { CompetitionMarqueeHeaderDelegate(requireContext()) }

//    private val footerDelegate by lazy { CompetitionFooterDelegate(context!!) }

    private val menuList = mutableListOf(
        HomeMenuBean("", "菜单一", icon = R.mipmap.ic_competition_menu_rank),
        HomeMenuBean("", "菜单二", icon = R.mipmap.ic_competition_reallocation),
        HomeMenuBean("", "菜单三", icon = R.mipmap.ic_competition_trader),
        HomeMenuBean("", "菜单四", icon = R.mipmap.ic_competition_winer)
    )


    override fun initAndObserve() {

        toolbar.run {
            center("多数据源多种样式")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        //静默加载，没有loading页
        muteLoadData = true
        mRefresh.isEnableRefresh = true

        mAdapter.addHeaderViewDelegate(bannerDelegate)
        mAdapter.addHeaderViewDelegate(menuDelegate)
        mAdapter.addHeaderViewDelegate(textBannerDelegate)

        menuDelegate.gridAdapter.replaceItems(menuList)
    }


    override fun customerDelegateWithParams(): MutableList<KClass<out BaseItemViewDelegate<DataWrapBean>>>? {
        return mutableListOf(
            PoetryDelegate::class,
            JokeDelegate::class,
            OneImageDelegate::class,
            MoreImageDelegate::class,
            VideoDelegate::class
        )
    }

    override fun initClickListener() {

        listenItemClick { _, _, data, id, p, _ ->
            when (id) {
                else -> {
                    (data as? ItemWrapBean<*>)?.itemData?.run {
                        when (this) {
                            //调仓动态跳转组合详情
                            is NewsBean -> WebActivity.toWebView(requireContext(), url)
                            else -> {

                            }
                        }
                    }
                }
            }
        }
    }

    override fun loadData(): LiveData<BaseResponse<MutableList<MultiSourceGroupBean>>>? {
        return viewModel.run {
            requestData(
                { newsService.listBanner() },
                {
                    bannerDelegate.bannerData =
                        mutableListOf(AdvertBean(image = "http://ww1.sinaimg.cn/large/7a8aed7bgw1evdga4dimoj20qo0hsmzf.jpg"))
                    //从网络获取 多图banner
//                        it.data?.map { AdvertBean(image = it.img) }.orEmpty().take(1).toMutableList()
                })

            requestData(
                { newsRepos.listTextBanner() },
                {
                    textBannerDelegate.bannerData = it.data.orEmpty().toMutableList()
                    mAdapter.notifyItemChanged(2)
                }
            )

            requestData(
                {
                    val source = mutableListOf<MultiSourceGroupBean>()

                    //唐诗
                    val jokeRes = newsService.getJoke()
                    source.add(MultiSourceGroupBean("笑话", jokeRes.data.orEmpty().toMutableList()))

                    //新闻
                    val newsRes = newsService.listNews()
                    source.add(MultiSourceGroupBean("新闻", newsRes.data.orEmpty().toMutableList()))

                    return@requestData BaseResponse(source, 200, "成功")
                }
            )

        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            bannerDelegate.viewHolder?.itemView?._banner?.stopAutoPlay()
            textBannerDelegate.viewHolder?.itemView?._text_banner?.stopViewAnimator()
        } else {
            bannerDelegate.viewHolder?.itemView?._banner?.start()
            textBannerDelegate.viewHolder?.itemView?._text_banner?.startViewAnimator()
        }
    }

//    override val groupLayoutId = R.layout.layout_smart_strategy_header
    override val groupBinding by doBinding(LayoutSmartStrategyHeaderBinding::inflate)

    override fun bindGroupData(
        viewHolder: BaseItemViewHolder,
        groupWrapData: GroupWrapBean<MultiSourceGroupBean, BaseBean>?,
        position: Int,
        realP: Int
    ) {
        viewHolder.itemView.run {
            groupWrapData?.groupData?.run {
                _title.text = title
                _title_des.text = ""
                _divider.isVisible = true
            }
        }
    }
}