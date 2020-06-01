package com.chen.basemodule.widget.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * 底部弹出 dialog fragment
 */
abstract class BaseBottomDialogFragment : BottomSheetDialogFragment() {

    abstract val contentLayoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(contentLayoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAndObserve()
    }

    /**
     * onViewCreated 之后调用
     */
    abstract fun initAndObserve()
}