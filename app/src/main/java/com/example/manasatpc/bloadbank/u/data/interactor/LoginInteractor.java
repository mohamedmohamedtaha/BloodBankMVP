package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.u.data.model.user.login.Login;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class LoginInteractor {
    private static APIServices APIServices;

    public interface OnLoginFinishedListener {
        void onErrorLogin();
        void onEmptyFields();
        void onSuccess();
        void hideProgress();
        void getToken();
        void navigateToRegister();
        void navigateToResetPassword();
        void showError(String message);
    }

    public void checkLogin(final String name, final String password, final OnLoginFinishedListener listener, final RememberMy rememberMy, final CheckBox checkBox) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            listener.onEmptyFields();
            return;
        }
        APIServices = getRetrofit().create(APIServices.class);
        Call<Login> loginCall = APIServices.getLogin(name, password);
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                try {
                    Login login = response.body();
                    if (login.getStatus() == 1) {
                        listener.onSuccess();
                        rememberMy.saveDateUserTwo(login.getData().getClient().getName(), login.getData().getClient().getPhone(),
                                login.getData().getClient().getEmail(), login.getData().getApiToken());
                        listener.getToken();
                        if (checkBox.isChecked()) {
                            rememberMy.saveDateUser(name, password, login.getData().getApiToken());
                        }
                    } else {
                        listener.showError(login.getMsg());
                    }
                } catch (Exception e) {
                    listener.showError(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                listener.showError(t.getMessage());
            }
        });
    }
}