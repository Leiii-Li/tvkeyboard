package com.excellence.tvkeyboard;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Method;

/**
 * <pre>
 *     author : Nelson
 *     GitHub : https://github.com/Nelson-KK
 *     time   : 2019/3/11
 *     desc   :
 * </pre>
 */
public class NotShowInputEditText extends android.support.v7.widget.AppCompatEditText {
    public NotShowInputEditText(Context context) {
        this(context, null);
    }

    public NotShowInputEditText(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public NotShowInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setFocusable(true);
        Class<EditText> cls = EditText.class;
        Method method;
        try {
            method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(this, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setCursorDrawable(int drawableResourceId) {
        try {
            java.lang.reflect.Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(this, drawableResourceId);
        } catch (Exception e) {

        }
    }
}
