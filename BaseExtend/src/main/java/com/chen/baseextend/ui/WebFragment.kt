package com.chen.baseextend.ui

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import com.just.agentweb.*
import com.chen.baseextend.BaseExtendApplication
import com.chen.baseextend.R
import com.chen.baseextend.base.fragment.BaseSimpleFragment
import com.chen.baseextend.ui.sonic.SonicJavaScriptInterface
import com.chen.baseextend.ui.sonic.SonicRuntimeImpl
import com.chen.baseextend.ui.sonic.SonicSessionClientImpl
import com.chen.basemodule.basem.argument.ArgString
import com.chen.basemodule.constant.BasePreference
import com.chen.basemodule.extend.toastWarn
import com.chen.basemodule.extend.visible
import com.tencent.sonic.sdk.SonicConfig
import com.tencent.sonic.sdk.SonicEngine
import com.tencent.sonic.sdk.SonicSessionConfig
import kotlinx.android.synthetic.main.fragment_web.*


open class WebFragment : BaseSimpleFragment() {

    override val contentLayoutId = R.layout.fragment_web

    open val url by ArgString()

    lateinit var mTitle: TextView

    lateinit var mClose: ImageView

    private var mWebChromeClient: WebChromeClient = object : WebChromeClient() {

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            mTitle.text = title
        }
    }

    private var mWebViewClient: WebViewClient = object : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            if (sonicSession != null) {
                mSessionClient.pageFinish(url)
            }
            mClose.visible(view?.canGoBack() == true)

        }

        @TargetApi(21)
        override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest): WebResourceResponse? {
            return shouldInterceptRequest(view, request.url.toString())
        }

        override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
            return if (sonicSession != null) {
                mSessionClient.requestResource(url) as WebResourceResponse?
            } else null
        }
    }

    val mAgentWeb: AgentWeb by lazy {

        AgentWeb.with(this) //
                .setAgentWebParent(_agent_web, -1, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)) //传入AgentWeb的父控件。
                .useDefaultIndicator(-1, 3) //设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setAgentWebWebSettings(object : AbsAgentWebSettings() {
                    override fun bindAgentWebSupport(agentWeb: AgentWeb?) {

                    }

                }) //设置 IAgentWebSettings。
                .setWebViewClient(mWebViewClient) //WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
                .setPermissionInterceptor { _, _, _ -> false } //权限拦截 2.0.0 加入。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK) //打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb() //创建AgentWeb。
                .ready() //设置 WebSettings。
                .go(null) //WebView载入该url地址的页面并显示。
    }

    private val mSessionClient by lazy { SonicSessionClientImpl(mAgentWeb) }

    private val sonicSession by lazy {
        // if it's sonic mode , startup sonic session at first time
        val sessionConfigBuilder = SonicSessionConfig.Builder()
        sessionConfigBuilder.setSupportLocalServer(true)
        // create sonic session and run sonic flow
        SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build())?.apply {
            bindClient(mSessionClient)
        }
    }

    override fun initAndObserve() {

        toolbar.run {
            mTitle = center("")
            left(R.mipmap.ic_back) {
                if (!mAgentWeb.back()) {
                    activity?.finish()
                }
            }
            mClose = left(R.mipmap.ic_close) {
                activity?.finish()
            }.apply {
                visible(false)
            }
        }

        mAgentWeb.jsInterfaceHolder.addJavaObject("android", AndroidInterface(activity))

        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(SonicRuntimeImpl(BaseExtendApplication.app?.applicationContext), SonicConfig.Builder().build())
        }

        sonicSession?.run {

            //4. 注入 JavaScriptInterface
            mAgentWeb.jsInterfaceHolder.addJavaObject("sonic", SonicJavaScriptInterface(mSessionClient, Intent()))

            mSessionClient.clientReady()
        } ?: run {
            mAgentWeb.urlLoader.loadUrl(url)
        }
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume() //恢复
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause() //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
        super.onPause()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return mAgentWeb.handleKeyEvent(keyCode, event)
    }

    /**
     * 清除 WebView 缓存
     */
    private fun toCleanWebCache() {
        mAgentWeb.clearWebCache()
    }

    /**
     * 打开浏览器
     *
     * @param targetUrl 外部浏览器打开的地址
     */
    private fun openBrowser(targetUrl: String) {
        if (TextUtils.isEmpty(targetUrl) || targetUrl.startsWith("file://")) {
            "$targetUrl 该链接无法使用浏览器打开。".toastWarn()
            return
        }

        startActivity(Intent().apply {
            action = "android.intent.action.VIEW"
            data = targetUrl.toUri()
        })
    }

    override fun onDestroyView() {
        mAgentWeb.webLifeCycle.onDestroy()
        sonicSession?.destroy()
        super.onDestroyView()
    }

    class AndroidInterface(val activity: Activity?) {

        @JavascriptInterface
        fun getToken(): String {
            return BasePreference.USER_TOKEN.orEmpty()
        }

        @JavascriptInterface
        fun finish() {
            activity?.finish()
        }
    }
}
