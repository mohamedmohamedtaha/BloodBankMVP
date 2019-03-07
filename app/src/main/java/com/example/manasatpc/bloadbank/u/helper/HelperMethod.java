package com.example.manasatpc.bloadbank.u.helper;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity;
import com.example.manasatpc.bloadbank.u.ui.activities.LoginActivity;
import com.example.manasatpc.bloadbank.u.ui.fregmants.userCycle.LoginFragment;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HelperMethod {
    public static final String API_KEY = "API_KEY";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String GET_DATA = "get_data";

    private static CountDownTimer countDownTimer;
    public static boolean exit;

    //This method for handle Fragments
    public static void replece(Fragment fragment, FragmentManager fragmentManager, int id, TextView toolbar, String title, Bundle bundle) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        if (toolbar != null) {
            toolbar.setText(title);
        }


    }

    public static void replece(Fragment fragment, FragmentManager fragmentManager, int id, Toolbar toolbar, String title, SaveData saveData) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GET_DATA, saveData);
        fragment.setArguments(bundle);
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        // for change from commit() because don't happen Error
        //   java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        transaction.commitAllowingStateLoss();

        if (toolbar != null) {
            toolbar.setTitle(title);
        }


    }

    // This method for check Do the internet is available or not ?
    public static boolean isNetworkConnected(Context context, View view) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            return true;
        } else {
            Snackbar.make(view, context.getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();
            return false;

        }
    }

    // This method for handle Activity
    public static void startActivity(Context context, Class<?> toActivity, String getAPI) {
        Intent startActivity = new Intent(context, toActivity);
        startActivity.putExtra(API_KEY, getAPI);
        startActivity.putExtra(API_KEY, getAPI);

        context.startActivity(startActivity);
    }

    // This method for handle Activity
    public static void startActivity(Context context, Class<?> toActivity, SaveData saveData) {
        Intent startActivity = new Intent(context, toActivity);
        startActivity.putExtra(GET_DATA, saveData);
        context.startActivity(startActivity);
    }

    //This method for set time for Reset the password
    public static void startCountdownTimer(final Context context, final View view, final FragmentManager fragmentManager, final TextView textView, final SaveData saveData) {
        countDownTimer = new CountDownTimer(50000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(context.getString(R.string.remind_time) + " " + millisUntilFinished / 1000);

            }

            @Override
            public void onFinish() {
                Snackbar.make(view, context.getString(R.string.time_out), Snackbar.LENGTH_LONG).show();
                HelperMethod.startActivity(context, LoginActivity.class, saveData);

                //    LoginFragment loginFragment = new LoginFragment();
                //      replece(loginFragment, fragmentManager, R.id.Cycle_User_contener, null, null, null);
            }
        }.start();

    }

    //Calender
    public static void showCalender(Context context, String title, final TextView text_view_data, final DateModel data1) {
        DatePickerDialog mDatePicker = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        DecimalFormat mFormat = new DecimalFormat("00");
                        if (!data1.getMonth().equals(mFormat.format(Double.valueOf(selectedMonth)))) {
                            String data = selectedYear + "-" + mFormat.format(Double.valueOf((selectedMonth + 1))) + "-" + mFormat.format(Double.valueOf(selectedDay));
                            data1.setDate_txt(data);
                            data1.setDay(mFormat.format(Double.valueOf(selectedDay)));
                            data1.setMonth(mFormat.format(Double.valueOf(selectedMonth + 1)));
                            data1.setYear(String.valueOf(selectedYear));
                            text_view_data.setText(data);
                        }
                    }
                }, Integer.parseInt(data1.getYear()), Integer.parseInt(data1.getMonth()), Integer.parseInt(data1.getDay()));
        mDatePicker.setTitle(title);
        mDatePicker.show();
    }

    //This method for show data in Spinner

    public static void showGovernorates(ArrayList<String> date, Context context, MaterialBetterSpinner spinner) {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, date);

        spinner.setAdapter(arrayAdapter);

    }

    public static void showGovernorates(ArrayList<String> date, Context context, Spinner spinner) {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, date);

        spinner.setAdapter(arrayAdapter);

    }

    //This method for Exit App
    public static void closeApp(Context context) {

        Intent exitAppIntent = new Intent(Intent.ACTION_MAIN);
        exitAppIntent.addCategory(Intent.CATEGORY_HOME);
        exitAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(exitAppIntent);
    }

    //This method for make Call
    public static void makePhoneCall(Context context, String phone) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phone));
        context.startActivity(callIntent);

    }


    public static void removeFragment(AppCompatActivity activity) {
        for (int j = 0; j < activity.getSupportFragmentManager().getFragments().size(); j++) {
            try {
                SupportRequestManagerFragment fragment = (SupportRequestManagerFragment) activity.getSupportFragmentManager().getFragments().get(j);

                activity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();

                break;
            } catch (Exception e) {

            }
        }
    }
    public static void openWebSite(Context context,String url){
        Uri UrlLink = Uri.parse(url);
        Intent webSite = new Intent(Intent.ACTION_VIEW,UrlLink);
        context.startActivity(webSite);
    }
}

























