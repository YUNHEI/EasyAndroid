package com.chen.app.ui.simple

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Launch
import com.chen.app.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.basemodule.extend.listenClick
import com.upchina.sdk.market.UPMarketCallback
import com.upchina.sdk.market.UPMarketConstant
import com.upchina.sdk.market.UPMarketManager
import com.upchina.sdk.market.UPMarketParam
import kotlinx.android.synthetic.main.fragment_print.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Launch
class PrintFragment : BaseSimpleFragment() {


    override val contentLayoutId = R.layout.fragment_print

    override suspend fun onReady() {
        super.onReady()


        withContext(Dispatchers.Default) {

            toolbar.run {
                center("打印")
                left(R.mipmap.ic_back) { activity?.finish() }
            }

            listenClick(_print) {
                when (it) {
                    _print -> {
                        println(_edit.text)
                    }
                    else -> {
                    }
                }
            }
        }
    }

    override fun initAndObserve() {
        super.initAndObserve()

        listenClick(_print_hq) {
            when (it) {
                _print_hq ->{

                    val param = UPMarketParam(UPMarketConstant.SETCODE_SZ, "000002")
                    UPMarketManager.requestStockHq(context, param) { upMarketResponse ->

                        if (upMarketResponse.isSuccessful && activity?.isFinishing == false) {
                            val stockData = upMarketResponse.data

                            if (stockData != null) {
                                Log.d("UPMarketSDKTest", "名称:" + stockData.name)
                                Log.d("UPMarketSDKTest", "现价:" + stockData.nowPrice)
                                Log.d("UPMarketSDKTest", "涨跌幅:" + stockData.changeRatio)
                                Log.d("UPMarketSDKTest", "涨跌额:" + stockData.changeValue)
                                Log.d("UPMarketSDKTest", "昨收:" + stockData.yClosePrice)
                                Log.d("UPMarketSDKTest", "今开:" + stockData.openPrice)
                            }
                        }
                    }
                }
                else -> {
                }
            }
        }
    }
}