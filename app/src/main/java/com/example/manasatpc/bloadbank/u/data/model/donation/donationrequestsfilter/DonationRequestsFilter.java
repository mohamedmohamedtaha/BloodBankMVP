
package com.example.manasatpc.bloadbank.u.data.model.donation.donationrequestsfilter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonationRequestsFilter {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataDonationRequestsFilter data;

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

    public DataDonationRequestsFilter getData() {
        return data;
    }

    public void setData(DataDonationRequestsFilter data) {
        this.data = data;
    }

}
