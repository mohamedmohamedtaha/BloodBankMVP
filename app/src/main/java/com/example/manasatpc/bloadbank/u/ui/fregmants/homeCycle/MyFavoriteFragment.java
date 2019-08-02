package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterMyFavorite;
import com.example.manasatpc.bloadbank.u.data.interactor.FavoriteInteractor;
import com.example.manasatpc.bloadbank.u.data.model.posts.my_favourites.DataMyFavouritesTwo;
import com.example.manasatpc.bloadbank.u.data.presenter.FavoritePresenter;
import com.example.manasatpc.bloadbank.u.data.view.FavoriteView;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFavoriteFragment extends Fragment implements FavoriteView {
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    Unbinder unbinder;
    AdapterMyFavorite adapterMyFavorite;
    @BindView(R.id.MyFavoriteFragment_RL_Empty_View)
    RelativeLayout MyFavoriteFragmentRLEmptyView;
    private FavoritePresenter presenter;

    public MyFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new FavoriteInteractor(this, getActivity());

        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
        } else {
            presenter.loadFavorite();
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                }
            });
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.my_favorite);
    }

    @Override
    public void showProgress() {
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void loadSuccess(ArrayList<DataMyFavouritesTwo> myFavourites) {
        adapterMyFavorite = new AdapterMyFavorite(getActivity(), myFavourites);
        list.setAdapter(adapterMyFavorite);
    }

    @Override
    public void empty() {
        MyFavoriteFragmentRLEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}