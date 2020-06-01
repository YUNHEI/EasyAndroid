package com.chen.baseextend.bean


import com.chen.basemodule.basem.BaseBean

/**
 * @author alan
 * @date 2019/3/28
 */
data class UnReadMsgBean(

        var newCommentCount: Int = 0,
        var newPraiseCount: Int = 0
) : BaseBean()
