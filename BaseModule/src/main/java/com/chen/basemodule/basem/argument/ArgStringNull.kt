package com.chen.basemodule.basem.argument

import com.chen.basemodule.basem.BaseFragment
import com.chen.basemodule.extend.argString
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 *  Created by 86152 on 2020-02-23
 **/

class ArgStringNull(val key: String? = null) : ReadOnlyProperty<BaseFragment, String?>, java.io.Serializable {

    private val UNINITIALIZED_VALUE = "#UNINITIALIZED_VALUE#"

    private var _value: String? = UNINITIALIZED_VALUE

    fun isInitialized(): Boolean = _value == UNINITIALIZED_VALUE

    override fun toString(): String = if (isInitialized()) _value.orEmpty() else "Lazy value not initialized yet."

    override fun getValue(thisRef: BaseFragment, property: KProperty<*>): String? {

        if (_value === UNINITIALIZED_VALUE) {
            _value = thisRef.argString(key ?: property.name)
        }
        return _value
    }


}