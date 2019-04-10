package com.excellence.tvkeyboard;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 * <pre>
 *     author : Nelson
 *     GitHub : https://github.com/Nelson-KK
 *     time   : 2019/2/26
 *     desc   : data数据的排列方式需要固定
 *        例如 :   7pqrs
 *                Up     -> 7
 *                Left   -> p
 *                Center -> q
 *                Right  -> R
 *                Down   -> s
 * </pre>
 */
public abstract class BaseKeyBoardWindow extends PopupWindow {
    public static final String TAG = BaseKeyBoardWindow.class.getSimpleName();
    private static final int UP_INDEX = 0;
    private static final int LEFT_INDEX = 1;
    private static final int CENTER_INDEX = 2;
    private static final int RIGHT_INDEX = 3;
    private static final int DOWN_INDEX = 4;

    protected Context mContext = null;
    protected KeyBoardItemClickListener mListener;
    protected List<String> mKeyBoardItems = new ArrayList<>();

    public BaseKeyBoardWindow(Context context, String data, KeyBoardItemClickListener listener) {
        init(context);
        for (char item : data.toCharArray()) {
            mKeyBoardItems.add(String.valueOf(item));
        }
        mListener = listener;

        getContentView().setFocusable(true);
        getContentView().requestFocus();
        getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                boolean notDispatchKey = true;
                try {
                    int index = -1;
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyEvent.getKeyCode()) {
                            case KeyEvent.KEYCODE_DPAD_UP:
                                index = UP_INDEX;
                                break;
                            case KeyEvent.KEYCODE_DPAD_LEFT:
                                index = LEFT_INDEX;
                                break;
                            case KeyEvent.KEYCODE_DPAD_CENTER:
                                index = CENTER_INDEX;
                                break;
                            case KeyEvent.KEYCODE_DPAD_RIGHT:
                                index = RIGHT_INDEX;
                                break;
                            case KeyEvent.KEYCODE_DPAD_DOWN:
                                index = DOWN_INDEX;
                                break;
                            default:
                                notDispatchKey = false;
                        }
                        if (index != -1) {
                            onClick(mKeyBoardItems.get(index));
                        }
                    } else {
                        notDispatchKey = false;
                    }
                } catch (Exception e) {
                    BaseKeyBoardWindow.this.dismiss();
                }
                return notDispatchKey;
            }
        });

        initView();
    }

    private void init(Context context) {
        mContext = context;

        setContentView(LayoutInflater.from(context).inflate(getContentViewId(), null, false));
        if (getWidthId() > 0) {
            setWidth(context.getResources().getDimensionPixelOffset(getWidthId()));
        } else if (getWidthId() == MATCH_PARENT) {
            setWidth(MATCH_PARENT);
        } else {
            setWidth(WRAP_CONTENT);
        }

        if (getHeightId() > 0) {
            setHeight(context.getResources().getDimensionPixelOffset(getHeightId()));
        } else if (getHeightId() == MATCH_PARENT) {
            setHeight(MATCH_PARENT);
        } else {
            setHeight(WRAP_CONTENT);
        }

        setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
    }

    private void onClick(String text) {
        if (mListener != null) {
            mListener.onClick(text);
        }
        this.dismiss();
    }

    public interface KeyBoardItemClickListener {
        void onClick(String text);
    }

    protected abstract int getHeightId();

    protected abstract int getWidthId();

    protected abstract int getContentViewId();

    protected abstract void initView();
}
