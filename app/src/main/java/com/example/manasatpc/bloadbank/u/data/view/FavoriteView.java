package com.example.manasatpc.bloadbank.u.data.view;

import com.example.manasatpc.bloadbank.u.data.model.posts.my_favourites.DataMyFavouritesTwo;

import java.util.ArrayList;

public interface FavoriteView {
    void showProgress();
    void hideProgress();
    void loadSuccess(ArrayList<DataMyFavouritesTwo> myFavourites);
    void empty();
    void showError(String message);

}
