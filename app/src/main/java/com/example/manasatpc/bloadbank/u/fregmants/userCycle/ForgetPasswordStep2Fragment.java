package com.example.manasatpc.bloadbank.u.fregmants.userCycle;


import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.rest.newpassword.NewPassword;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.fregmants.userCycle.ForgetPasswordStep1Fragment.PHONE;

public class ForgetPasswordStep2Fragment extends Fragment {
    private static CountDownTimer countDownTimer;

    private static APIServices APIServices;

    @BindView(R.id.Fragment_Code_Check_Step2)
    TextInputEditText FragmentCodeCheckStep2;
    @BindView(R.id.Fragment_New_Password_Step2)
    TextInputEditText FragmentNewPasswordStep2;
    @BindView(R.id.Fragment_Retry_New_Password_Step2)
    TextInputEditText FragmentRetryNewPasswordStep2;
    @BindView(R.id.bt_Change_Password_Step2)
    Button btChangePasswordStep2;
    Unbinder unbinder;
    @BindView(R.id.Forget_Step2_progress)
    ProgressBar ForgetStep2Progress;
    Bundle getPhoneBundle;
    String savePhone;
    @BindView(R.id.TV_Remind_Time)
    TextView TVRemindTime;

    public ForgetPasswordStep2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_step2, container, false);
        unbinder = ButterKnife.bind(this, view);
        getPhoneBundle = getArguments();
//        savePhone = getPhoneBundle.getString(PHONE);
        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(),getView());
        if (check_network == true) {
            HelperMethod.startCountdownTimer(getActivity(),getActivity().findViewById(android.R.id.content)
                    ,getActivity().getSupportFragmentManager(),TVRemindTime);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_Change_Password_Step2)
    public void onViewClicked() {
        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(),getView());
        if (check_network == false) {
            return;
        }
        String code = FragmentCodeCheckStep2.getText().toString().trim();
        String newPassword = FragmentNewPasswordStep2.getText().toString().trim();
        String retryNewPassword = FragmentRetryNewPasswordStep2.getText().toString().trim();
        if (code.isEmpty() || newPassword.isEmpty() || retryNewPassword.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.all_filed_request), Toast.LENGTH_LONG).show();
            return;
        }

        APIServices = getRetrofit().create(APIServices.class);

        Call<NewPassword> newPasswordCall = APIServices.getNewPassword(newPassword, retryNewPassword, code, savePhone);
        newPasswordCall.enqueue(new Callback<NewPassword>() {
            @Override
            public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                ForgetStep2Progress.setVisibility(View.VISIBLE);
                NewPassword newPassword1 = response.body();
                if (newPassword1.getStatus() == 1) {
                    ForgetStep2Progress.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), newPassword1.getMsg(), Toast.LENGTH_SHORT).show();
                    LoginFragment loginFragment = new LoginFragment();
                    HelperMethod.replece(loginFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_User_contener, null, null, null);

                } else {
                    ForgetStep2Progress.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), newPassword1.getMsg(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<NewPassword> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                ForgetStep2Progress.setVisibility(View.GONE);

            }
        });

    }


}
