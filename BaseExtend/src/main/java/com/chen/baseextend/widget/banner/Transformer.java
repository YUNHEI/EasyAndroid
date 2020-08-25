package com.chen.baseextend.widget.banner;

import androidx.viewpager.widget.ViewPager.PageTransformer;

import com.chen.baseextend.widget.banner.transformer.AccordionTransformer;
import com.chen.baseextend.widget.banner.transformer.BackgroundToForegroundTransformer;
import com.chen.baseextend.widget.banner.transformer.CubeInTransformer;
import com.chen.baseextend.widget.banner.transformer.CubeOutTransformer;
import com.chen.baseextend.widget.banner.transformer.DefaultTransformer;
import com.chen.baseextend.widget.banner.transformer.DepthPageTransformer;
import com.chen.baseextend.widget.banner.transformer.FlipHorizontalTransformer;
import com.chen.baseextend.widget.banner.transformer.FlipVerticalTransformer;
import com.chen.baseextend.widget.banner.transformer.ForegroundToBackgroundTransformer;
import com.chen.baseextend.widget.banner.transformer.RotateDownTransformer;
import com.chen.baseextend.widget.banner.transformer.RotateUpTransformer;
import com.chen.baseextend.widget.banner.transformer.ScaleInOutTransformer;
import com.chen.baseextend.widget.banner.transformer.StackTransformer;
import com.chen.baseextend.widget.banner.transformer.TabletTransformer;
import com.chen.baseextend.widget.banner.transformer.ZoomInTransformer;
import com.chen.baseextend.widget.banner.transformer.ZoomOutSlideTransformer;
import com.chen.baseextend.widget.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
