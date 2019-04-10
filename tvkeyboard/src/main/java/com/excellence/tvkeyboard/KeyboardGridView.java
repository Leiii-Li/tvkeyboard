package com.excellence.tvkeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;

/**
 * @author Shenlinliang
 * @date 2019/3/15
 */
public class KeyboardGridView extends GridView {
    private OnCenterKeyDownListener mOnCenterKeyDownListener;
    private OnNumberKeyDownListener mOnNumberKeyDownListener;
    private OnDelKeyDownListener mOnDelKeyDownListener;

    public KeyboardGridView(Context context) {
        super(context);
    }

    public KeyboardGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    if (mOnCenterKeyDownListener != null) {
                        mOnCenterKeyDownListener.onKeyDown(getSelectedView(), getSelectedItemPosition());
                        return true;
                    }
                    break;
                case KeyEvent.KEYCODE_0:
                case KeyEvent.KEYCODE_1:
                case KeyEvent.KEYCODE_2:
                case KeyEvent.KEYCODE_3:
                case KeyEvent.KEYCODE_4:
                case KeyEvent.KEYCODE_5:
                case KeyEvent.KEYCODE_6:
                case KeyEvent.KEYCODE_7:
                case KeyEvent.KEYCODE_8:
                case KeyEvent.KEYCODE_9:
                    if (mOnNumberKeyDownListener != null) {
                        mOnNumberKeyDownListener.onKeyDown(event.getKeyCode() - KeyEvent.KEYCODE_0);
                        return true;
                    }
                    break;
                case KeyEvent.KEYCODE_DEL:
                    if (mOnDelKeyDownListener != null) {
                        mOnDelKeyDownListener.onKeyDown(getSelectedView());
                        return true;
                    }
                    break;
            }
        }

        return super.dispatchKeyEvent(event);
    }

    public void setOnCenterKeyDownListener(OnCenterKeyDownListener onCenterKeyDownListener) {
        mOnCenterKeyDownListener = onCenterKeyDownListener;
    }

    public void setOnNumberKeyDownListener(OnNumberKeyDownListener onNumberKeyDownListener) {
        mOnNumberKeyDownListener = onNumberKeyDownListener;
    }

    public void setOnDelKeyDownListener(OnDelKeyDownListener onDelKeyDownListener) {
        mOnDelKeyDownListener = onDelKeyDownListener;
    }

    public interface OnCenterKeyDownListener {
        void onKeyDown(View view, int position);
    }

    public interface OnNumberKeyDownListener {
        void onKeyDown(int value);
    }

    public interface OnDelKeyDownListener {
        void onKeyDown(View view);
    }
}
