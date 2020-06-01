package com.chen.basemodule.basem.argument

import com.chen.basemodule.basem.BaseFragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 *  Created by 86152 on 2020-02-23
 **/

class ArgFloat(val default: Float = 0F, val key: String? = null) : ReadOnlyProperty<BaseFragment, Float>, java.io.Serializable {

    private var _value: Float? = null

    fun isInitialized(): Boolean = _value == null

    override fun toString(): String = if (isInitialized()) _value.toString() else "Lazy value not initialized yet."

    override fun getValue(thisRef: BaseFragment, property: KProperty<*>): Float {

        if (_value == null) {
            _value = thisRef.arguments?.getFloat(key ?: property.name, default)
        }
        return _value ?: default
    }


}