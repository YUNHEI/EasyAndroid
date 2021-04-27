package com.chen.basemodule.basem

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.enums.SwipeType
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.consumer.ActivitySlidingBackConsumer
import com.chen.basemodule.allroot.RootFragment
import com.chen.basemodule.basem.toolbar.ToolbarView
import com.chen.basemodule.constant.LiveBusKey
import com.chen.basemodule.event_bus.BaseCloseEvent
import com.chen.basemodule.extend.FRAGMENT_SWIPE_TYPE
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.coroutines.launch
import kotlin.reflect.jvm.jvmName


abstract class BaseFragment : RootFragment() {

    abstract val binding: ViewBinding

    // 临时存放Container
    var tempContainer: ViewGroup? = null

    protected var toolbarView: ToolbarView? = null

    open val toolbar: ToolbarView
        get() {
            if (toolbarView == null) {
                toolbarView = ToolbarView(requireActivity()).apply { attachToolbar(view) }
            }
            return toolbarView!!
        }

    val swipe by lazy {
        SmartSwipe.wrap(activity)
            .addConsumer(ActivitySlidingBackConsumer(activity))
            .setRelativeMoveFactor(0.5F) as ActivitySlidingBackConsumer
    }

    /**
     * 设置布局
     * @return content layout
     */
//    abstract val contentLayoutId: Int

    /**
     * onViewCreated 之后调用
     */
    abstract fun initAndObserve()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        view?.run {
            if (parent is ViewGroup) {
                (parent as ViewGroup).removeView(this)
                return this
            }
        }

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
        //临时存放 Container，保证viewbinding正常初始化
        tempContainer = container

        return binding.root
//        return inflater.inflate(contentLayoutId, container, false)
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        return if (nextAnim > 0) {
            AnimatorInflater.loadAnimator(activity, nextAnim).apply {
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        //动画开始
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        onFragmentAnimEnd()
                    }
                })
            }
        } else {
            null
        }
    }

    open fun onFragmentAnimEnd() {}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAndObserve()

        LiveEventBus.get(LiveBusKey.EVENT_CLOSE, BaseCloseEvent::class.java)
            .observe(this@BaseFragment, Observer {
                if (it.target.contains(this@BaseFragment::class.jvmName)) {
                    activity?.finish()
                }
            })
    }

    fun onShow() {}

    fun onHide() {}

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
