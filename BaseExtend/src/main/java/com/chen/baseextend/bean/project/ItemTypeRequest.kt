package com.chen.baseextend.bean.project

import com.chen.basemodule.network.base.BaseRequest

/**
 * @author alan
 * @date 2018/11/6
 */
class ItemTypeRequest(
        var itemTypeId: Int = 1, //1,任务类别， 2：步骤 3：任务状态 4：发单管理， 5：钱包操作 6：接单任务状态
        var itemId: String? = "1"
) : BaseRequest()
