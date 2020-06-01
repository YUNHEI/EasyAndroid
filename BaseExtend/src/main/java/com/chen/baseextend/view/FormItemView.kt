package com.chen.baseextend.view

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.chen.baseextend.R
import com.chen.basemodule.extend.color
import com.chen.basemodule.extend.load
import com.chen.basemodule.widget.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.item_form.view.*

/**
 * @author alan
 * @date 2019-05-14
 */
class FormItemView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    val mContent: EditText
        get() = _content

    init {
        LayoutInflater.from(context).inflate(R.layout.item_form, this)

        attrs?.run {
            context.obtainStyledAttributes(this, R.styleable.FormItemView).run {

                getString(R.styleable.FormItemView_form_item_title)?.run {
                    _item_title.visibility = View.VISIBLE
                    _item_title.text = this
                } ?: run { _item_title.visibility = View.GONE }

                _content.setText(getString(R.styleable.FormItemView_form_item_content))

                _content.hint = getString(R.styleable.FormItemView_form_item_hint)

                getInteger(R.styleable.FormItemView_form_item_length, 0).run {
                    if (this > 0) _content.filters = arrayOf(InputFilter.LengthFilter(this))
                }

                if (!getBoolean(R.styleable.FormItemView_form_item_enable, true)) {
                    _content.isEnabled = false
                    _content.setTextColor(context.color(R.color.gray_99))
                    _item_title.setTextColor(context.color(R.color.gray_99))
                }

                _content.run {
                    if (getBoolean(R.styleable.FormItemView_form_item_editable, true)) {
                        isFocusable = true
                        isFocusableInTouchMode = true
                        isLongClickable = true
                    } else {
                        isFocusable = false
                        isLongClickable = false
                    }
                }

                getDrawable(R.styleable.FormItemView_form_item_image_src)?.run {
                    _image.visibility = View.VISIBLE
                    _image.setImageDrawable(this)
                } ?: run { _image.visibility = View.GONE }

                getDrawable(R.styleable.FormItemView_form_item_icon)?.run {
                    _icon.visibility = View.VISIBLE
                    _icon.setImageDrawable(this)
                } ?: run { _icon.visibility = View.GONE }

                _forward.visibility = if (getBoolean(R.styleable.FormItemView_form_item_show_forward, false)) View.VISIBLE else View.GONE

                val layoutParams = _divider_cover.layoutParams

                if (getBoolean(R.styleable.FormItemView_form_item_show_divider, true)) {
                    if (getBoolean(R.styleable.FormItemView_form_item_show_divider_match, false)) {
                        _divider_cover.visibility = View.GONE
                    } else {
                        layoutParams.width = DensityUtil.dp2px(15f)
                    }
                } else {
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                }
                _divider_cover.layoutParams = layoutParams

                _divider_top.visibility = if (getBoolean(R.styleable.FormItemView_form_item_show_top_divider, false)) View.VISIBLE else View.GONE
                recycle()
            }
        }
    }


    fun setTitle(title: String) {
        _item_title.visibility = View.VISIBLE
        _item_title.text = title
    }

    fun setTitleParams(params: ConstraintLayout.LayoutParams) {
        _item_title.layoutParams = params
    }

    fun setContent(content: String) {
        _content.visibility = View.VISIBLE
        _content.setText(content)
    }

    fun setContentHint(content: String) {
        _content.visibility = View.VISIBLE
        _content.hint = content
    }

    fun setContentEnable(enable: Boolean) {
        _content.isEnabled = enable
    }

    fun getContent(): String {
        return _content.text.trim().toString()
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
            val layoutParams = _divider_cover.layoutParams
            layoutParams.width = DensityUtil.dp2px(15f)
            _divider_cover.layoutParams = layoutParams
        }
    }


    fun setArrowVisibility(visibility: Int) {
        _forward.visibility = visibility
    }

    fun setContentColor(@ColorRes color: Int) {
        _content.setTextColor(ContextCompat.getColor(context, color))
    }

    fun loadImage(url: String) {
        _image.visibility = View.VISIBLE
        _image.load(url, R.mipmap.ic_default_avatar)
    }

    fun setEnable(enable: Boolean) {

        if (!enable) {
            _content.isEnabled = false
            _content.setTextColor(ContextCompat.getColor(context, R.color.gray_99))
            _item_title.setTextColor(ContextCompat.getColor(context, R.color.gray_99))
        } else {
            _content.isEnabled = true
            _content.setTextColor(ContextCompat.getColor(context, R.color.gray_33))
            _item_title.setTextColor(ContextCompat.getColor(context, R.color.gray_33))
        }
    }

}