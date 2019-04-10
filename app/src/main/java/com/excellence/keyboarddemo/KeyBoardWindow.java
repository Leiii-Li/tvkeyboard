package com.excellence.keyboarddemo;

import android.content.Context;

import com.excellence.tvkeyboard.BaseKeyBoardWindow;


/**
 * <pre>
 *     author : Nelson
 *     GitHub : https://github.com/Nelson-KK
 *     time   : 2019/4/8
 *     desc   :
 * </pre>
 */
public class KeyBoardWindow extends BaseKeyBoardWindow {

    public KeyBoardWindow(Context context, String data, KeyBoardItemClickListener listener) {
        super(context, data, listener);
    }

    @Override
    protected int getHeightId() {
        return R.dimen.keyboard_window_size;
    }

    @Override
    protected int getWidthId() {
        return R.dimen.keyboard_window_size;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.keyboard_window;
    }

    @Override
    protected void initView() {

    }
}
