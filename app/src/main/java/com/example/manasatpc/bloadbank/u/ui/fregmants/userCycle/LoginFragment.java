package com.example.manasatpc.bloadbank.u.ui.fregmants.userCycle;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.interactor.LoginInteractor;
import com.example.manasatpc.bloadbank.u.data.presenter.LoginPresenter;
import com.example.manasatpc.bloadbank.u.data.view.LoginView;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;
import com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends Fragment implements LoginView {
    @BindView(R.id.LoginFragment_Forget_Password)
    TextView LoginFragmentForgetPassword;
    @BindView(R.id.LoginFragment_BT_Login)
    Button LoginFragmentBTLogin;
    @BindView(R.id.LoginFragment_BT_Register)
    Button LoginFragmentBTRegister;
    @BindView(R.id.LoginFragment_Phone)
    TextInputEditText LoginFragmentPhone;
    @BindView(R.id.LoginFragment_Password)
    TextInputEditText LoginFragmentPassword;
    @BindView(R.id.LoginFragment_Progress_Bar)
    ProgressBar LoginFragmentProgressBar;
    @BindView(R.id.LoginFragment_CB_Remeber_My)
    CheckBox LoginFragmentCBRemeberMy;
    Unbinder unbinder;
    RememberMy remeberMy;
    private LoginPresenter presenter;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        //for check if the user in login or not
        remeberMy = new RememberMy(getActivity());
        presenter = new LoginPresenter(this, new LoginInteractor());
        if (remeberMy.isRemember()) {
            HelperMethod.startActivity(getActivity(), HomeActivity.class);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.onDestory();
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.LoginFragment_Forget_Password, R.id.LoginFragment_BT_Login, R.id.LoginFragment_BT_Register})
    public void onViewClicked(View view) {
        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return;
        }
        switch (view.getId()) {
            case R.id.LoginFragment_Forget_Password:
                presenter.navigateToResetPassword();
                break;
            case R.id.LoginFragment_BT_Login:
                String email = LoginFragmentPhone.getText().toString().trim();
                String password = LoginFragmentPassword.getText().toString().trim();
                presenter.validate(email, password,remeberMy, LoginFragmentCBRemeberMy);
                break;
            case R.id.LoginFragment_BT_Register:
                presenter.navigateToRegister();
                break;
        }
    }

    @Override
    public void showProgress() {
        LoginFragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        LoginFragmentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToHome() {
        HelperMethod.startActivity(getActivity(), HomeActivity.class);

    }

    @Override
    public void navigateToRegister() {
        RegesterFragment regesterFragment = new RegesterFragment();
        HelperMethod.replece(regesterFragment, getActivity().getSupportFragmentManager(),
                R.id.Cycle_User_contener, null, getString(R.string.create_new_user));

    }

    @Override
    public void errorLogin() {
        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emptyFiled() {
        Toast.makeText(getActivity(), getString(R.string.all_filed_request), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressOnly() {
        LoginFragmentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void getToken() {
        String token_text = FirebaseInstanceId.getInstance().getToken();
        HelperMethod.getRegisterToken(getActivity(), token_text, remeberMy.getAPIKey(), "android");
    }

    @Override
    public void navigateToResetPassword() {
        ForgetPasswordStep1Fragment forgetPasswordStep1Fragment = new ForgetPasswordStep1Fragment();
        HelperMethod.replece(forgetPasswordStep1Fragment, getActivity().getSupportFragmentManager(),
                R.id.Cycle_User_contener, null, null);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }
}