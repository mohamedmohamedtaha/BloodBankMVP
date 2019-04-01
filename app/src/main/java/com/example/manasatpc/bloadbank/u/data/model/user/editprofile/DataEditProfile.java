
package com.example.manasatpc.bloadbank.u.data.model.user.editprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataEditProfile {

    @SerializedName("client")
    @Expose
    private ClientEditProfile client;

    public ClientEditProfile getClient() {
        return client;
    }

    public void setClient(ClientEditProfile client) {
        this.client = client;
    }

}
