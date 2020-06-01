package com.chen.basemodule.mlist


import android.content.Context
import com.chen.basemodule.allroot.RootBean


/**
 * Created by chen on 2018/11/13
 */
abstract class BaseFooterViewDelegate<T : RootBean>(context: Context) : BaseItemViewDelegate<T>(context) {

    override fun isThisDelegate(data: T, position: Int, realP: Int) = true
}
