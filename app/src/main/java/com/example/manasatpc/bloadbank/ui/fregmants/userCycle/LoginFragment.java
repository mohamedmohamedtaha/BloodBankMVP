package com.example.manasatpc.bloadbank.ui.fregmants.userCycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.ui.activities.HomeActivity;
import com.example.manasatpc.bloadbank.ui.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends Fragment {

    @BindView(R.id.Fragment_Phone_Login)
    TextInputLayout FragmentPhoneLogin;
    @BindView(R.id.Fragment_Password_Login)
    TextInputLayout FragmentPasswordLogin;
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.cretae_new_user)
    Button cretaeNewUser;
    Unbinder unbinder;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.forget_password, R.id.login, R.id.cretae_new_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forget_password:
                ForgetPasswordStep1Fragment forgetPasswordStep1Fragment = new ForgetPasswordStep1Fragment();
                HelperMethod.replece(forgetPasswordStep1Fragment,getActivity().getSupportFragmentManager(),R.id.Cycle_User_contener,null,null);
                break;
            case R.id.login:
                Intent homeActivity = new Intent(getActivity(),HomeActivity.class);
                startActivity(homeActivity);


                break;
            case R.id.cretae_new_user:
                RegesterFragment regesterFragment = new RegesterFragment();
                HelperMethod.replece(regesterFragment,getActivity().getSupportFragmentManager(),R.id.Cycle_User_contener,null,null);

                break;
        }
    }
}
