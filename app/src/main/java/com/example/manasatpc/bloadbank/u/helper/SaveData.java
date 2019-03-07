package com.example.manasatpc.bloadbank.u.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class SaveData implements Parcelable {
    private String api_token;
    private String name;
    private String phone;
    private String email;
    private String birthDate;
    private String cityId;
    private String donationLastDate;
    private String bloodType;

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDonationLastDate() {
        return donationLastDate;
    }

    public void setDonationLastDate(String donationLastDate) {
        this.donationLastDate = donationLastDate;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

      protected SaveData(Parcel in) {
        api_token = in.readString();
        name = in.readString();
        phone = in.readString();
        email = in.readString();
        birthDate= in.readString();
        cityId= in.readString();
        donationLastDate= in.readString();
        bloodType= in.readString();

      }

    public static final Creator<SaveData> CREATOR = new Creator<SaveData>() {
        @Override
        public SaveData createFromParcel(Parcel in) {
            return new SaveData(in);
        }

        @Override
        public SaveData[] newArray(int size) {
            return new SaveData[size];
        }
    };

    public SaveData(String apiToken, String name, String phone, String email,String birthDate,
                    String cityId, String donationLastDate,String bloodType) {
        this.api_token =apiToken;
        this.name = name;
        this.phone=phone;
        this.email=email;
        this.birthDate = birthDate;
        this.cityId = cityId;
        this.donationLastDate = donationLastDate;
        this.bloodType = bloodType;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(api_token);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(email);
        parcel.writeString(birthDate);
        parcel.writeString(cityId);
        parcel.writeString(donationLastDate);
        parcel.writeString(bloodType);



    }
}
