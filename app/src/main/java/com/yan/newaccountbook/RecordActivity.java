package com.yan.newaccountbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.yan.newaccountbook.Adapter.RecordVpAdapter;
import com.yan.newaccountbook.Fragment.IncomeFragment;
import com.yan.newaccountbook.Fragment.OutcomeFragment;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;

    ViewPager2 viewPager;
    RecordVpAdapter recordVpAdapter;
    List<Fragment> fragmentList;

    List<String> titles=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        initToolbar();
        initTabs();
        initVp();
    }

    private void initToolbar() {
        toolbar=findViewById(R.id.record_toolbar);
        toolbar.setTitle("记账");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initTabs() {
        tabLayout=findViewById(R.id.record_tabLayout);
        //设置tab水平滚动显示
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置字体颜色，选中与未选中
        tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                getResources().getColor(R.color.colorPrimary) );
        //设置选中下划线颜色
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        //设置下划线高度
        tabLayout.setSelectedTabIndicatorHeight(10);
        //设置下划线和文本宽度一致
        tabLayout.setTabIndicatorFullWidth(true);
        //设置标题居中
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        //设置标题
        titles.add("支出");
        titles.add("收入");

    }

    private void initVp() {
        viewPager=findViewById(R.id.record_viewPage);
        fragmentList=new ArrayList<>();
        fragmentList.add(new OutcomeFragment());
        fragmentList.add(new IncomeFragment());
        recordVpAdapter=new RecordVpAdapter(getSupportFragmentManager(),getLifecycle(),fragmentList);
        viewPager.setAdapter(recordVpAdapter);
        //tabLayout和viewPage2绑定
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayout, viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles.get(position));
            }
        });
        tabLayoutMediator.attach();
    }

}