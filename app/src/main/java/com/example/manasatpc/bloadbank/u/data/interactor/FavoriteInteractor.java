package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;

import com.example.manasatpc.bloadbank.u.data.model.posts.my_favourites.DataMyFavouritesTwo;
import com.example.manasatpc.bloadbank.u.data.model.posts.my_favourites.MyFavourites;
import com.example.manasatpc.bloadbank.u.data.presenter.FavoritePresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.FavoriteView;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class FavoriteInteractor implements FavoritePresenter {
    private FavoriteView favoriteView;
    private APIServices apiServices;
    private RememberMy rememberMy;


    public FavoriteInteractor(FavoriteView favoriteView) {
        this.favoriteView = favoriteView;
    }

    @Override
    public void loadFavorite(final Context context) {
        rememberMy = new RememberMy(context);
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getMyFavourites(rememberMy.getAPIKey())
                .enqueue(new Callback<MyFavourites>() {
                    @Override
                    public void onResponse(Call<MyFavourites> call, Response<MyFavourites> response) {
                        try {
                            ArrayList<DataMyFavouritesTwo> myFavourites = response.body().getData().getData();
                            if (response.body().getStatus() == 1) {
                                if (!myFavourites.isEmpty()) {
                                    favoriteView.loadSuccess(myFavourites);
                                    favoriteView.hideProgress();
                                } else {
                                    favoriteView.hideProgress();
                                    favoriteView.empty();
                                }
                            } else {
                                favoriteView.hideProgress();
                                favoriteView.showError(response.body().getMsg());
                            }
                        } catch (Exception e) {
                            favoriteView.hideProgress();
                            favoriteView.showError(e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(Call<MyFavourites> call, Throwable t) {
                        favoriteView.hideProgress();
                        favoriteView.showError(t.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {
        favoriteView = null;

    }
}
