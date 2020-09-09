package com.chen.basemodule.basem

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.consumer.ActivitySlidingBackConsumer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.chen.basemodule.allroot.RootFragment
import com.chen.basemodule.basem.toolbar.ToolbarView
import com.chen.basemodule.constant.LiveBusKey
import com.chen.basemodule.event_bus.BaseCloseEvent
import com.chen.basemodule.extend.FRAGMENT_SWIPE_TYPE
import com.alibaba.android.arouter.facade.enums.SwipeType
import com.chen.basemodule.extend.defaultLaunch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.jvm.jvmName

abstract class BaseFragment : RootFragment() {

    private var toolbarView: ToolbarView? = null

    val toolbar: ToolbarView
        get() {
            if (toolbarView == null) {
                toolbarView = ToolbarView(activity!!).apply { attachToolbar(view) }
            }
            return toolbarView!!
        }

    lateinit var swipe: ActivitySlidingBackConsumer

//    val swipe by lazy {
//        SmartSwipe.wrap(activity)
//                .addConsumer(ActivitySlidingBackConsumer(activity))
//                .setRelativeMoveFactor(0.5F) as ActivitySlidingBackConsumer
//    }

    /**
     * 设置布局
     * @return content layout
     */
    abstract val contentLayoutId: Int

    /**
     * onViewCreated 之后调用
     */
    abstract fun initAndObserve()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        var rootView: View? = null
        lifecycleScope.launch {

            view?.run {
                if (parent is ViewGroup) {
                    (parent as ViewGroup).removeView(this)
                    rootView = this
                    return@launch
                }
            }

            swipe = withContext(Dispatchers.Unconfined) {
                SmartSwipe.wrap(activity)
                        .addConsumer(ActivitySlidingBackConsumer(activity))
                        .setRelativeMoveFactor(0.5F) as ActivitySlidingBackConsumer
            }

            launch(Dispatchers.Default) {
                arguments?.run {
                    when (getSerializable(FRAGMENT_SWIPE_TYPE)) {
                        SwipeType.FROM_LEFT -> swipe.enableLeft()
                        SwipeType.FROM_RIGHT -> swipe.enableRight()
                        SwipeType.FROM_TOP -> swipe.enableTop()
                        SwipeType.FROM_BOTTOM -> swipe.enableBottom()
                        else -> {
                        }
                    }
                }
            }

            rootView = withContext(Dispatchers.Main) {
                inflater.inflate(contentLayoutId, container, false)
            }

        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAndObserve()

        //注册关闭广播
        LiveEventBus.get(LiveBusKey.EVENT_CLOSE, BaseCloseEvent::class.java)
                .observe(this, Observer {
                    if (it.target.contains(this::class.jvmName)) {
                        activity?.finish()
                    }
                })
    }

//    override fun onStop() {
//        super.onStop()
//        ActivityTranslucentUtil.convertWindowToTranslucent(activity)
//    }

    /**
     * call this method when layout is contain fragment container
     *
     * @param fragment
     */
    protected fun setFragment(@IdRes id: Int, fragment: Fragment) {
        childFragmentManager
                .beginTransaction()
                .replace(id, fragment)
                .commitAllowingStateLoss()
    }

    open fun onKeyUp(keyCode: Int, event: KeyEvent) = false//默认不拦截返回事件

    override fun onDestroyView() {
        toolbarView?.run {
            if (parent is ViewGroup) {
                removeView(toolbarView)
            }
            toolbarView = null
        }
        super.onDestroyView()
    }
}
