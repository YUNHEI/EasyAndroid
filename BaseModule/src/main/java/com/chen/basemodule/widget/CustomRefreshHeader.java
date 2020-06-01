package com.chen.basemodule.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chen.basemodule.R;
import com.chen.basemodule.widget.smartrefresh.layout.api.RefreshLayout;
import com.chen.basemodule.widget.smartrefresh.layout.header.ClassicsHeader;
import com.chen.basemodule.widget.smartrefresh.layout.util.DensityUtil;

public class CustomRefreshHeader extends ClassicsHeader {

    public static final byte ID_CNETER_GUIDE_LINE = 10;

    public CustomRefreshHeader(Context context) {
        super(context);

        View v = new View(context);
        v.setBackgroundColor(0x00000000);
        v.setId(ID_CNETER_GUIDE_LINE);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(2, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams1.addRule(CENTER_IN_PARENT);
        this.addView(v, layoutParams1);

        mTitleText.setTextColor(ContextCompat.getColor(context, R.color.gray_99));
        mLastUpdateText.setTextColor(ContextCompat.getColor(context, R.color.gray_99));

        final LinearLayout centerLayout = mCenterLayout;
        final ImageView progress = mProgressView;
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) progress.getLayoutParams();
        layoutParams2.rightMargin = DensityUtil.dp2px(58);

        final ImageView arrowView = mArrowView;
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) arrowView.getLayoutParams();
        layoutParams3.rightMargin = DensityUtil.dp2px(58);

        centerLayout.setGravity(Gravity.LEFT);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) centerLayout.getLayoutParams();

        layoutParams.leftMargin = DensityUtil.dp2px(-48);
        layoutParams.addRule(RIGHT_OF, ID_CNETER_GUIDE_LINE);
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        super.onFinish(layout, success);
        return 1500;
    }

}