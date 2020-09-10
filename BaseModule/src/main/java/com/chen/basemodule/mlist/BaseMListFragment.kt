package com.chen.basemodule.mlist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Rect
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
import com.chen.basemodule.R
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.basem.BaseDataFragment
import com.chen.basemodule.extend.dp2px
import com.chen.basemodule.extend.toast
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

/**
 *
 * 多种布局列表
 * 父类的泛型定义必须放在第一位
 * @param <V>
 * @param <K>
</K></V> */
abstract class BaseMListFragment<V : RootBean> : BaseDataFragment(), OnRefreshListener, OnLoadMoreListener {

    override val contentLayoutId = R.layout.base_mlist_fragment

    val mAdapter by lazy { BaseMAdapter<V>(context!!).apply { pageSize = this@BaseMListFragment.PAGE_SIZE } }

    protected var PAGE_SIZE = 20

    protected open val mPadding by lazy { dp2px(10) }

    var showEmpty = true

    var showCompleteFooter = true

    var autoLoadData = true

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
                removeItemDecoration(itemDecoration)
                if (value <= 1) {
                    layoutManager = lManager
                } else {
                    layoutManager = GridLayoutManager(context, value).apply {
                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                return if (mAdapter.getItemViewType(position) in 20000 until 30000) 1 else value
                            }
                        }
                    }
                    addItemDecoration(itemDecoration)
                }
            }
        }

    protected open val itemDecoration: RecyclerView.ItemDecoration by lazy {
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)
                if (mAdapter.getItemViewType(position) in 20000 until 30000) {
                    val realP = position - mAdapter.headerViewDelegates.size
                    val row = realP.rem(columns)
                    outRect.run {
                        left = mPadding - row * mPadding / columns
                        right = row.inc() * mPadding / columns
                        bottom = mPadding
                        if (realP < columns) {
                            top = mPadding
                        }
                    }
                }
            }
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

    protected abstract fun customerDelegateWithParams(): MutableList<Class<out BaseItemViewDelegate<V>>>?

    abstract fun initClickListener()

    protected abstract fun loadData(refresh: Boolean, lastItem: V?): LiveData<BaseResponse<MutableList<V>>>?

    /**######################抽象方法区 复写父类中的抽象方法，保证idea自动补全的顺序 ######################*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val classicsFooter = CustomRefreshFooter(context)
                .setFinishDuration(100)
                .setDrawableProgressSize(10f)
                .setProgressDrawable(ProgressDrawable())

        _refresh?.run {

            isEnableRefresh = true
            isEnableLoadMore = true
            setOnRefreshListener(this@BaseMListFragment)
            setOnLoadMoreListener(this@BaseMListFragment)
            setReboundDuration(500)
            setEnableAutoLoadMore(true)
            setRefreshHeader(SimpleHeader(context))
            setHeaderHeight(36F)
            setRefreshFooter(classicsFooter)
        }

        initClickListener()

        initDelegate(delegateBundle)

        columns = 1

        super.onViewCreated(view, savedInstanceState)

        enableRefreshTip()

        _recycler.run {
            adapter = mAdapter
            itemAnimator?.addDuration = 0
            itemAnimator?.changeDuration = 0
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        if (autoLoadData) startLoadData()
    }

    protected open fun initDelegate(bundle: Bundle) {

        mAdapter.addItemViewDelegate(*customerDelegateWithParams().orEmpty().toTypedArray(), delegateBundle = delegateBundle)
    }

    /**
     * 需要在onViewCreated内调用,在initAdapter() 调用之前
     */
    protected fun listenItemClick(vararg ids: Int, onClick: ((itemViewDelegate: BaseItemViewDelegate<V>, itemView: View, data: V?, id: Int, position: Int, dataPosition: Int) -> Unit)?) {
        mAdapter.addClickListener(*ids, onClick = onClick)
    }

    /**
     * 需要在onViewCreated内调用,在initAdapter() 调用之前
     */
    protected fun listenItemLongClick(vararg ids: Int, onLongClick: ((itemViewDelegate: BaseItemViewDelegate<V>, itemView: View, data: V?, id: Int, position: Int, dataPosition: Int) -> Unit)?) {
        mAdapter.setLongClickListener(*ids, onLongClick = onLongClick)
    }

    protected fun enableRefreshTip() {

//        refresh.addReboundAnimatorListener(ReboundAniUpdateListener())
        _refresh?.addReboundAnimatorListener { }

//        refresh.addAnimatorListener(AniListener())
        _refresh?.addAnimatorListener(object : AnimatorListenerAdapter() {

        })
    }

    override fun startLoadData(muteLoadData: Boolean?) {
        showShimmerCover(!(this.muteLoadData || (muteLoadData ?: false)), false, false)
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

        mAdapter.notifyItemRangeChanged(firstVisibleIndex, lastVisibleIndex - firstVisibleIndex, payload)
    }

    protected fun handleLiveData(liveData: LiveData<BaseResponse<MutableList<V>>>, refresh: Boolean) {
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
                    if (null == mAdapter.getDataByPosition(0) || pData[0] != mAdapter.getDataByPosition(0)) {
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
                refresh_tip?.text = "暂无更新"
            } else {
                if (pData.size < PAGE_SIZE) {
                    setLoadingStatus(COMPLETE)
                } else {
                    setLoadingStatus(LOADING_MORE)
                }
                refresh_tip?.text = String.format("为您推送%s条更新", pData.size)
            }
            mAdapter.replaceItems(pData)
        }
    }

    protected fun setLoadingStatus(@LoadingStatus status: Int) {
        when (status) {
            INIT -> {
                showShimmerCover(!muteLoadData, false, false)
                _refresh?.finishRefresh(1)
                _refresh?.finishLoadMore()
                mAdapter.showFooter = false
                _refresh?.isEnableLoadMore = false
            }
            NULL -> {
                showShimmerCover(false, false, showEmpty)
                _refresh?.finishRefresh(1)
                _refresh?.finishLoadMore()
                mAdapter.showFooter = false
                _refresh?.isEnableLoadMore = false
            }
            COMPLETE -> {
                showShimmerCover(false, false, false)
                _refresh?.finishRefresh(1)
                _refresh?.finishLoadMore()
                mAdapter.showFooter = true
                _refresh?.isEnableLoadMore = false
            }
            ERROR -> {
                showShimmerCover(true, true, false)
                _refresh?.finishRefresh(1)
                _refresh?.finishLoadMore(false)
                mAdapter.showFooter = false
                _refresh?.isEnableLoadMore = false
            }
            ERROR_TIP -> {
                showShimmerCover(false, false, false)
                _refresh?.finishRefresh(false)
                _refresh?.finishLoadMore(false)
                mAdapter.showFooter = false
                _refresh?.isEnableLoadMore = false
            }
            LOADING_MORE_ERROR -> {
                showShimmerCover(false, false, false)
                _refresh?.finishRefresh(1)
                _refresh?.finishLoadMore(false)
                mAdapter.showFooter = false
                _refresh?.isEnableLoadMore = true
            }
            LOADING_MORE -> {
                showShimmerCover(false, false, false)
                _refresh?.finishRefresh(1)
                _refresh?.finishLoadMore(100)
                mAdapter.showFooter = false
                _refresh?.isEnableLoadMore = true
            }
        }
    }

    protected fun disableLoadPageData() {
        PAGE_SIZE = Int.MAX_VALUE
        _refresh?.isEnableLoadMore = false
    }

    internal inner class ReboundAniUpdateListener : ValueAnimator.AnimatorUpdateListener {

        override fun onAnimationUpdate(animation: ValueAnimator) {
            container_refresh_tip!!.translationY = (-container_refresh_tip!!.height + animation.animatedValue as Int).toFloat()
        }

    }

    internal inner class AniListener : AnimatorListenerAdapter() {

        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            val ta = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f)
            ta.duration = 200
            container_refresh_tip!!.animation = ta
            container_refresh_tip!!.visibility = View.GONE
            refresh_tip!!.visibility = View.GONE
            bg_refresh_tip!!.visibility = View.GONE
        }

        override fun onAnimationCancel(animation: Animator) {
            super.onAnimationCancel(animation)
            super.onAnimationEnd(animation)
        }

        override fun onAnimationStart(animation: Animator) {
            super.onAnimationStart(animation)

            container_refresh_tip!!.visibility = View.VISIBLE

            val sa = ScaleAnimation(0.94f, 1.0f, 1.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f)

            sa.duration = 450
            sa.interpolator = AccelerateDecelerateInterpolator()
            bg_refresh_tip!!.animation = sa
            bg_refresh_tip!!.visibility = View.VISIBLE


            val set = AnimationSet(true)

            val sa1 = ScaleAnimation(0.8f, 1.0f, 0.95f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)

            val aa = AlphaAnimation(0.8f, 1.0f)
            set.interpolator = AccelerateDecelerateInterpolator()

            set.addAnimation(sa1)
            set.addAnimation(aa)
            set.duration = 400
            refresh_tip!!.animation = set
            refresh_tip!!.visibility = View.VISIBLE
            container_refresh_tip!!.translationY = 0f
        }

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
