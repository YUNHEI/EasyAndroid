package com.chen.app.ui.list

import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Launch
import com.alibaba.android.arouter.facade.enums.SwipeType
import com.chen.app.R
import com.chen.baseextend.base.fragment.SingleListFragment
import com.chen.baseextend.bean.WeatherBean
import com.chen.basemodule.extend.drawable
import com.chen.basemodule.mlist.BaseItemViewHolder
import com.chen.basemodule.mlist.layoutmanager.EchelonLayoutManager
import com.chen.basemodule.mlist.layoutmanager.ItemTouchHelperCallback
import com.chen.basemodule.mlist.layoutmanager.SlideLayoutManager
import com.chen.basemodule.network.base.BaseRequest
import com.chen.basemodule.network.base.BaseResponse
import kotlinx.android.synthetic.main.item_title.view.*

@Launch
class EchelonListSampleFragment : SingleListFragment<WeatherBean>() {

    override fun initAndObserve() {
        toolbar.run {
            center("banner列表")
            left(R.mipmap.ic_back) { activity?.finish() }
        }

        //禁用分页
        disableLoadPageData()

        mRefresh.isEnableRefresh = false
    }

    override val lManager: RecyclerView.LayoutManager by lazy { EchelonLayoutManager() }

    //item 点击事件
    override fun initClickListener() {
        listenItemClick { _, _, data, _, _, _ ->
            "点击了 - ${data!!.week}"
        }
    }

    //网络请求
    override fun loadData(refresh: Boolean, lastItem: WeatherBean?): LiveData<BaseResponse<MutableList<WeatherBean>>>? {
        return viewModel.run {
            requestData(
                    { weatherService.getWeekWeather(BaseRequest()).apply { status = 200 } }
            )
        }
    }

    //item 样式
    override val itemLayoutId = R.layout.item_title

    //item 数据绑定
    override fun bindItemData(viewHolder: BaseItemViewHolder, data: WeatherBean, position: Int, realP: Int) {
        viewHolder.itemView.run {
            _title.text = "${data.week} 天气:${data.wea}"
            background = drawable(R.color.main_theme)
        }
    }
}