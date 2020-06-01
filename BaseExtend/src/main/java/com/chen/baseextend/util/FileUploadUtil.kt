package com.chen.baseextend.util

import com.chen.baseextend.config.AppConfig.QINIU_HOST
import com.chen.baseextend.repos.SystemRepos
import com.chen.basemodule.network.base.BaseErrorResponse
import com.chen.basemodule.util.ImageUtil
import com.qiniu.android.storage.UploadManager
import com.qiniu.android.storage.UploadOptions
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.io.File

/**
 *  Created by chen on 2019/8/14
 **/
object FileUploadUtil {


    /**
     * 上传回调在io线程，更新ui请回到ui线程
     */
    fun uploadFile(path: String, onComplete: ((hashes: String) -> Unit)? = null, onProgress: ((progress: Pair<Int, Int>) -> Unit)? = null,
                   onFailure: ((info: Pair<Int, String>) -> Boolean)? = null, customOptions: UploadOptions? = null, sync: Boolean = false) {
        uploadFiles(mutableListOf(path), onComplete, onProgress, onFailure, customOptions, sync)
    }


    /**
     * 同步上传
     */
    fun uploadSyncFile(path: String, onProgress: ((progress: Pair<Int, Int>) -> Unit)? = null,
                       onFailure: ((info: Pair<Int, String>) -> Boolean)? = null, customOptions: UploadOptions? = null): String? {

        if (path.isEmpty()) {
            onFailure?.invoke(-1 to "上传图片为空")
            return null
        }

        val options: UploadOptions

        options = customOptions ?: UploadOptions(null, "image", true,
                { _, per ->
                    onProgress?.invoke(100 to per.times(100).toInt())
                }, null)

        val res = SystemRepos.service
                .getQiNiuToken()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .onErrorReturn { t: Throwable -> BaseErrorResponse(t) }
                .blockingFirst()

        if (res.suc()) {
            File(path).let {
                if (it.exists() && it.length() > 1) {

                    val size = ImageUtil.getImageWidthHeight(path)

                    val output = ImageUtil.compressImage(it)


                    val resp = UploadManager().syncPut(output, null, res.data, options)

                    return QINIU_HOST + resp.response.optString("hash") + "?width=" + size.first + "&height=" + size.second

                } else {
                    onFailure?.invoke(1 to "文件不存在")
                }
            }
        } else {
            onFailure?.invoke(-1 to "授权失败: ${res.message}")
        }
        return null
    }

    /**
     * 上传回调在io线程，更新ui请回到ui线程
     */
    fun uploadFiles(paths: List<String>, onComplete: ((hashes: String) -> Unit)? = null, onProgress: ((progress: Pair<Int, Int>) -> Unit)? = null,
                    onFailure: ((info: Pair<Int, String>) -> Boolean)? = null, customOptions: UploadOptions? = null, sync: Boolean = false) {

        if (paths.isEmpty()) {
            onFailure?.invoke(-1 to "上传图片为空")
            return
        }

        var isCancel = false

        var keepOrigin = false

        val options: UploadOptions
        if (paths.size == 1) {
            keepOrigin = paths[0].endsWith("gif", true)
            options = customOptions ?: UploadOptions(null, "image", true,
                    { _, per ->
                        onProgress?.invoke(100 to per.times(100).toInt())
                    }, null)

        } else {
            options = customOptions
                    ?: UploadOptions(null, "image", true, null, { isCancel })
        }

        val hashes = mutableListOf<Pair<Int, String>>()

        val ob = SystemRepos.service
                .getQiNiuToken()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .onErrorReturn { t: Throwable -> BaseErrorResponse(t) }
                .subscribe { o ->
                    if (o.suc() && !o.data.isNullOrEmpty()) {

                        paths.forEachIndexed { i, s ->
                            File(s).let {
                                if (it.exists() && it.length() > 1) {

                                    val size = ImageUtil.getImageWidthHeight(s)

                                    val output =  if (keepOrigin) it.readBytes() else ImageUtil.compressImage(it)

                                    fun handle(response: JSONObject?) {
                                        if (response != null) {
                                            hashes.add(i to QINIU_HOST + response.optString("hash") + "?width=" + size.first + "&height=" + size.second)
                                        } else {
                                            val interrupt = onFailure?.invoke(i to "文件不存在")
                                            isCancel = interrupt ?: false
                                            hashes.add(i to "upload fail")
                                        }

                                        when {
                                            isCancel -> onFailure?.invoke(-1 to "cancel upload")
                                            hashes.size == paths.size -> {
                                                onComplete?.run {
                                                    hashes.run {
                                                        sortBy { it.first }
                                                        onComplete.invoke(joinToString(",") { it.second })
                                                    }
                                                }
                                            }
                                            else -> onProgress?.invoke(paths.size to hashes.size)
                                        }
                                    }

                                    if (sync) {
                                        val resp = UploadManager().syncPut(output, null, o.data, options)
                                        handle(resp.response)
                                    } else {
                                        UploadManager().put(output, null, o.data, { _, _, response ->
                                            handle(response)
                                        }, options)
                                    }

                                } else {
                                    val interrupt = onFailure?.invoke(i to "文件不存在")
                                    isCancel = interrupt ?: false
                                    hashes.add(i to "upload fail file not exists")
                                    if (interrupt == true) return@subscribe
                                }
                            }
                        }
                    } else {
                        onFailure?.invoke(-1 to "授权失败: ${o.message}")
                    }
                }
    }
}