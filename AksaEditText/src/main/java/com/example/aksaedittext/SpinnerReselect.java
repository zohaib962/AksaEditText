package com.example.aksaedittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatSpinner;

import java.lang.reflect.Field;

@SuppressLint("AppCompatCustomView")
public class SpinnerReselect extends AppCompatSpinner {

    public SpinnerReselect(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public SpinnerReselect(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public SpinnerReselect(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setSelection(int position, boolean animate) {
        ignoreOldSelectionByReflection();
        super.setSelection(position, animate);
    }

    private void ignoreOldSelectionByReflection() {
        try {
            Class<?> c = this.getClass().getSuperclass().getSuperclass().getSuperclass();
            Field reqField = c.getDeclaredField("mOldSelectedPosition");
            reqField.setAccessible(true);
            reqField.setInt(this, -1);
        } catch (Exception e) {
            Log.d("Exception Private", "ex", e);
            // TODO: handle exception
        }
    }

    @Override
    public void setSelection(int position) {
        ignoreOldSelectionByReflection();
        super.setSelection(position);
    }


}
