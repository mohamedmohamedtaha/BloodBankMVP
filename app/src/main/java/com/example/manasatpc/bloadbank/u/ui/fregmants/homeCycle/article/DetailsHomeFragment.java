package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.article;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.posts.postdetails.PostDetails;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.DrawerLocker;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;
import static com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.article.HomeFragment.POST_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsHomeFragment extends Fragment {
    @BindView(R.id.DetailsHomeFragment_IM_Show_Image_Details)
    ImageView DetailsHomeFragmentIMShowImageDetails;
    @BindView(R.id.DetailsHomeFragment_IM_Show_Favorite_Details)
    ImageView DetailsHomeFragmentIMShowFavoriteDetails;
    @BindView(R.id.DetailsHomeFragment_Login_Progress)
    ProgressBar DetailsHomeFragmentLoginProgress;
    @BindView(R.id.DetailsHomeFragment_TV_Show_Title_Article_Details)
    TextView DetailsHomeFragmentTVShowTitleArticleDetails;
    @BindView(R.id.DetailsHomeFragment_TV_Show_Details)
    TextView DetailsHomeFragmentTVShowDetails;
    Unbinder unbinder;
    RememberMy rememberMy;
    private APIServices apiServices;
    Bundle bundle;

    public DetailsHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((DrawerLocker) getActivity()).setDraweEnabled(false);
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
        }
        bundle = getArguments();
        int postId = bundle.getInt(POST_ID);
        apiServices = getRetrofit().create(APIServices.class);
        DetailsHomeFragmentLoginProgress.setVisibility(View.VISIBLE);
        apiServices.getPostDetails(rememberMy.getAPIKey(), postId, 1).enqueue(new Callback<PostDetails>() {
            @Override
            public void onResponse(Call<PostDetails> call, Response<PostDetails> response) {
                PostDetails post = response.body();
                if (post.getStatus() == 1) {
                    DetailsHomeFragmentLoginProgress.setVisibility(View.GONE);
                    DetailsHomeFragmentTVShowTitleArticleDetails.setText(post.getData().getTitle());
                    toolbar.setTitle(post.getData().getTitle());
                    DetailsHomeFragmentTVShowDetails.setText(post.getData().getContent());
                    Glide.with(getActivity()).load(post.getData().getThumbnailFullPath())
                            .error(R.drawable.no_image).centerCrop().into(DetailsHomeFragmentIMShowImageDetails);
                    if (post.getData().getIsFavourite()) {
                        Glide.with(getActivity()).load(R.drawable.favorite_bold)
                                .error(R.drawable.no_image).centerCrop().into(DetailsHomeFragmentIMShowFavoriteDetails);
                    } else {
                        Glide.with(getActivity()).load(R.drawable.favorite)
                                .error(R.drawable.no_image).centerCrop().into(DetailsHomeFragmentIMShowFavoriteDetails);
                    }
                } else {
                    DetailsHomeFragmentLoginProgress.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), post.getMsg(), Toast.LENGTH_SHORT).show();
                    toolbar.setTitle("");
                }
            }

            @Override
            public void onFailure(Call<PostDetails> call, Throwable t) {
                DetailsHomeFragmentLoginProgress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
