package com.example.manasatpc.bloadbank.u.ui.fregmants.userCycle;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.interactor.CheckPasswordInteractor;
import com.example.manasatpc.bloadbank.u.data.presenter.CheckPasswordPresenter;
import com.example.manasatpc.bloadbank.u.data.view.CheckPasswordView;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordStep1Fragment extends Fragment implements CheckPasswordView {
    @BindView(R.id.ForgetPasswordStep1Fragment_Phone)
    TextInputEditText ForgetPasswordStep1FragmentPhone;
    @BindView(R.id.ForgetPasswordStep1Fragment_BT_Send)
    Button ForgetPasswordStep1FragmentBTSend;
    @BindView(R.id.ForgetPasswordStep1Fragment_Progress_Bar)
    ProgressBar ForgetPasswordStep1FragmentProgressBar;
    Unbinder unbinder;
    Bundle bundle;
    public static final String PHONE = "phone";
    private CheckPasswordPresenter presenter;
    String phone;

    public ForgetPasswordStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_step1, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new CheckPasswordPresenter(this, new CheckPasswordInteractor());
        bundle = new Bundle();
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.onDestory();
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ForgetPasswordStep1Fragment_BT_Send)
    public void onViewClicked() {
        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return;
        }
        phone = ForgetPasswordStep1FragmentPhone.getText().toString().trim();
        presenter.checkEmail(phone, getActivity());
    }


    @Override
    public void showProgress() {
        ForgetPasswordStep1FragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        ForgetPasswordStep1FragmentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void send() {
        bundle.putString(PHONE, phone);
        ForgetPasswordStep2Fragment forgetPasswordStep2Fragment = new ForgetPasswordStep2Fragment();
        HelperMethod.replece(forgetPasswordStep2Fragment, getActivity().getSupportFragmentManager(),
                R.id.Cycle_User_contener, (Toolbar) null, null, bundle);
    }

    @Override
    public void isEmpty() {
        ForgetPasswordStep1FragmentPhone.setError(getString(R.string.filed_request));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
