package com.chen.app.navigationtest

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.chen.app.R
import com.chen.app.ui.simple.toolbar.SimpleFirstFragment
import com.chen.basemodule.basem.BaseSimActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.jvm.jvmName

/**
 * @author CE Chen
 */
class MainActivity : BaseSimActivity() {
    override val contentLayoutId = R.layout.activity_main
}