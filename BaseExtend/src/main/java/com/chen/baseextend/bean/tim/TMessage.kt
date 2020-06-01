package com.chen.baseextend.bean.tim

import cn.jpush.im.android.api.enums.MessageDirect
import cn.jpush.im.android.api.model.Message
import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/8/20
 **/
data class TMessage(var message: Message) : BaseBean() {

    val isSelf = message.direct == MessageDirect.send

}