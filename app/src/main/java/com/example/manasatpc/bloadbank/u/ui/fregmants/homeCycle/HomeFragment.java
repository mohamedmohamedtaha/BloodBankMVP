package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.rest.posts.Data2Posts;
import com.example.manasatpc.bloadbank.u.data.rest.posts.Posts;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.OnEndless;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.API_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    AdapterArticle adapterArticle;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.empty_image)
    ImageView emptyImage;
    @BindView(R.id.tv_empty_view)
    TextView tvEmptyView;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    Unbinder unbinder;


    ArrayList<Data2Posts> postsArrayList = new ArrayList<>();
    @BindView(R.id.empty_view_category)
    RelativeLayout emptyViewCategory;
    @BindView(R.id.Fab_Posts)
    FloatingActionButton FabPosts;
    private int max = 0;
    private APIServices apiServices;
    public static final String POST_ID = "post_id";


    Bundle bundle;


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

        apiServices = getRetrofit().create(APIServices.class);
       /* if (bundle != null) {
            Toast.makeText(getActivity(), "Bundle :" + bundle.getString(API_KEY), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Error:", Toast.LENGTH_SHORT).show();

        }*/

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        list.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    getPosts(current_page);

                }

            }
        };

        list.addOnScrollListener(onEndless);
        adapterArticle = new AdapterArticle(getActivity(), postsArrayList, new AdapterArticle.showDetial() {

            @Override
            public void itemShowDetail(Data2Posts position) {
                int posts = position.getId();
                Toast.makeText(getActivity(), "AA : " + posts, Toast.LENGTH_SHORT).show();
                DetailsHomeFragment detailsHomeFragment = new DetailsHomeFragment();
                //  bundle.putInt(POST_ID,posts);
                HelperMethod.replece(detailsHomeFragment, getActivity().getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, null, null, bundle);


            }
        });
        list.setAdapter(adapterArticle);

        getPosts(1);
        FabPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailsHomeFragment detailsHomeFragment = new DetailsHomeFragment();
                HelperMethod.replece(detailsHomeFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, null, null, null);
            }
        });

        return view;
    }

    private void getPosts(int page) {
//"8KTqGqCh3ofQvl0DySaPNBw0TZODwgxlfZ0nHmWxigWlrKK3cpVLJQEb0bju"

        apiServices.getPosts(bundle.getString(API_KEY),
                page).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                try {
                    loadingIndicator.setVisibility(View.VISIBLE);
                    Posts posts = response.body();
                    if (posts.getStatus() == 1) {
                        list.setVisibility(View.VISIBLE);
                        loadingIndicator.setVisibility(View.GONE);
                        emptyViewCategory.setVisibility(View.GONE);
                        postsArrayList.addAll(posts.getDataPosts().getData());
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


    }

    public void getProperties() {
        list.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.GONE);
        emptyViewCategory.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
