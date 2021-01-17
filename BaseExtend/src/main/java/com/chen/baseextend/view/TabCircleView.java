package com.chen.baseextend.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chen.basemodule.widget.smartrefresh.layout.util.DensityUtil;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView;


@SuppressLint("AppCompatCustomView")
public class TabCircleView extends TextView implements IMeasurablePagerTitleView {
    protected boolean selectBold = true;
    protected int mSelectedColor;
    protected int mNormalColor;

    public TabCircleView(Context context) {
        super(context, (AttributeSet) null);
    }

    public TabCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context) {
        this.setGravity(17);
        setPadding(DensityUtil.dp2px(8), 0, DensityUtil.dp2px(8), 0);
        this.setSingleLine();
        this.setEllipsize(TruncateAt.END);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        this.setTextColor(this.mSelectedColor);
        if (selectBold) setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        this.setTextColor(this.mNormalColor);
        setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
    }

    @Override
    public int getContentLeft() {
        Rect bound = new Rect();
        this.getPaint().getTextBounds(this.getText().toString(), 0, this.getText().length(), bound);
        int contentWidth = bound.width();
        return this.getLeft() + this.getWidth() / 2 - contentWidth / 2;
    }

    @Override
    public int getContentTop() {
        FontMetrics metrics = this.getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int) ((float) (this.getHeight() / 2) - contentHeight / 2.0F);
    }

    @Override
    public int getContentRight() {
        Rect bound = new Rect();
        this.getPaint().getTextBounds(this.getText().toString(), 0, this.getText().length(), bound);
        int contentWidth = bound.width();
        return this.getLeft() + this.getWidth() / 2 + contentWidth / 2;
    }

    @Override
    public int getContentBottom() {
        FontMetrics metrics = this.getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int) ((float) (this.getHeight() / 2) + contentHeight / 2.0F);
    }

    public void setSelectBold(boolean selectBold) {
        this.selectBold = selectBold;
    }

    public int getSelectedColor() {
        return this.mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.mSelectedColor = selectedColor;
    }

    public int getNormalColor() {
        return this.mNormalColor;
    }

    public void setNormalColor(int normalColor) {
        this.mNormalColor = normalColor;
    }
}
