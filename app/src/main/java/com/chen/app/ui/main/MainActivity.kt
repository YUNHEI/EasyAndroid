package com.chen.app.ui.main

import android.view.KeyEvent
import com.chen.basemodule.basem.BaseSimActivity

class MainActivity : BaseSimActivity() {

    override val fragment by lazy { MainFragment() }
    
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        return if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            moveTaskToBack(true)
        } else super.onKeyDown(keyCode, event)
    }

}