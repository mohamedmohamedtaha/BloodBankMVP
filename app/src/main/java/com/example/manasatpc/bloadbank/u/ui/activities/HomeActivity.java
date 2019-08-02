package com.example.manasatpc.bloadbank.u.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.notification.notificationscount.NotificationsCount;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.DrawerLocker;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.CustomFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.EditInformationFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.MyFavoriteFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.notifications.NotificationListFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.notifications.SettingsNotificationFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others.AboutAppFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others.ConnectWithUsFragment;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker {
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.TV_Count_Notification)
    TextView TVCountNotification;
    private static final String SAVE_TITLE = "save_title";
    public static Toolbar toolbar;
    RememberMy logout;
    @BindView(R.id.Notification_count_Image)
    ImageView NotificationCountImage;
    private Boolean exitApp = false;
    public static int titleID;
    ActionBarDrawerToggle toggle;
    private APIServices apiServices;
    //For save current fragment
    private int currentMenuItem;
    private Fragment fragmentCurrent;

    private EditInformationFragment editInformationFragment = new EditInformationFragment();
    private SettingsNotificationFragment settingsNotificationFragment = new SettingsNotificationFragment();
    private MyFavoriteFragment myFavoriteFragment = new MyFavoriteFragment();
    private AboutAppFragment used_information = new AboutAppFragment();
    private ConnectWithUsFragment connectWithUsFragment = new ConnectWithUsFragment();
    private AboutAppFragment aboutAppFragment = new AboutAppFragment();
    private CustomFragment customFragmentDefault = new CustomFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        logout = new RememberMy(this);

        // for show Menu notification
        setSupportActionBar(toolbar);
        if (savedInstanceState != null) {
            titleID = savedInstanceState.getInt(SAVE_TITLE);
            toolbar.setTitle(titleID);

        } else {
            currentMenuItem = R.id.home;
            //  CustomFragment customFragmentForHome = new CustomFragment();
            titleID = R.string.home;
            fragmentCurrent = customFragmentDefault;
            HelperMethod.add(customFragmentDefault, getSupportFragmentManager(),
                    R.id.Cycle_Home_contener, toolbar, String.valueOf(titleID));

        }
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        //For show Notification count
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getNotificationsCount(logout.getAPIKey()).enqueue(new Callback<NotificationsCount>() {
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
        } else if (!fragmentCurrent.equals(customFragmentDefault)) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            HelperMethod.startActivity(getApplicationContext(), HomeActivity.class);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == currentMenuItem) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }

        switch (id) {
            case R.id.my_information:
                fragmentCurrent = editInformationFragment;
                HelperMethod.replaceFragment(getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, editInformationFragment);
                break;
            case R.id.setting_notification:
                fragmentCurrent = settingsNotificationFragment;
                HelperMethod.replece(settingsNotificationFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.setting_notification));
                break;
            case R.id.my_favorite:
                fragmentCurrent = myFavoriteFragment;
                HelperMethod.replece(myFavoriteFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.my_information));
                break;
            case R.id.used_information:
                fragmentCurrent = used_information;
                HelperMethod.replece(fragmentCurrent, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.used_information));
                break;
            case R.id.connect_with_us:
                fragmentCurrent = connectWithUsFragment;
                HelperMethod.replece(connectWithUsFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.connect_with_us));
                break;
            case R.id.about_app:
                fragmentCurrent = aboutAppFragment;
                HelperMethod.replece(aboutAppFragment, getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.about_app));
                break;
            case R.id.evaluate_app:

                break;
            case R.id.sign_out:
                String tokentxt = FirebaseInstanceId.getInstance().getToken();
                HelperMethod.getRemoveToken(getApplicationContext(), tokentxt, logout.getAPIKey());
                logout.removeDateUser(this);
                HelperMethod.startActivity(getApplicationContext(), LoginActivity.class);
                break;
            default:
                fragmentCurrent = customFragmentDefault;
                HelperMethod.replece(customFragmentDefault
                        , getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.home));
        }
        currentMenuItem = id;
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

    @OnClick(R.id.Notification_count_Image)
    public void onViewClicked() {
        NotificationListFragment notificationListFragment = new NotificationListFragment();
        HelperMethod.replece(notificationListFragment, getSupportFragmentManager(),
                R.id.Cycle_Home_contener, toolbar, getString(R.string.alarms));
    }
}