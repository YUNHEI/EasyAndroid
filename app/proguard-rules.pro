# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

############fastjson
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*; }

########jsoup 网页解析
-dontwarn org.jsoup.**
-keep class org.jsoup.**{*; }

######微信
-dontwarn com.tencent.mm.opensdk.**
-keep class com.tencent.mm.opensdk.**{*; }
-keep class com.tencent.wxop.** { *; }
-keep class com.tencent.mm.sdk.** { *; }

#okhttp下载进度
-keep class me.jessyan.progressmanager.** { *; }
-keep interface me.jessyan.progressmanager.** { *; }

################rxlifecycle2###############
-dontwarn com.trello.**
-keep class com.trello.** { *; }

################rx###############
-dontwarn rx.**
-keep class rx.** { *; }

################rx###############
-dontwarn com.qiniu.**
-keep class com.qiniu.** { *; }
-keep interface com.qiniu.** { *; }

#################retrofit###############
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Excepti

-dontwarn okio.**

################glide###############
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

################okhttp###############
-keepattributes Signature
-keepattributes EnclosingMethod
-keepattributes *Annotation*
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn com.squareup.okhttp3.**
-dontwarn okhttp3.**

##################arouter##################
-keep public class com.alibaba.android.arouter.routes.**{*;}

###############eventbus#############################
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}

-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keep  class * extends com.lxkj.basemodule.allroot.RootBean{*;}
-keep  class * extends com.lxkj.basemodule.allroot.RootRequest{*;}
-keep  class * extends com.lxkj.basemodule.allroot.RootResponse{*;}

## BaseItemViewDelegate 通过反射实例化，需要禁止混淆构造函数
-keepclassmembers class * extends com.lxkj.basemodule.mlist.BaseItemViewDelegate{
    public <init>(android.content.Context);
}

## fragment 通过反射实例化，需要禁止混淆构造函数
#-keep class * extends androidx.fragment.app.Fragment

##TIM
-keep class com.tencent.** { *; }

-dontoptimize
-dontpreverify
-keepattributes  EnclosingMethod,Signature

##极光
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

-dontwarn cn.jmessage.**
-keep class cn.jmessage.**{ *; }

-keepclassmembers class ** {
    public void onEvent*(**);
}

#========================gson================================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

# 枚举类不能被混淆
-keep enum * {*;}
-dontwarn com.jeremyliao.liveeventbus.**
-keep class com.jeremyliao.liveeventbus.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.arch.core.** { *; }

# agentweb
-keep class com.just.agentweb.** { *; }
-dontwarn com.just.agentweb.**
######Java 注入类不要混淆 ， 例如 sample 里面的 AndroidInterface 类 ， 需要 Keep 。
-keepclassmembers class com.lxkj.baseextend.ui.WebFragment.AndroidInterface{ *; }

#安全联盟
-keep class com.bun.miitmdid.core.** {*;}

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}