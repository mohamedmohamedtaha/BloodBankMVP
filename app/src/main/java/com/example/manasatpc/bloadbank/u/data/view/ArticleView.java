package com.example.manasatpc.bloadbank.u.data.view;

import com.example.manasatpc.bloadbank.u.data.model.posts.posts.Data2Posts;

import java.util.ArrayList;

public interface ArticleView {
    void showProgress();
    void hideProgress();
    void loadSuccess(ArrayList<Data2Posts> postsArrayList);
    void empty();
    void showRecyclerView();
    void showRelativeView();
    void showError(String message);

}
