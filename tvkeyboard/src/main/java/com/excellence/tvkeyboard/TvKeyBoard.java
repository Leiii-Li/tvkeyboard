package com.excellence.tvkeyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;


/**
 * <pre>
 *     author : Nelson
 *     GitHub : https://github.com/Nelson-KK
 *     time   : 2019/4/4
 *     desc   :
 * </pre>
 */
public class TvKeyBoard extends LinearLayout {
    public static final String TAG = TvKeyBoard.class.getSimpleName();
    private static final int DEFAULT_NUM_COLUMNS = 3;

    private NotShowInputEditText mEditText;
    private KeyboardGridView mKeyBoardView;
    private int mEditTextBackground;
    private int mEditTextContentColor;
    private int mEditTextCursorDrawable;
    private float mContentTextSize;
    private int mEditTextHeight;
    private int mNumColumns;
    private int mContentMargin;
    private TvKeyBoardListener mListener;
    private KeyBoardAdapter mAdapter;
    private int mEmptyIndex = 0;
    private int mBackSpaceIndex = 0;

    public TvKeyBoard(Context context) {
        this(context, null);
    }

    public TvKeyBoard(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TvKeyBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(attrs);
        init();
    }

    private void initAttribute(AttributeSet attrs) {
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.TvKeyBoard, 0, 0);
        for (int i = 0; i < attr.getIndexCount(); i++) {
            int value = attr.getIndex(i);
            if (value == R.styleable.TvKeyBoard_editTextBackground) {
                mEditTextBackground = attr.getResourceId(value, 0);
            } else if (value == R.styleable.TvKeyBoard_editTextContentColor) {
                mEditTextContentColor = attr.getColor(value, Color.BLACK);
            } else if (value == R.styleable.TvKeyBoard_editTextCursorDrawable) {
                mEditTextCursorDrawable = attr.getResourceId(value, 0);
            } else if (value == R.styleable.TvKeyBoard_contentMargin) {
                mContentMargin = attr.getDimensionPixelSize(value, 0);
            } else if (value == R.styleable.TvKeyBoard_editTextContentSize) {
                mContentTextSize = attr.getDimension(value, R.dimen.defaultTextSize);
            } else if (value == R.styleable.TvKeyBoard_editTextHeight) {
                mEditTextHeight = attr.getDimensionPixelSize(value, R.dimen.defaultEditTextHeight);
            } else if (value == R.styleable.TvKeyBoard_numColumns) {
                mNumColumns = attr.getInt(value, DEFAULT_NUM_COLUMNS);
            }
        }
        attr.recycle();
    }

    private void init() {
        this.setOrientation(VERTICAL);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setGravity(Gravity.CENTER_HORIZONTAL);

        mEditText = new NotShowInputEditText(getContext());
        mEditText.setSingleLine();
        mEditText.addTextChangedListener(new SearchTextWatcher());
        mEditText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        mKeyBoardView = new KeyboardGridView(getContext());
        mKeyBoardView.setVerticalScrollBarEnabled(false);
        mKeyBoardView.setOnDelKeyDownListener(new KeyboardGridView.OnDelKeyDownListener() {
            @Override
            public void onKeyDown(View view) {
                onBackSpaceClick();
            }
        });
        mKeyBoardView.setOnNumberKeyDownListener(new KeyboardGridView.OnNumberKeyDownListener() {
            @Override
            public void onKeyDown(int value) {
                setEditTextContent(mEditText.getText().toString() + value);
            }
        });
        mKeyBoardView.setSelector(new BitmapDrawable());

        setAttr();
        initView();
    }

    private void initView() {
        if (mAdapter != null) {
            mKeyBoardView.setAdapter(mAdapter);
        }
        if (mListener != null) {
            mKeyBoardView.setOnCenterKeyDownListener(new KeyboardGridView.OnCenterKeyDownListener() {
                @Override
                public void onKeyDown(View view, int position) {
                    if (position == mEmptyIndex) {
                        mListener.onEmptyKeyClick();
                    } else if (position == mBackSpaceIndex) {
                        mListener.onBackSpaceKeyClick();
                    } else {
                        String item = mAdapter.getItem(position);
                        if (item.length() > 1) {
                            mListener.showKeyBoardWindow(view, item, position);
                        } else {
                            mListener.onSingleTextClick(item);
                        }
                    }
                }
            });
        }
    }

    public void onEmptyClick() {
        mEditText.setText("");
    }

    public void onBackSpaceClick() {
        String content = mEditText.getText().toString();
        if (content.length() == 0) {
            return;
        }
        content = content.substring(0, content.length() - 1);
        setEditTextContent(content);
    }

    private void setAttr() {
        //set edit text attr
        if (mContentTextSize == 0) {
            mContentTextSize = getResources().getDimension(R.dimen.defaultTextSize);
        }

        mEditText.setBackgroundResource(mEditTextBackground);
        mEditText.setTextColor(mEditTextContentColor);
        mEditText.setTextSize(mContentTextSize);
        mEditText.setCursorDrawable(mEditTextCursorDrawable);

        LinearLayout.LayoutParams editTextParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        if (mEditTextHeight != 0) {
            editTextParams.height = MeasureSpec.makeMeasureSpec(mEditTextHeight, MeasureSpec.EXACTLY);
        }
        this.addView(mEditText, editTextParams);

        // set grid view attr
        if (mNumColumns == 0) {
            mNumColumns = DEFAULT_NUM_COLUMNS;
        }

        mKeyBoardView.setNumColumns(mNumColumns);
        LinearLayout.LayoutParams keyBoardViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        keyBoardViewParams.topMargin = mContentMargin;
        mKeyBoardView.setGravity(Gravity.CENTER_HORIZONTAL);
        this.addView(mKeyBoardView, keyBoardViewParams);
    }

    /**
     * @param adapter
     * @param listener
     * @param emptyIndex     清空按钮的下标
     * @param backSpaceIndex 退格按钮的下标
     */
    public void init(KeyBoardAdapter adapter, TvKeyBoardListener listener, int emptyIndex, int backSpaceIndex) {
        mEmptyIndex = emptyIndex;
        mBackSpaceIndex = backSpaceIndex;
        mAdapter = adapter;
        mListener = listener;
        initView();
    }

    public EditText getEditText() {
        return mEditText;
    }

    public GridView getKeyBoardView() {
        return mKeyBoardView;
    }

    public void onSingleTextClick(String text) {
        appendEditText(text);
    }

    private class SearchTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mListener != null) {
                mListener.onTextChange(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public void appendEditText(String text) {
        setEditTextContent(mEditText.getText() + text);
    }

    private void setEditTextContent(String text) {
        mEditText.setText(text);
        mEditText.setSelection(mEditText.getText().toString().length());
    }
}
