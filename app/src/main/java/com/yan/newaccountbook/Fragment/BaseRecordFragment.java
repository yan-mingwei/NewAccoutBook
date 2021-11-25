package com.yan.newaccountbook.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.yan.newaccountbook.Adapter.recd_frag_rlv_adapter;
import com.yan.newaccountbook.R;
import com.yan.newaccountbook.bean.AccountBean;
import com.yan.newaccountbook.bean.TypeBean;
import com.yan.newaccountbook.dialog.RemarkDialog;
import com.yan.newaccountbook.utils.CalToDate;
import com.yan.newaccountbook.utils.KeyBoardUtils;
import com.yan.newaccountbook.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class BaseRecordFragment extends Fragment implements View.OnClickListener{
    ImageView typeIv;
    int selTypeIgId;
    TextView typeTv;
    EditText moneyEt;

    RecyclerView rlv;
    List<TypeBean> typeList;
    recd_frag_rlv_adapter rlv_adapter;

    TextView remarkTv,timeTv;
    KeyboardView keyboardView;

    Calendar cld;

    AccountBean accountBean;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_base_record, container, false);
        initView(view);
        loadDataToRlv();
        initTime();
        initEvent();
        initKeyBoard();
        return view;
    }

    /**
     * 初始化view
     * @param view
     */
    private void initView(View view) {
        typeIv=view.findViewById(R.id.recd_frag_top_im);
        typeTv=view.findViewById(R.id.recd_frag_top_tv);
        moneyEt=view.findViewById(R.id.recd_frag_top_et);

        rlv=view.findViewById(R.id.recd_frag_rlv);

        remarkTv=view.findViewById(R.id.recd_frag_remark);
        timeTv=view.findViewById(R.id.recd_frag_time);

        keyboardView=view.findViewById(R.id.recd_frag_keyboardView);
    }

    /**
     * 列表加载数据的方法，这个方法需要被重写 ，很重要的一种技巧
     */
    public void loadDataToRlv() {
        typeList=new ArrayList<>();
        rlv_adapter=new recd_frag_rlv_adapter(getContext(), typeList, new recd_frag_rlv_adapter.OnClickListener() {
            /**
             * 接口回调，更新头部控件
             * @param typeBean
             */
            @Override
            public void onRefreshView(TypeBean typeBean,int pos) {
                typeIv.setImageResource(typeBean.getsImageId());
                typeTv.setText(typeBean.getTypeName());
                selTypeIgId=typeBean.getsImageId();
                rlv_adapter.setPos(pos);
                rlv_adapter.notifyDataSetChanged();
            }
        });
        rlv.setAdapter(rlv_adapter);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),5);
        rlv.setLayoutManager(gridLayoutManager);
    }

    /**
     * 初始化时间
     */
    private void initTime() {
        cld=Calendar.getInstance();
        timeTv.setText(CalToDate.trans(cld));
    }
    /**
     * 设置事件
     */
    private void initEvent(){
        timeTv.setOnClickListener(this);
        remarkTv.setOnClickListener(this);
    }
    /**
     * 弹出软键盘
     */
    private void initKeyBoard(){
        KeyBoardUtils keyBoardUtils=new KeyBoardUtils(keyboardView,moneyEt);
        keyBoardUtils.showKeyBoard();
        keyBoardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            /**
             * 软键盘中  确定按钮点击后 回调的方法
             */
            @Override
            public void onEnsure() {
                String moneyStr=moneyEt.getText().toString().trim();
                ToastUtil.show(getContext(),moneyStr);
                if (TextUtils.isEmpty(moneyStr) || moneyStr.equals("0")){
                    getActivity().finish();
                    return;
                }
                accountBean=new AccountBean();
                accountBean.setMoney(Float.parseFloat(moneyStr));
                accountBean.setTypeName(typeTv.getText().toString());
                accountBean.setsImageId(selTypeIgId);
                accountBean.setRemark(remarkTv.getText().toString());
                accountBean.setTime(timeTv.getText().toString());
                accountBean.setYear(cld.get(Calendar.YEAR));
                accountBean.setMonth(cld.get(Calendar.MONTH)+1); //这样得到的月份会比实际小1
                accountBean.setDay(cld.get(Calendar.DAY_OF_MONTH));

                saveAccountToDB();
                getActivity().finish();
            }
        });
    }

    public void saveAccountToDB() {

    }

    /**
     * 响应点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.recd_frag_time:
                showDatePick();
                break;
            case R.id.recd_frag_remark:
                showRemarkDialog();
                break;

            default:
        }
    }

    /**
     * 弹出日期选择框
     */
    private void showDatePick(){
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                cld=Calendar.getInstance();
                cld.set(year,month,day);
                showTimePick();
            }

        },2021,11,23).show();
    }

    /**
     * 间接弹出时间选择
     */
    private void showTimePick(){
        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                cld.set(Calendar.HOUR_OF_DAY,hourOfDay);
                cld.set(Calendar.MINUTE,minute);
                timeTv.setText(CalToDate.trans(cld));
            }
        },12,36,true).show();
    }

    /**
     * 弹出备注添加
     */
    private void showRemarkDialog(){
        final RemarkDialog remarkDialog=new RemarkDialog(getContext());
        remarkDialog.show();
        remarkDialog.setDialogSize();
        remarkDialog.setOnSureListener(new RemarkDialog.OnSureListener() {
            @Override
            public void onEnsure() {
                String msg=remarkDialog.getEditText();
                if(!TextUtils.isEmpty(msg)){
                    remarkTv.setText(msg);
                }
                remarkDialog.cancel();
            }
        });
    }
}