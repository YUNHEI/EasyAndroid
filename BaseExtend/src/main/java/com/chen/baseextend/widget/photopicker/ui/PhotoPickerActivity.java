package com.chen.baseextend.widget.photopicker.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chen.baseextend.R;
import com.chen.baseextend.widget.photopicker.entity.Photo;
import com.chen.baseextend.widget.photopicker.event.OnItemCheckListener;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static com.chen.baseextend.widget.photopicker.PhotoPicker.DEFAULT_COLUMN_NUMBER;
import static com.chen.baseextend.widget.photopicker.PhotoPicker.DEFAULT_MAX_COUNT;
import static com.chen.baseextend.widget.photopicker.PhotoPicker.EXTRA_GRID_COLUMN;
import static com.chen.baseextend.widget.photopicker.PhotoPicker.EXTRA_MAX_COUNT;
import static com.chen.baseextend.widget.photopicker.PhotoPicker.EXTRA_ORIGINAL_PHOTOS;
import static com.chen.baseextend.widget.photopicker.PhotoPicker.EXTRA_PREVIEW_ENABLED;
import static com.chen.baseextend.widget.photopicker.PhotoPicker.EXTRA_SHOW_CAMERA;
import static com.chen.baseextend.widget.photopicker.PhotoPicker.EXTRA_SHOW_GIF;
import static com.chen.baseextend.widget.photopicker.PhotoPicker.EXTRA_SHOW_SELECT;
import static com.chen.baseextend.widget.photopicker.PhotoPicker.EXTRA_URL;
import static com.chen.baseextend.widget.photopicker.PhotoPicker.KEY_SELECTED_PHOTOS;

public class PhotoPickerActivity extends AppCompatActivity {

    private PhotoPickerFragment pickerFragment;
    private ImagePagerFragment imagePagerFragment;
    private MenuItem menuDoneItem;

    private int maxCount = DEFAULT_MAX_COUNT;

    /**
     * to prevent multiple calls to inflate menu
     */
    private boolean menuIsInflated = false;

    private boolean showGif = false;
    private ArrayList<String> originalPhotos = null;

    private boolean showSelect = false;

    TextView mSelectImage;

    private ArrayList<String> mUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean showCamera = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        boolean showGif = getIntent().getBooleanExtra(EXTRA_SHOW_GIF, false);
        boolean previewEnabled = getIntent().getBooleanExtra(EXTRA_PREVIEW_ENABLED, true);
        mUrls = getIntent().getStringArrayListExtra(EXTRA_URL);

        showSelect = getIntent().getBooleanExtra(EXTRA_SHOW_SELECT, false);

        setShowGif(showGif);

        setContentView(R.layout.__picker_activity_photo_picker);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(R.string.__picker_title);

        ImageView mBack = findViewById(R.id.img_left_icon);
        mBack.setVisibility(View.VISIBLE);
        mBack.setImageResource(R.mipmap.ic_back);
        mBack.setOnClickListener(v -> {
            if (imagePagerFragment != null && imagePagerFragment.isVisible()) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                }
            } else {
                finish();
            }
        });

        if (showSelect) {
            mSelectImage = findViewById(R.id.tv_right_text);
            mSelectImage.setTextColor(getResources().getColor(R.color.blue_text));
            mSelectImage.setVisibility(View.VISIBLE);
            mSelectImage.setText("完成");
            mSelectImage.setOnClickListener(v -> {
                Intent intent = new Intent();
                ArrayList<String> selectedPhotos = null;
                if (pickerFragment != null) {
                    selectedPhotos = pickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();
                }
                //当在列表没有选择图片，又在详情界面时默认选择当前图片
                if (selectedPhotos.size() <= 0) {
                    if (imagePagerFragment != null && imagePagerFragment.isResumed()) {
                        // 预览界面
                        selectedPhotos = imagePagerFragment.getCurrentPath();
                    }
                }
                if (mUrls != null && mUrls.size() > 0) {
                    if (imagePagerFragment != null && imagePagerFragment.isResumed()) {
                        // 预览界面
                        selectedPhotos.addAll(imagePagerFragment.getCurrentPath());
                    }
                }
                if (selectedPhotos != null && selectedPhotos.size() > 0) {
                    intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, selectedPhotos);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }


        TextView title = findViewById(R.id.tv_left_text_2);
        title.setVisibility(View.VISIBLE);
        title.setText("图片");

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setElevation(25);
        }

        maxCount = getIntent().getIntExtra(EXTRA_MAX_COUNT, DEFAULT_MAX_COUNT);
        int columnNumber = getIntent().getIntExtra(EXTRA_GRID_COLUMN, DEFAULT_COLUMN_NUMBER);
        originalPhotos = getIntent().getStringArrayListExtra(EXTRA_ORIGINAL_PHOTOS);

        pickerFragment = (PhotoPickerFragment) getSupportFragmentManager().findFragmentByTag("tag");
        if (pickerFragment == null) {
            pickerFragment = PhotoPickerFragment
                    .newInstance(showCamera, showGif, previewEnabled, columnNumber, maxCount, originalPhotos, showSelect, mUrls);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, pickerFragment, "tag")
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }

        pickerFragment.getPhotoGridAdapter().setOnItemCheckListener(new OnItemCheckListener() {
            @Override
            public boolean onItemCheck(int position, Photo photo, final int selectedItemCount) {

                if (showSelect) {
                    mSelectImage.setEnabled(selectedItemCount > 0);
                }
                menuDoneItem.setEnabled(selectedItemCount > 0);

                if (maxCount <= 1) {
                    List<String> photos = pickerFragment.getPhotoGridAdapter().getSelectedPhotos();
                    if (!photos.contains(photo.getPath())) {
                        pickerFragment.getPhotoGridAdapter().resetData();
                        photos.clear();
                        pickerFragment.getPhotoGridAdapter().notifyDataSetChanged();
                    }
                    return true;
                }

                if (selectedItemCount > maxCount) {
                    Toast.makeText(getActivity(), getString(R.string.__picker_over_max_count_tips, maxCount),
                            LENGTH_LONG).show();
                    return false;
                }
                if (maxCount > 1) {
                    if (showSelect) {
                        mSelectImage.setText(getString(R.string.__picker_done_with_count, selectedItemCount, maxCount));
                    }
                    menuDoneItem.setTitle(getString(R.string.__picker_done_with_count, selectedItemCount, maxCount));
                } else {
                    if (showSelect) {
                        mSelectImage.setText(getString(R.string.__picker_done));
                    }
                    menuDoneItem.setTitle(getString(R.string.__picker_done));
                }
                return true;
            }
        });


    }

    //刷新右上角按钮文案
    public void updateTitleDoneItem() {
        if (menuIsInflated) {
//            if (pickerFragment != null && pickerFragment.isResumed()) {
            List<String> photos = pickerFragment.getPhotoGridAdapter().getSelectedPhotos();
            int size = photos == null ? 0 : photos.size();
            menuDoneItem.setEnabled(size > 0);
            if (showSelect) {
                mSelectImage.setEnabled(size > 0);
            }
            if (maxCount > 1) {
                menuDoneItem.setTitle(getString(R.string.__picker_done_with_count, size, maxCount));
                if (showSelect) {
                    mSelectImage.setText(getString(R.string.__picker_done_with_count, size, maxCount));
                }
            } else {
                if (showSelect) {
                    mSelectImage.setText(getString(R.string.__picker_done));
                }
                menuDoneItem.setTitle(getString(R.string.__picker_done));
            }

//            } else if (imagePagerFragment != null && imagePagerFragment.isResumed()) {
//                //预览界面 完成总是可点的，没选就把默认当前图片
//                menuDoneItem.setEnabled(true);
//                if (showSelect) {
//                    mSelectImage.setEnabled(true);
//                }
//            }

        }
    }

    /**
     * Overriding this method allows us to exe our exit animation first, then exiting
     * the activity when it complete.
     */
    @Override
    public void onBackPressed() {
        if (imagePagerFragment != null && imagePagerFragment.isVisible()) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            }
        } else {
            super.onBackPressed();
        }
    }


    public void addImagePagerFragment(ImagePagerFragment imagePagerFragment) {
        this.imagePagerFragment = imagePagerFragment;
        imagePagerFragment.setOnCheckClickListener(new ImagePagerFragment.OnCheckClickListener() {
            @Override
            public boolean onCheckClickListener(Photo photo) {
                if (maxCount <= 1) {
                    if (!photo.isCheck()) {
                        pickerFragment.getPhotoGridAdapter().resetData();
                        imagePagerFragment.resetData();
                    }
                }
//                if (!photo.isCheck()) {
//                    if (pickerFragment.getPhotoGridAdapter().getSelectedPhotos().size() >= maxCount) {
//                        Toast.makeText(getActivity(), getString(R.string.__picker_over_max_count_tips, maxCount),
//                                LENGTH_LONG).show();
//                        return false;
//                    }
//                }

                pickerFragment.getPhotoGridAdapter().toggleSelection(photo);
                return true;
            }

            @Override
            public int onCheckCountChangeListener() {
                return pickerFragment.getPhotoGridAdapter().getSelectedPhotos().size();
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, this.imagePagerFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!menuIsInflated) {
            getMenuInflater().inflate(R.menu.__picker_menu_picker, menu);
            menuDoneItem = menu.findItem(R.id.done);
            if (originalPhotos != null && originalPhotos.size() > 0) {
                menuDoneItem.setEnabled(true);
                menuDoneItem.setTitle(
                        getString(R.string.__picker_done_with_count, originalPhotos.size(), maxCount));
            } else {
                menuDoneItem.setEnabled(false);
            }
            menuIsInflated = true;
            return true;
        }
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        if (item.getItemId() == R.id.done) {
            Intent intent = new Intent();
            ArrayList<String> selectedPhotos = null;
            if (pickerFragment != null) {
                selectedPhotos = pickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();
            }
            //当在列表没有选择图片，又在详情界面时默认选择当前图片
            if (selectedPhotos.size() <= 0) {
                if (imagePagerFragment != null && imagePagerFragment.isResumed()) {
                    // 预览界面
                    selectedPhotos = imagePagerFragment.getCurrentPath();
                }
            }
            if (selectedPhotos != null && selectedPhotos.size() > 0) {
                intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, selectedPhotos);
                setResult(RESULT_OK, intent);
                finish();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public PhotoPickerActivity getActivity() {
        return this;
    }

    public boolean isShowGif() {
        return showGif;
    }

    public void setShowGif(boolean showGif) {
        this.showGif = showGif;
    }
}
