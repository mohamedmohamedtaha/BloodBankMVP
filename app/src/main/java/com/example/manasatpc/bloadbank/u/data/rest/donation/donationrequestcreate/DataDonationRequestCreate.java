
package com.example.manasatpc.bloadbank.u.data.rest.donation.donationrequestcreate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataDonationRequestCreate {

    @SerializedName("donationRequest")
    @Expose
    private DonationRequest donationRequest;

    public DonationRequest getDonationRequest() {
        return donationRequest;
    }

    public void setDonationRequest(DonationRequest donationRequest) {
        this.donationRequest = donationRequest;
    }

}
