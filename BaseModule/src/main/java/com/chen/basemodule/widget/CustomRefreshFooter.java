package com.chen.basemodule.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chen.basemodule.R;
import com.chen.basemodule.widget.smartrefresh.layout.api.RefreshLayout;
import com.chen.basemodule.widget.smartrefresh.layout.footer.ClassicsFooter;
import com.chen.basemodule.widget.smartrefresh.layout.util.DensityUtil;

public class CustomRefreshFooter extends ClassicsFooter {

    public static final byte ID_CENTER_GUIDE_LINE = 10;

    public CustomRefreshFooter(Context context) {
        super(context);

        View v = new View(context);
//        v.setBackgroundColor(0xFF000000);
        v.setId(ID_CENTER_GUIDE_LINE);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(2, 2);
        layoutParams1.addRule(CENTER_IN_PARENT);
        this.addView(v, layoutParams1);

        mTitleText.setTextColor(ContextCompat.getColor(context, R.color.gray_cc));
        mTitleText.setTextSize(11);

        final LinearLayout centerLayout = mCenterLayout;

        final ImageView progress = mProgressView;

        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) progress.getLayoutParams();
        layoutParams2.addRule(RIGHT_OF, ID_CENTER_GUIDE_LINE);
        layoutParams2.addRule(CENTER_VERTICAL);
        layoutParams2.removeRule(LEFT_OF);
        layoutParams2.leftMargin = DensityUtil.dp2px(16);


        centerLayout.setGravity(Gravity.LEFT);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) centerLayout.getLayoutParams();

        layoutParams.rightMargin = DensityUtil.dp2px(-14);
        layoutParams.addRule(LEFT_OF, ID_CENTER_GUIDE_LINE);
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        super.onFinish(layout, success);
        return 0;
    }
}