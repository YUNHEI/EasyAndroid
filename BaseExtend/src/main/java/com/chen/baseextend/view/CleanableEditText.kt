package com.chen.baseextend.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.chen.baseextend.R

class CleanableEditText @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle) : AppCompatEditText(context, attrs, defStyleAttr), OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private var mClearDrawable: Drawable? = null
    /**
     * 控件是否有焦点
     */
    private var hasFocus = false
    private var isMobile = false
    private var isPrice = false
    private var needFilter = true
    private var currentSelection = 0
    private var l: OnFocusChangeListener? = null
    private fun init() { // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = compoundDrawables[2] //左 上 右 下
        if (mClearDrawable == null) {
            mClearDrawable = ContextCompat.getDrawable(context, DEFAULT_DELETE_IMG)
            if (mClearDrawable == null) {
                throw NullPointerException("You can add drawableRight attribute in XML")
            }
        }
        //        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        mClearDrawable!!.setBounds(0, 0, 40, 40)
        // 默认设置隐藏图标
        setClearIconVisible(false)
        // 设置焦点改变的监听
        super.setOnFocusChangeListener(this)
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this)
    }

    companion object {
        private val DEFAULT_DELETE_IMG = R.mipmap.ic_input_clean
    }

    init {
        if (attrs != null) {
            val ta = getContext().obtainStyledAttributes(attrs, R.styleable.CleanableEditText)
            val type = ta.getInt(R.styleable.CleanableEditText_clean_edit_text_type, 0)
            isMobile = type == 1 || ta.getBoolean(R.styleable.CleanableEditText_clean_edit_text_is_mobile, false)
            isPrice = type == 2
            if (isMobile) {
                inputType = InputType.TYPE_CLASS_TEXT
                keyListener = DigitsKeyListener.getInstance("0123456789 ")
                filters = arrayOf<InputFilter>(LengthFilter(13))
            } else if (isPrice) {
                inputType = InputType.TYPE_CLASS_TEXT
                keyListener = DigitsKeyListener.getInstance("0123456789.")
                filters = arrayOf<InputFilter>(LengthFilter(10))
            }
            ta.recycle()
        }
        init()
    }

    override fun setOnFocusChangeListener(l: OnFocusChangeListener) {
        this.l = l
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件 当我们按下的位置 在 EditText的宽度 -
     * 图标到控件右边的间距 - 图标的宽度 和 EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (compoundDrawables[2] != null) {
                val touchable = event.x > width - totalPaddingRight && event.x < width - paddingRight
                if (touchable) {
                    this.setText("")
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], right, compoundDrawables[3])
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (isMobile && needFilter) {
            needFilter = false
            currentSelection = selectionStart
            val mobile = s.toString().replace(" ", "")
            val editable = text
            editable!!.clear()
            editable.append(mobile.substring(0, Math.min(mobile.length, 3)))
            if (mobile.length > 3) {
                editable.append(" ")
                editable.append(mobile.substring(3, Math.min(mobile.length, 7)))
            }
            if (mobile.length > 7) {
                editable.append(" ")
                editable.append(mobile.substring(7))
            }
            if (editable.length > 8) {
                if (currentSelection == 9) currentSelection = if (count > 0) 10 else 8
                if (currentSelection == 4) currentSelection = if (count > 0) 5 else 3
            } else if (editable.length > 4) {
                if (currentSelection == 4) currentSelection = if (count > 0) 5 else 3
            }
            setSelection(Math.min(currentSelection, editable.length))
            needFilter = true
        } else if (isPrice && needFilter) {
            needFilter = false
            var value = s.toString()
            val dotCount = countCharacter(value, '.')
            if (dotCount < 1 && !value.startsWith("0")) {
                needFilter = true
                return
            }
            if (!value.startsWith(".") && dotCount <= 1 && value.indexOf('.') >= value.length - 3 && (!value.startsWith("0") || value.startsWith("0."))) {
                needFilter = true
                return
            }
            currentSelection = selectionStart
            val editable = text
            editable!!.clear()
            if (value == ".") {
                editable.append("0.")
            } else if (value.startsWith("0") && !value.startsWith("0.")) {
                while (value.startsWith("0")) {
                    value = value.substring(1)
                }
                editable.append(value)
            } else if (count > 0) {
                val pre = if (start > 0) value.substring(0, start) else ""
                val `in` = value.substring(start, start + count)
                val suf = value.substring(start + count)
                if (dotCount > 0) {
                    if (dotCount > 1) {
                        val result = pre + `in`.replace(".", "") + suf
                        val v = result.substring(0, Math.min(result.indexOf(".") + 3, result.length))
                        editable.append(v)
                    } else {
                        val v = value.substring(0, Math.min(value.indexOf(".") + 3, value.length))
                        editable.append(v)
                    }
                } else {
                    editable.append(value)
                }
            } else {
                editable.append(value)
            }
            needFilter = true
        }
    }

    private fun countCharacter(value: String, c: Char): Int {
        var count = 0
        for (i in 0 until value.length) {
            if (value[i] == c) {
                count++
            }
        }
        return count
    }

    val string: String
        get() {
            var s = text.toString().trim { it <= ' ' }
            if (isMobile) s = s.replace(" ".toRegex(), "")
            return s
        }

    override fun afterTextChanged(s: Editable) {
        if (hasFocus) {
            setClearIconVisible(s.length > 0)
        }
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        this.hasFocus = hasFocus
        if (hasFocus) {
            setClearIconVisible(text!!.length > 0)
        } else {
            setClearIconVisible(false)
        }
        if (l != null) {
            l!!.onFocusChange(v, hasFocus)
        }
    }

}