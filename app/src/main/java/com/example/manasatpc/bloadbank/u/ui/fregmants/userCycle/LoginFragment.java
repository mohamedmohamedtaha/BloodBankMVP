package com.example.manasatpc.bloadbank.u.ui.fregmants.userCycle;

import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.manasatpc.bloadbank.u.helper.SaveData;
import com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.rest.user.login.Login;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

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
    @BindView(R.id.Fragment_Phone_Login)
    TextInputEditText FragmentPhoneLogin;
    @BindView(R.id.Fragment_Password_Login)
    TextInputEditText FragmentPasswordLogin;
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.cretae_new_user)
    Button cretaeNewUser;
    Unbinder unbinder;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.CB_Remeber_My)
    CheckBox CBRemeberMy;
    RememberMy remeberMy;
    String phone;
    String password;
    String getAPI_key;
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
            HelperMethod.startActivity(getActivity(), HomeActivity.class,getAPI_key);
        }
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.forget_password, R.id.login, R.id.cretae_new_user})
    public void onViewClicked(View view) {
        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return;
        }
        switch (view.getId()) {
            case R.id.forget_password:
                ForgetPasswordStep1Fragment forgetPasswordStep1Fragment = new ForgetPasswordStep1Fragment();
                HelperMethod.replece(forgetPasswordStep1Fragment, getActivity().getSupportFragmentManager(),
                        R.id.Cycle_User_contener, null, null, saveData);
                break;
            case R.id.login:
                phone = FragmentPhoneLogin.getText().toString().trim();
                password = FragmentPasswordLogin.getText().toString().trim();

                APIServices = getRetrofit().create(APIServices.class);
                if (phone.isEmpty()) {
                    FragmentPhoneLogin.setError(getString(R.string.filed_request));
                    return;
                }
                if (password.isEmpty()) {
                    FragmentPasswordLogin.setError(getString(R.string.filed_request));
                    return;
                } else {
                    loginProgress.setVisibility(View.VISIBLE);
                    Call<Login> loginCall = APIServices.getLogin(phone, password);

                    loginCall.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            try {

                                Login login = response.body();



                                if (login.getStatus() == 1) {
                                    SaveData saveData = new SaveData(login.getData().getApiToken(),
                                            login.getData().getClient().getName(),login.getData().getClient().getPhone()
                                            , login.getData().getClient().getEmail()
                                    ,login.getData().getClient().getBirthDate(),login.getData().getClient().getCityId(),
                                            login.getData().getClient().getDonationLastDate(),login.getData().getClient().getBloodTypeId());




                                    if (CBRemeberMy.isChecked()) {
                                        remeberMy.saveDateUser(phone, password,getAPI_key);
                                    }
                                   // HelperMethod.startActivity(getActivity(), HomeActivity.class,getAPI_key);
                                    HelperMethod.startActivity(getActivity(), HomeActivity.class,saveData);


                                    Toast.makeText(getActivity(), login.getMsg(), Toast.LENGTH_LONG).show();
                                    loginProgress.setVisibility(View.GONE);

                                    }

                                 else {
                                    Toast.makeText(getActivity(), login.getMsg(), Toast.LENGTH_LONG).show();
                                    loginProgress.setVisibility(View.GONE);

                                }

                            } catch (Exception e) {

                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            loginProgress.setVisibility(View.GONE);

                        }
                    });
                }

                break;
            case R.id.cretae_new_user:
                RegesterFragment regesterFragment = new RegesterFragment();
                HelperMethod.replece(regesterFragment, getActivity().getSupportFragmentManager(),
                        R.id.Cycle_User_contener, null, getString(R.string.create_new_user), saveData);
                break;
        }
    }

}
