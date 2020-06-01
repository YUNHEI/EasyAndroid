package com.chen.baseextend.view

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView

class TabGridView(context: Context, attrs: AttributeSet?) : GridView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE.shr(2), MeasureSpec.AT_MOST)

        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}