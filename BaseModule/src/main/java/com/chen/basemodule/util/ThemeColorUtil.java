package com.chen.basemodule.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.widget.CompoundButton;

/**
 * Created by chen on 2016/7/18.
 */
public class ThemeColorUtil extends CompoundButton {

    public ThemeColorUtil(Context context) {
        super(context);
    }

    public static ColorStateList createColorStateList(int defaultColor, int selectedColor) {
        final int[][] states = new int[3][];
        final int[] colors = new int[3];
        int i = 0;

        states[i] = SELECTED_STATE_SET;
        colors[i] = selectedColor;
        i++;

        int st[] = {android.R.attr.state_checked};
        states[i] = st;
        colors[i] = selectedColor;
        i++;

        // Default enabled state
        states[i] = EMPTY_STATE_SET;
        colors[i] = defaultColor;
        i++;

        return new ColorStateList(states, colors);
    }
}
