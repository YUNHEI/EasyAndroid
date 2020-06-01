package com.chen.baseextend.bean.project

import androidx.room.Entity
import androidx.room.Ignore
import com.chen.basemodule.basem.BaseOfflineRoomBean
import java.math.BigDecimal

/**
 *  Created by chen on 2019/8/14
 **/
@Entity(primaryKeys = ["id", "category"])
data class ItemTypeBean(
        var id: String = (0..9999999999).random().toString() + System.currentTimeMillis(),//类型id,
        var index: Int? = null,
        var itemName: String? = null,
        var itemDesc: String? = null,
        var itemId: String? = null,
        var imageUrl: String? = null,//网页地址
        var itemTypeDesc: String? = null,
        var itemTypeId: Int? = null,
        var parentId: Int? = null,
        var minNum: Int = 0,
        var minUnitprice: BigDecimal = BigDecimal(0),
        var userType: Int = 0,
        var bond: BigDecimal = BigDecimal(0),
        var serviceChargeProportion: BigDecimal = java.math.BigDecimal(0),
        var discount: BigDecimal = BigDecimal(1),
        var rootId: Int? = null,
        @Ignore
        var children: MutableList<ItemTypeBean>? = null
) : BaseOfflineRoomBean()