package com.chen.basemodule.allroot

import androidx.fragment.app.Fragment

/**
 * fragment 空节点，所有fragment都是RootFragment子节点，不允许在根节点实现任何方法
 *  Created by chen on 2019/5/28
 **/
abstract class RootFragment : Fragment()