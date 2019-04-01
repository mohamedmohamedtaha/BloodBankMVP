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
import com.example.manasatpc.bloadbank.u.data.model.user.login.Login;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;
import com.example.manasatpc.bloadbank.u.helper.SaveData;
import com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;

public class LoginFragment extends Fragment {

    private static APIServices APIServices;

    @BindView(R.id.LoginFragment_Forget_Password)
    TextView LoginFragmentForgetPassword;
    @BindView(R.id.LoginFragment_BT_Login)
    Button LoginFragmentBTLogin;
    @BindView(R.id.LoginFragment_BT_Register)
    Button LoginFragmentBTRegister;
    Unbinder unbinder;

    @BindView(R.id.LoginFragment_CB_Remeber_My)
    CheckBox LoginFragmentCBRemeberMy;
    RememberMy remeberMy;
    String phone;
    String password;
    String getAPI_key;
    @BindView(R.id.LoginFragment_Phone)
    TextInputEditText LoginFragmentPhone;
    @BindView(R.id.LoginFragment_Password)
    TextInputEditText LoginFragmentPassword;
    @BindView(R.id.LoginFragment_Progress_Bar)
    ProgressBar LoginFragmentProgressBar;
    private SaveData saveData;

    public LoginFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);


        //for check if the user in login or not
        remeberMy = new RememberMy(getActivity());
        if (remeberMy.isRemember()) {
            HelperMethod.startActivity(getActivity(), HomeActivity.class, getAPI_key);
        }
        return view;
    }


    @Override
    public void onDestroyView() {
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
                ForgetPasswordStep1Fragment forgetPasswordStep1Fragment = new ForgetPasswordStep1Fragment();
                HelperMethod.replece(forgetPasswordStep1Fragment, getActivity().getSupportFragmentManager(),
                        R.id.Cycle_User_contener, null, null, saveData);
                break;
            case R.id.LoginFragment_BT_Login:
                phone = LoginFragmentPhone.getText().toString().trim();
                password = LoginFragmentPassword.getText().toString().trim();

                APIServices = getRetrofit().create(APIServices.class);
                if (phone.isEmpty()) {
                    LoginFragmentPhone.setError(getString(R.string.filed_request));
                    return;
                }
                if (password.isEmpty()) {
                    LoginFragmentPassword.setError(getString(R.string.filed_request));
                    return;
                } else {
                    LoginFragmentProgressBar.setVisibility(View.VISIBLE);
                    Call<Login> loginCall = APIServices.getLogin(phone, password);

                    loginCall.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            try {

                                Login login = response.body();


                                if (login.getStatus() == 1) {
                                    SaveData saveData = new SaveData(login.getData().getApiToken(),
                                            login.getData().getClient().getName(), login.getData().getClient().getPhone()
                                            , login.getData().getClient().getEmail()
                                            , login.getData().getClient().getBirthDate(), login.getData().getClient().getCityId(),
                                            login.getData().getClient().getDonationLastDate(), login.getData().getClient().getBloodTypeId());


                                    if (LoginFragmentCBRemeberMy.isChecked()) {
                                        remeberMy.saveDateUser(phone, password, getAPI_key);
                                    }
                                    // HelperMethod.startActivity(getActivity(), HomeActivity.class,getAPI_key);
                                    HelperMethod.startActivity(getActivity(), HomeActivity.class, saveData);


                                    Toast.makeText(getActivity(), login.getMsg(), Toast.LENGTH_LONG).show();
                                    LoginFragmentProgressBar.setVisibility(View.GONE);

                                } else {
                                    Toast.makeText(getActivity(), login.getMsg(), Toast.LENGTH_LONG).show();
                                    LoginFragmentProgressBar.setVisibility(View.GONE);

                                }

                            } catch (Exception e) {

                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            LoginFragmentProgressBar.setVisibility(View.GONE);
                        }
                    });
                }

                break;
            case R.id.LoginFragment_BT_Register:
                RegesterFragment regesterFragment = new RegesterFragment();
                HelperMethod.replece(regesterFragment, getActivity().getSupportFragmentManager(),
                        R.id.Cycle_User_contener, null, getString(R.string.create_new_user), saveData);
                break;
        }
    }

}
