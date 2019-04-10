package com.excellence.keyboarddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.excellence.tvkeyboard.KeyBoardAdapter;
import com.excellence.tvkeyboard.TvKeyBoard;
import com.excellence.tvkeyboard.TvKeyBoardListener;
import com.excellence.tvkeyboard.ViewHolder;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TvKeyBoard mTvKeyBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvKeyBoard = findViewById(R.id.keyboard);
        String[] data = getResources().getStringArray(R.array.search_key_board);
        mTvKeyBoard.init(new MyAdapter(Arrays.asList(data), R.layout.search_keyboard_item), new TvKeyBoardListener(mTvKeyBoard) {
            @Override
            public void showKeyBoardWindow(View view, String item, int position) {

            }

            @Override
            public void onTextChange(String text) {

            }
        }, 9, 11);
        mTvKeyBoard.getEditText().setPadding(10, 0, 0, 0);
    }

    private class MyAdapter extends KeyBoardAdapter {

        public MyAdapter(List<String> data, int layoutResourceId) {
            super(data, layoutResourceId);
        }

        @Override
        public void convert(ViewHolder viewHolder, String item, int position) {
            TextView tvFirst = viewHolder.getView(R.id.first_tv);
            tvFirst.setText(item);
        }
    }

}
