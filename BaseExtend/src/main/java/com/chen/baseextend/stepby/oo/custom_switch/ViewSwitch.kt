package com.chen.baseextend.stepby.oo.custom_switch

import android.view.View

import com.chen.baseextend.stepby.oo.base.BaseSwitch

open class ViewSwitch protected constructor(private val view: View, private val type: String) : BaseSwitch(view.id.toString() + type) {

    companion object {

        val TYPE_CLICK = "click"

        val TYPE_FOCUS = "focus"

        val TYPE_VISIBLE = "visible"
    }

    override fun define() {
        when (type) {
            TYPE_CLICK -> view.setOnClickListener {
                open(it, true)
                close(it, true)
            }
            TYPE_VISIBLE -> view.setOnSystemUiVisibilityChangeListener { visibility ->
                if (View.VISIBLE == visibility) {
                    open(view, true)
                } else {
                    close(view, true)
                }
            }
            TYPE_FOCUS -> view.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    open(v, true)
                } else {
                    close(v, true)
                }
            }
        }
    }

    override fun onOpen(o: Any?) {
        when (type) {
            TYPE_CLICK -> view.performClick()
            TYPE_FOCUS -> view.requestFocus()
            TYPE_VISIBLE -> view.visibility = View.VISIBLE
        }
    }

    override fun onClose(o: Any?) {
        when (type) {
            TYPE_FOCUS -> view.clearFocus()
            TYPE_VISIBLE -> view.visibility = View.GONE
        }
    }
}
