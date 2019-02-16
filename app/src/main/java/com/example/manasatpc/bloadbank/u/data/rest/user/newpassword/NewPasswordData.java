
package com.example.manasatpc.bloadbank.u.data.rest.user.newpassword;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewPasswordData {

    @SerializedName("phone")
    @Expose
    private List<String> phone = null;

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

}
