
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
    private DataDonationRequestCretae dataDonationRequestCretae;

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

    public DataDonationRequestCretae getDataDonationRequestCretae() {
        return dataDonationRequestCretae;
    }

    public void setDataDonationRequestCretae(DataDonationRequestCretae dataDonationRequestCretae) {
        this.dataDonationRequestCretae = dataDonationRequestCretae;
    }

}
