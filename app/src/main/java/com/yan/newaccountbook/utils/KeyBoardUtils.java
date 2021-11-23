package com.yan.newaccountbook.utils;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.yan.newaccountbook.R;

/**
 * 软键盘工具类
 */
public class KeyBoardUtils {
    /**
     * 通过接口，实现事件响应的效果
     */
    public interface OnEnsureListener{
        void onEnsure();
    }


    private KeyboardView keyboardView;

    private final Keyboard  keyboard;
    private EditText editText;

    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public KeyBoardUtils(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText=editText;
        this.editText.setInputType(InputType.TYPE_NULL);//取消弹出系统键盘
        keyboard=new Keyboard(this.editText.getContext(), R.xml.keyboard);

        this.keyboardView.setKeyboard(keyboard);
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        /**
         * 键盘点击后的监听响应
         */
        this.keyboardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int i) {

            }

            @Override
            public void onRelease(int i) {

            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                Editable editable=editText.getText();
                int start=editText.getSelectionStart();
                switch(primaryCode){
                    case Keyboard.KEYCODE_DELETE:   //点击了删除键
                        if (editable!=null &&editable.length()>0) {
                            if (start>0) {
                                editable.delete(start-1,start);
                            }
                        }
                        break;
                    case Keyboard.KEYCODE_CANCEL:   //点击了清零
                        editable.clear();
                        break;
                    case Keyboard.KEYCODE_DONE:    //点击了完成
                        onEnsureListener.onEnsure();   //通过接口回调的方法，当点击确定时，可以调用这个方法
                        break;
                    default:  //其他数字直接插入
                        editable.insert(start,Character.toString((char)primaryCode));
                        break;
                }
            }

            @Override
            public void onText(CharSequence charSequence) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        });
    }

    /**
     * 显示自定义键盘
     */
    public void showKeyBoard() {
        int visibility=keyboardView.getVisibility();
        if(visibility== View.INVISIBLE || visibility==View.GONE){
            keyboardView.setVisibility(View.VISIBLE);
        }
    }
}
