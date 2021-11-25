package com.yan.newaccountbook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.yan.newaccountbook.Adapter.main_rlv_adapter;
import com.yan.newaccountbook.bean.AccountBean;
import com.yan.newaccountbook.db.DBManger;
import com.yan.newaccountbook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    FloatingActionButton fab;   //悬浮按钮

    private TextView outTv,inTv,todayTv;
    private ImageView hideIv;
    int year,month,day;  //当日的时间

    RecyclerView rlv;
    List<AccountBean> accountBeansList;
    main_rlv_adapter rlv_adapter;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDrawerPager();
        initToolBar();
        initView();
        initTime();

    }

    /**
     * 当获得焦点是，被调用的方法
     */
    @Override
    protected void onResume() {
        super.onResume();
        initTop();
        initRlv();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        fab=findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RecordActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 初始化当日时间
     */
    private void initTime(){
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);
    }
    /**
     * 头布局的显示
     */
    private void initTop(){
        outTv=findViewById(R.id.main_top_monthOut_tv);
        inTv=findViewById(R.id.main_top_monthIn_tv);
        hideIv=findViewById(R.id.main_top_hide_iv);
        todayTv=findViewById(R.id.main_top_today_tv);

        float outcomeDay= DBManger.getSumMoneyThisDay(year,month,day,0);
        float incomeDay= DBManger.getSumMoneyThisDay(year,month,day,1);
        String infoDay="今日支出："+outcomeDay+" 今日收入："+incomeDay;
        todayTv.setText(infoDay);

        float outcomeMonth=DBManger.getSumMoneyThisMonth(year,month,0);
        float incomeMonth=DBManger.getSumMoneyThisMonth(year,month,1);
        outTv.setText("￥"+outcomeMonth);
        inTv.setText("￥"+incomeMonth);

        hideIv.setOnClickListener(this);
    }


    /**
     * 列表的显示
     */
    private void initRlv(){
        accountBeansList=new ArrayList<>();
        List<AccountBean> todayAccount=DBManger.getDayAccount(year,month,day);
        accountBeansList.addAll(todayAccount);
        rlv_adapter=new main_rlv_adapter(this,accountBeansList);
        rlv_adapter.setOnClickListener(new main_rlv_adapter.OnClickListener() {
            @Override
            public void OnDelAccount(AccountBean accountBean) {
                showDelDia(accountBean);
            }
        });
        rlv=findViewById(R.id.main_rlv);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rlv.setLayoutManager(linearLayoutManager);
        rlv.setAdapter(rlv_adapter);
    }

    /**
     * 弹出删除警示框
     */
    private void showDelDia(AccountBean accountBean) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                accountBeansList.remove(accountBean);
                DBManger.delAccount(accountBean);
                rlv_adapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }


    /**
     * 初始化抽屉页面
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initDrawerPager() {
        toolbar=findViewById(R.id.main_toolbar);
        drawerLayout=findViewById(R.id.main_drawerLayout);
        navigationView=findViewById(R.id.main_navigationView);
        // 设置左上角图标["三" —— "←"]效果
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        // 添加DrawerLayout监听器，这里根据DrawerLayout的回调方法实现HeaderView的动画效果
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        // 设置不允许 NavigationMenuView 滚动
        NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        if (navigationMenuView != null) {
            navigationMenuView.setVerticalScrollBarEnabled(false);
        }
        /**
         * 抽屉页面 菜单中item 的选择点击事件响应
         */
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_menu_item_user:
                        ToastUtil.show(MainActivity.this,"个人中心");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.main_menu_item_account:
                        ToastUtil.show(MainActivity.this,"历史记录");
                        drawerLayout.closeDrawers();
                        Intent intent=new Intent(MainActivity.this,HistoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.main_menu_item_details:
                        ToastUtil.show(MainActivity.this,"账单统计");
                        drawerLayout.closeDrawers();
                        break;
                    default:
                }
                return false;
            }
        });
    }

    /**
     * 初始化右边菜单
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initToolBar() {

        // 设置ToolBar标题
        toolbar.setTitle("个人账本");
        // 设置ToolBar副标题
//        toolbar.setSubtitle("this is toolbar");
        // 设置navigation button  导航按钮
//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24,null));
//        // 设置Logo图标
//        toolbar.setLogo(getResources().getDrawable(R.drawable.ic_baseline_group_24,null));
        // 设置溢出菜单的图标
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_baseline_more_vert_24,null));
        // 设置Menu
        toolbar.inflateMenu(R.menu.toolbar_menu);

        //设置menu 监听
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_menu_item_setting:
                        ToastUtil.show(MainActivity.this,"设置");
                        break;
                    case R.id.main_menu_item_about:
                        ToastUtil.show(MainActivity.this,"关于");
                        break;
                    case R.id.main_menu_item_search:
                        ToastUtil.show(MainActivity.this,"搜索");
                        Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                        startActivity(intent);
                        break;
                    default:
                }
                return false;
            }
        });
    }

    /**
     * 点击响应
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_top_hide_iv:
                changeTopView();
                break;
            default:
        }
    }

    /**
     * 明文 ，暗文显示切换
     */
    boolean isShow=true;
    private void changeTopView() {
        if (isShow){
            PasswordTransformationMethod passwordMethod = PasswordTransformationMethod.getInstance();
            outTv.setTransformationMethod(passwordMethod);
            inTv.setTransformationMethod(passwordMethod);
            hideIv.setImageResource(R.mipmap.ih_hide);
            isShow=false;
        }else{
            HideReturnsTransformationMethod hideMethod = HideReturnsTransformationMethod.getInstance();
            outTv.setTransformationMethod(hideMethod);
            inTv.setTransformationMethod(hideMethod);
            hideIv.setImageResource(R.mipmap.ih_show);
            isShow=true;
        }
    }
}