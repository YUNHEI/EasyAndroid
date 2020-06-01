package com.chen.basemodule.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * 图片添加半透明内描边
 * Created by chen on 2018/11/22
 **/
public class ImageInline extends BitmapTransformation {

    public static final int PAINT_FLAGS = Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG;
    private static final Paint DEFAULT_PAINT = new Paint(PAINT_FLAGS);
    private static final Paint STROKE_PAINT = new Paint(PAINT_FLAGS);
    private static final int STROKE_WIDTH = 1;

    private static final String ID = "com.bumptech.glide.load.resource.bitmap.ImageInline";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    public int color = -1;

    public ImageInline() {
    }

    public ImageInline(int color) {
        this.color = color;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap result = pool.get(outWidth, outHeight, getNonNullConfig(toTransform));

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(toTransform, 0, 0, DEFAULT_PAINT);

        STROKE_PAINT.setStrokeWidth(STROKE_WIDTH);
        STROKE_PAINT.setStyle(Paint.Style.STROKE);
        if (color != -1) {
            STROKE_PAINT.setColor(color);
        } else {
            STROKE_PAINT.setColor(Color.parseColor("#11000000"));
        }

        canvas.drawRect(STROKE_WIDTH, STROKE_WIDTH, outWidth - STROKE_WIDTH, outHeight - STROKE_WIDTH, STROKE_PAINT);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ImageInline;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    @NonNull
    private static Bitmap.Config getNonNullConfig(@NonNull Bitmap bitmap) {
        return bitmap.getConfig() != null ? bitmap.getConfig() : Bitmap.Config.ARGB_8888;
    }
}
