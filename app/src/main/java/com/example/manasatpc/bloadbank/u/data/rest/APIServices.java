package com.example.manasatpc.bloadbank.u.data.rest;

import com.example.manasatpc.bloadbank.u.data.rest.login.Login;
import com.example.manasatpc.bloadbank.u.data.rest.newpassword.NewPassword;
import com.example.manasatpc.bloadbank.u.data.rest.profile.User;
import com.example.manasatpc.bloadbank.u.data.rest.register.Register;
import com.example.manasatpc.bloadbank.u.data.rest.resetpassword.ResetPassword;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIServices {

 //   ---------------------  for User  ---------------------------------------
    @POST("login")
    @FormUrlEncoded
    Call<Login> getLogin(@Field("phone") String phone,@Field("password") String password);

    @POST("register")
    @FormUrlEncoded
    Call<Register> getRegister(@Field("name") String name, @Field("email") String email,
                               @Field("birth_date")  String birth_date,@Field("city_id") int city_id,
                               @Field("phone") String number, @Field("donation_last_date") String donation_last_date,
                               @Field("password") String password,@Field("password_confirmation")String password_confirmation ,
                               @Field("blood_type") String blood_type);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassword> getResetPassword(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPassword> getNewPassword(@Field("password") String password, @Field("password_confirmation") String password_confirmation,
                                     @Field("pin_code")  String pin_code,@Field("phone")  String phone);

    @POST("profile")
    @FormUrlEncoded
    Call<ResetPassword> getProfile();

    //   ---------------------  End User  ---------------------------------------

    //   ---------------------  for posts ---------------------------------------

}













