package com.chen.basemodule.basem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentBindingDelegate<VB : ViewBinding>(
    private val inflate: (li: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB
) : ReadOnlyProperty<BaseFragment, VB> {

    private var isInitialized = false
    private var _binding: VB? = null
    private val binding: VB get() = _binding!!

    override fun getValue(thisRef: BaseFragment, property: KProperty<*>): VB {
        if (!isInitialized) {
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroyView() {
                    _binding = null
                }
            })
            _binding = inflate.invoke(thisRef.layoutInflater, thisRef.tempContainer, false)
            thisRef.tempContainer = null
            isInitialized = true
        }
        return binding
    }
}