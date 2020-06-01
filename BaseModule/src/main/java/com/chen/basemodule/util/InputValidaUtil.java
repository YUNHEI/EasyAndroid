package com.chen.basemodule.util;

import android.text.Editable;
import android.widget.TextView;


public class InputValidaUtil {


    public InputValidaUtil() {
    }

    public static boolean hasInput(TextView textView) {
        String i = textView.getText().toString().trim();
        return i.length() > 0;
    }

    public static boolean hasInput(Editable e) {
        String i = e.toString().trim();
        return i.length() > 0;
    }
}
