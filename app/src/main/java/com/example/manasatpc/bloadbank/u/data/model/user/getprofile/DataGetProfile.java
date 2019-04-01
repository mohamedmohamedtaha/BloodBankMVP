
package com.example.manasatpc.bloadbank.u.data.model.user.getprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataGetProfile {

    @SerializedName("client")
    @Expose
    private ClientGetProfile client;

    public ClientGetProfile getClient() {
        return client;
    }

    public void setClient(ClientGetProfile client) {
        this.client = client;
    }

}
