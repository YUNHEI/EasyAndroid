package com.chen.baseextend.widget.banner.loader;

import android.content.Context;
import android.view.View;

import com.chen.baseextend.bean.AdvertBean;

import java.io.Serializable;


public interface ImageLoaderInterface<T extends View> extends Serializable {

    void displayImage(Context context, String path, T imageView);

    T createImageView(Context context);

    void setInformation(Context context, AdvertBean item, View view);
}
