package com.chen.basemodule.allroot

import androidx.appcompat.app.AppCompatActivity

/**
 * activity 空节点，所有activity都是RootActivity子节点，不允许在根节点实现任何方法
 *  Created by chen on 2019/5/28
 **/
abstract class RootActivity : AppCompatActivity()