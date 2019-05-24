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
import com.example.manasatpc.bloadbank.u.data.model.user.resetpassword.ResetPassword;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;

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
public class ForgetPasswordStep1Fragment extends Fragment {
    @BindView(R.id.ForgetPasswordStep1Fragment_Phone)
    TextInputEditText ForgetPasswordStep1FragmentPhone;
    @BindView(R.id.ForgetPasswordStep1Fragment_BT_Send)
    Button ForgetPasswordStep1FragmentBTSend;
    @BindView(R.id.ForgetPasswordStep1Fragment_Progress_Bar)
    ProgressBar ForgetPasswordStep1FragmentProgressBar;
    Unbinder unbinder;
    Bundle bundle;
    public static final String PHONE = "phone";

    public ForgetPasswordStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_step1, container, false);
        unbinder = ButterKnife.bind(this, view);
        bundle = new Bundle();
        return view;
    }

    @Override
    public void onDestroyView() {
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
        final String phone = ForgetPasswordStep1FragmentPhone.getText().toString().trim();
        if (!phone.isEmpty()) {
            ForgetPasswordStep1FragmentProgressBar.setVisibility(View.VISIBLE);
            APIServices apiServices = getRetrofit().create(APIServices.class);
            Call<ResetPassword> resetPassword = apiServices.getResetPassword(phone);
            resetPassword.enqueue(new Callback<ResetPassword>() {
                @Override
                public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                    ResetPassword resetPassword1 = response.body();
                    bundle.putString(PHONE, phone);
                    if (resetPassword1.getStatus() == 1) {
                        ForgetPasswordStep2Fragment forgetPasswordStep2Fragment = new ForgetPasswordStep2Fragment();
                        HelperMethod.replece(forgetPasswordStep2Fragment, getActivity().getSupportFragmentManager(),
                                R.id.Cycle_User_contener, (Toolbar) null, null, bundle);

                        Toast.makeText(getActivity(), resetPassword1.getMsg(), Toast.LENGTH_LONG).show();
                        ForgetPasswordStep1FragmentProgressBar.setVisibility(View.GONE);
                    } else {
                        ForgetPasswordStep1FragmentProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), resetPassword1.getMsg(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResetPassword> call, Throwable t) {
                    ForgetPasswordStep1FragmentProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            ForgetPasswordStep1FragmentPhone.setError(getString(R.string.filed_request));
        }
    }
}
