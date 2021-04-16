package com.chen.basemodule.extend

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.chen.basemodule.R
import com.chen.basemodule.util.FileUtil
import com.chen.basemodule.util.ImageUtil
import com.chen.basemodule.util.ImageUtil.getImageContentUri
import kotlin.math.max
import kotlin.math.min

/**
 *  Created by chen on 2019/6/25
 **/
fun ImageView.load(url: String? = null, @DrawableRes place: Int = R.drawable.ic_placeholder, isOriginSize: Boolean = false, thumPath: String? = null,
                   imageWidth: Float = 0f, imageHeight: Float = 0f, autoAdjust: Boolean = false, vararg trans: Transformation<Bitmap>) {

    val manager = Glide.with(this@load)

    val options = RequestOptions()
            .placeholder(place)//加载成功之前占位图
            .error(place)//加载成功之前占位图
            .skipMemoryCache(false)//跳过内存缓存
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)//图像缓存

    if (url.isNullOrEmpty()) {
        manager.load(url)
                .apply(options)
                .into(this@load)
        return
    }

    var bitmapWidth = 0f
    var bitmapHeight = 0f

    if (FileUtil.isLocalPath(url)) {
        ImageUtil.getImageWidthHeight(url).run {
            bitmapWidth = first.toFloat()
            bitmapHeight = second.toFloat()
        }
    } else if (url.startsWith("http") && url.contains("?") && url.contains("width=", true)) {
        val patternWidth = "width=(\\d+)"
        val patternHeight = "height=(\\d+)"
        bitmapWidth = Regex(patternWidth, RegexOption.IGNORE_CASE).find(url)?.groupValues?.get(1)?.toFloat()
                ?: 0f
        bitmapHeight = Regex(patternHeight, RegexOption.IGNORE_CASE).find(url)?.groupValues?.get(1)?.toFloat()
                ?: 0f
    }

    if (bitmapWidth <= 0) bitmapWidth = imageWidth

    if (bitmapHeight <= 0) bitmapHeight = imageHeight

    val viewMinWidth = minimumWidth.toFloat()
    val viewMaxWidth = maxWidth.toFloat()

    val viewMinHeight = minimumHeight.toFloat()
    val viewMaxHeight = maxHeight.toFloat()


    val transList = mutableListOf<Transformation<Bitmap>>()

    if (isOriginSize) {
        transList.add(CenterInside())
        if (trans.isNotEmpty()) transList.addAll(trans)
        val transform = options.transform(MultiTransformation(transList))

        if (FileUtil.isLocalPath(url)) {
            manager.load(getImageContentUri(context, url))
                    .apply(transform)
                    .transition(DrawableTransitionOptions.withCrossFade() as TransitionOptions<*, in Drawable>)
                    .into(this@load)
        }else if (bitmapHeight <= 0 || bitmapWidth <= 0 || !url.contains("qiniu.")) {
            manager.load(url)
                    .apply(transform)
                    .transition(DrawableTransitionOptions.withCrossFade() as TransitionOptions<*, in Drawable>)
                    .into(this@load)
        } else {

            manager.load(url).run {
                if (!thumPath.isNullOrEmpty()) thumbnail(manager.load(thumPath))
                apply(transform).transition(DrawableTransitionOptions.withCrossFade() as TransitionOptions<*, in Drawable>)
                        .into(this@load)
            }
        }
    } else if (autoAdjust && bitmapHeight > 0 && bitmapWidth > 0) {

        var zoomIn :Float
        var zoomInWidth = 1f
        var zoomInHeight = 1f
        var zoomOut :Float
        var zoomOutWidth = 1f
        var zoomOutHeight = 1f

        if (viewMinWidth > 0) {
            zoomInWidth = viewMinWidth.div(bitmapWidth)
        }
        if (viewMinHeight > 0) {
            zoomInHeight = viewMinHeight.div(bitmapHeight)
        }
        var scale = if (zoomInWidth > 1 || zoomInHeight > 1) {
            max(zoomInWidth, zoomInHeight)
        } else {
            if (viewMaxWidth > 0) {
                zoomOutWidth = viewMaxWidth.div(bitmapWidth)
            }
            if (viewMaxHeight > 0) {
                zoomOutHeight = viewMaxHeight.div(bitmapHeight)
            }
            zoomOut = min(zoomOutWidth, zoomOutHeight)
            zoomIn = max(zoomInWidth, zoomInHeight)
            max(zoomOut, zoomIn)
        }

        layoutParams.run {
            width = (if (viewMaxWidth > 0) min(viewMaxWidth, bitmapWidth.times(scale)) else bitmapWidth.times(scale)).toInt()
            height = (if (viewMaxHeight > 0) min(viewMaxHeight, bitmapHeight.times(scale)) else bitmapHeight.times(scale)).toInt()
        }


        transList.add(CenterCrop())
        if (trans.isNotEmpty()) transList.addAll(trans)
        val transform = options.transform(MultiTransformation(transList))

        if (FileUtil.isLocalPath(url)) {
            manager.load(getImageContentUri(context, url))
                    .apply(transform)
                    .transition(DrawableTransitionOptions.withCrossFade() as TransitionOptions<*, in Drawable>)
                    .into(this@load)
        }else if (!url.contains("qiniu.")) {
            manager.load(url)
                    .apply(transform)
                    .transition(DrawableTransitionOptions.withCrossFade() as TransitionOptions<*, in Drawable>)
                    .into(this@load)
        } else {
            val params = "imageView2/1/w/${width}/h/${height}/format/jpg/q/90|imageslim"
            val resizeUrl = url.run { if (contains("?")) "$this&$params" else "$this?$params" }

            manager.load(resizeUrl)
                    .apply(transform)
                    .transition(DrawableTransitionOptions.withCrossFade() as TransitionOptions<*, in Drawable>)
                    .into(this@load)
        }
    } else if (layoutParams.width > 0 && layoutParams.height > 0) {
        val builder = if (FileUtil.isLocalPath(url)) manager.load(getImageContentUri(context, url)) else manager.load(url)
        builder.centerCrop()
                .apply(options.apply {
                    if (trans.isNotEmpty()) {
                        transform(MultiTransformation(*trans))
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade() as TransitionOptions<*, in Drawable>)
                .into(this@load)

    } else {
        viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {

            override fun onPreDraw(): Boolean {

                val viewWidth = width.toFloat()
                val viewHeight = height.toFloat()

                var desWidth :Float
                var desHeight = 0f

                if (viewWidth > 0 && viewHeight > 0) {

                    val builder = if (FileUtil.isLocalPath(url)) manager.load(getImageContentUri(context, url)) else manager.load(url)
                    builder.centerCrop()
                            .apply(options.apply {
                                if (trans.isNotEmpty()) {
                                    transform(MultiTransformation(*trans))
                                }
                            })
                            .transition(DrawableTransitionOptions.withCrossFade() as TransitionOptions<*, in Drawable>)
                            .into(this@load)

                } else {
                    transList.add(CenterCrop())
                    if (trans.isNotEmpty()) transList.addAll(trans)

                    if ((viewWidth > 0 || viewHeight > 0) && !autoAdjust) {
                        when {
                            viewHeight <= 0 -> {
                                desWidth = viewWidth
                                desHeight = bitmapHeight.times(desWidth.div(bitmapWidth))
                            }
                            viewWidth <= 0 -> {
                                desWidth = bitmapWidth.times(desHeight.div(bitmapHeight))
                                desHeight = viewHeight
                            }
                            else -> {
                                desWidth = viewWidth
                                desHeight = viewHeight
                            }
                        }
                    } else {
                        var zoomIn :Float
                        var zoomInWidth = 1f
                        var zoomInHeight = 1f
                        var zoomOut = 1f
                        var zoomOutWidth = 1f
                        var zoomOutHeight = 1f

                        if (viewMinWidth > 0) {
                            zoomInWidth = viewMinWidth.div(bitmapWidth)
                        }
                        if (viewMinHeight > 0) {
                            zoomInHeight = viewMinHeight.div(bitmapHeight)
                        }
                        var scale = if (zoomInWidth > 1 || zoomInHeight > 1) {
                            max(zoomInWidth, zoomInHeight)
                        } else {
                            if (viewMaxWidth > 0) {
                                zoomOutWidth = viewMaxWidth.div(bitmapWidth)
                            }
                            if (viewMaxHeight > 0) {
                                zoomOutHeight = viewMaxHeight.div(bitmapHeight)
                            }
                            zoomOut = min(zoomOutWidth, zoomOutHeight)
                            zoomIn = max(zoomInWidth, zoomInHeight)
                            max(zoomOut, zoomIn)
                        }

                        desWidth = if (viewMaxWidth > 0) min(viewMaxWidth, bitmapWidth.times(scale)) else bitmapWidth.times(scale)

                        desHeight = if (viewMaxHeight > 0) min(viewMaxHeight, bitmapHeight.times(scale)) else bitmapHeight.times(scale)
                    }

                    val imageUrl = if (imageWidth <= 0 && !FileUtil.isLocalPath(url)) {
                        val params = "imageView2/1/w/${desWidth.toInt()}/h/${desHeight.toInt()}/format/jpg/q/75|imageslim"
                        url.run { if (contains("?")) "$this&$params" else "$this?$params" }
                    } else {
                        url.orEmpty()
                    }

                    layoutParams?.run {
                        if (autoAdjust || (width == 0 && height == 0)) {
                            width = desWidth.toInt()
                            height = desHeight.toInt()
                        }
                    }

                    manager.load(url.run { if (FileUtil.isLocalPath(url)) getImageContentUri(context, url) else imageUrl }).run {
                        apply(options.apply {
                            if (autoAdjust && FileUtil.isLocalPath(url)) override(desWidth.toInt(), desHeight.toInt())
                            if (trans.isNotEmpty()) {
                                transform(MultiTransformation(transList))
                            }
                        }).transition(DrawableTransitionOptions.withCrossFade() as TransitionOptions<*, in Drawable>)
                                .into(this@load)
                    }
                }
                viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }
}

fun ImageView.loadSimple(url: String? = null) {

    val options = RequestOptions()
            .skipMemoryCache(false)//跳过内存缓存
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)//图像缓存

    Glide.with(this)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade() as TransitionOptions<*, in Drawable>)
            .apply(options)
            .into(this)
}

fun ImageView.avatar(url: String? = null){
    load(url, R.mipmap.ic_default_avatar)
}

fun ImageView.loadBitmap(url: String? = null) {

    val options = RequestOptions()
            .skipMemoryCache(false)//跳过内存缓存
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)//图像缓存

    Glide.with(this)
            .asBitmap()
            .load(url)
            .apply(options)
            .into(object : CustomViewTarget<ImageView, Bitmap>(this) {
                override fun onLoadFailed(errorDrawable: Drawable?) {
                }

                override fun onResourceCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    setImageBitmap(resource)
                }
            })
}

fun ImageView.release() {
    Glide.with(this).clear(this)
}


