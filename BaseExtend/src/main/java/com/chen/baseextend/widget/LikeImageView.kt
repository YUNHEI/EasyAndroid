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
class LikeImageView : AppCompatImageView {

    private val mAnimation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.like_click)
    }

    private var mCurrentLikeStatus = LikeStatus.UN_LIKE

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {

    }

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