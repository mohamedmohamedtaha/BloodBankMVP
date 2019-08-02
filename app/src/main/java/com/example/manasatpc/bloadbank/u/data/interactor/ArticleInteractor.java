package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.posts.categories.Categories;
import com.example.manasatpc.bloadbank.u.data.model.posts.categories.DataCategories;
import com.example.manasatpc.bloadbank.u.data.model.posts.posts.Data2Posts;
import com.example.manasatpc.bloadbank.u.data.model.posts.posts.Posts;
import com.example.manasatpc.bloadbank.u.data.presenter.ArticlePresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.ArticleView;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class ArticleInteractor implements ArticlePresenter {
    private ArticleView articleView;
    private APIServices apiServices;
    final ArrayList<Integer> IdsCategories = new ArrayList<>();
    ArrayList<Data2Posts> postsArrayList = new ArrayList<>();
    RememberMy rememberMy;
    int catdgory_id = 0;
    Context context;

    public ArticleInteractor(ArticleView articleView, Context context) {
        this.articleView = articleView;
        this.context = context;
        apiServices = getRetrofit().create(APIServices.class);
        rememberMy = new RememberMy(context);
    }

    @Override
    public void onDestroy() {
        articleView = null;
    }

    @Override
    public void loadData(int page) {
        if (articleView != null) {
            articleView.showProgress();
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
                                articleView.loadSuccess(postsArrayList);
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
    public void search(TextInputEditText HomeFragmentTietSearch) {
        String text = HomeFragmentTietSearch.getText().toString().trim();
        if (text.isEmpty()) {
            articleView.empty();
        } else {
            postsArrayList.clear();
            articleView.showProgress();
            final Call<Posts> postsFilterCall = apiServices.getPostFilter(rememberMy.getAPIKey(),
                    1, text, catdgory_id);
            postsFilterCall.enqueue(new Callback<Posts>() {
                @Override
                public void onResponse(Call<Posts> call, Response<Posts> response) {
                    Posts postsFilter = response.body();
                    try {
                        if (postsFilter.getStatus() == 1) {
                            postsArrayList.addAll(postsFilter.getData().getData());
                            if (!postsArrayList.isEmpty()) {
                                articleView.hideProgress();
                                articleView.showRecyclerView();
                                postsArrayList.addAll(postsFilter.getData().getData());
                                articleView.loadSuccess(postsArrayList);
                            } else {
                                articleView.showError(postsFilter.getMsg());
                                articleView.showRelativeView();
                                articleView.hideProgress();
                            }
                        } else {
                            articleView.showError(postsFilter.getMsg());
                            articleView.showRelativeView();
                            articleView.hideProgress();
                        }
                    } catch (Exception e) {
                        articleView.showError(e.getMessage());
                        articleView.showRelativeView();
                        articleView.hideProgress();
                    }
                }

                @Override
                public void onFailure(Call<Posts> call, Throwable t) {
                    articleView.showError(t.getMessage());
                    articleView.showRelativeView();
                    articleView.hideProgress();
                }
            });
        }
    }

    @Override
    public void getCategory(final Spinner HomeFragmentSelectCategory, final Context context) {
        final Call<Categories> categoriesCall = apiServices.getCategories();
        categoriesCall.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                String getResult;
                ArrayList<String> strings = new ArrayList<>();
                Categories bloodTypes = response.body();
                if (bloodTypes.getStatus() == 1) {
                    try {
                        Integer positionCategories;
                        strings.add(context.getString(R.string.categories));
                        IdsCategories.add(0);
                        List<DataCategories> dataCategoriesList = bloodTypes.getData();
                        for (int i = 0; i < dataCategoriesList.size(); i++) {
                            getResult = dataCategoriesList.get(i).getName();
                            strings.add(getResult);
                            positionCategories = dataCategoriesList.get(i).getId();
                            IdsCategories.add(positionCategories);
                        }
                        HelperMethod.showGovernorates(strings, context, HomeFragmentSelectCategory);
                        HomeFragmentSelectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    postsArrayList.clear();
                                    articleView.showProgress();
                                    catdgory_id = IdsCategories.get(position);
                                    final Call<Posts> postsFilterCall = apiServices.getPostFilter(rememberMy.getAPIKey(),
                                            1, null, catdgory_id);
                                    postsFilterCall.enqueue(new Callback<Posts>() {
                                        @Override
                                        public void onResponse(Call<Posts> call, Response<Posts> response) {
                                            Posts postsFilter = response.body();
                                            try {
                                                if (postsFilter.getStatus() == 1) {
                                                    postsArrayList.addAll(postsFilter.getData().getData());
                                                    if (!postsArrayList.isEmpty()) {
                                                        articleView.showRecyclerView();
                                                        articleView.hideProgress();
                                                        articleView.loadSuccess(postsArrayList);
                                                    } else {
                                                        articleView.hideProgress();
                                                        articleView.showRecyclerView();
                                                    }
                                                } else {
                                                    articleView.showError(postsFilter.getMsg());
                                                    articleView.hideProgress();
                                                    articleView.showRecyclerView();
                                                }

                                            } catch (Exception e) {
                                                articleView.showError(e.getMessage());
                                                articleView.hideProgress();
                                                articleView.showRecyclerView();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Posts> call, Throwable t) {
                                            articleView.showError(t.getMessage());
                                            articleView.hideProgress();
                                            articleView.showRecyclerView();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } catch (Exception e) {
                        articleView.showError(e.getMessage());
                        articleView.hideProgress();
                        articleView.showRecyclerView();
                    }
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                articleView.showError(t.getMessage());
                articleView.hideProgress();
                articleView.showRecyclerView();

            }
        });
    }
}