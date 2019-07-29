package com.example.manasatpc.bloadbank.u.data.interactor;

import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.posts.postdetails.PostDetails;
import com.example.manasatpc.bloadbank.u.data.presenter.DetailsHomePresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.DetailsHomeView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;

public class DetailsHomeInteractor implements DetailsHomePresenter {
    private DetailsHomeView detailsHomeView;
    private APIServices apiServices;

    public DetailsHomeInteractor(DetailsHomeView detailsHomeView) {
        this.detailsHomeView = detailsHomeView;
    }

    @Override
    public void onDestroy() {
        detailsHomeView = null;
    }

    @Override
    public void getPostDetails(String apiKey, int postId, int page) {
        detailsHomeView.showProgress();
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getPostDetails(apiKey, postId, page).enqueue(new Callback<PostDetails>() {
            @Override
            public void onResponse(Call<PostDetails> call, Response<PostDetails> response) {
                PostDetails post = response.body();
                try{
                    if (post.getStatus() == 1) {
                        detailsHomeView.hideProgress();
                        detailsHomeView.loadSuccess(post);
                        if (post.getData().getIsFavourite()) {
                            detailsHomeView.isFavourite();
                        } else {
                            detailsHomeView.isNotFavourite();
                        }
                    } else {
                        detailsHomeView.hideProgress();
                        detailsHomeView.showError(post.getMsg());
                        detailsHomeView.toolbarEmpty();
                    }
                }
               catch (Exception e){
                   detailsHomeView.hideProgress();
                   detailsHomeView.showError(e.getMessage());
               }
            }

            @Override
            public void onFailure(Call<PostDetails> call, Throwable t) {
                detailsHomeView.hideProgress();
                detailsHomeView.showError(t.getMessage());
            }
        });
    }
}
