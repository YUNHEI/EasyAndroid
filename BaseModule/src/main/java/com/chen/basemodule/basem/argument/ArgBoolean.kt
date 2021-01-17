package com.chen.basemodule.basem.argument

import com.chen.basemodule.basem.BaseFragment
import com.chen.basemodule.extend.argBoolean
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 *  Created by 86152 on 2020-02-23
 **/

class ArgBoolean(val key: String? = null, val default: Boolean = false) :
    ReadOnlyProperty<BaseFragment, Boolean>, java.io.Serializable {

    private var _value: Boolean? = null

    fun isInitialized(): Boolean = _value == null

    override fun toString(): String =
        if (isInitialized()) _value.toString() else "Lazy value not initialized yet."

    override fun getValue(thisRef: BaseFragment, property: KProperty<*>): Boolean {

        if (_value == null) {
            _value = thisRef.argBoolean(key ?: property.name)
        }
        return _value ?: default
    }


}