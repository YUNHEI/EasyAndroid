package com.chen.basemodule.mlist.bean

import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.mlist.BaseMultiGroupListFragment

data class GroupWrapBean<P : RootBean, C : RootBean>(
    val groupData: P? = null,
    var isExpand: Boolean = true
) : ItemWrapBean<C>()