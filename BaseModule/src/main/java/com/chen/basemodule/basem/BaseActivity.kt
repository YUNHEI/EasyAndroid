package com.chen.basemodule.basem

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.chen.basemodule.R
import com.chen.basemodule.allroot.RootActivity
import com.chen.basemodule.extend.color

abstract class BaseActivity : RootActivity() {

    protected abstract val fragment: BaseFragment?

    protected abstract val contentLayoutId: Int

    @SuppressLint("PrivateApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {

                    val decorViewClazz = Class.forName("com.android.internal.policy.DecorView")
                    val field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor")
                    field.isAccessible = true
                    field.setInt(window.decorView, Color.TRANSPARENT)  //改为透明
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            window.statusBarColor = color(R.color.trans00)
        }

        if (contentLayoutId != -1) {
            setContentView(contentLayoutId)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.remove("android:support:fragments")
    }

    /**
     * call this method when layout is contain fragment container
     *
     * @param fragment
     */
    protected fun setFragment(@IdRes id: Int, fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(id, fragment, fragment::class.simpleName)
                .commitAllowingStateLoss()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return if (fragment is BaseFragment) {
            (fragment as BaseFragment).onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event)
        } else {
            super.onKeyUp(keyCode, event)
        }
    }
}
