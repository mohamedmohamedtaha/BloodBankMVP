package com.example.manasatpc.bloadbank.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.ui.fregmants.splashCycle.SplashFragment;
import com.example.manasatpc.bloadbank.ui.helper.HelperMethod;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SplashFragment splashFragment = new SplashFragment();
        HelperMethod.replece(splashFragment,getSupportFragmentManager(),R.id.Cycle_Splash_contener,null,null);

   }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
