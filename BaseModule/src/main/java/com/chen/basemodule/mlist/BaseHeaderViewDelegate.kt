package com.chen.basemodule.mlist


import android.content.Context
import com.chen.basemodule.allroot.RootBean


/**
 * Created by chen on 2018/11/13
 */
abstract class BaseHeaderViewDelegate(context: Context) : BaseItemViewDelegate<RootBean>(context) {

    override fun isThisDelegate(data: RootBean, position: Int, realP: Int) = true

}
