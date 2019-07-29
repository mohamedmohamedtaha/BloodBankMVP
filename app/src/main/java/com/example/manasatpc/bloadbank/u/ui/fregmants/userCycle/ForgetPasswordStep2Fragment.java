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
import com.example.manasatpc.bloadbank.u.data.interactor.ChangePasswordInteractor;
import com.example.manasatpc.bloadbank.u.data.presenter.ChangePasswordPresenter;
import com.example.manasatpc.bloadbank.u.data.view.ChangePasswordView;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.manasatpc.bloadbank.u.ui.fregmants.userCycle.ForgetPasswordStep1Fragment.PHONE;

public class ForgetPasswordStep2Fragment extends Fragment implements ChangePasswordView {
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
    Bundle getPhoneBundle;
    String savePhone;
    boolean check_network;
    private ChangePasswordPresenter presenter;

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
        presenter = new ChangePasswordPresenter(this, new ChangePasswordInteractor());
        check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        // for check network
        if (check_network == true) {
            presenter.isNetworkAvailable();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.onDestory();
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ForgetPasswordStep2Fragment_BT_Change_Password)
    public void onViewClicked() {
        String code = ForgetPasswordStep2FragmentCodeCheck.getText().toString().trim();
        String newPassword = ForgetPasswordStep2FragmentNewPassword.getText().toString().trim();
        String retryNewPassword = ForgetPasswordStep2FragmentRetryNewPassword.getText().toString().trim();
        presenter.changePassword(newPassword, retryNewPassword, code, savePhone, getActivity(), getView());
    }

    @Override
    public void hideProgress() {
        ForgetPasswordStep2FragmentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        ForgetPasswordStep2FragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateToLogin() {
        LoginFragment loginFragment = new LoginFragment();
        HelperMethod.replece(loginFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_User_contener, null, null);
    }

    @Override
    public void setEmpty() {
        Toast.makeText(getActivity(), getString(R.string.all_filed_request), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void networkAvailable() {
        HelperMethod.startCountdownTimer(getActivity(), getActivity().findViewById(android.R.id.content)
                , getActivity().getSupportFragmentManager(), ForgetPasswordStep2FragmentTVRemindTime);
    }

    @Override
    public void stopCounter() {
        HelperMethod.stopCountdownTimer();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }
}