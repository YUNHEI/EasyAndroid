package com.chen.baseextend.stepby.oo.base

abstract class BaseSwitch(val key: String, var openStatus: Boolean = false) {

    val panel: BaseOOPanel by lazy { BaseOOPanel() }

    val isOpen: Boolean
        get() = openStatus

    init {
        define()
    }

    abstract fun define()

    fun open(o: Any? = null, self: Boolean = false): Boolean {
        openStatus = true
        if (self) onOpen(o)
        panel.open(o!!)
        return false
    }

    fun close(o: Any? = null, self: Boolean = false): Boolean {
        openStatus = false
        if (self) onClose(o)
        panel.close(o!!)
        return false
    }

    open fun onOpen(o: Any?) {}

    open fun onClose(o: Any?) {}

    fun clear() {
        panel.clear()
    }
}
