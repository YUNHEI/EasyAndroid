package com.chen.baseextend.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.chen.baseextend.R
import com.chen.basemodule.extend.dp2px
import kotlinx.android.synthetic.main.item_data.view.*

/**
 *  Created by chen on 2019/7/25
 **/
class ItemDataView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {


    private var reversal: Boolean = false

    init {

        attrs?.run {
            context.obtainStyledAttributes(this, R.styleable.ItemDataView).run {

                getBoolean(R.styleable.ItemDataView_data_reversal, false).run {
                    reversal = this
                }

                inflate(context, if (reversal) R.layout.item_reversal_data else R.layout.item_data, this@ItemDataView)

                getString(R.styleable.ItemDataView_data_title).run {
                    title = this
                }
                getString(R.styleable.ItemDataView_data_unit).run {
                    unit = this
                }
                getColor(R.styleable.ItemDataView_data_title_color, Color.GRAY).run {
                    _title.setTextColor(this)
                }
                getDimension(R.styleable.ItemDataView_data_unit_size, dp2px(16f)).run {
                    _unit.setTextSize(TypedValue.COMPLEX_UNIT_PX, this)
                }
                getDimension(R.styleable.ItemDataView_data_size, dp2px(24f)).run {
                    _data.setTextSize(TypedValue.COMPLEX_UNIT_PX, this)
                }
                getDimension(R.styleable.ItemDataView_data_space, dp2px(0f)).run {
                    space = toInt()
                }
                getDimension(R.styleable.ItemDataView_data_title_size, dp2px(16f)).run {
                    _title.setTextSize(TypedValue.COMPLEX_UNIT_PX, this)
                }
                getColor(R.styleable.ItemDataView_data_text_color, Color.GRAY).run {
                    _unit.setTextColor(this)
                    _data.setTextColor(this)
                }
                recycle()
            }
        }
    }

    var data: CharSequence?
        get() = _data.text
        set(value) {
            _data.text = value
        }

    var space: Int = 0
        set(value) {
            field = value
            if (reversal) {
                (_data.layoutParams as? MarginLayoutParams)?.run {
                    topMargin = space
                }
            } else {
                (_title.layoutParams as? MarginLayoutParams)?.run {
                    topMargin = space
                }
            }
        }

    var unit: CharSequence? = null
        get() = _unit.text
        set(value) {
            field = value
            _unit.visibility = if (!value.isNullOrEmpty()) View.VISIBLE else View.GONE
            _unit.text = value
        }

    var title: CharSequence?
        get() = _title.text
        set(value) {
            _title.text = value
            _title.visibility = if (value.isNullOrEmpty()) View.GONE else View.VISIBLE
        }
}