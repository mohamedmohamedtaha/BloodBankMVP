package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.article;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterArticle;
import com.example.manasatpc.bloadbank.u.data.interactor.ArticleInteractor;
import com.example.manasatpc.bloadbank.u.data.model.posts.posts.Data2Posts;
import com.example.manasatpc.bloadbank.u.data.model.posts.posttogglefavourite.PostToggleFavourite;
import com.example.manasatpc.bloadbank.u.data.presenter.ArticlePresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.ArticleView;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.OnEndless;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.donation.RequestDonationFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ArticleView {
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
    @BindView(R.id.Fab_Request_Donation)
    FloatingActionButton FabRequestDonation;
    @BindView(R.id.HomeFragment_Select_Category)
    Spinner HomeFragmentSelectCategory;
    Unbinder unbinder;
    RememberMy rememberMy;
    private int max = 0;
    private APIServices apiServices;
    public static final String POST_ID = "post_id";
    AdapterArticle adapterArticle;
    Bundle bundle;
    ImageView imageViewFavorite;
    private ArticlePresenter presenter;
    OnEndless onEndless;

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new ArticleInteractor(this, getContext());
        rememberMy = new RememberMy(getActivity());
        apiServices = getRetrofit().create(APIServices.class);
        bundle = new Bundle();
        imageViewFavorite = (ImageView) view.findViewById(R.id.IM_Favorite);
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
        }
        {
            presenter.getCategory(HomeFragmentSelectCategory, getActivity());
            HomeFragmentRecycleView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            HomeFragmentRecycleView.setLayoutManager(linearLayoutManager);
            onEndless = new OnEndless(linearLayoutManager, 1) {
                @Override
                public void onLoadMore(int current_page) {
                    if (current_page <= max) {
                        if (max != 0 && current_page != 1) {
                            onEndless.previous_page = current_page;
                            presenter.loadData(current_page);
                        } else {
                            onEndless.current_page = onEndless.previous_page;
                        }

                    } else {
                        onEndless.current_page = onEndless.previous_page;
                    }
                }
            };
            HomeFragmentRecycleView.addOnScrollListener(onEndless);
            presenter.loadData(1);
            HomeFragmentTietSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handle = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        presenter.search(HomeFragmentTietSearch);
                        handle = true;
                    }
                    return handle;
                }
            });
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fab_Request_Donation)
    public void onViewClicked() {
        RequestDonationFragment requestDonationFragment = new RequestDonationFragment();
        HelperMethod.replece(requestDonationFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.request_donation));
    }

    @Override
    public void showProgress() {
        HomeFragmentLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        if (HomeFragmentLoadingIndicator != null) {
            HomeFragmentLoadingIndicator.setVisibility(View.GONE);

        }
    }

    @Override
    public void loadSuccess(ArrayList<Data2Posts> postsArrayList) {
        adapterArticle = new AdapterArticle(getActivity(), postsArrayList, new AdapterArticle.showDetial() {
            @Override
            public void itemShowDetail(Data2Posts position) {
                int posts = position.getId();
                DetailsHomeFragment detailsHomeFragment = new DetailsHomeFragment();
                bundle.putInt(POST_ID, posts);
                HelperMethod.replece(detailsHomeFragment, getActivity().getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, position.getTitle(), bundle);
            }
        }, new AdapterArticle.saveFavorite() {
            @Override
            public void itemSaveFavorite(final Data2Posts position) {
                String postFavourite = String.valueOf(position.getId());
                apiServices.getPostToggleFavourite(rememberMy.getAPIKey(), postFavourite).enqueue(new Callback<PostToggleFavourite>() {
                    @Override
                    public void onResponse(Call<PostToggleFavourite> call, Response<PostToggleFavourite> response) {
                        PostToggleFavourite postToggleFavourite = response.body();
                        if (postToggleFavourite.getStatus() == 1) {
                            adapterArticle.notifyDataSetChanged();
                            Toast.makeText(getActivity(), postToggleFavourite.getMsg(), Toast.LENGTH_SHORT).show();
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
        adapterArticle.notifyDataSetChanged();
        HomeFragmentRecycleView.setAdapter(adapterArticle);
    }

    @Override
    public void empty() {
        Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRecyclerView() {
        HomeFragmentRecycleView.setVisibility(View.VISIBLE);
        HomeFragmentRLEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showRelativeView() {
        HomeFragmentRecycleView.setVisibility(View.GONE);
        HomeFragmentRLEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}