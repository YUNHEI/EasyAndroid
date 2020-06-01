package com.chen.baseextend.stepby.oo

import com.chen.baseextend.stepby.oo.base.BaseSwitch

class OOPanelManager {

    val pm by lazy { hashMapOf<String, BaseSwitch>() }

    fun addSwitch(s: BaseSwitch) {
        pm[s.key] = s
    }

    fun <T : BaseSwitch> getSwitch(key: String) = pm[key] as T?

    fun destory() {
        for (s1 in pm.keys) {
            pm[s1] ?.clear()
        }
    }
}
