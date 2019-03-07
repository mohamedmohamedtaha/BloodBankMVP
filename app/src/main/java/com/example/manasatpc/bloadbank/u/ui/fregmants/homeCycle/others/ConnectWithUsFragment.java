package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.rest.general.contact.Contact;
import com.example.manasatpc.bloadbank.u.data.rest.general.settings.Settings;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;
import com.example.manasatpc.bloadbank.u.helper.SaveData;
import com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.API_KEY;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectWithUsFragment extends Fragment {


    @BindView(R.id.IM_Face_Book)
    ImageView IMFaceBook;
    @BindView(R.id.IM_Twitter)
    ImageView IMTwitter;
    @BindView(R.id.IM_YouTube)
    ImageView IMYouTube;
    @BindView(R.id.IM_Instgram)
    ImageView IMInstgram;
    @BindView(R.id.IM_whatsApp)
    ImageView IMWhatsApp;
    @BindView(R.id.IM_GooglePlus)
    ImageView IMGooglePlus;
    @BindView(R.id.Fragment_Connect_Us_Name)
    TextView FragmentConnectUsName;
    @BindView(R.id.Fragment_Connect_Us_Email)
    TextView FragmentConnectUsEmail;
    @BindView(R.id.Fragment_Connect_Us_Phone)
    TextView FragmentConnectUsPhone;
    @BindView(R.id.Fragment_Connect_Us_title)
    TextInputEditText FragmentConnectUsTitle;
    @BindView(R.id.Fragment_Connect_Us_Text_Message)
    TextInputEditText FragmentConnectUsTextMessage;
    @BindView(R.id.BT_Send_Message)
    Button BTSendMessage;
    Unbinder unbinder;
    private APIServices apiServices;
    Bundle bundle;
    private RememberMy rememberMy;
    private String getAPI_key;
    private SaveData saveData;
    private String urlYouTube, urlWhatsApp,urlTwitter, urlInstgram,urlGooglePlus,urlFacebook;

    public ConnectWithUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connect_with_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        bundle = getArguments();

        saveData = getArguments().getParcelable(GET_DATA);
        String email = saveData.getEmail();
        String name= saveData.getName();
        String phone= saveData.getPhone();
        getAPI_key=bundle.getString(API_KEY);
        FragmentConnectUsPhone.append(phone);
        FragmentConnectUsEmail.append(email);
        FragmentConnectUsName.append(name);


        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getSettings(saveData.getApi_token()).enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                Settings settings = response.body();
                if (settings.getStatus() == 1){
                    urlGooglePlus = settings.getData().getGoogleUrl();
                    urlInstgram = settings.getData().getInstagramUrl();
                    urlTwitter = settings.getData().getTwitterUrl();
                    urlWhatsApp = settings.getData().getWhatsapp();
                    urlYouTube = settings.getData().getYoutubeUrl();
                    urlFacebook = settings.getData().getFacebookUrl();

                }else {
                    Toast.makeText(getActivity(), settings.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.IM_Face_Book, R.id.IM_Twitter, R.id.IM_YouTube, R.id.IM_Instgram, R.id.IM_whatsApp, R.id.IM_GooglePlus, R.id.BT_Send_Message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.IM_Face_Book:
                HelperMethod.openWebSite(getActivity(), urlFacebook);
                break;
            case R.id.IM_Twitter:
                HelperMethod.openWebSite(getActivity(), urlTwitter);
                break;
            case R.id.IM_YouTube:
                HelperMethod.openWebSite(getActivity(), urlTwitter);
                break;
            case R.id.IM_Instgram:
                HelperMethod.openWebSite(getActivity(), urlInstgram);
                break;
            case R.id.IM_whatsApp:
                Uri uri = Uri.parse("smsto:" + urlWhatsApp);
                Intent openWhatsApp = new Intent(Intent.ACTION_SENDTO,uri);
                openWhatsApp.putExtra(Intent.EXTRA_TEXT, "this is my text");
                openWhatsApp.setPackage("com.whatsapp");
                startActivity(openWhatsApp);
                break;
            case R.id.IM_GooglePlus:
                HelperMethod.openWebSite(getActivity(), urlGooglePlus);
                break;
            case R.id.BT_Send_Message:
                final String title_message = FragmentConnectUsTitle.getText().toString();
                final String text_message = FragmentConnectUsTextMessage.getText().toString();
                apiServices = getRetrofit().create(APIServices.class);
                if ( TextUtils.isEmpty(title_message) || TextUtils.isEmpty(text_message)){
                    Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_SHORT).show();}
                else {
                    apiServices.getContact(getAPI_key,title_message,text_message).enqueue(new Callback<Contact>() {
                    @Override
                    public void onResponse(Call<Contact> call, Response<Contact> response) {
                        Contact contact = response.body();

                            if ( contact.getStatus() == 1 ){
                                Toast.makeText(getActivity(), contact.getMsg(), Toast.LENGTH_SHORT).show();
                                FragmentConnectUsTitle.setText("");
                                FragmentConnectUsTextMessage.setText("");
                            }
                            else {
                                Toast.makeText(getActivity(), contact.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    @Override
                    public void onFailure(Call<Contact> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });}

                break;
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.connect_with_us);

    }
}
