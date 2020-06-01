package com.chen.basemodule.allroot

import androidx.fragment.app.FragmentActivity

/**
 * activity 空节点，特殊的如SplashActivity继承RootFragmentActivity，不允许在根节点实现任何方法
 *  Created by chen on 2019/5/28
 **/
abstract class RootFragmentActivity : FragmentActivity()