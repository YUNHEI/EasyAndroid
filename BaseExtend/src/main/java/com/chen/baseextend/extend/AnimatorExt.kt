package com.chen.baseextend.extend

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator

/**
 *  Created by chen on 2019/8/26
 **/

fun startValueAnimate(from: Int, to: Int, time: Long,
                      onStart: ((animation: Animator?) -> Unit)? = null,
                      onUpdate: ((animation: ValueAnimator) -> Unit)? = null,
                      onCancel: ((animation: Animator?) -> Unit)? = null,
                      onFinish: ((animation: Animator?) -> Unit)? = null,
                      onRepeat: ((animation: Animator?) -> Unit)? = null,
                      autoStart: Boolean = true): ValueAnimator = ValueAnimator.ofInt(from, to).apply {

    duration = time

    interpolator = AccelerateDecelerateInterpolator()

    addUpdateListener(onUpdate)

    addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
            onRepeat?.invoke(animation)
        }

        override fun onAnimationEnd(animation: Animator?) {
            onFinish?.invoke(animation)
        }

        override fun onAnimationCancel(animation: Animator?) {
            onCancel?.invoke(animation)
        }

        override fun onAnimationStart(animation: Animator?) {
            onStart?.invoke(animation)
        }
    })

    if (autoStart) start()
}