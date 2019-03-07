package com.example.manasatpc.bloadbank.u.ui.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.helper.SaveData;
import com.example.manasatpc.bloadbank.u.ui.fregmants.splashCycle.SplashFragment;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;

import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;

public class SplashActivity extends AppCompatActivity {
    private Boolean exitApp = false;
    private SaveData saveData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        saveData = getIntent().getParcelableExtra(GET_DATA);

        SplashFragment splashFragment = new SplashFragment();
        HelperMethod.replece(splashFragment,getSupportFragmentManager(),R.id.Cycle_Splash_contener,null,null,saveData);

   }

    @Override
    public void onBackPressed() {
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

    }
}
