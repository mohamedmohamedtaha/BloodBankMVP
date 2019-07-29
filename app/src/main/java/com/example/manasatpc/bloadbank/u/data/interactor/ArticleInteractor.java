package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.u.data.model.posts.posts.Data2Posts;
import com.example.manasatpc.bloadbank.u.data.model.posts.posts.Posts;
import com.example.manasatpc.bloadbank.u.data.presenter.ArticlePresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.ArticleView;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class ArticleInteractor implements ArticlePresenter {
    private ArticleView articleView;
    private APIServices apiServices;
    RememberMy rememberMy;
    Context context;

    public ArticleInteractor(ArticleView articleView, Context context) {
        this.articleView = articleView;
        this.context = context;
        rememberMy = new RememberMy(context);
    }

    @Override
    public void onDestroy() {
        articleView = null;
    }

    @Override
    public void loadData(int page) {
        final ArrayList<Data2Posts> postsArrayList = new ArrayList<>();
        if (articleView != null) {
            articleView.showProgress();
            apiServices = getRetrofit().create(APIServices.class);
            apiServices.getPosts(rememberMy.getAPIKey(),
                    page).enqueue(new Callback<Posts>() {
                @Override
                public void onResponse(Call<Posts> call, Response<Posts> response) {
                    try {
                        Posts posts = response.body();
                        if (posts.getStatus() == 1) {
                            postsArrayList.addAll(posts.getData().getData());
                            if (!postsArrayList.isEmpty()) {
                                articleView.loadSuccess(postsArrayList);
                                articleView.hideProgress();
                                articleView.showRecyclerView();
                            } else {
                                articleView.hideProgress();
                                articleView.showRelativeView();
                            }
                        } else {
                            articleView.hideProgress();
                            articleView.showRelativeView();
                            articleView.showError(posts.getMsg());
                        }
                    } catch (Exception e) {
                        articleView.hideProgress();
                        articleView.showRelativeView();
                        articleView.showError(e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Posts> call, Throwable t) {
                    articleView.hideProgress();
                    articleView.showRelativeView();
                    articleView.showError(t.getMessage());
                }
            });
        }


    }

    @Override
    public void search() {

    }
}
