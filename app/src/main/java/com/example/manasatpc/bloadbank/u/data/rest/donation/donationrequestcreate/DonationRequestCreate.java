
package com.example.manasatpc.bloadbank.u.data.rest.donation.donationrequestcreate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonationRequestCreate {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataDonationRequestCreate data;

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

    public DataDonationRequestCreate getData() {
        return data;
    }

    public void setData(DataDonationRequestCreate data) {
        this.data = data;
    }

}
