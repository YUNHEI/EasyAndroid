package com.chen.basemodule.util;

import android.graphics.Paint;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextViewUtil {

    public static void setBold(TextView textView, float weight) {

        if (textView != null) {

            textView.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
            textView.getPaint().setStrokeWidth(weight);
        }
    }

    public static Spanned transToHighLight(String source, String key) {
        return transToHighLight(source, key, "#e4402f");
    }

    public static Spanned transToHighLight(String source, String key, String color) {

        if (StringUtil.isEmpty(source) || StringUtil.isEmpty(key) || !source.contains(key)) {
            return Html.fromHtml(source);
        }

        Pattern compile = Pattern.compile(key, Pattern.CASE_INSENSITIVE);

        Matcher matcher = compile.matcher(source);

        StringBuilder builder = new StringBuilder();

        String highKey = "";

        String temp = "";

        int offset = 0;

        while (matcher.find()) {
            temp = source.substring(offset, matcher.start());
            highKey = source.substring(matcher.start(), matcher.end());
            offset = matcher.end();
            builder.append(temp).append("<font color='" + color + "'>").append(highKey).append("</font>");
        }

        if (offset < source.length()) {
            builder.append(source.substring(offset, source.length()));
        }

        return Html.fromHtml(builder.toString());

    }

}
