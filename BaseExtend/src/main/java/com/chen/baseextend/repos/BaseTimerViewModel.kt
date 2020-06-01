package com.chen.baseextend.repos
import androidx.lifecycle.MutableLiveData
import android.os.CountDownTimer

import com.chen.basemodule.util.PrefUtils

class BaseTimerViewModel : MainViewModel() {

    var countDownTime = MutableLiveData<Int>()

    private var timer: CountDownTimer? = null

    var isCounting = false

    protected val key = ""

    init {

        val millTime = PrefUtils.getLong(key + GET_CODE_MILL_TIME)

        val count = millTime + SEND_SMS_PERIOD * 1000 - System.currentTimeMillis()

        if (count > 1000) {
            initTimer(count)

            timer!!.start()

            isCounting = true
        }

    }

    private fun initTimer(mill: Long) {
        timer = object : CountDownTimer(mill, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished >= 1000) {
                    countDownTime.value = (millisUntilFinished / 1000).toInt()
                }
            }

            override fun onFinish() {

                isCounting = false
                countDownTime.value = 0
                this.cancel()
                timer = null
            }
        }
    }

    fun startTimer() {

        PrefUtils.putLong(key + GET_CODE_MILL_TIME, System.currentTimeMillis())

        if (timer == null) initTimer((SEND_SMS_PERIOD * 1000).toLong())

        timer!!.start()

        isCounting = true
    }

    fun stopTimer() {
        if (timer != null) timer!!.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }

    companion object {

        private val GET_CODE_MILL_TIME = "get_code_mill_time"

        //验证码发送间隔
        val SEND_SMS_PERIOD = 60
    }
}
