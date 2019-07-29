package com.example.manasatpc.bloadbank.u.data.view;

import com.example.manasatpc.bloadbank.u.data.model.EditProfileModel;
import com.example.manasatpc.bloadbank.u.data.model.user.getprofile.ClientGetProfile;
import com.example.manasatpc.bloadbank.u.data.model.user.getprofile.GetProfile;

public interface EditProfileView {
    void showProgress();
    void hideProgress();
    void editSuccess();
    void emptyField();
    void emptyCity();
    void showError(String message);
    void retrieveSuccess(ClientGetProfile getProfile);
}
