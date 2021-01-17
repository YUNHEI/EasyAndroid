package com.chen.basemodule.mlist.animator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.animation.*
import kotlinx.android.synthetic.main.base_mlist_fragment.view.*
import kotlinx.android.synthetic.main.layout_refresh_tip.view.*

class AniListener(val view: View?) : AnimatorListenerAdapter() {

    override fun onAnimationEnd(animation: Animator) {
        super.onAnimationEnd(animation)
        view?.run {
            val ta = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f
            )
            ta.duration = 150
            _container_refresh_tip!!.animation = ta
            _container_refresh_tip!!.visibility = View.GONE
            _refresh_tip!!.visibility = View.GONE
            _bg_refresh_tip!!.visibility = View.GONE
        }
    }

    override fun onAnimationCancel(animation: Animator) {
        super.onAnimationCancel(animation)
        super.onAnimationEnd(animation)
    }

    override fun onAnimationStart(animation: Animator?) {
        super.onAnimationStart(animation)
        view?.run {
            _layout_refresh_tip?.visibility = View.VISIBLE
            _container_refresh_tip.visibility = View.VISIBLE

            val sa = ScaleAnimation(
                0.94f, 1.0f, 1.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f
            )

            sa.duration = 250
            sa.interpolator = AccelerateDecelerateInterpolator()
            _bg_refresh_tip.animation = sa
            _bg_refresh_tip.visibility = View.VISIBLE


            val set = AnimationSet(true)

            val sa1 = ScaleAnimation(
                0.8f, 1.0f, 0.95f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
            )

            val aa = AlphaAnimation(0.8f, 1.0f)
            set.interpolator = AccelerateDecelerateInterpolator()

            set.addAnimation(sa1)
            set.addAnimation(aa)
            set.duration = 250
            _refresh_tip.animation = set
            _refresh_tip.visibility = View.VISIBLE
            _container_refresh_tip.translationY = 0f
        }
    }

}
