package com.chen.app.ui.delegate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chen.app.R
import com.chen.app.databinding.LayoutStrategyBannerHeaderBinding
import com.chen.baseextend.bean.AdvertBean
import com.chen.baseextend.widget.banner.loader.ImageLoaderInterface
import com.chen.basemodule.allroot.RootBean
import com.chen.basemodule.extend.createBinding
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.load
import com.chen.basemodule.mlist.BaseHeaderViewDelegate
import com.chen.basemodule.mlist.BaseItemViewHolder
import kotlinx.android.synthetic.main.layout_strategy_banner_header.view.*

class BannerHeaderDelegate(context: Context, private val ratio: String? = null) :
    BaseHeaderViewDelegate(context) {

//    override val layoutId = R.layout.layout_strategy_banner_header

    override val binding get() = createBinding(LayoutStrategyBannerHeaderBinding::inflate)

    var bannerData = mutableListOf<AdvertBean>()
        set(value) {
            bannerData.clear()
            bannerData.addAll(value)
        }

    override fun bindData(
        viewHolder: BaseItemViewHolder,
        data: RootBean?,
        position: Int,
        realP: Int
    ) {
        viewHolder.itemView.run {

            _banner.run {

                if (!ratio.isNullOrEmpty()) {
                    (layoutParams as ConstraintLayout.LayoutParams).run {
                        height = 0
                        dimensionRatio = ratio
                    }
                }

                setImageLoader(object : ImageLoaderInterface<View> {
                    override fun displayImage(context: Context?, path: String?, imageView: View?) {
                        (imageView as FrameLayout).run {
                            findViewById<ImageView>(R.id._image).load(
                                path,
                                place = R.drawable.bg_item_white,
                                isOriginSize = true
                            )
                        }
                    }

                    override fun createImageView(context: Context?): View {
                        return LayoutInflater.from(context)
                            .inflate(R.layout.item_banner_image, null)
                    }

                    override fun setInformation(context: Context?, item: AdvertBean?, view: View?) {
                    }

                })

                setOnBannerListener {
//                advertBeans[it].run {
//                    val jObj = JSON.parseObject(param)
//                    when (jumpType) {
//                        2 -> WebActivity.toWebView(context, jObj?.getString("url").orEmpty())
//                        3 -> context.startPage(ProjectDetailFragment::class, "id" to jObj?.getString("taskId").orEmpty())
//                        4 -> {
//                            if(!ReLoginUtil.requestLogin(context)) {
//                                return@run
//                            }
//                            context.startPage(InviteFragment::class)
//                        }
//                        else -> {
//                        }
//                    }
//                }
                }

                setAds(bannerData)

                start()
            }
        }

    }
}