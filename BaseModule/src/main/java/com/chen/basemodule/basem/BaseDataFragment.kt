package com.chen.basemodule.basem

import android.graphics.PorterDuff
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.chen.basemodule.R
import com.chen.basemodule.allroot.RootFragment
import com.chen.basemodule.constant.LiveBusKey
import com.chen.basemodule.event_bus.BaseCloseEvent
import com.chen.basemodule.event_bus.BaseNetworkEvent
import com.chen.basemodule.event_bus.BaseRefreshEvent
import com.chen.basemodule.widget.dialog.LoadingDialog
import com.chen.basemodule.widget.dialog.WarningDialog
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.layout_fragment_loading.view.*
import kotlinx.android.synthetic.main.layout_loading_blank.view.*
import kotlinx.android.synthetic.main.layout_loading_cover.*
import kotlinx.android.synthetic.main.layout_loading_error.view.*
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

/**
 * 泛型覆盖处需要复写反射实现
 */
abstract class BaseDataFragment : BaseFragment() {

    companion object {

        const val LOADING = 0

        const val HIDE = 1

        const val BLANK = 2

        const val ERROR = 3
    }

    @IntDef(LOADING, HIDE, BLANK, ERROR)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class LoadingType

    private val loadingDialogLazy = lazy { LoadingDialog(requireActivity()) }

    open val loadingDialog by loadingDialogLazy

    open val warningDialog by lazy { WarningDialog(requireActivity()) }

    val mLoadingView by lazy {
        layoutInflater.inflate(
            R.layout.layout_fragment_loading,
            _loading_cover
        ).run { findViewById<ConstraintLayout>(R.id._loading_view) }
    }

    open val mNetBlank by lazy {
        layoutInflater.inflate(
            R.layout.layout_loading_blank,
            _loading_cover
        ).run { findViewById<ConstraintLayout>(R.id._empty_container) }
    }

    open val mNetError by lazy {
        layoutInflater.inflate(
            R.layout.layout_loading_error,
            _loading_cover
        ).run { findViewById<ConstraintLayout>(R.id._error_container) }
    }

    val refreshOb by lazy {
        LiveEventBus.get(
            LiveBusKey.EVENT_REFRESH,
            BaseRefreshEvent::class.java
        )!!
    }

    val networkOb by lazy {
        LiveEventBus.get(
            LiveBusKey.EVENT_NETWORK,
            BaseNetworkEvent::class.java
        )!!
    }

    //网络数据为空的时候提示语
    var blankTip = "暂无内容"

    //是否静默加载
    var muteLoadData = false

    /**######################抽象方法区 复写父类中的抽象方法，保证idea自动补全的顺序 ######################*/

    /* viewModel 用户网络加载等操作*/
    abstract val viewModel: BaseViewModel

    /**
     * 设置布局
     * @return content layout
     */
//    abstract override val contentLayoutId: Int

    /**
     * onViewCreated 之后调用
     */
//    abstract override fun initAndObserve()

    abstract fun startLoadData(muteLoadData: Boolean? = false)

    /**######################抽象方法区 复写父类中的抽象方法，保证idea自动补全的顺序 ######################*/


    protected open fun showLoadingCover(@LoadingType type: Int) {

        _loading_cover?.run {
            _nest_loading_cover.isVisible = type != HIDE
            if (type == LOADING) {
                mLoadingView.isVisible = true

                mLoadingView.progress.indeterminateDrawable
                    .setColorFilter(
                        ContextCompat.getColor(context, R.color.gray_99),
                        PorterDuff.Mode.MULTIPLY
                    )
                mLoadingView.progress?.show()
            } else {
                mLoadingView.isVisible = false
                mLoadingView.progress?.hide()
            }
            mNetError?.isVisible = type == ERROR
            if (mNetError != null && type == ERROR) {
                mNetError._retry?.setOnClickListener {
                    startLoadData()
                }
            }
            mNetBlank?.isVisible = type == BLANK
            if (mNetBlank != null && type == BLANK) {
                mNetBlank._blank_msg?.run { text = blankTip }
                mNetBlank._empty_container?.setOnClickListener { startLoadData() }
            }
        }
    }

    fun postRefresh(vararg targetClass: KClass<out RootFragment>, obj: Any? = null) {
        postRefresh(*targetClass.map { it.jvmName }.toTypedArray(), obj = obj)
    }

    fun postRefresh(vararg target: String, obj: Any? = null) {
        LiveEventBus.get(LiveBusKey.EVENT_REFRESH)
            .post(BaseRefreshEvent(this::class, *target, obj = obj))
    }

    fun postClose(vararg targetClass: KClass<out RootFragment>) {
        postClose(*targetClass.map { it.jvmName }.toTypedArray())
    }

    fun postClose(vararg target: String) {
        LiveEventBus.get(LiveBusKey.EVENT_CLOSE).post(BaseCloseEvent(this::class.jvmName, *target))
    }

    open fun observeRefresh(
        key: String = this::class.jvmName,
        refresh: (event: BaseRefreshEvent) -> Unit
    ) {
        refreshOb.observe(this, Observer {
            if (it.target.contains(key) || it.target.contains("*")) {
                refresh.invoke(it)
            }
        })
    }

    open fun observeNetwork(networkChange: (connect: Boolean) -> Unit) {
        networkOb.observe(this, Observer { networkChange.invoke(it.isConnect) })
    }

    override fun onDestroyView() {
        if (loadingDialogLazy.isInitialized() && loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
        super.onDestroyView()
    }
}
