package com.yan.newaccountbook.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yan.newaccountbook.R;

import java.util.List;

/**
 * 输入备注的自定义警告框
 */
public class RemarkDialog extends Dialog  implements View.OnClickListener  {

    public interface OnSureListener{
        /**
         * 作为一个事件发生信号，太秀了
         */
        public void onEnsure();
    }

    EditText editText;
    Button cancelBt,ensureBt;

    OnSureListener onSureListener;

    public void setOnSureListener(OnSureListener onSureListener) {
        this.onSureListener = onSureListener;
    }

    public RemarkDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_remark);
        editText=findViewById(R.id.dia_remark_et);
        cancelBt=findViewById(R.id.dia_remark_bt_no);
        ensureBt= findViewById(R.id.dia_remark_bt_yes);
        cancelBt.setOnClickListener(this);
        ensureBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dia_remark_bt_no:
                cancel();
                break;
            case R.id.dia_remark_bt_yes:
                /**
                 *  事件回调
                 */
                onSureListener.onEnsure();
                break;

            default:
        }

    }
    /**
     * 获取输入框的内容
     */
    public String getEditText(){
        return editText.getText().toString().trim();
    }


    /** 设置Dialog的尺寸和屏幕尺寸一致*/
    public void setDialogSize(){
//        获取当前窗口对象
        Window window = getWindow();
//        获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
//        获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int)(d.getWidth());  //对话框窗口为屏幕窗口
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
        handler.sendEmptyMessageDelayed(1,100);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //自动弹出软键盘的方法
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };
}
