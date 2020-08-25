package com.chen.baseextend.widget.photopicker.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.chen.baseextend.R;
import com.chen.baseextend.widget.photopicker.entity.Photo;
import com.chen.baseextend.widget.photopicker.entity.PhotoDirectory;
import com.chen.baseextend.widget.photopicker.event.OnItemCheckListener;
import com.chen.baseextend.widget.photopicker.event.OnPhotoClickListener;
import com.chen.baseextend.widget.photopicker.utils.AndroidLifecycleUtils;
import com.chen.baseextend.widget.photopicker.utils.MediaStoreHelper;
import com.chen.basemodule.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donglua on 15/5/31.
 */
public class PhotoGridAdapter extends SelectableAdapter<PhotoGridAdapter.PhotoViewHolder> {

    private RequestManager glide;
    private Context context;

    private OnItemCheckListener onItemCheckListener = null;
    private OnPhotoClickListener onPhotoClickListener = null;
    private View.OnClickListener onCameraClickListener = null;

    public final static int ITEM_TYPE_CAMERA = 100;
    public final static int ITEM_TYPE_PHOTO = 101;
    private final static int COL_NUMBER_DEFAULT = 3;

    private boolean hasCamera = true;
    private boolean previewEnable = true;
    private boolean showSelect = false;

    private int imageSize;
    private int columnNumber = COL_NUMBER_DEFAULT;


    public PhotoGridAdapter(Context context, RequestManager requestManager, List<PhotoDirectory> photoDirectories, ArrayList<String> urls,
                            ArrayList<String> orginalPhotos) {
        super(urls,orginalPhotos);
        this.photoDirectories = photoDirectories;
        this.glide = requestManager;
        this.context = context;
        setColumnNumber(context, columnNumber);
    }

    public PhotoGridAdapter(Context context, RequestManager requestManager, List<PhotoDirectory> photoDirectories,
                            ArrayList<String> orginalPhotos, int colNum,
                            boolean showSelect, ArrayList<String> urls) {

        this(context, requestManager, photoDirectories, urls,orginalPhotos);
        setColumnNumber(context, colNum);
        selectedPhotos = new ArrayList<>();
        if (orginalPhotos != null) selectedPhotos.addAll(orginalPhotos);
        this.showSelect = showSelect;
    }

    private void setColumnNumber(Context context, int columnNumber) {
        this.columnNumber = columnNumber;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        imageSize = widthPixels / columnNumber;
    }

    @Override
    public int getItemViewType(int position) {
        return (showCamera() && position == 0) ? ITEM_TYPE_CAMERA : ITEM_TYPE_PHOTO;
    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.__picker_item_photo, parent, false);
        final PhotoViewHolder holder = new PhotoViewHolder(itemView);
        if (viewType == ITEM_TYPE_CAMERA) {
            holder.vSelected.setVisibility(View.GONE);
            holder.ivPhoto.setScaleType(ImageView.ScaleType.CENTER);

            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onCameraClickListener != null) {
                        onCameraClickListener.onClick(view);
                    }
                }
            });
        }
        return holder;
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, int position) {

        if (getItemViewType(position) == ITEM_TYPE_PHOTO) {


            List<Photo> photos = getCurrentPhotos();
            final Photo photo;

            if (showCamera()) {
                photo = photos.get(position - 1);
            } else {
                photo = photos.get(position);
            }

            boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(holder.ivPhoto.getContext());

            if (canLoadImage) {
                final RequestOptions options = new RequestOptions();

                options.centerCrop()
                        .override(imageSize, imageSize)
                        .placeholder(R.drawable.ic_placeholder_dark)
                        .error(R.drawable.ic_errorholder);

                if (mUrls.size() > 0) {
                    glide.setDefaultRequestOptions(options)
                            .load(ImageUtil.INSTANCE.getImageContentUri(context, photo.getPath()))
                            .thumbnail(0.5f)
                            .into(holder.ivPhoto);
                } else {
                    glide.setDefaultRequestOptions(options)
                            .load(ImageUtil.INSTANCE.getImageContentUri(context, photo.getPath()))
                            .thumbnail(0.5f)
                            .into(holder.ivPhoto);

                }
            }

            final boolean isChecked = isSelected(photo);

            if (showSelect) {
                holder.vSelected.setVisibility(View.VISIBLE);
            } else {
                holder.vSelected.setVisibility(View.GONE);
            }

            holder.vSelected.setSelected(isChecked);
            holder.ivPhoto.setSelected(isChecked);

            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPhotoClickListener != null) {
                        int pos = holder.getAdapterPosition();
                        if (previewEnable) {
                            onPhotoClickListener.onClick(view, pos, showCamera());
                        } else {
                            holder.vSelected.performClick();
                        }
                    }
                }
            });
            holder.vSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getAdapterPosition();
                    boolean isEnable = true;

                    if (onItemCheckListener != null) {
                        isEnable = onItemCheckListener.onItemCheck(pos, photo,
                                getSelectedPhotos().size() + (isSelected(photo) ? -1 : 1));
                    }
                    if (isEnable) {
                        toggleSelection(photo);
                        photo.setCheck(!photo.isCheck());
                        notifyItemChanged(pos);
                    }
                }
            });

        } else {
            holder.ivPhoto.setImageResource(R.drawable.__picker_camera);
        }
    }


    @Override
    public int getItemCount() {
        /**网络图片*/
        if (mUrls != null && mUrls.size() > 0) {
            return mUrls.size();
        }

        /**本地图片*/
        int photosCount =
                photoDirectories.size() == 0 ? 0 : getCurrentPhotos().size();
        if (showCamera()) {
            return photosCount + 1;
        }
        return photosCount;
    }


    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private View vSelected;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
        }
    }


    public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
        this.onItemCheckListener = onItemCheckListener;
    }


    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }


    public void setOnCameraClickListener(View.OnClickListener onCameraClickListener) {
        this.onCameraClickListener = onCameraClickListener;
    }


    public ArrayList<String> getSelectedPhotoPaths() {
        ArrayList<String> selectedPhotoPaths = new ArrayList<>(getSelectedItemCount());

        for (String photo : selectedPhotos) {
            selectedPhotoPaths.add(photo);
        }

        return selectedPhotoPaths;
    }


    public void setShowCamera(boolean hasCamera) {
        this.hasCamera = hasCamera;
    }

    public void setPreviewEnable(boolean previewEnable) {
        this.previewEnable = previewEnable;
    }

    public boolean showCamera() {
        return (hasCamera && currentDirectoryIndex == MediaStoreHelper.INDEX_ALL_PHOTOS);
    }

    @Override
    public void onViewRecycled(PhotoViewHolder holder) {
        glide.clear(holder.ivPhoto);
        super.onViewRecycled(holder);
    }


    public void resetData() {
        for (int i = 0 ; i<getCurrentPhoto().size() ; i++){
            if (getCurrentPhoto().get(i).isCheck()) {
                getCurrentPhoto().get(i).setCheck(false);
                notifyItemChanged(i);
            }
        }
        clearSelection();
    }
}
