package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.interactor.EditProfileInteractor;
import com.example.manasatpc.bloadbank.u.data.model.EditProfileModel;
import com.example.manasatpc.bloadbank.u.data.model.user.getprofile.ClientGetProfile;
import com.example.manasatpc.bloadbank.u.data.presenter.EditProfilePresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.EditProfileView;
import com.example.manasatpc.bloadbank.u.helper.DateModel;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.article.HomeFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditInformationFragment extends Fragment implements EditProfileView {
    @BindView(R.id.EditInformationFragment_Name)
    TextInputEditText EditInformationFragmentName;
    @BindView(R.id.EditInformationFragment_Email)
    TextInputEditText EditInformationFragmentEmail;
    @BindView(R.id.EditInformationFragment_Date_Of_Birth)
    TextView EditInformationFragmentDateOfBirth;
    @BindView(R.id.EditInformationFragment_SP_Blood_Type)
    Spinner EditInformationFragmentSPBloodType;
    @BindView(R.id.EditInformationFragment_Last_Date_Donation)
    TextView EditInformationFragmentLastDateDonation;
    @BindView(R.id.EditInformationFragment_SP_Gaverment)
    Spinner EditInformationFragmentSPGaverment;
    @BindView(R.id.EditInformationFragment_SP_City)
    Spinner EditInformationFragmentSPCity;
    @BindView(R.id.EditInformationFragment_Phone)
    TextInputEditText EditInformationFragmentPhone;
    @BindView(R.id.EditInformationFragment_Password)
    TextInputEditText EditInformationFragmentPassword;
    @BindView(R.id.EditInformationFragment_Retry_Password)
    TextInputEditText EditInformationFragmentRetryPassword;
    @BindView(R.id.EditInformationFragment_BT_Regester)
    Button EditInformationFragmentBTRegester;
    @BindView(R.id.EditInformationFragment_Progress_Bar)
    ProgressBar EditInformationFragmentProgressBar;
    Unbinder unbinder;
    boolean check_network;
    private DateModel dateModel1;
    private DateModel dateModel2;
    RememberMy rememberMy;
    private EditProfileInteractor editProfilePresenter;

    public EditInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_information, container, false);
        unbinder = ButterKnife.bind(this, view);
        editProfilePresenter = new EditProfileInteractor(this, getActivity());
        rememberMy = new RememberMy(getActivity());

        check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        DecimalFormat format = new DecimalFormat("00");
        Calendar calendar = Calendar.getInstance();
        String startDay = format.format(Double.valueOf(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))));
        String startMonth = format.format(Double.valueOf(String.valueOf(calendar.get(Calendar.MONTH + 1))));
        String startYear =String.valueOf(calendar.get(Calendar.YEAR));

        dateModel1 = new DateModel(startDay, startMonth     , startYear, null);
        dateModel2 = new DateModel(startDay, startMonth ,startYear,  startDay + "-" + startMonth +" -" + startYear);
        if (check_network == false) {
            EditInformationFragmentProgressBar.setVisibility(View.GONE);
        }
        editProfilePresenter.getProfile(rememberMy.getAPIKey());

        EditInformationFragmentDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethod.showCalender(getActivity(), getString(R.string.date), EditInformationFragmentDateOfBirth, dateModel1);
            }
        });

        EditInformationFragmentLastDateDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethod.showCalender(getActivity(), getString(R.string.last_date), EditInformationFragmentLastDateDonation, dateModel2);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        editProfilePresenter.onDestroy();
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.EditInformationFragment_BT_Regester)
    public void onViewClicked() {
        // for check network
        if (check_network == false) {
            return;
        }
        String new_user = EditInformationFragmentName.getText().toString().trim();
        String email = EditInformationFragmentEmail.getText().toString().trim();
        String date_birth = EditInformationFragmentDateOfBirth.getText().toString().trim();
        String last_date = EditInformationFragmentLastDateDonation.getText().toString().trim();
        String phone = EditInformationFragmentPhone.getText().toString().trim();
        String password = EditInformationFragmentPassword.getText().toString().trim();
        String retry_password = EditInformationFragmentRetryPassword.getText().toString().trim();
        editProfilePresenter.editProfile(new_user, email, date_birth, last_date, phone, password, retry_password,
                EditInformationFragmentSPBloodType, EditInformationFragmentSPCity);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.edit_information);
    }

    @Override
    public void showProgress() {
        EditInformationFragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        EditInformationFragmentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void editSuccess() {
        HomeFragment homeFragment = new HomeFragment();
        HelperMethod.replece(homeFragment, getActivity().getSupportFragmentManager(),
                R.id.Cycle_Home_contener, toolbar, getString(R.string.home));
    }

    @Override
    public void emptyField() {
        Toast.makeText(getActivity(), getString(R.string.all_filed_request), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emptyCity() {
        Toast.makeText(getActivity(), getString(R.string.selct_blood_and_city), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void retrieveSuccess(ClientGetProfile getProfile) {
        editProfilePresenter.getGaverment(getActivity(), EditInformationFragmentSPGaverment, EditInformationFragmentSPCity);
        editProfilePresenter.getBloodTypes(getActivity(), EditInformationFragmentSPBloodType);
        EditInformationFragmentDateOfBirth.setText(getProfile.getBirthDate());
        EditInformationFragmentEmail.setText(getProfile.getEmail());
        EditInformationFragmentLastDateDonation.setText(getProfile.getDonationLastDate());
        EditInformationFragmentName.setText(getProfile.getName());
        EditInformationFragmentPhone.setText(getProfile.getPhone());
    }


}