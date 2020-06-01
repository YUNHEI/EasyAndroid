package com.chen.basemodule.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


/**
 * @author alan
 * @date 2019-06-19
 */
object SystemUtil {

    fun callPhone(context: Context?=null, phone : String){
        val intent = Intent()
        intent.action = Intent.ACTION_CALL
        intent.data = Uri.parse("tel:$phone")
        context?.startActivity(intent)
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName: String = reader.readLine()
            if (processName.isNotEmpty()) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }
}