package com.chen.basemodule.basem.argument

import com.chen.basemodule.basem.BaseFragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 *  Created by 86152 on 2020-02-23
 **/

class ArgString(val default: String? = null, val key: String? = null) : ReadOnlyProperty<BaseFragment, String>, java.io.Serializable {

    private val UNINITIALIZED_VALUE = "#UNINITIALIZED_VALUE#"

    private var _value: String = UNINITIALIZED_VALUE

    fun isInitialized(): Boolean = _value == UNINITIALIZED_VALUE

    override fun toString(): String = if (isInitialized()) _value.toString() else "Lazy value not initialized yet."

    override fun getValue(thisRef: BaseFragment, property: KProperty<*>): String {

        if (_value === UNINITIALIZED_VALUE) {
            _value = thisRef.arguments?.getString(key ?: property.name) ?: run {
                if (default == null){
                    throw RuntimeException("属性名:${property.name} 参数为空,或者属性名被混淆")
                }else {
                    return default
                }
            }
        }
        return _value
    }


}