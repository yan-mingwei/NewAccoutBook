package com.yan.newaccountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.yan.newaccountbook.Adapter.account_rlv_adapter;
import com.yan.newaccountbook.Adapter.main_rlv_adapter;
import com.yan.newaccountbook.bean.AccountBean;
import com.yan.newaccountbook.db.DBManger;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView backIv;
    private EditText searchEt;

    RecyclerView rlv;
    List<AccountBean> accountBeanList=new ArrayList<>();
    account_rlv_adapter rlv_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initBack();
        initEdit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initRlv();
    }

    private void initBack() {
        backIv=findViewById(R.id.search_back_iv);
        backIv.setOnClickListener(this);
    }

    private void initEdit() {
        searchEt=findViewById(R.id.search_et);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String msg=searchEt.getText().toString().trim();
                List<AccountBean> list= DBManger.findByRemark(msg);
                accountBeanList.clear();
                for (AccountBean accountBean:
                     list) {
                    accountBeanList.add(accountBean);
                }
                rlv_adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initRlv() {
        rlv=findViewById(R.id.search_rlv);
        rlv_adapter=new account_rlv_adapter(this,accountBeanList);
        rlv.setAdapter(rlv_adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rlv.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_back_iv:
                finish();
                break;
            default:
        }
    }
}