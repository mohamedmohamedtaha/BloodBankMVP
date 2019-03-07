
package com.example.manasatpc.bloadbank.u.data.rest.donation.donationrequests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonationRequests {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataDonationRequests data;

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

    public DataDonationRequests getData() {
        return data;
    }

    public void setData(DataDonationRequests data) {
        this.data = data;
    }

}
