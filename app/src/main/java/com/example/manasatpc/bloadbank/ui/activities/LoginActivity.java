package com.example.manasatpc.bloadbank.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.ui.fregmants.splashCycle.SplashFragment;
import com.example.manasatpc.bloadbank.ui.fregmants.userCycle.LoginFragment;
import com.example.manasatpc.bloadbank.ui.helper.HelperMethod;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginFragment splashFragment = new LoginFragment();
        HelperMethod.replece(splashFragment,getSupportFragmentManager(),R.id.Cycle_User_contener,null,null);

    }
}
