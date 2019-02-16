package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.rest.user.profile.Profile;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.HomeFragment;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditInformationFragment extends Fragment {


    @BindView(R.id.Fragment_Name_Regester)
    TextInputEditText FragmentNameRegester;
    @BindView(R.id.Fragment_Email_Regester)
    TextInputEditText FragmentEmailRegester;
    @BindView(R.id.Fragment_Date_Regester)
    TextView FragmentDateRegester;
    @BindView(R.id.Fragment_Blood_Regester)
    TextInputEditText FragmentBloodRegester;
    @BindView(R.id.Fragment_Last_Date_Regester)
    TextView FragmentLastDateRegester;
    @BindView(R.id.Spinner_Gaverment)
    MaterialBetterSpinner SpinnerGaverment;
    @BindView(R.id.Spinner_City)
    MaterialBetterSpinner SpinnerCity;
    @BindView(R.id.Fragment_Phone_Regester)
    TextInputEditText FragmentPhoneRegester;
    @BindView(R.id.Fragment_Password_Regester)
    TextInputEditText FragmentPasswordRegester;
    @BindView(R.id.Fragment_Retry_Password_Regester)
    TextInputEditText FragmentRetryPasswordRegester;
    @BindView(R.id.regester_bt)
    Button regesterBt;
    Unbinder unbinder;
    private APIServices apiServices;

    public EditInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_information, container, false);
        unbinder = ButterKnife.bind(this, view);

        apiServices = getRetrofit().create(APIServices.class);
        /*apiServices.getProfile().enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Profile profile = response.body();
                if (profile.getStatus() == 1) {
                    FragmentNameRegester.setText(profile.getProfileData().getUser().getName());
                    FragmentBloodRegester.setText(profile.getProfileData().getUser().getBloodType());
                    FragmentEmailRegester.setText(profile.getProfileData().getUser().getEmail());
                    FragmentLastDateRegester.setText(profile.getProfileData().getUser().getDonationLastDate());
                    FragmentDateRegester.setText(profile.getProfileData().getUser().getBirthDate());
                    FragmentPhoneRegester.setText(profile.getProfileData().getUser().getPhone());

                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

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

    @OnClick(R.id.regester_bt)
    public void onViewClicked() {


        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return;
        }
        String new_user = FragmentNameRegester.getText().toString().trim();
        String email = FragmentEmailRegester.getText().toString().trim();
        String date_birth = FragmentDateRegester.getText().toString().trim();
        String blood_type = FragmentBloodRegester.getText().toString().trim();
        String last_date = FragmentLastDateRegester.getText().toString().trim();
        String phone = FragmentPhoneRegester.getText().toString().trim();
        String password = FragmentPasswordRegester.getText().toString().trim();
        String retry_password = FragmentRetryPasswordRegester.getText().toString().trim();


        Call<Profile> profileCall = apiServices.getProfile(new_user, email,
                date_birth, "1", phone,
                last_date, password, retry_password, blood_type,"");
        profileCall.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                try {
                    Profile profile = response.body();
                    if (profile.getStatus() == 0) {
                        Toast.makeText(getActivity(), profile.getMsg(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), profile.getMsg(), Toast.LENGTH_LONG).show();
                        HomeFragment homeFragment = new HomeFragment();
                        HelperMethod.replece(homeFragment, getActivity().getSupportFragmentManager(),
                                R.id.Cycle_Home_contener, null, null, null);


                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

            }
        });
    }
}