package com.example.manasatpc.bloadbank.u.data.rest;

import com.example.manasatpc.bloadbank.u.data.rest.donation.Donation;
import com.example.manasatpc.bloadbank.u.data.rest.donation.donationrequest.DonationRequest;
import com.example.manasatpc.bloadbank.u.data.rest.donation.donationrequestcreate.DonationRequestCreate;
import com.example.manasatpc.bloadbank.u.data.rest.general.cities.Cities;
import com.example.manasatpc.bloadbank.u.data.rest.general.contact.Contact;
import com.example.manasatpc.bloadbank.u.data.rest.general.governorates.Governorates;
import com.example.manasatpc.bloadbank.u.data.rest.posts.Posts;
import com.example.manasatpc.bloadbank.u.data.rest.posts.my_favourites.MyFavourites;
import com.example.manasatpc.bloadbank.u.data.rest.posts.post.Post;
import com.example.manasatpc.bloadbank.u.data.rest.user.login.Login;
import com.example.manasatpc.bloadbank.u.data.rest.user.newpassword.NewPassword;
import com.example.manasatpc.bloadbank.u.data.rest.user.profile.Profile;
import com.example.manasatpc.bloadbank.u.data.rest.user.register.Register;
import com.example.manasatpc.bloadbank.u.data.rest.user.resetpassword.ResetPassword;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIServices {

    //   ---------------------  for User  ---------------------------------------
    @POST("login")
    @FormUrlEncoded
    Call<Login> getLogin(@Field("phone") String phone, @Field("password") String password);

    @POST("register")
    @FormUrlEncoded
    Call<Register> getRegister(@Field("name") String name, @Field("email") String email,
                               @Field("birth_date") String birth_date, @Field("city_id") int city_id,
                               @Field("phone") String number, @Field("donation_last_date") String donation_last_date,
                               @Field("password") String password, @Field("password_confirmation") String password_confirmation,
                               @Field("blood_type") String blood_type);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassword> getResetPassword(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPassword> getNewPassword(@Field("password") String password, @Field("password_confirmation") String password_confirmation,
                                     @Field("pin_code") String pin_code, @Field("phone") String phone);

    @POST("profile")
    @FormUrlEncoded
    Call<Profile> getProfile(@Field("name") String name,
                             @Field("email") String email, @Field("birth_date") String birth_date, @Field("city_id") String city_id,
                             @Field("phone") String phone, @Field("donation_last_date") String donation_last_date, @Field("password") String password,
                             @Field("password_confirmation") String password_confirmation, @Field("blood_type") String blood_type, @Field("api_token") String api_token);


    //   ---------------------  End User  ---------------------------------------

    //   ---------------------  for posts ---------------------------------------
    @GET("posts")
    Call<Posts> getPosts(@Query("api_token") String api_token, @Query("page") int page);
    //   ---------------------  for post ---------------------------------------

    @GET("post")
    Call<Post> getPost(@Query("api_token") String api_token, @Query("post_id") int post_id);

    //   ---------------------  for Governorates ---------------------------------------

    @GET("governorates")
    Call<Governorates> getGovernorates();

    @GET("cities")
    Call<Cities> getCities(@Query("governorate_id") int governorate_id);


    @POST("contact")
    @FormUrlEncoded
    Call<Contact> getContact(@Field("api_token") String api_token, @Field("title") String title,
                             @Field("message") String message,@Field("patient_name") String patient_name
    ,@Field("phone") String phone ,@Field("email") String email);


    @GET("donation-requests")
    Call<Donation> getDonationRequests(@Query("api_token") String api_token);


    @GET("donation-request")
    Call<DonationRequest> getDonationRequest(@Query("api_token") String api_token, @Query("donation_id") int donation_id);

    @POST("donation-request/create")
    @FormUrlEncoded
    Call<DonationRequestCreate> getDonationRequestCreate(@Field("api_token") String api_token,
                                                         @Field("patient_name") String patient_name, @Field("patient_age") String patient_age, @Field("blood_type") String blood_type,
                                                         @Field("bags_num") String bags_num, @Field("hospital_name") String hospital_name, @Field("hospital_address") String hospital_address,
                                                         @Field("city_id") String city_id, @Field("phone") String phone, @Field("notes") String notes,
                                                         @Field("latitude") String latitude, @Field("longitude") String longitude);

    @GET("my-favourites")
    Call<MyFavourites> getMyFavourites(@Query("api_token") String api_token);

}