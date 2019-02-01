package com.example.manasatpc.bloadbank.u.fregmants.userCycle;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.rest.register.Register;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.SelectDateFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegesterFragment extends Fragment {


    @BindView(R.id.Fragment_Name_Regester)
    TextInputEditText FragmentNameRegester;
    @BindView(R.id.Fragment_Email_Regester)
    TextInputEditText FragmentEmailRegester;
    @BindView(R.id.Fragment_Date_Regester)
    TextInputEditText FragmentDateRegester;
    @BindView(R.id.Fragment_Blood_Regester)
    TextInputEditText FragmentBloodRegester;
    @BindView(R.id.Fragment_Last_Date_Regester)
    TextInputEditText FragmentLastDateRegester;
    @BindView(R.id.Fragment_Phone_Regester)
    TextInputEditText FragmentPhoneRegester;
    @BindView(R.id.Fragment_Password_Regester)
    TextInputEditText FragmentPasswordRegester;
    @BindView(R.id.Fragment_Retry_Password_Regester)
    TextInputEditText FragmentRetryPasswordRegester;
    @BindView(R.id.regester_bt)
    Button regesterBt;
    Unbinder unbinder;

    private static final String DATE_START_DIALOG_ID_BIRTH_DAY = "DatePicker_birth_date";
    private static final String DATE_START_DIALOG_ID_Last_Date = "DatePicker_last_date";

    String getDate = null;
    @BindView(R.id.Spinner_Gaverment)
    com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner SpinnerGaverment;
    @BindView(R.id.Spinner_City)
    com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner SpinnerCity;


    public RegesterFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_regester, container, false);
        unbinder = ButterKnife.bind(this, view);

        FragmentDateRegester.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    DialogFragment newFragment = new SelectDateFragment();
                    newFragment.show(getFragmentManager(), DATE_START_DIALOG_ID_BIRTH_DAY);
                }
                // getDate = bundle.getString("date","0");
                //  FragmentDateRegester.setText(getDate);


            }


        });


        FragmentLastDateRegester.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    DialogFragment newFragment = new SelectDateFragment();
                    newFragment.show(getFragmentManager(), DATE_START_DIALOG_ID_Last_Date);

                }

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.regester_bt)
    public void onViewClicked() {
        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(),getView());
        if (check_network == false) {
            return;
        }
        String new_user = FragmentNameRegester.getText().toString().trim();
        String email = FragmentEmailRegester.getText().toString().trim();
        String date_birth = FragmentDateRegester.getText().toString().trim();
        String blood_type = FragmentBloodRegester.getText().toString().trim();
        String last_date = FragmentLastDateRegester.getText().toString().trim();
        String phone = FragmentPhoneRegester.getText().toString().trim();
        String password = FragmentPasswordRegester.getText().toString().trim();
        String retry_password = FragmentRetryPasswordRegester.getText().toString().trim();


        APIServices apiServices = getRetrofit().create(APIServices.class);
        Call<Register> registerCall = apiServices.getRegister(new_user, email,
                "1988-06-30", 1, phone,
                "2018-12-11", password, retry_password, blood_type);
        registerCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                try {
                    Register register = response.body();
                    if (register.getStatus() == 0) {
                        Toast.makeText(getActivity(), register.getMsg(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), register.getMsg(), Toast.LENGTH_LONG).show();

                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.Spinner_Gaverment, R.id.Spinner_City})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Spinner_Gaverment:
                break;
            case R.id.Spinner_City:
                break;
        }
    }
}






















