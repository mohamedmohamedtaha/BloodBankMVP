package com.example.manasatpc.bloadbank.ui.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

public class HelperMethod {
    public static void replece(Fragment fragment, FragmentManager fragmentManager, int id, TextView toolbar, String title){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(id,fragment);
        transaction.addToBackStack(null);

        transaction.commit();
        if (toolbar != null){
            toolbar.setText(title);
        }


    }

}
