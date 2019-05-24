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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterArticle;
import com.example.manasatpc.bloadbank.u.data.model.posts.categories.Categories;
import com.example.manasatpc.bloadbank.u.data.model.posts.categories.DataCategories;
import com.example.manasatpc.bloadbank.u.data.model.posts.posts.Data2Posts;
import com.example.manasatpc.bloadbank.u.data.model.posts.posts.Posts;
import com.example.manasatpc.bloadbank.u.data.model.posts.posttogglefavourite.PostToggleFavourite;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.OnEndless;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.donation.RequestDonationFragment;

import java.util.ArrayList;
import java.util.List;

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
    ArrayList<Data2Posts> postsArrayList = new ArrayList<>();
    Integer positionCategories;
    final ArrayList<Integer> IdsCategories = new ArrayList<>();
    Bundle bundle;
    ImageView imageViewFavorite;
    int catdgory_id = 0;


    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        apiServices = getRetrofit().create(APIServices.class);
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
        }
        bundle = new Bundle();
        getCategory();
        imageViewFavorite = (ImageView) view.findViewById(R.id.IM_Favorite);
        postsArrayList.clear();
        HomeFragmentRecycleView.setHasFixedSize(true);
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
        HomeFragmentRecycleView.setAdapter(adapterArticle);
        getPosts(1);

        HomeFragmentTietSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handle = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    catdgory_id = IdsCategories.get(HomeFragmentSelectCategory.getSelectedItemPosition());
                    String text = HomeFragmentTietSearch.getText().toString().trim();
                    if (text.isEmpty()) {
                        Toast.makeText(getActivity(), getString(R.string.filed_request), Toast.LENGTH_SHORT).show();
                    } else {
                        postsArrayList.clear();
                        HomeFragmentLoadingIndicator.setVisibility(View.VISIBLE);
                        final Call<Posts> postsFilterCall = apiServices.getPostFilter(rememberMy.getAPIKey(),
                                1, text, catdgory_id);
                        postsFilterCall.enqueue(new Callback<Posts>() {
                            @Override
                            public void onResponse(Call<Posts> call, Response<Posts> response) {
                                Posts postsFilter = response.body();
                                try {
                                    if (postsFilter.getStatus() == 1) {
                                        postsArrayList.addAll(postsFilter.getData().getData());
                                        if (!postsArrayList.isEmpty()) {
                                            HomeFragmentRecycleView.setVisibility(View.VISIBLE);
                                            HomeFragmentLoadingIndicator.setVisibility(View.GONE);
                                            HomeFragmentRLEmptyView.setVisibility(View.GONE);
                                            postsArrayList.addAll(postsFilter.getData().getData());
                                            adapterArticle.notifyDataSetChanged();
                                        } else {
                                            getProperties();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), postsFilter.getMsg(), Toast.LENGTH_LONG).show();
                                        getProperties();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    getProperties();
                                }
                            }

                            @Override
                            public void onFailure(Call<Posts> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                                getProperties();
                            }
                        });
                    }
                    handle = true;
                }
                return handle;
            }
        });
        return view;
    }

    private void getPosts(int page) {
        apiServices.getPosts(rememberMy.getAPIKey(),
                page).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                try {
                    HomeFragmentLoadingIndicator.setVisibility(View.VISIBLE);
                    Posts posts = response.body();
                    if (posts.getStatus() == 1) {
                        postsArrayList.addAll(posts.getData().getData());
                        if (!postsArrayList.isEmpty()) {
                            HomeFragmentRecycleView.setVisibility(View.VISIBLE);
                            HomeFragmentLoadingIndicator.setVisibility(View.GONE);
                            HomeFragmentRLEmptyView.setVisibility(View.GONE);
                            adapterArticle.notifyDataSetChanged();
                        } else {
                            getProperties();
                        }
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
    }

    private void getCategory() {
        final Call<Categories> categoriesCall = apiServices.getCategories();
        categoriesCall.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                String getResult;
                ArrayList<String> strings = new ArrayList<>();
                Categories bloodTypes = response.body();
                if (bloodTypes.getStatus() == 1) {
                    try {
                        strings.add(getString(R.string.categories));
                        IdsCategories.add(0);
                        List<DataCategories> dataCategoriesList = bloodTypes.getData();
                        for (int i = 0; i < dataCategoriesList.size(); i++) {
                            getResult = dataCategoriesList.get(i).getName();
                            strings.add(getResult);
                            positionCategories = dataCategoriesList.get(i).getId();
                            IdsCategories.add(positionCategories);
                        }
                        HelperMethod.showGovernorates(strings, getActivity(), HomeFragmentSelectCategory);
                        HomeFragmentSelectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    postsArrayList.clear();
                                    HomeFragmentLoadingIndicator.setVisibility(View.VISIBLE);
                                    final Call<Posts> postsFilterCall = apiServices.getPostFilter(rememberMy.getAPIKey(),
                                            1, null, IdsCategories.get(position));
                                    postsFilterCall.enqueue(new Callback<Posts>() {
                                        @Override
                                        public void onResponse(Call<Posts> call, Response<Posts> response) {
                                            Posts postsFilter = response.body();
                                            try {
                                                if (postsFilter.getStatus() == 1) {
                                                    postsArrayList.addAll(postsFilter.getData().getData());
                                                    if (!postsArrayList.isEmpty()) {
                                                        HomeFragmentRecycleView.setVisibility(View.VISIBLE);
                                                        HomeFragmentLoadingIndicator.setVisibility(View.GONE);
                                                        HomeFragmentRLEmptyView.setVisibility(View.GONE);
                                                        adapterArticle.notifyDataSetChanged();
                                                    } else {
                                                        getProperties();
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), postsFilter.getMsg(), Toast.LENGTH_LONG).show();
                                                    getProperties();
                                                }

                                            } catch (Exception e) {
                                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                                getProperties();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Posts> call, Throwable t) {
                                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                                            getProperties();
                                        }
                                    });


                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public void getProperties() {
//        HomeFragmentRecycleView.setVisibility(View.GONE);
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
        HelperMethod.replece(requestDonationFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.request_donation));
    }
}
