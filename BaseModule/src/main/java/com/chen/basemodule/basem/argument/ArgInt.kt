package com.chen.basemodule.basem.argument

import com.chen.basemodule.basem.BaseFragment
import com.chen.basemodule.extend.argInt
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 *  Created by 86152 on 2020-02-23
 **/

class ArgInt(val default: Int = 0, val key: String? = null) : ReadOnlyProperty<BaseFragment, Int>,
    java.io.Serializable {

    private var _value: Int? = null

    fun isInitialized(): Boolean = _value == null

    override fun toString(): String =
        if (isInitialized()) _value.toString() else "Lazy value not initialized yet."

    override fun getValue(thisRef: BaseFragment, property: KProperty<*>): Int {

        if (_value == null) {
            _value = thisRef.argInt(key ?: property.name, default)
        }
        return _value ?: default
    }


}