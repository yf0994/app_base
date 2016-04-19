package com.category.base.listener;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Author:fengyin
 * Date: 16-3-18    09:31
 * Email:594601408@qq.com
 * LastUpdateTime: 16-3-18
 * LastUpdateBy:594601408@qq.com
 */
public class EditTextTouchListener implements View.OnTouchListener {

    private Activity mActivity;
    private EditText mEditText;

    public EditTextTouchListener(Activity activity, EditText editText) {
        this.mActivity = activity;
        this.mEditText = editText;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Drawable[] drawables = mEditText.getCompoundDrawables();
        Drawable passwordDrawable = drawables[2];

        if (passwordDrawable == null) {
            return false;
        }
        if (event.getAction() != event.ACTION_DOWN) {
            return false;
        }

        if (event.getX() > mEditText.getWidth() - mEditText.getPaddingRight() - drawables[2].getIntrinsicWidth()) {
            return true;
        }
        return false;
    }
}
