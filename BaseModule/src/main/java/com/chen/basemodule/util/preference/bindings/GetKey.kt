package com.chen.basemodule.util.preference.bindings

import kotlin.reflect.KProperty

internal fun getKey(keySet: String?, property: KProperty<*>) = keySet ?: "${property.name}Key"
