package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;
import android.view.View;

import com.example.manasatpc.bloadbank.u.data.model.user.newpassword.NewPassword;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class ChangePasswordInteractor {
    private static APIServices APIServices;

    public interface OnChangedListener {
        void hideProgress();

        void showProgress();

        void onSuccess();

        void empty();

        void stopCounter();

        void isNetworkAvailable();

        void showError(String message);
    }

    public void changePassword(String newPassword, String retryNewPassword, String code, String savePhone, final OnChangedListener listener, final Context context, View view) {
        if (code.isEmpty() || newPassword.isEmpty() || retryNewPassword.isEmpty()) {
            listener.empty();
            return;
        }
        APIServices = getRetrofit().create(APIServices.class);
        Call<NewPassword> newPasswordCall = APIServices.getNewPassword(newPassword, retryNewPassword, code, savePhone);
        newPasswordCall.enqueue(new Callback<NewPassword>() {
            @Override
            public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                NewPassword newPassword1 = response.body();
                try {
                    if (newPassword1.getStatus() == 1) {
                        listener.stopCounter();
                        listener.onSuccess();
                        listener.hideProgress();
                    } else {
                        listener.showError(newPassword1.getMsg());
                    }
                } catch (Exception e) {
                    listener.showError(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<NewPassword> call, Throwable t) {
                listener.showError(t.getMessage());
            }
        });
    }
}
