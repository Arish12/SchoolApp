package it.arisetech.app.arish.ui.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.BindDimen;
import butterknife.BindString;
import it.arisetech.app.arish.R;
import it.arisetech.app.arish.profileview.ProfileActivity;
;import static it.arisetech.app.arish.R.id.relativeLayout;

/**
 * Created by Miroslaw Stanek on 15.07.15.
 */
public class BaseDrawerActivity extends BaseActivity  {

        @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
        @BindView(R.id.vNavigation)
    NavigationView vNavigation;
    @BindDimen(R.dimen.global_menu_avatar_size)
    int avatarSize;


    //Cannot be bound via Butterknife, hosting view is initialized later (see setupHeader() method)
    private ImageView ivMenuUserProfilePhoto;

    private boolean pendingIntroAnimation;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.activity_drawer);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);

        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);

        bindViews();
//        setupHeader();
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        startActivity(new Intent(BaseDrawerActivity.this, MainActivity.class));
                        break;
                    case R.id.menu_feed:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int[] startingLocation = new int[2];
                                EventsActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                                overridePendingTransition(0, 0);
                            }
                        }, 300);
                        break;
                    case R.id.menu_profile:
                        startActivity(new Intent(BaseDrawerActivity.this, ProfileActivity.class));
                        break;



//                    case R.id.login:
//                        startActivity(new Intent(BaseDrawerActivity.this, ProfileActivity.class));
//                        break;
                    case R.id.menu_about:
                        startActivity(new Intent(BaseDrawerActivity.this, AboutUsActivity.class));
                        break;
//                                    startActivity(new Intent(BaseDrawerActivity.this,M));

                }

                return true;
            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



}