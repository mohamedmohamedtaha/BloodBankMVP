package com.example.manasatpc.bloadbank.u.helper;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;

public class MyFirebaseInstanceIDService  extends FirebaseInstanceIdService {
    RememberMy rememberMy;
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
//        rememberMy = new RememberMy(getApplicationContext());
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
  //      HelperMethod.getRemoveToken(getApplicationContext(),refreshedToken,rememberMy.getAPIKey());

    }
}
