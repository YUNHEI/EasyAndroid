package com.chen.baseextend.widget.photopicker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chen.baseextend.R;
import com.chen.baseextend.widget.photopicker.adapter.PhotoPagerAdapter;
import com.chen.baseextend.widget.photopicker.entity.Photo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.chen.baseextend.widget.photopicker.PhotoPicker.DEFAULT_MAX_COUNT;

/**
 * Image Pager Fragment
 */
public class ImagePagerFragment extends Fragment {

    //    public final static String ARG_PATH = "PATHS";
    public final static String ARG_PATH_PHOTO = "PATHS_PHOTO";
    public final static String ARG_CURRENT_ITEM = "ARG_CURRENT_ITEM";
    public final static String NEED_SHOW_CHECK = "NEED_SHOW_CHECK";
    private final static String EXTRA_COUNT = "EXTRA_COUNT";

    private ArrayList<Photo> paths;
    private ViewPager mViewPager;
    private PhotoPagerAdapter mPagerAdapter;

    private int currentItem = 0;

    private CheckBox checkBox;
    private LinearLayout mBgCheckBox;
    private TextView mCount;

    private int mMaxCount = DEFAULT_MAX_COUNT;

//    public static ImagePagerFragment newInstance(List<String> paths, int currentItem) {
//
//        ImagePagerFragment f = new ImagePagerFragment();
//
//        Bundle args = new Bundle();
//        args.putStringArray(ARG_PATH, paths.toArray(new String[paths.size()]));
//        args.putInt(ARG_CURRENT_ITEM, currentItem);
//
//        f.setArguments(args);
//
//        return f;
//    }

    public static ImagePagerFragment newInstance(List<Photo> paths, int currentItem, boolean needShowCheck, int maxCount) {

        ImagePagerFragment f = new ImagePagerFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_PATH_PHOTO, (Serializable) paths);
//        args.putStringArray(ARG_PATH, paths.toArray(new String[paths.size()]));
        args.putInt(ARG_CURRENT_ITEM, currentItem);
        args.putBoolean(NEED_SHOW_CHECK, needShowCheck);
        args.putInt(EXTRA_COUNT, maxCount);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof PhotoPickerActivity) {
            PhotoPickerActivity photoPickerActivity = (PhotoPickerActivity) getActivity();
            photoPickerActivity.updateTitleDoneItem();
        }
    }

    public void setPhotos(List<String> paths, int currentItem) {
        this.paths.clear();
        List<Photo> photos = new ArrayList<>();
        for (String path : paths) {
            Photo photo = new Photo();
            photo.setPath(path);
            photos.add(photo);
        }

        this.paths.addAll(photos);
        this.currentItem = currentItem;

        mViewPager.setCurrentItem(currentItem);
        mViewPager.getAdapter().notifyDataSetChanged();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        paths = new ArrayList<>();

        Bundle bundle = getArguments();

        if (bundle != null) {
            paths = (ArrayList<Photo>) bundle.getSerializable(ARG_PATH_PHOTO);
//            paths.clear();
//            if (pathArr != null) {
//                paths = new ArrayList<>(Arrays.asList(pathArr));
//            }

            currentItem = bundle.getInt(ARG_CURRENT_ITEM);
            mMaxCount = bundle.getInt(EXTRA_COUNT);

        }


        mPagerAdapter = new PhotoPagerAdapter(Glide.with(this), paths);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.__picker_picker_fragment_image_pager, container, false);

        mViewPager = rootView.findViewById(R.id.vp_photos);
        checkBox = rootView.findViewById(R.id.checkbox);
        mBgCheckBox = rootView.findViewById(R.id.bg_check);
        mCount = rootView.findViewById(R.id.count);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentItem);
        mViewPager.setOffscreenPageLimit(5);

        if (getArguments() != null) {
            boolean showCheck = getArguments().getBoolean(NEED_SHOW_CHECK, false);
            if (showCheck) {
                mBgCheckBox.setVisibility(View.VISIBLE);
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                checkBox.setChecked(paths.get(position).isCheck());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBgCheckBox.setOnClickListener(v -> {
            if (mListener != null) {
                if (!paths.get(mViewPager.getCurrentItem()).isCheck() &&
                        mListener.onCheckCountChangeListener() >= mMaxCount) {
                    Toast.makeText(getActivity(), getString(R.string.__picker_over_max_count_tips, mMaxCount),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mListener.onCheckClickListener(paths.get(mViewPager.getCurrentItem()));
                paths.get(mViewPager.getCurrentItem()).setCheck(!checkBox.isChecked());

                if (getActivity() instanceof PhotoPickerActivity) {
                    PhotoPickerActivity photoPickerActivity = (PhotoPickerActivity) getActivity();
                    photoPickerActivity.updateTitleDoneItem();
                }

                checkBox.setChecked(!checkBox.isChecked());

                if (mListener != null) {
                    mCount.setText("已选 " + mListener.onCheckCountChangeListener());
                }


            }
        });

        if (paths.size() > 0) {
            checkBox.setChecked(paths.get(currentItem).isCheck());
        }

        if (mListener != null) {
            mCount.setText("已选 " + mListener.onCheckCountChangeListener());
        }

        return rootView;
    }


    public ViewPager getViewPager() {
        return mViewPager;
    }


    public ArrayList<String> getPaths() {
        ArrayList<String> photos = new ArrayList<>();
        for (Photo photo : paths) {
            photos.add(photo.getPath());
        }
        return photos;
    }

    public ArrayList<Photo> getPhotoPaths() {
        return paths;
    }


    public ArrayList<String> getCurrentPath() {
        ArrayList<String> list = new ArrayList<>();
        int position = mViewPager.getCurrentItem();
        if (paths != null && paths.size() > position) {
            list.add(paths.get(position).getPath());
        }
        return list;
    }

    public int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        paths.clear();
//        paths = null;

        if (mViewPager != null) {
            mViewPager.setAdapter(null);
        }
    }


    OnCheckClickListener mListener;

    public void resetData() {
        for (Photo item : paths) {
            item.setCheck(false);
        }
        mPagerAdapter.notifyDataSetChanged();
    }

    public interface OnCheckClickListener {
        boolean onCheckClickListener(Photo photo);

        int onCheckCountChangeListener();

    }

    public void setOnCheckClickListener(OnCheckClickListener mListener) {
        this.mListener = mListener;
    }

}
