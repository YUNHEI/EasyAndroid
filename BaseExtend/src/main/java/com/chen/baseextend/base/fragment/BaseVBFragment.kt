package com.chen.baseextend.base.fragment

import androidx.viewbinding.ViewBinding

abstract class BaseVBFragment<VB : ViewBinding> : BaseSimpleFragment() {

    lateinit var vb: VB


}
