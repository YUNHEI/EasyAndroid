package com.chen.basemodule.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class SoftKeyBoardUtil implements ViewTreeObserver.OnGlobalLayoutListener {


    private View mContentView;
    public int mOriginHeight;
    private int mPreHeight;
    private Activity activity;
    private KeyBoardListener listener;

    private Handler handler;


    public static SoftKeyBoardUtil bindPage(Activity activity) {
        return new SoftKeyBoardUtil(activity);
    }

    private SoftKeyBoardUtil(Activity activity) {
        this.activity = activity;

        if (activity == null) throw new NullPointerException("activity can't be null");

        handler = new Handler(activity.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (listener != null && mContentView != null && !activity.isFinishing() && !activity.isDestroyed()) {
                    mContentView.post(() -> listener.onKeyboardChange(false, msg.arg1));
                }
            }
        };

        mContentView = activity.findViewById(android.R.id.content);
        if (mContentView == null) throw new NullPointerException("content view can't be null");

        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);

        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Point p = new Point();
        wm.getDefaultDisplay().getSize(p);
        mOriginHeight = p.y;
    }


    public void addKeyBoardChangedListener(KeyBoardListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }

    @Override
    public void onGlobalLayout() {
        int currHeight = mContentView.getHeight();
        if (currHeight == 0) {
            return;
        }
        boolean hasChange = false;
        if (mPreHeight == 0) {
            mPreHeight = currHeight;
        } else {
            if (mPreHeight != currHeight) {
                hasChange = true;
                mPreHeight = currHeight;
            } else {
                hasChange = false;
            }
        }
        if (hasChange) {
            boolean isShow;
            int keyboardHeight = 0;
            if (mOriginHeight - currHeight < 200) {
                //hidden
                isShow = false;
            } else {
                //show
                keyboardHeight = mOriginHeight - currHeight;
                isShow = true;
            }

            if (listener != null) {
                if (isShow) {
                    handler.removeMessages(1);
                    listener.onKeyboardChange(isShow, keyboardHeight);
                } else {
                    Message msg = new Message();
                    msg.arg1 = keyboardHeight;
                    msg.what = 1;
                    handler.sendMessageDelayed(msg, 200);
                }
            }
        }

    }


    public interface KeyBoardListener {

        void onKeyboardChange(boolean isShow, int keyboardHeight);
    }

    public void clearFocus(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.clearFocus();
        }
    }

    public void hide() {
        activity.getWindow().getDecorView().clearFocus();
        hideSoftInput(activity.getWindow().getDecorView().getWindowToken());
    }

    public void hide(View v) {
        if (v != null) {
            v.clearFocus();
            hideSoftInput(v.getWindowToken());
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null) {
                im.hideSoftInputFromWindow(token,
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public boolean show(View v) {
        InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        v.requestFocus();
        return im.showSoftInput(v, 0);
    }
}
