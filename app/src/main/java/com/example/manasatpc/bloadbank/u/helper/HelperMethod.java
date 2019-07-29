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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.general.GeneralResponseData;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.BloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.DataBloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.Governorates;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.GovernoratesData;
import com.example.manasatpc.bloadbank.u.data.model.notification.firebaseApiToken.registertoken.RegisterToken;
import com.example.manasatpc.bloadbank.u.data.model.notification.firebaseApiToken.removetoken.RemoveToken;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.ui.activities.LoginActivity;
import com.example.manasatpc.bloadbank.u.ui.fregmants.userCycle.LoginFragment;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class HelperMethod {
    public static final String API_KEY = "API_KEY";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String GET_DATA = "get_data";

    private static CountDownTimer countDownTimer;
    static APIServices apiServices;

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

    //This method for handle Fragments
    public static void replece(Fragment fragment, FragmentManager fragmentManager, int id, Toolbar toolbar, String title, Bundle bundle) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        if (toolbar != null) {
            toolbar.setTitle(title);
        }

    }
    //This method for handle Fragments
    public static void add(Fragment fragment, FragmentManager fragmentManager, int id, Toolbar toolbar, String title, Bundle bundle) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        transaction.add(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        if (toolbar != null) {
            toolbar.setTitle(title);
        }

    }

    public static void replece(Fragment fragment, FragmentManager fragmentManager, int id, Toolbar toolbar, String title) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        // for change from commit() because don't happen Error
        //   java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        transaction.commitAllowingStateLoss();

        if (toolbar != null) {
            toolbar.setTitle(title);
        }


    }

    /*
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
    */
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
    public static void startActivity(Context context, Class<?> toActivity) {
        Intent startActivity = new Intent(context, toActivity);
        context.startActivity(startActivity);
    }

    //This method for set time for Reset the password
    public static void startCountdownTimer(final Context context, final View view, final FragmentManager fragmentManager, final TextView textView) {
        countDownTimer = new CountDownTimer(50000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(context.getString(R.string.remind_time) + " " + millisUntilFinished / 1000);

            }

            @Override
            public void onFinish() {
                Snackbar.make(view, context.getString(R.string.time_out), Snackbar.LENGTH_LONG).show();
                HelperMethod.startActivity(context, LoginActivity.class);

                LoginFragment loginFragment = new LoginFragment();
                replece(loginFragment, fragmentManager, R.id.Cycle_User_contener, null, null);
            }
        };
        countDownTimer.start();

    }

    public static void stopCountdownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    //Calender

//    public static void showCalender(Context context, String title, final TextView text_view_data, final DateModel data1) {
//        DatePickerDialog mDatePicker = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
//                new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
//                        DecimalFormat mFormat = new DecimalFormat("00");
//                        if (!data1.getMonth().equals(mFormat.format(Double.valueOf(selectedMonth)))) {
//                            String data = selectedYear + "-" + mFormat.format(Double.valueOf((selectedMonth + 1))) + "-" + mFormat.format(Double.valueOf(selectedDay));
//                            data1.setDate_txt(data);
//                            data1.setDay(mFormat.format(Double.valueOf(selectedDay)));
//                            data1.setMonth(mFormat.format(Double.valueOf(selectedMonth + 1)));
//                            data1.setYear(String.valueOf(selectedYear));
//                            text_view_data.setText(data);
//                        }
//                    }
//                }, Integer.parseInt(data1.getYear()), Integer.parseInt(data1.getMonth()), Integer.parseInt(data1.getDay()));
//        mDatePicker.setTitle(title);
//        mDatePicker.show();
//    }
public static void showCalender(Context context, String title, final TextView text_view_data, final DateModel data1) {
    DatePickerDialog mDatePicker = new DatePickerDialog(context, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat mFormat = new DecimalFormat("00", symbols);
            String data = selectedYear + "-" + mFormat.format(Double.valueOf((selectedMonth + 1))) + "-" + mFormat.format(Double.valueOf(selectedDay));
            data1.setDate_txt(data);
            data1.setDay(mFormat.format(Double.valueOf(selectedDay)));
            data1.setMonth(mFormat.format(Double.valueOf(selectedMonth + 1)));
            data1.setYear(String.valueOf(selectedYear));
            text_view_data.setText(data);
        }
    }, Integer.parseInt(data1.getYear()), Integer.parseInt(data1.getMonth()) - 1, Integer.parseInt(data1.getDay()));
    mDatePicker.setTitle(title);
    mDatePicker.show();
}

       //This method for show data in Spinner

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

    public static void openWebSite(Context context, String url) {
        Uri UrlLink = Uri.parse(url);
        Intent webSite = new Intent(Intent.ACTION_VIEW, UrlLink);
        context.startActivity(webSite);
    }

    public static String formatDateFromDateString(String inputDateFormat, String outputDateFormat, String inputDate) throws ParseException {
        Date mParsedDate;
        String mOutputDateString;
        SimpleDateFormat mInputDateFormat = new SimpleDateFormat(inputDateFormat, java.util.Locale.getDefault());
        SimpleDateFormat mOutputDateFormat = new SimpleDateFormat(outputDateFormat, java.util.Locale.getDefault());
        mParsedDate = mInputDateFormat.parse(inputDate);
        mOutputDateString = mOutputDateFormat.format(mParsedDate);
        return mOutputDateString;
    }

    public static String formatDay(String inputFormat, String inputDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(inputFormat);
        Date dt1 = dateFormat.parse(inputDate);
        DateFormat dateFormat1 = new SimpleDateFormat("EEEE", new Locale("ar"));
        String finalDay = dateFormat1.format(dt1);
        return finalDay;
    }

    public static void getRemoveToken(final Context context, String token, String api_token) {
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getRemoveToken(token, api_token).enqueue(new Callback<RemoveToken>() {
            @Override
            public void onResponse(Call<RemoveToken> call, Response<RemoveToken> response) {
                RemoveToken removeToken = response.body();
                try {
                    if (removeToken.getStatus() == 1) {
                        Toast.makeText(context.getApplicationContext(), removeToken.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context.getApplicationContext(), removeToken.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RemoveToken> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static void getRegisterToken(final Context context, String token, String api_token, String platform) {
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getRegisterToken(token, api_token, platform).enqueue(new Callback<RegisterToken>() {
            @Override
            public void onResponse(Call<RegisterToken> call, Response<RegisterToken> response) {
                RegisterToken registerToken = response.body();
                try {
                    if (registerToken.getStatus() == 1) {
                        Toast.makeText(context.getApplicationContext(), registerToken.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context.getApplicationContext(), registerToken.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RegisterToken> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    public static ArrayList<Integer> getBloodTypes(final Context context, final Spinner RegesterFragmentBloodType) {
        APIServices apiServicesgetBloodTypes = getRetrofit().create(APIServices.class);
        final ArrayList<Integer> IdsBloodType = new ArrayList<>();
        final Call<BloodTypes> bloodTypesCall = apiServicesgetBloodTypes.getBloodTypes();
        bloodTypesCall.enqueue(new Callback<BloodTypes>() {
            @Override
            public void onResponse(Call<BloodTypes> call, Response<BloodTypes> response) {
                String getResult;
                ArrayList<String> strings = new ArrayList<>();
                BloodTypes bloodTypes = response.body();
                if (bloodTypes.getStatus() == 1) {
                    try {
                        strings.add(context.getString(R.string.blood_type));
                        IdsBloodType.add(0);
                        List<GeneralResponseData> bloodTypesList = bloodTypes.getData();
                        for (int i = 0; i < bloodTypesList.size(); i++) {
                            getResult = bloodTypesList.get(i).getName();
                            strings.add(getResult);
                            Integer positionBloodType = bloodTypesList.get(i).getId();
                            IdsBloodType.add(positionBloodType);
                        }
                        HelperMethod.showGovernorates(strings, context, RegesterFragmentBloodType);
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<BloodTypes> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        return IdsBloodType;
    }

    public static ArrayList<Integer> getGovernorates(final Context context, final Spinner ListRequestsDonationFragmentSelectGeverment) {
        final Call<Governorates> governorates = apiServices.getGovernorates();
        final ArrayList<Integer> IdsGeverment = new ArrayList<>();
        governorates.enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    try {
                        String getResult;
                        Governorates governorates1 = response.body();
                        ArrayList<String> stringsGeverment = new ArrayList<>();
                        stringsGeverment.add(context.getString(R.string.select_gaverment));
                        IdsGeverment.add(0);
                        List<GeneralResponseData> governoratesData = governorates1.getData();
                        for (int i = 0; i < governoratesData.size(); i++) {
                            getResult = governoratesData.get(i).getName();
                            stringsGeverment.add(getResult);
                         Integer  positionGeverment = governoratesData.get(i).getId();
                            IdsGeverment.add(positionGeverment);
                        }
                        HelperMethod.showGovernorates(stringsGeverment, context, ListRequestsDonationFragmentSelectGeverment);
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        return IdsGeverment;

    }

}