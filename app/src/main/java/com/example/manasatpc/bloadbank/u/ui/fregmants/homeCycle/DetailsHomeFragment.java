package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle;


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
import com.example.manasatpc.bloadbank.u.data.model.posts.post.Post;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.DrawerLocker;
import com.example.manasatpc.bloadbank.u.helper.SaveData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;
import static com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.HomeFragment.POST_ID;

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
    private APIServices apiServices;
    Bundle bundle;
    SaveData saveData;

    public DetailsHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((DrawerLocker) getActivity()).setDraweEnabled(false);

        bundle = getArguments();
        int postId = bundle.getInt(POST_ID);
        apiServices = getRetrofit().create(APIServices.class);
        DetailsHomeFragmentLoginProgress.setVisibility(View.VISIBLE);
        apiServices.getPost(saveData.getApi_token(), postId).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post = response.body();
                if (post.getStatus() == 1) {
                    DetailsHomeFragmentLoginProgress.setVisibility(View.GONE);
                    DetailsHomeFragmentTVShowTitleArticleDetails.setText(post.getDataPost().getTitle());
                    toolbar.setTitle(post.getDataPost().getTitle());
                    DetailsHomeFragmentTVShowDetails.setText(post.getDataPost().getContent());
                    Glide.with(getActivity()).load(post.getDataPost().getThumbnailFullPath())
                            .error(R.drawable.no_image).centerCrop().into(DetailsHomeFragmentIMShowImageDetails);
                    if (post.getDataPost().getIsFavourite()) {
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
            public void onFailure(Call<Post> call, Throwable t) {
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
