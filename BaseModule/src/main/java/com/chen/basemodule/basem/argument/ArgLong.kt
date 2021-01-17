package com.chen.basemodule.basem.argument

import com.chen.basemodule.basem.BaseFragment
import com.chen.basemodule.extend.argLong
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 *  Created by 86152 on 2020-02-23
 **/

class ArgLong(val default: Long = 0, val key: String? = null) : ReadOnlyProperty<BaseFragment, Long>, java.io.Serializable {

    private var _value: Long? = null

    fun isInitialized(): Boolean = _value == null

    override fun toString(): String = if (isInitialized()) _value.toString() else "Lazy value not initialized yet."

    override fun getValue(thisRef: BaseFragment, property: KProperty<*>): Long {

        if (_value == null) {
            _value = thisRef.argLong(key ?: property.name, default)
        }
        return _value ?: default
    }


}