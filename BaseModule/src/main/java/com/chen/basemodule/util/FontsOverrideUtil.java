package com.chen.basemodule.util;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;


import com.chen.basemodule.R;

import java.lang.reflect.Field;

public class FontsOverrideUtil {

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
//        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
//                fontAssetName);
        replaceFont(staticTypefaceFieldName, ResourcesCompat.getFont(context, R.font.roundfont));
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
