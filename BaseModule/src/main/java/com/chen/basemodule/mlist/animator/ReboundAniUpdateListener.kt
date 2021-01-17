package com.chen.basemodule.mlist.animator

import android.animation.ValueAnimator
import android.view.View
import kotlinx.android.synthetic.main.layout_refresh_tip.view.*

class ReboundAniUpdateListener(val view: View?) : ValueAnimator.AnimatorUpdateListener {

    override fun onAnimationUpdate(animation: ValueAnimator) {
        view?.run {
            _container_refresh_tip!!.translationY =
                (-_container_refresh_tip!!.height + animation.animatedValue as Int).toFloat()
        }
    }
}