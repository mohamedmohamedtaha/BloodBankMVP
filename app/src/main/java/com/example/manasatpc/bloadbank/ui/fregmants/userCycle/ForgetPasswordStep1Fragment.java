package com.example.manasatpc.bloadbank.ui.fregmants.userCycle;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.ui.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordStep1Fragment extends Fragment {


    @BindView(R.id.image_logo)
    ImageView imageLogo;
    @BindView(R.id.tv_forget_my_password)
    TextView tvForgetMyPassword;
    @BindView(R.id.Fragment_Phone_Forget_Step1)
    TextInputLayout FragmentPhoneForgetStep1;
    @BindView(R.id.bt_Send_Forget_Step1)
    Button btSendForgetStep1;
    Unbinder unbinder;

    public ForgetPasswordStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_step1, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_Send_Forget_Step1)
    public void onViewClicked() {
        ForgetPasswordStep2Fragment forgetPasswordStep2Fragment = new ForgetPasswordStep2Fragment();
        HelperMethod.replece(forgetPasswordStep2Fragment,getActivity().getSupportFragmentManager(),R.id.Cycle_User_contener,null,null);

    }
}
