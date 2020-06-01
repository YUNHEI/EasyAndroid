package com.chen.basemodule.allroot

import androidx.lifecycle.ViewModel

/**
 *  ViewModel 的根节点，所有ViewModel都是RootViewModel的子节点，不允许在根节点实现任何方法
 *  Created by chen on 2019/5/28
 **/
abstract class RootViewModel : ViewModel()