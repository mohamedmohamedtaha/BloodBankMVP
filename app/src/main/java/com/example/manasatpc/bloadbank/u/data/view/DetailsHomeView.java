package com.example.manasatpc.bloadbank.u.data.view;

import com.example.manasatpc.bloadbank.u.data.model.posts.postdetails.PostDetails;

public interface DetailsHomeView {
    void showProgress();
    void hideProgress();
    void isFavourite();
    void isNotFavourite();
    void toolbarEmpty();
    void showError(String message);
    void loadSuccess(PostDetails post);

}
