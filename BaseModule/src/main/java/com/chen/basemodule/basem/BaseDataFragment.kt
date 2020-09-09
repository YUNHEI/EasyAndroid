package com.chen.basemodule.basem

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.chen.basemodule.R
import com.chen.basemodule.allroot.RootFragment
import com.chen.basemodule.constant.LiveBusKey
import com.chen.basemodule.event_bus.BaseCloseEvent
import com.chen.basemodule.event_bus.BaseNetworkEvent
import com.chen.basemodule.event_bus.BaseRefreshEvent
import com.chen.basemodule.extend.color
import com.chen.basemodule.widget.ShimmerLayout
import com.chen.basemodule.widget.dialog.LoadingDialog
import com.chen.basemodule.widget.dialog.WarningDialog
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.layout_shimmer_cover.*
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

/**
 * 泛型覆盖处需要复写反射实现
 */
abstract class BaseDataFragment : BaseFragment() {

    private val loadingDialogLazy = lazy { LoadingDialog(activity!!) }

    open val loadingDialog by loadingDialogLazy

    open val warningDialog by lazy { WarningDialog(activity!!) }

    val mShimmer by lazy { layoutInflater.inflate(R.layout.layout_loading_shimmer, _shimmer_cover).run { findViewById<ShimmerLayout>(R.id._shimmer) } }

    open val mNetBlank by lazy { layoutInflater.inflate(R.layout.layout_loading_blank, _shimmer_cover).run { findViewById<ConstraintLayout>(R.id._empty_container) } }

    open val mNetError by lazy { layoutInflater.inflate(R.layout.layout_loading_error, _shimmer_cover).run { findViewById<ConstraintLayout>(R.id._error_container) } }

    val refreshOb by lazy { LiveEventBus.get(LiveBusKey.EVENT_REFRESH, BaseRefreshEvent::class.java)!! }

    val networkOb by lazy { LiveEventBus.get(LiveBusKey.EVENT_NETWORK, BaseNetworkEvent::class.java)!! }

    var blankTip = "暂无内容"

    var muteLoadData = false

    /**######################抽象方法区 复写父类中的抽象方法，保证idea自动补全的顺序 ######################*/

    /* viewModel 用户网络加载等操作*/
    abstract val viewModel: BaseViewModel

    /**
     * 设置布局
     * @return content layout
     */
    abstract override val contentLayoutId: Int

    /**
     * onViewCreated 之后调用
     */
    abstract override fun initAndObserve()

    abstract fun startLoadData(muteLoadData: Boolean? = false)
    /**######################抽象方法区 复写父类中的抽象方法，保证idea自动补全的顺序 ######################*/

    protected open fun showShimmerCover(show: Boolean, showError: Boolean, showBlank: Boolean) {

        _shimmer_cover?.run {

            setBackgroundColor(color(R.color.bg_common_gray))

            visibility = if (showError || show || showBlank) View.VISIBLE else View.GONE

            mShimmer.run {
                if (showError || showBlank) {
                    visibility = View.GONE
                    stopShimmerAnimation()
                } else {
                    visibility = View.VISIBLE
                    startShimmerAnimation()
                }
            }

            mNetError.run {

                if (showError) {

                    findViewById<TextView>(R.id._retry).setOnClickListener {
                        startLoadData()
                    }

                    visibility = View.VISIBLE

                } else {
                    visibility = View.GONE
                }

            }

            mNetBlank.run {

                if (showBlank) {

                    findViewById<TextView>(R.id._msg)?.run {
                        text = blankTip
                    }

                    findViewById<View>(R.id._empty_container)?.setOnClickListener {  startLoadData() }

                    visibility = View.VISIBLE

                } else {
                    visibility = View.GONE
                }
            }
        }
    }

    fun postRefresh(vararg targetClass: KClass<out RootFragment>, obj: Any? = null) {
        postRefresh(*targetClass.map { it.jvmName }.toTypedArray(), obj = obj)
    }

    fun postRefresh(vararg target: String, obj: Any? = null) {
        LiveEventBus.get(LiveBusKey.EVENT_REFRESH).post(BaseRefreshEvent(this::class, *target, obj = obj))
    }

    fun postClose(vararg targetClass: KClass<out RootFragment>) {
        postClose(*targetClass.map { it.jvmName }.toTypedArray())
    }

    fun postClose(vararg target: String) {
        LiveEventBus.get(LiveBusKey.EVENT_CLOSE).post(BaseCloseEvent(this::class.jvmName, *target))
    }

    open fun observeRefresh(key: String = this::class.jvmName, refresh: (event: BaseRefreshEvent) -> Unit) {
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
