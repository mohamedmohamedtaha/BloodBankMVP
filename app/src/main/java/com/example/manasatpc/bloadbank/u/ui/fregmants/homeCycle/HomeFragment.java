package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterArticle;
import com.example.manasatpc.bloadbank.u.data.model.posts.Data2Posts;
import com.example.manasatpc.bloadbank.u.data.model.posts.Posts;
import com.example.manasatpc.bloadbank.u.data.model.posts.posttogglefavourite.PostToggleFavourite;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.OnEndless;
import com.example.manasatpc.bloadbank.u.helper.SaveData;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets.RequestDonationFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
public class HomeFragment extends Fragment {
    @BindView(R.id.HomeFragment_Tiet_Search)
    TextInputEditText HomeFragmentTietSearch;
    @BindView(R.id.HomeFragment_Recycle_View)
    RecyclerView HomeFragmentRecycleView;
    @BindView(R.id.HomeFragment_Empty_Image)
    ImageView HomeFragmentEmptyImage;
    @BindView(R.id.HomeFragment_TV_Empty_View)
    TextView HomeFragmentTVEmptyView;
    @BindView(R.id.HomeFragment_RL_Empty_View)
    RelativeLayout HomeFragmentRLEmptyView;
    @BindView(R.id.HomeFragment_Loading_Indicator)
    ProgressBar HomeFragmentLoadingIndicator;
    Unbinder unbinder;
    @BindView(R.id.Fab_Request_Donation)
    FloatingActionButton FabRequestDonation;
    private int max = 0;
    private APIServices apiServices;
    public static final String POST_ID = "post_id";
    AdapterArticle adapterArticle;
    ArrayList<Data2Posts> postsArrayList = new ArrayList<>();

    Bundle bundle;
    private SaveData saveData;


    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        bundle = getArguments();
        saveData = getArguments().getParcelable(GET_DATA);
        postsArrayList.clear();
        HomeFragmentRecycleView.setHasFixedSize(true);
        apiServices = getRetrofit().create(APIServices.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        HomeFragmentRecycleView.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {

            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    getPosts(current_page);

                }

            }
        };
        HomeFragmentRecycleView.addOnScrollListener(onEndless);
        adapterArticle = new AdapterArticle(getActivity(), postsArrayList, new AdapterArticle.showDetial() {
            @Override
            public void itemShowDetail(Data2Posts position) {
                int posts = position.getId();
                DetailsHomeFragment detailsHomeFragment = new DetailsHomeFragment();
                bundle.putInt(POST_ID, posts);
                HelperMethod.replece(detailsHomeFragment, getActivity().getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, null, null, bundle);
            }
        }, new AdapterArticle.saveFavorite() {
            @Override
            public void itemSaveFavorite(final Data2Posts position) {
                String postFavourite = String.valueOf(position.getId());
                apiServices.getPostToggleFavourite(saveData.getApi_token(), postFavourite).enqueue(new Callback<PostToggleFavourite>() {
                    @Override
                    public void onResponse(Call<PostToggleFavourite> call, Response<PostToggleFavourite> response) {
                        PostToggleFavourite postToggleFavourite = response.body();
                        if (postToggleFavourite.getStatus() == 1) {
                            adapterArticle.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), postToggleFavourite.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostToggleFavourite> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        HomeFragmentRecycleView.setAdapter(adapterArticle);

        getPosts(1);

        return view;
    }

    private void getPosts(int page) {
        apiServices.getPosts(saveData.getApi_token(),
                page).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                try {
                    HomeFragmentLoadingIndicator.setVisibility(View.VISIBLE);
                    Posts posts = response.body();
                    if (posts.getStatus() == 1) {
                        HomeFragmentRecycleView.setVisibility(View.VISIBLE);
                        HomeFragmentLoadingIndicator.setVisibility(View.GONE);
                        HomeFragmentRLEmptyView.setVisibility(View.GONE);
                        postsArrayList.addAll(posts.getData().getData());
                        adapterArticle.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getActivity(), posts.getMsg(), Toast.LENGTH_SHORT).show();
                        getProperties();

                    }


                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    getProperties();
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                getProperties();

            }
        });
        HomeFragmentTietSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = HomeFragmentTietSearch.getText().toString().trim();
                adapterArticle.getFilter().filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void getProperties() {
        HomeFragmentRecycleView.setVisibility(View.GONE);
        HomeFragmentLoadingIndicator.setVisibility(View.GONE);
        HomeFragmentRLEmptyView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fab_Request_Donation)
    public void onViewClicked() {
        RequestDonationFragment requestDonationFragment = new RequestDonationFragment();
        HelperMethod.replece(requestDonationFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.request_donation), saveData);
    }
}
