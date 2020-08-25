package com.chen.baseextend.widget.photopicker.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.chen.baseextend.R;
import com.chen.baseextend.widget.photopicker.entity.Photo;
import com.chen.baseextend.widget.photopicker.utils.AndroidLifecycleUtils;
import com.chen.basemodule.util.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by donglua on 15/6/21.
 */
public class PhotoPagerAdapter extends PagerAdapter {

  private List<Photo> paths = new ArrayList<>();
  private RequestManager mGlide;

  public PhotoPagerAdapter(RequestManager glide, List<Photo> paths) {
    this.paths = paths;
    this.mGlide = glide;
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    final Context context = container.getContext();
    View itemView = LayoutInflater.from(context)
        .inflate(R.layout.__picker_picker_item_pager, container, false);

    final ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_pager);

    final String path = paths.get(position).getPath();
    final Uri uri;
    if (path.startsWith("http")) {
      uri = Uri.parse(path);
    } else {
      uri = Uri.fromFile(new File(path));
    }

    boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(context);

    if (canLoadImage) {
      final RequestOptions options = new RequestOptions();
      options.dontAnimate()
          .dontTransform()
          .override(800, 800)
          .placeholder(R.drawable.ic_placeholder_dark)
          .error(R.drawable.ic_placeholder_dark);
      mGlide.setDefaultRequestOptions(options).load(ImageUtil.INSTANCE.getImageContentUri(context, path))
              .thumbnail(0.1f)
              .into(imageView);
    }

    imageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (context instanceof Activity) {
          if (!((Activity) context).isFinishing()) {
            ((Activity) context).onBackPressed();
          }
        }
      }
    });

    container.addView(itemView);

    return itemView;
  }


  @Override public int getCount() {
    return paths.size();
  }


  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }


  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
    mGlide.clear((View) object);
  }

  @Override
  public int getItemPosition (Object object) { return POSITION_NONE; }

}
