
package com.example.manasatpc.bloadbank.u.data.rest.donation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donation {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataDonationTwo data;

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

    public DataDonationTwo getData() {
        return data;
    }

    public void setData(DataDonationTwo dataDonationTwo) {
        this.data = dataDonationTwo;
    }

}
