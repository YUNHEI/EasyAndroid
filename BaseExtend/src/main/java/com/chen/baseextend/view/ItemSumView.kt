package com.chen.baseextend.view

import android.content.Context
import android.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.chen.baseextend.R
import kotlinx.android.synthetic.main.item_sum.view.*

/**
 *  Created by chen on 2019/7/25
 **/
class ItemSumView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.item_sum, this)
        attrs?.run {
            context.obtainStyledAttributes(this, R.styleable.ItemSumView).run {
                getString(R.styleable.ItemSumView_title)?.run {
                    _title.text = this
                }
                getBoolean(R.styleable.ItemSumView_show_unit, true).run {
                    _unit.visibility = if (this) View.VISIBLE else View.GONE
                }
                getColor(R.styleable.ItemSumView_text_color, Color.GRAY).run {
                    _title.setTextColor(this)
                    _price.setTextColor(this)
                    _unit.setTextColor(this)
                }
                recycle()
            }
        }
    }

    var price: CharSequence?
        get() = _price.text
        set(value) {
            _price.text = value
        }

    var title: CharSequence?
        get() = _title.text
        set(value) {
            _title.text = value
        }
}