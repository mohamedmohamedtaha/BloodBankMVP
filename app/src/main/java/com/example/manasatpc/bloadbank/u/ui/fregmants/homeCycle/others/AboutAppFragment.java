package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.interactor.ConnectWithUsInteractor;
import com.example.manasatpc.bloadbank.u.data.model.general.settings.Settings;
import com.example.manasatpc.bloadbank.u.data.presenter.ConnectWithUsPresenter;
import com.example.manasatpc.bloadbank.u.data.view.ConnectWithUsView;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;

public class AboutAppFragment extends Fragment implements ConnectWithUsView {
    @BindView(R.id.TV_Show_About_App)
    TextView TVShowAboutApp;
    @BindView(R.id.AboutAppFragment_Progress_Bar)
    ProgressBar AboutAppFragmentProgressBar;
    Unbinder unbinder;
    RememberMy rememberMy;
    private ConnectWithUsPresenter presenter;

    public AboutAppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        presenter = new ConnectWithUsInteractor(this, getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.about_app);
        presenter.getSettings(rememberMy.getAPIKey());
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showProgress() {
        AboutAppFragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        AboutAppFragmentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void send() {
    }

    @Override
    public void isEmpty() {
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void retrieveData(Settings settings) {
        TVShowAboutApp.setText(settings.getData().getAboutApp());
    }
}