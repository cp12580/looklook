package com.aaron.looklook;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aaron.view.BaseFragment;
import com.aaron.view.MeizhiFragment;
import com.aaron.view.PengfuFragment;
import com.aaron.view.ZhihuFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<BaseFragment> fragments;
    private ActionBar mActionBar;
    private long mCurrTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new ZhihuFragment());
        fragments.add(new PengfuFragment());
        fragments.add(new MeizhiFragment());
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_camera);
        setFragment("zhihu");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(System.currentTimeMillis() - mCurrTime > 2000){
                mCurrTime = System.currentTimeMillis();
                Snackbar.make(drawer,"再按一次退出",Snackbar.LENGTH_SHORT).show();
                return;
            }
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            setFragment("zhihu");
        } else if (id == R.id.nav_gallery) {
            setFragment("pengfu");
        } else if (id == R.id.nav_slideshow) {
            setFragment("meizhi");
        } else if (id == R.id.nav_manage) {
            Snackbar.make(drawer,"也没什么好设置的╮(～▽～)╭(lan)",Snackbar.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            String url = "http://fir.im/20161111";
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            new AlertDialog.Builder(this)
                    .setMessage("数据来源：daily.zhihu.com,m.pengfu.com,gank.io")
                    .setPositiveButton("朕知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface anInterface, int i) {

                        }
                    }).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(String tag){
        switch (tag){
            case "zhihu":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fm_container,fragments.get(0),"zhihu")
                .commit();
                mActionBar.setTitle("知乎日报");
                break;
            case "pengfu":
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fm_container,fragments.get(1),"pengfu")
                    .commit();
                mActionBar.setTitle("捧腹网");
                break;
            case "meizhi":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fm_container,fragments.get(2),"meizhi")
                        .commit();
                mActionBar.setTitle("妹纸");
                break;

        }
    }
}
