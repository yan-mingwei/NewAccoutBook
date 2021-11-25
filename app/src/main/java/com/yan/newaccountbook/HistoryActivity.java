package com.yan.newaccountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.Account;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yan.newaccountbook.Adapter.account_rlv_adapter;
import com.yan.newaccountbook.Adapter.main_rlv_adapter;
import com.yan.newaccountbook.bean.AccountBean;
import com.yan.newaccountbook.db.DBManger;
import com.yan.newaccountbook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView allTv,yearTv,monthTv,changeTv;

    RecyclerView rlv;
    List<AccountBean> accountBeanList=new ArrayList<>();
    account_rlv_adapter rlv_adapter;

    int flag=0;   //0 代表支出，  1 代表收入
    int year,month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initTime();
        initTv();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRlv();
    }

    /**
     * 初始化时间
     */
    private void initTime(){
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
    }

    /**
     * 初始化 textview
     */
    private void initTv() {
        allTv=findViewById(R.id.history_all_tv);
        yearTv=findViewById(R.id.history_year_tv);
        monthTv=findViewById(R.id.history_month_tv);
        changeTv=findViewById(R.id.history_change_tv);

        allTv.setOnClickListener(this);
        yearTv.setOnClickListener(this);
        monthTv.setOnClickListener(this);
        changeTv.setOnClickListener(this);
    }

    private void initRlv() {
        rlv=findViewById(R.id.his_rlv);
        accountBeanList= DBManger.findAccount(flag);
        rlv_adapter=new account_rlv_adapter(this,accountBeanList);
        rlv.setAdapter(rlv_adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rlv.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.history_all_tv:
                ToastUtil.show(this,"所有");
                List<AccountBean> all_list = DBManger.findAccount(flag);
                accountBeanList.clear();
                for (AccountBean a:
                        all_list) {
                    accountBeanList.add(a);
                }
                rlv_adapter.notifyDataSetChanged();
                break;
            case R.id.history_year_tv:
                ToastUtil.show(this,"当年");
                List<AccountBean> year_list=DBManger.findAccount( flag, year);
                accountBeanList.clear();
                for (AccountBean a:
                        year_list) {
                    accountBeanList.add(a);
                }
                rlv_adapter.notifyDataSetChanged();
                break;

            case R.id.history_month_tv:
                ToastUtil.show(this,"当月");
                List<AccountBean> month_list=DBManger.findAccount( flag, year, month);
                accountBeanList.clear();
                for (AccountBean a:
                        month_list) {
                    accountBeanList.add(a);
                }
                rlv_adapter.notifyDataSetChanged();
                break;
            case R.id.history_change_tv:
                ToastUtil.show(this,"改变");
                if (flag==0){
                    flag=1;
                    changeTv.setText("收入");
                    List<AccountBean> list = DBManger.findAccount(flag);
                    accountBeanList.clear();
                    for (AccountBean a:
                         list) {
                        accountBeanList.add(a);
                    }
                    rlv_adapter.notifyDataSetChanged();
                }else{
                    flag=0;
                    changeTv.setText("支出");
                    List<AccountBean> list = DBManger.findAccount(flag);
                    accountBeanList.clear();
                    for (AccountBean a:
                            list) {
                        accountBeanList.add(a);
                    }
                    rlv_adapter.notifyDataSetChanged();
                }
                break;
            default:
        }
    }
}