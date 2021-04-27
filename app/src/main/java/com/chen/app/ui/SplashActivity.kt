package com.chen.app.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.chen.app.R
import com.chen.app.databinding.ActivitySplashBinding
import com.chen.app.ui.main.MainActivity
import com.chen.baseextend.base.activity.BaseFragmentActivity
import com.chen.baseextend.bean.ADRequest
import com.chen.baseextend.bean.AdvertBean
import com.chen.baseextend.ui.WebActivity
import com.chen.basemodule.constant.BasePreference
import com.chen.basemodule.extend.doBinding
import com.chen.basemodule.extend.load
import com.chen.basemodule.widget.dialog.WarningDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import java.util.*

class SplashActivity : BaseFragmentActivity() {

    private val permissions by lazy { RxPermissions(this) }

    private val dialog by lazy { WarningDialog(this) }

    private var timer: CountDownTimer? = null

    override val binding by doBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //防止点击图标重启启动页
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
//        setContentView(R.layout.activity_splash)

        if (!BasePreference._FIRST_START && false) {
            viewModel.run {
                requestData(
                        { adService.listAdvertise(ADRequest("1")) },
                        { o ->
                            if (o.data.isNullOrEmpty()) {
                                binding.IvAdvert.setImageResource(R.mipmap.bg_invite)
                                toNext()
                            } else {
                                showAdvert(o.data!![Random().nextInt(o.data!!.size)])
                            }
                        },
                        {
                            binding.IvAdvert.setImageResource(R.mipmap.bg_invite)
                            toNext()
                        }
                )
            }

        } else {
            BasePreference._FIRST_START = false
            toNext()
        }

    }

    private fun showAdvert(bean: AdvertBean) {

        if (bean.bg_pic == null) {
            toNext()
        } else {
            initTimer(3)

            binding.Skip.setOnClickListener {
                timer!!.cancel()
                toNext()
            }

            timer!!.start()

            binding.IvAdvert.load(bean.bg_pic, isOriginSize = true)

            if (bean.jumpType == 2) {
                binding.IvAdvert.setOnClickListener {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    WebActivity.toWebView(this, "www.baidu.com")
                    if (timer != null) {
                        timer!!.cancel()
                    }
                    finish()
                }
            }
        }

    }

    private fun initTimer(second: Int) {

        binding.Skip.visibility = View.VISIBLE
        binding.Tip.text = String.format("%s", second)

        timer = object : CountDownTimer((second * 1000 + 900).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.e("time", millisUntilFinished.toString() + "")
                val t = (millisUntilFinished / 1000).toInt()
                if (t > 0) binding.Tip.text = String.format("%s", t)

                if (t == 2) {
                    binding.Tip.post { toNext() }
                }
            }

            override fun onFinish() {
                binding.Tip.post { toNext() }
            }

        }

    }

    private fun toNext() {

        val subscribe = permissions.request(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE).subscribe {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                dialog.setMsg("请授权相关权限")
                        .setConfirm("退出", R.color.error) {
                            finish()
                            it.dismiss()
                        }
                        .show()
            }
        }
    }

    override fun onDestroy() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
        super.onDestroy()
    }

}
