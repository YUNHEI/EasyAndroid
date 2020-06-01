package com.chen.basemodule.util.preference

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.chen.basemodule.BaseModuleLoad
import com.chen.basemodule.util.preference.bindings.*
import java.lang.reflect.Type
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

abstract class PreferenceHolder {

    val preferences: SharedPreferences by lazy { BaseModuleLoad.context.run { getSharedPreferences(packageName + "cache_preferences", MODE_PRIVATE) } }

    protected inline fun <reified T : Any> bindToPreferenceField(default: T, key: String? = null, caching: Boolean = true): ReadWriteProperty<PreferenceHolder, T> = bindToPreferenceField(T::class, object : TypeToken<T>() {}.type, default, key, caching)

    protected inline fun <reified T : Any> bindToPreferenceFieldNullable(key: String? = null, caching: Boolean = true): ReadWriteProperty<PreferenceHolder, T?> = bindToPreferenceFieldNullable(T::class, object : TypeToken<T>() {}.type, key, caching)

    protected fun <T : Any> bindToPreferenceField(clazz: KClass<T>, type: Type, default: T, key: String?, caching: Boolean = true): ReadWriteProperty<PreferenceHolder, T> = if (caching) PreferenceFieldBinderCaching(clazz, type, default, key) else PreferenceFieldBinder(clazz, type, default, key)

    protected fun <T : Any> bindToPreferenceFieldNullable(clazz: KClass<T>, type: Type, key: String?, caching: Boolean = true): ReadWriteProperty<PreferenceHolder, T?> = if (caching) PreferenceFieldBinderNullableCaching(clazz, type, key) else PreferenceFieldBinderNullable(clazz, type, key)

    /**
     *  Function used to clear all SharedPreference and PreferenceHolder data. Useful especially
     *  during tests or when implementing Logout functionality.
     */
    fun clear(safety: Boolean = true) {
        forEachDelegate { delegate, property ->
            if (safety && property.name.startsWith("_")) return@forEachDelegate
            delegate.clear(this, property)
        }
    }

    fun clearCache() {
        forEachDelegate { delegate, property ->
            delegate.clearCache()
        }
    }

    private fun forEachDelegate(f: (Clearable, KProperty<*>) -> Unit) {
        val properties = this::class.declaredMemberProperties
                .filterIsInstance<KProperty1<SharedPreferences, *>>()
        for (p in properties) {
            val prevAccessible = p.isAccessible
            if (!prevAccessible) p.isAccessible = true
            val delegate = p.getDelegate(preferences)
            if (delegate is Clearable) f(delegate, p)
            p.isAccessible = prevAccessible
        }
    }
}