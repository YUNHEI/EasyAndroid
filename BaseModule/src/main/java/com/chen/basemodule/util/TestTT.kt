package com.chen.basemodule.util

import java.util.regex.Pattern

/**
 *  Created by chen on 2019/6/5
 **/
class TestTT {


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val url = "https://xinzhao.mbcloud.com/cms-h5/meetingroom?meet_room_id=abc&schemes=xinzhao://meetingroom/aaa?id=a"

            val schemes = url.substring(url.indexOfLast { c -> c == '?' })

            val p = Pattern.compile("id=(.*)&?")

            val m = p.matcher(schemes)

            while (m.find()) {
                if (m.groupCount() > 0) {
                    val b = m.group(1)
                }
            }
        }
    }

}