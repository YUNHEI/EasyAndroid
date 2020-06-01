package com.chen.basemodule.util.preference.bindings

import com.chen.basemodule.util.preference.PreferenceHolder
import kotlin.reflect.KProperty

interface Clearable {
    fun clear(thisRef: PreferenceHolder, property: KProperty<*>)
    fun clearCache()
}