package com.example.manasatpc.bloadbank.u.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.notification.notificationscount.NotificationsCount;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.DrawerLocker;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;
import com.example.manasatpc.bloadbank.u.helper.SaveData;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.notifications.NotificationListFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.notifications.SettingsNotificationFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others.AboutAppFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others.ConnectWithUsFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.CustomFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.EditInformationFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.MyFavoriteFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker {
    private static final String SAVE_TITLE = "save_title";
    // @BindView(R.id.toolbar)
    public static Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    Intent intent;
    Bundle bundle;
    RememberMy logout;
    @BindView(R.id.TV_Count_Notification)
    TextView TVCountNotification;
    private Boolean exitApp = false;
    SaveData saveData;
    public static int titleID;
    ActionBarDrawerToggle toggle;
    private APIServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        intent = getIntent();
        bundle = intent.getExtras();
        logout = new RememberMy(this);
        saveData = getIntent().getParcelableExtra(GET_DATA);

        // for show Menu notification
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        if (savedInstanceState != null) {
            titleID = savedInstanceState.getInt(SAVE_TITLE);
            toolbar.setTitle(titleID);

        } else {
            CustomFragment customFragmentForHome = new CustomFragment();
            titleID = R.string.home;
            HelperMethod.replece(customFragmentForHome, getSupportFragmentManager(),
                    R.id.Cycle_Home_contener, toolbar, String.valueOf(titleID), saveData);

        }


        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        //For show Notification count
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getNotificationsCount("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl").enqueue(new Callback<NotificationsCount>() {
            @Override
            public void onResponse(Call<NotificationsCount> call, Response<NotificationsCount> response) {
                NotificationsCount notificationsCount = response.body();
                try {
                    if (notificationsCount.getStatus() == 1) {
                        TVCountNotification.setText(notificationsCount.getData().getNotificationsCount().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), notificationsCount.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationsCount> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onBackPressed() {

        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        setDraweEnabled(true);


        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else if (fragments == 1) {
            if (exitApp) {
                HelperMethod.closeApp(getApplicationContext());
                return;
            }
            exitApp = true;
            Toast.makeText(this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    exitApp = false;

                }
            }, 2000);
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
        switch (id) {
            case R.id.home:
                onBackPressed();
                break;
            case R.id.action_notification:
                NotificationListFragment notificationListFragment = new NotificationListFragment();
                HelperMethod.replece(notificationListFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.alarms), saveData);
                break;
            default:

        }

        //noinspection SimplifiableIfStatement
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
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.my_information), saveData);
                break;
            case R.id.setting_notification:
                SettingsNotificationFragment settingsNotificationFragment = new SettingsNotificationFragment();
                HelperMethod.replece(settingsNotificationFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.setting_notification), saveData);
                break;
            case R.id.my_favorite:
                MyFavoriteFragment myFavoriteFragment = new MyFavoriteFragment();
                HelperMethod.replece(myFavoriteFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.my_information), saveData);
                break;
            case R.id.used_information:


                break;
            case R.id.connect_with_us:
                ConnectWithUsFragment connectWithUsFragment = new ConnectWithUsFragment();
                HelperMethod.replece(connectWithUsFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.connect_with_us), saveData);
                break;
            case R.id.about_app:
                AboutAppFragment aboutAppFragment = new AboutAppFragment();
                HelperMethod.replece(aboutAppFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.about_app), saveData);
                break;
            case R.id.evaluate_app:

                break;
            case R.id.sign_out:
                logout.removeDateUser(this);
                HelperMethod.startActivity(getApplicationContext(), LoginActivity.class, saveData);


/*                MapFragment mapFragment = new MapFragment();
                HelperMethod.replece(mapFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, null, null, saveData);*/
                break;
            default:
                CustomFragment customFragmentDefault = new CustomFragment();
                HelperMethod.replece(customFragmentDefault
                        , getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.home), saveData);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_TITLE, titleID);
    }


    @Override
    public void setDraweEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawerLayout.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }

    @Override
    protected void onPause() {
        HelperMethod.removeFragment(this);

        super.onPause();
    }
}
