package com.example.manasatpc.bloadbank.u.data.presenter;

public interface ArticlePresenter {
    void onDestroy();
    void loadData(int page);
    void search();
}
