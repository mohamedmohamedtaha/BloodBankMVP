package com.example.manasatpc.bloadbank.u.ui.fregmants.userCycle;


import android.os.Bundle;
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
import com.example.manasatpc.bloadbank.u.data.interactor.RegisterInteractor;
import com.example.manasatpc.bloadbank.u.data.presenter.RegisterPresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.RegisterView;
import com.example.manasatpc.bloadbank.u.helper.DateModel;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegesterFragment extends Fragment implements RegisterView {
    @BindView(R.id.RegesterFragment_Name)
    TextInputEditText RegesterFragmentName;
    @BindView(R.id.RegesterFragment_Email)
    TextInputEditText RegesterFragmentEmail;
    @BindView(R.id.RegesterFragment__Date_Of_Birth)
    TextView RegesterFragmentDateOfBirth;
    @BindView(R.id.RegesterFragment_Blood_Type)
    Spinner RegesterFragmentBloodType;
    @BindView(R.id.RegesterFragment_SP_Gaverment)
    Spinner RegesterFragmentSPGaverment;
    @BindView(R.id.RegesterFragment_SP_City)
    Spinner RegesterFragmentSPCity;
    @BindView(R.id.RegesterFragment_Last_Date_Donation)
    TextView RegesterFragmentLastDateDonation;
    @BindView(R.id.RegesterFragment_Phone)
    TextInputEditText RegesterFragmentPhone;
    @BindView(R.id.RegesterFragment_Password)
    TextInputEditText RegesterFragmentPassword;
    @BindView(R.id.RegesterFragment_Retry_Password)
    TextInputEditText RegesterFragment_Retry_Password;
    @BindView(R.id.RegesterFragment_BT_Regester)
    Button RegesterFragmentBTRegester;
    @BindView(R.id.test)
    TextView test;
    @BindView(R.id.RegesterFragment_Progress_Bar)
    ProgressBar RegesterFragmentProgressBar;
    Unbinder unbinder;
    private DateModel dateModel1;
    private DateModel dateModel2;
    final Calendar getDatenow = Calendar.getInstance();
    private int startYear;
    private int startMonth;
    private int startDay;
    //for gaverment and cities
    String getResult;
    int IDPosition;
    Integer positionCity;
    Integer positionBloodType;
    int blood_type;
    int cityId;
    ArrayList<String> strings = new ArrayList<>();
    final ArrayList<Integer> Ids = new ArrayList<>();
    private ArrayList<Integer> IdsCity = new ArrayList<>();
    ArrayList<Integer> IdsBloodType = new ArrayList<>();
    APIServices apiServices;
    private RegisterPresenter presenter;

    public RegesterFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_regester, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new RegisterInteractor(this);
        presenter.getBloodTypes(getActivity(), RegesterFragmentBloodType);
        presenter.getGaverment(getActivity(), RegesterFragmentSPGaverment, RegesterFragmentSPCity);

        startYear = getDatenow.get(Calendar.YEAR);
        startMonth = getDatenow.get(Calendar.MONTH);
        startDay = getDatenow.get(Calendar.DAY_OF_MONTH);

        dateModel1 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth)
                , String.valueOf(startDay), null);
        dateModel2 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth)
                , String.valueOf(startDay), null);

        RegesterFragmentDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethod.showCalender(getActivity(), getString(R.string.date), RegesterFragmentDateOfBirth, dateModel1);
            }
        });
        RegesterFragmentLastDateDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethod.showCalender(getActivity(), getString(R.string.last_date), RegesterFragmentLastDateDonation, dateModel2);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.RegesterFragment_BT_Regester)
    public void onViewClicked() {
        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return;
        }
        String new_user = RegesterFragmentName.getText().toString().trim();
        String email = RegesterFragmentEmail.getText().toString().trim();
        String date_birth = RegesterFragmentDateOfBirth.getText().toString().trim();
        String last_date = RegesterFragmentLastDateDonation.getText().toString().trim();
        String phone = RegesterFragmentPhone.getText().toString().trim();
        String password = RegesterFragmentPassword.getText().toString().trim();
        String retry_password = RegesterFragment_Retry_Password.getText().toString().trim();
        presenter.register(new_user, email, date_birth, last_date, phone, password, retry_password,
                getActivity(), RegesterFragmentSPCity, RegesterFragmentBloodType);
    }

    @Override
    public void showProgress() {
        RegesterFragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        RegesterFragmentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void emptyFiled() {
        Toast.makeText(getActivity(), getString(R.string.all_filed_request), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        LoginFragment loginFragment = new LoginFragment();
        HelperMethod.replece(loginFragment, getActivity().getSupportFragmentManager(),
                R.id.Cycle_User_contener, null, null);
    }

    @Override
    public void cityEmpty() {
        Toast.makeText(getActivity(), getString(R.string.selct_blood_and_city), Toast.LENGTH_SHORT).show();
    }
}