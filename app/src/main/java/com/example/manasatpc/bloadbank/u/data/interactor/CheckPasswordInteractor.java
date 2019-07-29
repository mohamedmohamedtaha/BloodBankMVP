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
        void showError(String message);
    }

    public void checkPhone(String phone, final OnChangedListener listener, final Context context) {
        if (!phone.isEmpty()) {
            APIServices apiServices = getRetrofit().create(APIServices.class);
            Call<ResetPassword> resetPassword = apiServices.getResetPassword(phone);
            resetPassword.enqueue(new Callback<ResetPassword>() {
                @Override
                public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                   try {
                       ResetPassword resetPassword1 = response.body();
                       if (resetPassword1.getStatus() == 1) {
                           listener.send();
                       } else {
                           listener.showError(resetPassword1.getMsg());
                       }
                   }catch (Exception e){
                       listener.showError(e.getMessage());

                   }
                }

                @Override
                public void onFailure(Call<ResetPassword> call, Throwable t) {
                    listener.showError(t.getMessage());
                }
            });
        } else {
            listener.setEmpty();
        }
    }
}
