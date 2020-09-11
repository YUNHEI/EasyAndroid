package com.chen.baseextend.widget

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView
import com.chen.baseextend.R

/**
 * @author alan
 * @date 2019/3/28
 * 点赞小图标
 */
class LikeImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private val mAnimation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.like_click)
    }

    private var mCurrentLikeStatus = LikeStatus.UN_LIKE

    public fun startAnimation() {
        clearAnimation()
        startAnimation(mAnimation)
    }


    public fun setLikeStatus(status: LikeStatus) {
        this.mCurrentLikeStatus = status
    }


    enum class LikeStatus(var status: String) {
        // 已点赞
        LIKING("LIKING"),
        // 未点赞
        UN_LIKE("UN_LIKE")
    }


}