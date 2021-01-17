package com.chen.basemodule.mlist.bean

import com.chen.basemodule.allroot.RootBean

open class ItemWrapBean<C : RootBean>(val itemData: C? = null) : DataWrapBean()