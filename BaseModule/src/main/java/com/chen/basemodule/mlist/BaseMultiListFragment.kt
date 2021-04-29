package com.chen.basemodule.mlist

import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.view.animation.*
import androidx.annotation.IntDef
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewbinding.ViewBinding
import com.chen.basemodule.R
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.basem.BaseDataFragment
import com.chen.basemodule.databinding.BaseMlistFragmentBinding
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.dp2px
import com.chen.basemodule.extend.toast
import com.chen.basemodule.mlist.animator.AniListener
import com.chen.basemodule.mlist.animator.ReboundAniUpdateListener
import com.chen.basemodule.mlist.itemdecoration.Divider
import com.chen.basemodule.mlist.itemdecoration.SimpleItemDecoration
import com.chen.basemodule.mlist.layoutmanager.AutoGridLayoutManager
import com.chen.basemodule.network.base.BaseResponse
import com.chen.basemodule.widget.CustomRefreshFooter
import com.chen.basemodule.widget.smartrefresh.layout.SmartRefreshLayout
import com.chen.basemodule.widget.smartrefresh.layout.api.RefreshLayout
import com.chen.basemodule.widget.smartrefresh.layout.footer.ClassicsFooter
import com.chen.basemodule.widget.smartrefresh.layout.header.ClassicsHeader
import com.chen.basemodule.widget.smartrefresh.layout.header.SimpleHeader
import com.chen.basemodule.widget.smartrefresh.layout.header.SimpleHeader.REFRESH_HEADER_FINISH
import com.chen.basemodule.widget.smartrefresh.layout.internal.ProgressDrawable
import com.chen.basemodule.widget.smartrefresh.layout.listener.OnLoadMoreListener
import com.chen.basemodule.widget.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.base_mlist_fragment.*
import kotlinx.android.synthetic.main.layout_refresh_tip.*
import kotlin.reflect.KClass

/**
 *
 * 多种类型布局列表
 *
 * @param <V>
 * @param <K>
</K></V> */
abstract class BaseMultiListFragment<V : RootBean> : BaseDataFragment(), OnRefreshListener,
    OnLoadMoreListener {

    override val binding by doBinding(BaseMlistFragmentBinding::inflate)

    open val mAdapter by lazy {
        BaseMultiAdapter<V>(requireContext()).apply {
            pageSize = PAGE_SIZE
        }
    }

    protected var PAGE_SIZE = 20

    protected open val mPadding by lazy { dp2px(10) }

    var showEmpty = true

    var showCompleteFooter = true

    var autoLoadData = true

    /**
     * fragment 懒加载，fragment显示的时候触发loadData
     */
    open var lazyLoad = true

    /**
     * { bundle 参数设置在 @link #customerDelegateWithParams() 方法中进行}
     */
    val delegateBundle by lazy { Bundle() }

    val mRecycler: RecyclerView get() = _recycler

    val mRefresh: SmartRefreshLayout get() = _refresh

    open val lManager: RecyclerView.LayoutManager by lazy { LinearLayoutManager(context) }

    @IntDef(INIT, NULL, COMPLETE, ERROR, LOADING_MORE, LOADING_MORE_ERROR)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class LoadingStatus


    open var columns = 1
        set(value) {
            field = value
            delegateBundle.putInt("columns", value)
            _recycler.run {
//                removeItemDecoration(itemDecoration)
                if (value <= 1) {
                    layoutManager = lManager
                } else {
                    layoutManager = AutoGridLayoutManager(context, value).apply {
                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                return if (mAdapter.isItem(position)) 1 else value
                            }
                        }
                    }
//                    addItemDecoration(itemDecoration)
                }
            }
        }

    open var divider: Divider? = null
        set(value) {
            field = value
            _recycler.run {
                addItemDecoration(SimpleItemDecoration(value ?: Divider()))
            }
        }

    /**######################抽象方法区 复写父类中的抽象方法，保证idea自动补全的顺序 ######################*/
    /**
     * 返回liveData对象自动进入通用处理流程，返回null 进入自定义流程
     *
     * @param refresh
     * @param lastItem refresh 为true时  lastItem 为null
     * @return
     */
//    abstract override fun initAndObserve()

    protected abstract fun customerDelegateWithParams(): MutableList<KClass<out BaseItemViewDelegate<V>>>?

    abstract fun initClickListener()

    protected abstract fun loadData(
        refresh: Boolean,
        lastItem: V?
    ): LiveData<BaseResponse<MutableList<V>>>?

    /**######################抽象方法区 复写父类中的抽象方法，保证idea自动补全的顺序 ######################*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val classicsFooter = CustomRefreshFooter(context)
            .setFinishDuration(100)
            .setDrawableProgressSize(10f)
            .setProgressDrawable(ProgressDrawable())

        _refresh?.run {
            isEnableLoadMore = true
            setOnRefreshListener(this@BaseMultiListFragment)
            setOnLoadMoreListener(this@BaseMultiListFragment)
            setReboundDuration(550)
            setEnableAutoLoadMore(true)
            setRefreshHeader(SimpleHeader(context))
            setHeaderHeight(36F)
            setRefreshFooter(classicsFooter)
        }

        initClickListener()

        initDelegate(delegateBundle)

        columns = 1

        enableRefreshTip(false)

        super.onViewCreated(view, savedInstanceState)

        _recycler.run {
            adapter = mAdapter
            itemAnimator?.addDuration = 0
            itemAnimator?.changeDuration = 0
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        if (lazyLoad) setLoadingStatus(INIT)
        if (autoLoadData && !lazyLoad) startLoadData()
    }

    override fun onResume() {
        super.onResume()
        if (autoLoadData && lazyLoad) {
            lazyLoad = false
            startLoadData()
        }
    }

    protected open fun initDelegate(bundle: Bundle) {

        mAdapter.addItemViewDelegate(
            *customerDelegateWithParams().orEmpty().toTypedArray(),
            delegateBundle = delegateBundle
        )
    }

    /**
     * 需要在onViewCreated内调用,在initAdapter() 调用之前
     */
    protected fun listenItemClick(
        vararg ids: Int,
        onClick: ((itemViewDelegate: BaseItemViewDelegate<V>, itemView: View, data: V?, id: Int, position: Int, dataPosition: Int) -> Unit)?
    ) {
        mAdapter.addClickListener(*ids, onClick = onClick)
    }

    /**
     * 需要在onViewCreated内调用,在initAdapter() 调用之前
     */
    protected fun listenItemLongClick(
        onLongClick: ((itemViewDelegate: BaseItemViewDelegate<V>, viewHolder: BaseItemViewHolder, data: V?, id: Int, position: Int, dataPosition: Int) -> Unit)?
    ) {
        mAdapter.setLongClickListener(onLongClick)
    }

    protected fun enableRefreshTip(showAnimate: Boolean = false) {

        if (showAnimate) {
            _refresh?.addReboundAnimatorListener(ReboundAniUpdateListener(view))
            _refresh?.addAnimatorListener(AniListener(view))
        } else {
            _refresh?.addReboundAnimatorListener { }
            _refresh?.addAnimatorListener(object : AnimatorListenerAdapter() {})
        }
    }

    override fun startLoadData(muteLoadData: Boolean?) {
        showLoadingCover(if (this.muteLoadData || muteLoadData == true) HIDE else LOADING)
        onRefresh(null)
    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {
        loadData(true, null)?.run {
            handleLiveData(this, true)
        }
    }

    fun autoRefresh() {
        _recycler.scrollToPosition(0)
        _refresh?.autoRefresh(1, 200, 1f)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        loadData(false, mAdapter.lastItem)?.run {
            handleLiveData(this, false)
        }
    }

    fun updateVisibleItem(payload: Any) {
        var firstVisibleIndex = 0
        var lastVisibleIndex = 0
        (_recycler.layoutManager as LinearLayoutManager).run {
            firstVisibleIndex = findFirstVisibleItemPosition()
            lastVisibleIndex = findLastVisibleItemPosition()

        }

        mAdapter.notifyItemRangeChanged(
            firstVisibleIndex,
            lastVisibleIndex - firstVisibleIndex,
            payload
        )
    }

    protected fun handleLiveData(
        liveData: LiveData<BaseResponse<MutableList<V>>>,
        refresh: Boolean
    ) {
        liveData.observe(viewLifecycleOwner, Observer { handleResponse(it!!, refresh) })
    }

    protected fun handleResponse(s: BaseResponse<MutableList<V>>, refresh: Boolean) {
        if (s.suc()) {
            handleData(s.data, refresh)
        } else if (s.localSuc()) {
            setLoadingStatus(ERROR_TIP)
            mAdapter.replaceItems(s.data)
        } else if (s.status in 300..399) {
            s.toast()
        } else if (s.status >= 400) {
            if (refresh) {
                if (s.status == 410) {
                    setLoadingStatus(ERROR_TIP)
                } else {
                    setLoadingStatus(ERROR)
                }
            } else {
                setLoadingStatus(LOADING_MORE_ERROR)
                s.toast()
            }
        }
    }

    protected fun handleData(pData: List<V>?, refresh: Boolean) {
        val lastItem = mAdapter.lastItem
        mAdapter.showCompleteFooter(!refresh && showCompleteFooter)
        if (!refresh && lastItem != null) {
            if (pData == null || pData.size < PAGE_SIZE) {
                setLoadingStatus(COMPLETE)
            } else {
                setLoadingStatus(LOADING_MORE)
            }
            mAdapter.addItems(pData)
        } else {
            if (refresh) {
                if (pData.isNullOrEmpty()) {
                    REFRESH_HEADER_FINISH = "暂无更新"
                } else {
                    if (null == mAdapter.getDataByPosition(0) || pData[0] != mAdapter.getDataByPosition(
                            0
                        )
                    ) {
                        REFRESH_HEADER_FINISH = "已更新"
                    } else {
                        REFRESH_HEADER_FINISH = "已经是最新"
                    }
                }
            }

            if (pData.isNullOrEmpty()) {
                if (mAdapter.headerViewDelegates.isNotEmpty() || mAdapter.footerViewDelegates.isNotEmpty()) {
                    setLoadingStatus(COMPLETE)
                } else {
                    setLoadingStatus(NULL)
                }
                _refresh_tip?.text = "暂无更新"
            } else {
                if (pData.size < PAGE_SIZE) {
                    setLoadingStatus(COMPLETE)
                } else {
                    setLoadingStatus(LOADING_MORE)
                }
                _refresh_tip?.text = String.format("为您推送%s条更新", pData.size)
            }
            mAdapter.replaceItems(pData)
        }
    }

    protected fun setLoadingStatus(@LoadingStatus status: Int) {
        showLoadingCover(
            when {
                status == INIT && !muteLoadData -> LOADING
                status == NULL && showEmpty -> BLANK
                status == ERROR -> ERROR
                else -> HIDE
            }
        )

        mAdapter.showFooter = status == COMPLETE

        _refresh?.run {
            if (status == ERROR_TIP) {
                finishRefresh(false)
            } else {
                finishRefresh(1)
            }

            isEnableLoadMore = status in setOf(LOADING_MORE_ERROR, LOADING_MORE)

            if (status in setOf(ERROR, ERROR_TIP)) {
                finishLoadMore(false)
            } else if (status == LOADING_MORE) {
                finishLoadMore(100)
            } else {
                finishLoadMore()
            }
        }

    }

    protected fun disableLoadPageData() {
        PAGE_SIZE = Int.MAX_VALUE
        _refresh?.isEnableLoadMore = false
    }

    companion object {

        const val INIT = 0

        const val NULL = 1

        const val COMPLETE = 2

        const val ERROR = 3

        const val LOADING_MORE = 4

        const val LOADING_MORE_ERROR = 5

        const val ERROR_TIP = 6

        init {
            ClassicsHeader.REFRESH_HEADER_UPDATE = "最后更新: MM-dd HH:mm"
            ClassicsHeader.REFRESH_HEADER_RELEASE = "松开立即刷新"
            ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新"

            ClassicsFooter.REFRESH_FOOTER_FINISH = ""
            ClassicsFooter.REFRESH_FOOTER_LOADING = "加载中"
        }
    }

}
