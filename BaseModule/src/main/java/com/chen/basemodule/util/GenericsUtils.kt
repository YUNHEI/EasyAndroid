package com.chen.basemodule.util

import java.lang.reflect.ParameterizedType

/**
 * 用于处理泛型的工具类
 * Created by chen on 2019/4/28
 */
object GenericsUtils {

    /**
     * 获取泛型对象的Class
     */
    fun <T> findGenericsClass(clazz: Class<*>, index: Int): Class<T>? {

        val pt = clazz.genericSuperclass as ParameterizedType

        val actualTypeArguments = pt.actualTypeArguments

        return if (actualTypeArguments.size > index) actualTypeArguments[index] as Class<T> else null
    }

    /**
     * 获取泛型对象的Class
     */
    fun <T> findGenericsClass(sourceClazz: Class<*>, target: Class<*>): Class<T>? {

        val pt = sourceClazz.genericSuperclass as ParameterizedType

        val actualTypeArguments = pt.actualTypeArguments

        for (a in actualTypeArguments) {
            if (a.javaClass == target::class.java) {
                return a as Class<T>
            }
        }
        return null
    }
}
