package com.example.manasatpc.bloadbank.u.ui.fregmants.userCycle;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.user.newpassword.NewPassword;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.SaveData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;
import static com.example.manasatpc.bloadbank.u.ui.fregmants.userCycle.ForgetPasswordStep1Fragment.PHONE;

public class ForgetPasswordStep2Fragment extends Fragment {
    @BindView(R.id.ForgetPasswordStep2Fragment_Code_Check)
    TextInputEditText ForgetPasswordStep2FragmentCodeCheck;
    @BindView(R.id.ForgetPasswordStep2Fragment_New_Password)
    TextInputEditText ForgetPasswordStep2FragmentNewPassword;
    @BindView(R.id.ForgetPasswordStep2Fragment_Retry_New_Password)
    TextInputEditText ForgetPasswordStep2FragmentRetryNewPassword;
    @BindView(R.id.ForgetPasswordStep2Fragment_BT_Change_Password)
    Button ForgetPasswordStep2FragmentBTChangePassword;
    @BindView(R.id.ForgetPasswordStep2Fragment_TV_Remind_Time)
    TextView ForgetPasswordStep2FragmentTVRemindTime;
    @BindView(R.id.ForgetPasswordStep2Fragment_Progress_Bar)
    ProgressBar ForgetPasswordStep2FragmentProgressBar;
    Unbinder unbinder;
    private static APIServices APIServices;
    Bundle getPhoneBundle;
    String savePhone;
    private SaveData saveData;

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
        savePhone = getPhoneBundle.getString(PHONE);
        saveData = getArguments().getParcelable(GET_DATA);
        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == true) {
            HelperMethod.startCountdownTimer(getActivity(), getActivity().findViewById(android.R.id.content)
                    , getActivity().getSupportFragmentManager(), ForgetPasswordStep2FragmentTVRemindTime, saveData);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ForgetPasswordStep2Fragment_BT_Change_Password)
    public void onViewClicked() {
        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return;
        }
        String code = ForgetPasswordStep2FragmentCodeCheck.getText().toString().trim();
        String newPassword = ForgetPasswordStep2FragmentNewPassword.getText().toString().trim();
        String retryNewPassword = ForgetPasswordStep2FragmentRetryNewPassword.getText().toString().trim();
        if (code.isEmpty() || newPassword.isEmpty() || retryNewPassword.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.all_filed_request), Toast.LENGTH_LONG).show();
            return;
        }
        ForgetPasswordStep2FragmentProgressBar.setVisibility(View.VISIBLE);

        APIServices = getRetrofit().create(APIServices.class);

        Call<NewPassword> newPasswordCall = APIServices.getNewPassword(newPassword, retryNewPassword, code, savePhone);
        newPasswordCall.enqueue(new Callback<NewPassword>() {
            @Override
            public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                NewPassword newPassword1 = response.body();
                if (newPassword1.getStatus() == 1) {
                    HelperMethod.stopCountdownTimer();
                    ForgetPasswordStep2FragmentProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), newPassword1.getMsg(), Toast.LENGTH_SHORT).show();
                    LoginFragment loginFragment = new LoginFragment();
                    HelperMethod.replece(loginFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_User_contener, null, null, saveData);

                } else {
                    ForgetPasswordStep2FragmentProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), newPassword1.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewPassword> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                ForgetPasswordStep2FragmentProgressBar.setVisibility(View.GONE);
            }
        });

    }
}
