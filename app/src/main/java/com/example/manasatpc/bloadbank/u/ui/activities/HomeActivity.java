package com.example.manasatpc.bloadbank.u.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others.AboutAppFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others.ConnectWithUsFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others.CustomFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others.EditInformationFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others.MyFavoriteFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others.SettingsNotificationFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets.MapFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets.RequestDonationFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.userCycle.LoginFragment;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.API_KEY;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    Intent intent ;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        intent =getIntent();
        bundle = intent.getExtras();

        setSupportActionBar(toolbar);
  //      ActionBar  actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_navigation);


        if (savedInstanceState != null) {

        } else {
            CustomFragment customFragmentForHome = new CustomFragment();
            HelperMethod.replece(customFragmentForHome
                    , getSupportFragmentManager(),
                    R.id.Cycle_Home_contener, null, null, bundle);

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.my_information:
                EditInformationFragment editInformationFragment = new EditInformationFragment();
                HelperMethod.replece(editInformationFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, null, null, null);
                break;
            case R.id.setting_notification:
                SettingsNotificationFragment settingsNotificationFragment = new SettingsNotificationFragment();
                HelperMethod.replece(settingsNotificationFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, null, null, null);
                break;
            case R.id.my_favorite:
                MyFavoriteFragment myFavoriteFragment = new MyFavoriteFragment();
                HelperMethod.replece(myFavoriteFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, null, null, bundle);
                break;
            case R.id.used_information:
                break;
            case R.id.connect_with_us:
                ConnectWithUsFragment connectWithUsFragment = new ConnectWithUsFragment();
                HelperMethod.replece(connectWithUsFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, null, null, null);
                break;
            case R.id.about_app:
                AboutAppFragment aboutAppFragment = new AboutAppFragment();
                HelperMethod.replece(aboutAppFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, null, null, null);
                break;
            case R.id.evaluate_app:

                break;
            case R.id.sign_out:
                /*
                LoginFragment loginFragment = new LoginFragment();
                HelperMethod.replece(loginFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, null, null, null);*/
                MapFragment mapFragment = new MapFragment();
                HelperMethod.replece(mapFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, null, null, null);
                break;
            default:

                CustomFragment customFragmentForHome = new CustomFragment();
                HelperMethod.replece(customFragmentForHome
                        , getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, null, null, bundle);
                break;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
