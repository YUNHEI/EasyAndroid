package com.chen.basemodule.basem

import com.alibaba.fastjson.JSON
import com.chen.basemodule.allroot.RootBean

/**
 *  Created by chen on 2019/6/5
 **/
open class BaseBean : RootBean {

    override var isHideBaseListItem: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other != null) {
            if (JSON.toJSONString(this) == JSON.toJSONString(other)) {
                return true
            }
        }
        return super.equals(other)
    }
}