package com.excellence.tvkeyboard;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/19
 *     desc   : 多种类型GridView ListView通用适配器
 *              由于需要适配清空退格特殊按键，在Adapter的Data中也要做处理
 *              规则：如果为特殊按钮，在Data需要空字符串占位
 *
 * </pre>
 */

public abstract class KeyBoardAdapter extends BaseAdapter {

    private List<String> mData;
    private int mLayoutResourceId;

    public KeyBoardAdapter(@NonNull List<String> data, @IdRes int layoutResourceId) {
        mData = data;
        mLayoutResourceId = layoutResourceId;
    }


    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(parent.getContext(), convertView, parent, mLayoutResourceId);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder viewHolder, String item, int position);
}
