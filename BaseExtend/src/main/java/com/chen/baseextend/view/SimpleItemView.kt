package com.chen.baseextend.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.chen.baseextend.R
import com.chen.basemodule.extend.color
import com.chen.basemodule.extend.load
import com.chen.basemodule.extend.visible
import com.chen.basemodule.widget.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.layout_user_info_item.view.*

class SimpleItemView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    var content: String?
        get() = _content.text.toString()
        set(value) {
            _content.visibility = View.VISIBLE
            _content.text = value
        }

    var title: String?
        get() = _title.text.toString()
        set(value) {
            _title.text = value
        }

    init {

        LayoutInflater.from(context).inflate(R.layout.layout_user_info_item, this)

        if (attrs != null) {
            val ta = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleItemView)
            val title = ta.getString(R.styleable.SimpleItemView_item_title)
            val content = ta.getString(R.styleable.SimpleItemView_item_content)
            val drawable = ta.getDrawable(R.styleable.SimpleItemView_item_image_src)
            val icon = ta.getDrawable(R.styleable.SimpleItemView_item_icon)
            val showForward = ta.getBoolean(R.styleable.SimpleItemView_item_show_forward, false)
            val showDivider = ta.getBoolean(R.styleable.SimpleItemView_item_show_divider, true)
            val showTopDivider = ta.getBoolean(R.styleable.SimpleItemView_item_show_top_divider, false)
            val showDividerMatch = ta.getBoolean(R.styleable.SimpleItemView_item_show_divider_match, false)

            val gravity = ta.getInt(R.styleable.SimpleItemView_item_content_gravity, 1)

            _title.text = title

            _content.visible(!content.isNullOrEmpty())
            _content.text = content
            _content.gravity = if (gravity == 0) Gravity.START else Gravity.END

            _image.visibility = if (drawable == null) View.GONE else View.VISIBLE
            if (drawable != null) _image.setImageDrawable(drawable)

            _forward.visibility = if (showForward) View.VISIBLE else View.GONE

            val layoutParams = _divider_cover.layoutParams
            if (showDivider) {
                if (showDividerMatch) {
                    _divider_cover.visibility = View.GONE
                } else {
                    layoutParams.width = DensityUtil.dp2px(15f)
                }
            } else {
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            _divider_cover.layoutParams = layoutParams

            if (showTopDivider) {
                _divider_top.visibility = View.VISIBLE
            } else {
                _divider_top.visibility = View.GONE
            }

            if (icon != null) {
                _icon.visibility = View.VISIBLE
                _icon.setImageDrawable(icon)
            } else {
                _icon.visibility = View.GONE
            }

            ta.recycle()
        }
    }

    fun setTitleParams(params: ConstraintLayout.LayoutParams) {
        _title.layoutParams = params
    }

    fun setShowForward(showForward: Boolean) {
        _forward.visibility = if (showForward) View.VISIBLE else View.GONE
    }

    fun setShowDivider(divider: Boolean) {
        _divider_cover.visibility = if (divider) View.VISIBLE else View.GONE
    }

    fun setShowTopDivider(divider: Boolean) {
        _divider_top.visibility = if (divider) View.VISIBLE else View.GONE
    }

    fun setDividerMatch(isMatch: Boolean) {
        if (isMatch) {
            _divider_cover.visibility = View.GONE
        } else {
            _divider_cover.visibility = View.VISIBLE
            val layoutParams = _divider_cover.getLayoutParams()
            layoutParams.width = DensityUtil.dp2px(15f)
            _divider_cover.layoutParams = layoutParams
        }
    }


    fun setArrowVisibility(visibility: Int) {
        _forward.visibility = visibility
    }

    fun setContentColor(@ColorRes color: Int) {
        _content.setTextColor(context.color(color))
    }

    fun loadImage(url: String?) {
        _image.visibility = View.VISIBLE
        _image.load(url, R.mipmap.ic_default_avatar)
    }

}
