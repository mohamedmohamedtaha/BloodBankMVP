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
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterMyFavorite;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.model.posts.my_favourites.DataMyFavouritesTwo;
import com.example.manasatpc.bloadbank.u.data.model.posts.my_favourites.MyFavourites;
import com.example.manasatpc.bloadbank.u.helper.SaveData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFavoriteFragment extends Fragment {
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.tv_empty_view)
    TextView tvEmptyView;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    Unbinder unbinder;
    private APIServices apiServices;
    AdapterMyFavorite adapterMyFavorite;
    Bundle bundle;
    private SaveData saveData;


    public MyFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);
        bundle = getArguments();

        saveData = getArguments().getParcelable(GET_DATA);

        list.setEmptyView(tvEmptyView);
        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getMyFavourites(saveData.getApi_token())
                .enqueue(new Callback<MyFavourites>() {
            @Override
            public void onResponse(Call<MyFavourites> call, Response<MyFavourites> response) {
                ArrayList<DataMyFavouritesTwo> myFavourites = response.body().getData().getData();
                if (response.body().getStatus() == 1){
                    adapterMyFavorite = new AdapterMyFavorite(getActivity(),myFavourites);
                    list.setAdapter(adapterMyFavorite);
                    loadingIndicator.setVisibility(View.GONE);

                }else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    loadingIndicator.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<MyFavourites> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                loadingIndicator.setVisibility(View.GONE);

            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });







        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.my_favorite);

    }
}
