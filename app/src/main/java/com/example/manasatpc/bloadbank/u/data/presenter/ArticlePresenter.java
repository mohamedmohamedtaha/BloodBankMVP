package com.example.manasatpc.bloadbank.u.data.presenter;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.widget.Spinner;

public interface ArticlePresenter {
    void onDestroy();
    void loadData(int page);
    void search(TextInputEditText HomeFragmentTietSearch);
    void getCategory(Spinner HomeFragmentSelectCategory, Context context);
}
