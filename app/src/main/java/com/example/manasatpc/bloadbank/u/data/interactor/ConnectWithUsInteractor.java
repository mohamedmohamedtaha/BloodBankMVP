package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.general.contact.Contact;
import com.example.manasatpc.bloadbank.u.data.model.general.settings.Settings;
import com.example.manasatpc.bloadbank.u.data.presenter.ConnectWithUsPresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.ConnectWithUsView;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class ConnectWithUsInteractor implements ConnectWithUsPresenter {
    private ConnectWithUsView connectWithUsView;
    private ConnectWithUsPresenter presenter;
    private APIServices apiServices;
    private RememberMy rememberMy;
    private Settings settings;

    public ConnectWithUsInteractor(ConnectWithUsView connectWithUsView, Context context) {
        this.connectWithUsView = connectWithUsView;
        rememberMy = new RememberMy(context);
    }

       @Override
    public void onDestroy() {
        connectWithUsView = null;
    }

    @Override
    public void getContact(String title_message, String text_message) {
        apiServices = getRetrofit().create(APIServices.class);
        if (TextUtils.isEmpty(title_message) || TextUtils.isEmpty(text_message)) {
            connectWithUsView.isEmpty();
        } else {
            connectWithUsView.showProgress();
            apiServices.getContact(rememberMy.getAPIKey(), title_message, text_message).enqueue(new Callback<Contact>() {
                @Override
                public void onResponse(Call<Contact> call, Response<Contact> response) {
                    Contact contact = response.body();
                    try {
                        if (contact.getStatus() == 1) {
                            connectWithUsView.showMessage(contact.getMsg());
                            connectWithUsView.send();
                            connectWithUsView.hideProgress();
                        } else {
                            connectWithUsView.showMessage(contact.getMsg());
                            connectWithUsView.hideProgress();
                        }
                    } catch (Exception e) {
                        connectWithUsView.showMessage(e.getMessage());
                        connectWithUsView.hideProgress();
                    }
                }

                @Override
                public void onFailure(Call<Contact> call, Throwable t) {
                    connectWithUsView.showMessage(t.getMessage());
                    connectWithUsView.hideProgress();
                }
            });
        }
    }

    @Override
    public void openWebSite(Context context, String platform) {
        HelperMethod.openWebSite(context, platform);

    }

    @Override
    public void getSettings(String apiKey) {
        connectWithUsView.showProgress();
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getSettings(apiKey).enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                try {
                    settings = response.body();
                    if (settings.getStatus() == 1) {
                        connectWithUsView.retrieveData(settings);
                        connectWithUsView.hideProgress();
                    } else {
                        connectWithUsView.showMessage(settings.getMsg());
                        connectWithUsView.hideProgress();
                    }
                }catch (Exception e){
                    connectWithUsView.showMessage(e.getMessage());
                    connectWithUsView.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                connectWithUsView.showMessage(t.getMessage());
                connectWithUsView.hideProgress();
            }
        });
    }
}
