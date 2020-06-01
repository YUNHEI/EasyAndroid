package com.chen.baseextend.widget.dialog

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

/**
 *  Created by chen on 2019/8/2
 **/
class DialogItem(
        val title: String, val id: String? = null, @ColorInt val textColor: Int? = null, @DrawableRes val img: Int? = null,
        val onClick: ((id: String?) -> Boolean)? = null)