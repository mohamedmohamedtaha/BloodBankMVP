package com.example.manasatpc.bloadbank.u.data.rest;

import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DonationRequest;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequestcreate.DonationRequestCreate;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequests.DonationRequests;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequestsfilter.DonationRequestsFilter;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.BloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.Cities;
import com.example.manasatpc.bloadbank.u.data.model.general.contact.Contact;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.Governorates;
import com.example.manasatpc.bloadbank.u.data.model.general.settings.Settings;
import com.example.manasatpc.bloadbank.u.data.model.notification.getnotificationssettings.GetNotificationSsettings;
import com.example.manasatpc.bloadbank.u.data.model.notification.notificationscount.NotificationsCount;
import com.example.manasatpc.bloadbank.u.data.model.notification.notificationslist.NotificationsList;
import com.example.manasatpc.bloadbank.u.data.model.posts.Posts;
import com.example.manasatpc.bloadbank.u.data.model.posts.my_favourites.MyFavourites;
import com.example.manasatpc.bloadbank.u.data.model.posts.post.Post;
import com.example.manasatpc.bloadbank.u.data.model.posts.posttogglefavourite.PostToggleFavourite;
import com.example.manasatpc.bloadbank.u.data.model.user.editprofile.EditProfile;
import com.example.manasatpc.bloadbank.u.data.model.user.getprofile.GetProfile;
import com.example.manasatpc.bloadbank.u.data.model.user.login.Login;
import com.example.manasatpc.bloadbank.u.data.model.user.newpassword.NewPassword;
import com.example.manasatpc.bloadbank.u.data.model.user.register.Register;
import com.example.manasatpc.bloadbank.u.data.model.user.resetpassword.ResetPassword;

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
                               @Field("blood_type_id") int blood_type);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassword> getResetPassword(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPassword> getNewPassword(@Field("password") String password, @Field("password_confirmation") String password_confirmation,
                                     @Field("pin_code") String pin_code, @Field("phone") String phone);

    @POST("profile")
    @FormUrlEncoded
    Call<EditProfile> editProfile(@Field("name") String name,
                                  @Field("email") String email, @Field("birth_date") String birth_date, @Field("city_id") int city_id,
                                  @Field("phone") String phone, @Field("donation_last_date") String donation_last_date, @Field("password") String password,
                                  @Field("password_confirmation") String password_confirmation, @Field("blood_type_id") int blood_type_id, @Field("api_token") String api_token);

    @POST("profile")
    @FormUrlEncoded
    Call<GetProfile> getProfile(@Field("api_token") String api_token);
    //   ---------------------  End User  ---------------------------------------

    //   ---------------------  for posts ---------------------------------------
    @GET("posts")
    Call<Posts> getPosts(@Query("api_token") String api_token, @Query("page") int page);

    @GET("post")
    Call<Post> getPost(@Query("api_token") String api_token, @Query("post_id") int post_id);

    @GET("my-favourites")
    Call<MyFavourites> getMyFavourites(@Query("api_token") String api_token);

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<PostToggleFavourite> getPostToggleFavourite(@Field("api_token") String api_token, @Field("post_id") String post_id);
    //   ---------------------  End Posts ---------------------------------------


    //   ---------------------  for General ---------------------------------------
    @GET("governorates")
    Call<Governorates> getGovernorates();

    @GET("cities")
    Call<Cities> getCities(@Query("governorate_id") int governorate_id);

    @POST("contact")
    @FormUrlEncoded
    Call<Contact> getContact(@Field("api_token") String api_token, @Field("title") String title,
                             @Field("message") String message);

    @GET("blood-types")
    Call<BloodTypes> getBloodTypes();

    @GET("settings")
    Call<Settings> getSettings(@Query("api_token") String api_token);
    //   ---------------------  for Donation ---------------------------------------

    @GET("donation-requests")
    Call<DonationRequests> getDonationRequests(@Query("api_token") String api_token);

    @GET("donation-request")
    Call<DonationRequest> getDonationRequest(@Query("api_token") String api_token, @Query("donation_id") int donation_id);

    @POST("donation-request/create")
    @FormUrlEncoded
    Call<DonationRequestCreate> getDonationRequestCreate(@Field("api_token") String api_token,
                                                         @Field("patient_name") String patient_name, @Field("patient_age") String patient_age, @Field("blood_type") int blood_type,
                                                         @Field("bags_num") String bags_num, @Field("hospital_name") String hospital_name, @Field("hospital_address") String hospital_address,
                                                         @Field("city_id") int city_id, @Field("phone") String phone, @Field("notes") String notes,
                                                         @Field("latitude") String latitude, @Field("longitude") String longitude);

    @GET("donation-requests")
    Call<DonationRequestsFilter> getDonationRequestFilter(@Query("api_token") String api_token, @Query("blood_type") String blood_type, @Query("city_id") int city_id, @Query("page") int page);


    //   ---------------------  for Notification ---------------------------------------

    @FormUrlEncoded
    @POST("notifications-settings")
    Call<GetNotificationSsettings> getGetNotificationsSettings(@Field("api_token") String api_token);

    @FormUrlEncoded
    @POST("notifications-settings")
    Call<GetNotificationSsettings> getGetNotificationsSettings(@Field("api_token") String api_token,@Field("governorates[0]") String governoratesZero
    ,@Field("governorates[1]") String governoratesOne,@Field("blood_types[0]") String blood_typesZero,@Field("blood_types[1]") String blood_typesOne);

    @GET("notifications")
    Call<NotificationsList> getNotificationsList(@Query("api_token") String api_token);

    @GET("notifications-count")
    Call<NotificationsCount> getNotificationsCount(@Query("api_token") String api_token);

}
