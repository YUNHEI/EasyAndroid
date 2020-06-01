package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BaseRequest

class ModifyPwdRequest(var newPassword: String, var oldPassword: String) : BaseRequest()
