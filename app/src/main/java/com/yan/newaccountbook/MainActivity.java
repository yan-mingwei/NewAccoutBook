package com.yan.newaccountbook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.yan.newaccountbook.utils.ToastUtil;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    FloatingActionButton fab;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDrawerPager();
        initToolBar();
        initView();

    }

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
                        ToastUtil.show(MainActivity.this,"账单详情");
                        drawerLayout.closeDrawers();
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

        initToolBar();
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
                        break;

                    default:

                }
                return false;
            }
        });

    }


}