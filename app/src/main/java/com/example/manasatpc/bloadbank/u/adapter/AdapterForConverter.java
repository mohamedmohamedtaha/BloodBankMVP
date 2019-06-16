package com.example.manasatpc.bloadbank.u.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.article.HomeFragment;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.donation.ListRequestsDonationFragment;

public class AdapterForConverter extends FragmentPagerAdapter {
    private Context mContext;
    Bundle bundle;
    public AdapterForConverter(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext =mContext;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0 ){
            HomeFragment homeFragment = new HomeFragment();
            return homeFragment;
        }else {
            ListRequestsDonationFragment listRequestsDonationFragment = new ListRequestsDonationFragment();
            return listRequestsDonationFragment;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.articles);
        }else {
            return mContext.getString(R.string.request_donation);

        }
    }
}