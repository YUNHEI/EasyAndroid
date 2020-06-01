package com.chen.baseextend.view

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import com.chen.baseextend.R
import com.chen.basemodule.extend.listenClick
import com.chen.basemodule.widget.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.view_layout_password_input.view.*

class PasswordEditText(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    //注意设置密码的明暗文，需要组合使用InputType
    private val hide = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

    private val show = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

    private val layout = R.layout.view_layout_password_input//修改样式请直接修改本布局

    val text get() = _pwd.text!!.trim().toString()

    val editText by lazy { _pwd }

    init {
        View.inflate(context, layout, this)

        if (attrs != null) {
            val ta = getContext().obtainStyledAttributes(attrs, R.styleable.PasswordEditText)
            _pwd.hint = ta.getString(R.styleable.PasswordEditText_pwd_hint)
            _pwd.setText(ta.getString(R.styleable.PasswordEditText_pwd_text))
            val textSize = ta.getDimensionPixelSize(R.styleable.PasswordEditText_pwd_text_size, DensityUtil.dp2px(15f)).toFloat()
            val showLeftIcon = ta.getBoolean(R.styleable.PasswordEditText_pwd_show_left_icon, true)
            _pwd.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

            if (!showLeftIcon) _pwd.setCompoundDrawables(null, null, null, null)

            ta.recycle()
        }

        listenClick(_visible) {

            val inputType = _pwd.inputType
            val start = _pwd.selectionStart
            if (inputType == 129) {
                _pwd.inputType = show
                _visible.setImageResource(R.mipmap.ic_pwd_visible)
            } else {
                _pwd.inputType = hide
                _visible.setImageResource(R.mipmap.ic_pwd_invisible)
            }

            _pwd.setSelection(start)
        }
    }

    override fun setOnFocusChangeListener(l: View.OnFocusChangeListener) {
        _pwd.onFocusChangeListener = l
    }
}
