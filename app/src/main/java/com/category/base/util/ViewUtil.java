package com.category.base.util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * Created by fengyin on 16-4-26.
 */
public class ViewUtil {
    public static int convertToDip(DisplayMetrics displayMetrics, int sizeInPixels){
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, sizeInPixels, displayMetrics);
    }

    public static int convertToPix(float density, int sizeInDips){
        float size = sizeInDips * density;
        return (int)size;
    }

    public static <T extends View> T findViewById(Activity activity, int id){
        T view = null;
        View genericView = activity.findViewById(id);
        view = (T)genericView;
        return view;
    }

    public static <T extends View> T findViewById(View parentView, int id){
        T view = null;
        View genericView = parentView.findViewById(id);
        view = (T)genericView;
        return view;
    }

    public static String getText(TextView view){
        return view == null ? "" : view.getText().toString().trim();
    }


}
