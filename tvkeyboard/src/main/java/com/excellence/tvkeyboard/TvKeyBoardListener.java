package com.excellence.tvkeyboard;

import android.view.View;

/**
 * <pre>
 *     author : Nelson
 *     GitHub : https://github.com/Nelson-KK
 *     time   : 2019/4/4
 *     desc   :
 * </pre>
 */
public abstract class TvKeyBoardListener {
    private TvKeyBoard mKeyBoard;

    public TvKeyBoardListener(TvKeyBoard tvKeyBoard) {
        mKeyBoard = tvKeyBoard;
    }

    public void onEmptyKeyClick() {
        mKeyBoard.onEmptyClick();
    }

    public void onBackSpaceKeyClick() {
        mKeyBoard.onBackSpaceClick();
    }

    public void onSingleTextClick(String text){
        mKeyBoard.onSingleTextClick(text);
    }

    public abstract void showKeyBoardWindow(View view, String item, int position);

    public abstract void onTextChange(String text);
}
