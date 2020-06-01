package com.chen.basemodule.widget.smartrefresh.layout.util;

import android.content.res.Resources;

/**
 * 像素密度计算工具
 */
@SuppressWarnings("unused")
public class DensityUtil {

    public float density;

    public DensityUtil() {

        density = Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 虚拟像素
     * @return 像素
     */
    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue 像素
     * @return 虚拟像素
     */
    public static float px2dp(int pxValue) {
        return (pxValue / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 虚拟像素
     * @return 像素
     */
    public int dip2px(float dpValue) {
        return (int) (0.5f + dpValue * density);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue 像素
     * @return 虚拟像素
     */
    public float px2dip(int pxValue) {
        return (pxValue / density);
    }

    // 将px值转换为sp值
    public static int px2sp(float pxValue) {
        return (int) (pxValue / Resources.getSystem().getDisplayMetrics().scaledDensity + 0.5f);
    }

    // 将sp值转换为px值
    public static int sp2px(float spValue) {
        return (int) (spValue * Resources.getSystem().getDisplayMetrics().scaledDensity + 0.5f);
    }

    public static int screenWidth(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int screenHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}  