package com.chen.basemodule.widget.smartrefresh.layout.header;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.basemodule.R;
import com.chen.basemodule.widget.smartrefresh.layout.api.RefreshHeader;
import com.chen.basemodule.widget.smartrefresh.layout.api.RefreshLayout;
import com.chen.basemodule.widget.smartrefresh.layout.constant.RefreshState;
import com.chen.basemodule.widget.smartrefresh.layout.constant.SpinnerStyle;
import com.chen.basemodule.widget.smartrefresh.layout.internal.InternalClassics;
import com.chen.basemodule.widget.smartrefresh.layout.util.DensityUtil;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 经典下拉头部
 * Created by SCWANG on 2017/5/28.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class SimpleHeader extends InternalClassics<SimpleHeader> implements RefreshHeader {

    public static final byte ID_TEXT_UPDATE = 4;

    public static String REFRESH_HEADER_PULLING = null;//"下拉可以刷新";
    public static String REFRESH_HEADER_REFRESHING = null;//"正在刷新...";
    public static String REFRESH_HEADER_LOADING = null;//"正在加载...";
    public static String REFRESH_HEADER_RELEASE = null;//"释放立即刷新";
    public static String REFRESH_HEADER_FINISH = null;//"刷新完成";
    public static String REFRESH_HEADER_FAILED = null;//"刷新失败";
    public static String REFRESH_HEADER_SECONDARY = null;//"释放进入二楼";

    private ContentLoadingProgressBar progressBar;
    //<editor-fold desc="RelativeLayout">
    public SimpleHeader(Context context) {
        this(context, null);
    }

    public SimpleHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View v = LayoutInflater.from(context).inflate(R.layout.layout_simple_refresh_header, null);

        LayoutParams lpHeaderLayout = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        lpHeaderLayout.addRule(CENTER_IN_PARENT);
        addView(v,lpHeaderLayout);

        progressBar = v.findViewById(R.id._progressBar);

        mTitleText = v.findViewById(R.id._title);

        progressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(context, R.color.gray_99), PorterDuff.Mode.MULTIPLY);

        if (REFRESH_HEADER_PULLING == null) {
            REFRESH_HEADER_PULLING = context.getString(R.string.srl_header_pulling);
        }
        if (REFRESH_HEADER_REFRESHING == null) {
            REFRESH_HEADER_REFRESHING = context.getString(R.string.srl_header_refreshing);
        }
        if (REFRESH_HEADER_LOADING == null) {
            REFRESH_HEADER_LOADING = context.getString(R.string.srl_header_loading);
        }
        if (REFRESH_HEADER_RELEASE == null) {
            REFRESH_HEADER_RELEASE = context.getString(R.string.srl_header_release);
        }
        if (REFRESH_HEADER_FINISH == null) {
            REFRESH_HEADER_FINISH = context.getString(R.string.srl_header_finish);
        }
        if (REFRESH_HEADER_FAILED == null) {
            REFRESH_HEADER_FAILED = context.getString(R.string.srl_header_failed);
        }
        if (REFRESH_HEADER_SECONDARY == null) {
            REFRESH_HEADER_SECONDARY = context.getString(R.string.srl_header_secondary);
        }

        final ViewGroup centerLayout = mCenterLayout;
        final DensityUtil density = new DensityUtil();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClassicsHeader);

        mFinishDuration = ta.getInt(R.styleable.ClassicsHeader_srlFinishDuration, mFinishDuration);
        mSpinnerStyle = SpinnerStyle.values()[ta.getInt(R.styleable.ClassicsHeader_srlClassicsSpinnerStyle, mSpinnerStyle.ordinal())];

        if (ta.hasValue(R.styleable.ClassicsHeader_srlPrimaryColor)) {
            setPrimaryColor(ta.getColor(R.styleable.ClassicsHeader_srlPrimaryColor, 0));
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_srlAccentColor)) {
            setAccentColor(ta.getColor(R.styleable.ClassicsHeader_srlAccentColor, 0));
        }

        ta.recycle();

        mTitleText.setText(isInEditMode() ? REFRESH_HEADER_REFRESHING : REFRESH_HEADER_PULLING);

    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        progressBar.setVisibility(GONE);
        if (success) {
            mTitleText.setText(REFRESH_HEADER_FINISH);
        } else {
            mTitleText.setText(REFRESH_HEADER_FAILED);
        }
        return super.onFinish(layout, success);//延迟500毫秒之后再弹回
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        mArrowView.setVisibility(GONE);
        switch (newState) {
            case None:
            case PullDownToRefresh:
                progressBar.setVisibility(GONE);
                mTitleText.setText(REFRESH_HEADER_PULLING);
                break;
            case Refreshing:
            case RefreshReleased:
                progressBar.setVisibility(VISIBLE);
                mTitleText.setText(REFRESH_HEADER_REFRESHING);
                break;
            case ReleaseToRefresh:
                progressBar.setVisibility(GONE);
                mTitleText.setText(REFRESH_HEADER_RELEASE);
                break;
            case ReleaseToTwoLevel:
                progressBar.setVisibility(GONE);
                mTitleText.setText(REFRESH_HEADER_SECONDARY);
                break;
            case Loading:
                progressBar.setVisibility(GONE);
                mTitleText.setText(REFRESH_HEADER_LOADING);
                break;
        }
    }

    public SimpleHeader setAccentColor(@ColorInt int accentColor) {
        return super.setAccentColor(accentColor);
    }



}
