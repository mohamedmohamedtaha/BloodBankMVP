package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others;


import android.os.Bundle;
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

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.rest.general.contact.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.API_KEY;

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


       /*  apiServices.getContact().enqueue(new Callback<Contact>() {
             @Override
             public void onResponse(Call<Contact> call, Response<Contact> response) {

             }

             @Override
             public void onFailure(Call<Contact> call, Throwable t) {

             }
         });
*/
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
                break;
            case R.id.IM_Twitter:
                break;
            case R.id.IM_YouTube:
                break;
            case R.id.IM_Instgram:
                break;
            case R.id.IM_whatsApp:
                break;
            case R.id.IM_GooglePlus:
                break;
            case R.id.BT_Send_Message:
                final String name_pationt = FragmentConnectUsName.getText().toString();
                final String email = FragmentConnectUsEmail.getText().toString();
                final String phone = FragmentConnectUsPhone.getText().toString();
                final String title_message = FragmentConnectUsTitle.getText().toString();
                final String text_message = FragmentConnectUsTextMessage.getText().toString();
                apiServices = getRetrofit().create(APIServices.class);
                if (TextUtils.isEmpty(name_pationt) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)
                        || TextUtils.isEmpty(title_message) || TextUtils.isEmpty(text_message)){
                    Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_SHORT).show();}
                else {
                    apiServices.getContact("8KTqGqCh3ofQvl0DySaPNBw0TZODwgxlfZ0nHmWxigWlrKK3cpVLJQEb0bju",title_message,text_message,name_pationt,phone,email).enqueue(new Callback<Contact>() {
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
}
