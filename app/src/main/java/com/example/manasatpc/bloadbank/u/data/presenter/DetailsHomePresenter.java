package com.example.manasatpc.bloadbank.u.data.presenter;

public interface DetailsHomePresenter {
    void onDestroy();
    void getPostDetails(String apiKey, int postId, int page);
}
