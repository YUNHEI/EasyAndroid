package com.chen.basemodule.extend

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.chen.basemodule.basem.FragmentBindingDelegate
import com.chen.basemodule.mlist.BaseItemViewDelegate


fun <VB : ViewBinding> Fragment.doBinding(
    inflate: (li: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB
) = FragmentBindingDelegate(inflate)

fun <VB : ViewBinding> Activity.doBinding(inflate: (li: LayoutInflater) -> VB) = lazy {
    inflate.invoke(layoutInflater)
}

fun <VB : ViewBinding> BaseItemViewDelegate<*>.createBinding(
    inflate: (li: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB
) =  inflate.invoke(LayoutInflater.from(context), parent, false)


