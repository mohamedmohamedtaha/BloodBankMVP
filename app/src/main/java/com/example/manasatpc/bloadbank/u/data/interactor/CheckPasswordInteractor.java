package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.u.data.model.user.resetpassword.ResetPassword;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class CheckPasswordInteractor {
    public interface OnChangedListener {
        void showProgress();

        void hideProgress();

        void send();

        void setEmpty();
    }

    public void checkPhone(String phone, final OnChangedListener listener, final Context context) {
        if (!phone.isEmpty()) {
            APIServices apiServices = getRetrofit().create(APIServices.class);
            Call<ResetPassword> resetPassword = apiServices.getResetPassword(phone);
            resetPassword.enqueue(new Callback<ResetPassword>() {
                @Override
                public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                    ResetPassword resetPassword1 = response.body();
                    if (resetPassword1.getStatus() == 1) {
                        listener.send();
                    } else {
                        Toast.makeText(context, resetPassword1.getMsg(), Toast.LENGTH_LONG).show();
                        listener.hideProgress();
                    }
                }

                @Override
                public void onFailure(Call<ResetPassword> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    listener.hideProgress();
                }
            });
        } else {
            listener.setEmpty();
        }
    }
}
