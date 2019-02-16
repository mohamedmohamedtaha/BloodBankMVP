
package com.example.manasatpc.bloadbank.u.data.rest.general.contact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataContact dataContact;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataContact getDataContact() {
        return dataContact;
    }

    public void setDataContact(DataContact dataContact) {
        this.dataContact = dataContact;
    }

}
