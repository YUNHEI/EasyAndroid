package com.chen.baseextend.bean.mz

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

/**
 *  Created by chen on 2019/7/12
 **/
data class UserInfoAndAccountBean(
        val basicInfo: UserInfoBean,
        val freezeAmount: BigDecimal = BigDecimal(0),
        val waitCashOutAmount: BigDecimal = BigDecimal(0),
        val walletInfo: WalletInfoBean? = null) : BaseBean()