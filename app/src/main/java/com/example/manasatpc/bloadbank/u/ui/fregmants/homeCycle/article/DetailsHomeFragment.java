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
import com.example.manasatpc.bloadbank.u.data.interactor.DetailsHomeInteractor;
import com.example.manasatpc.bloadbank.u.data.model.posts.postdetails.PostDetails;
import com.example.manasatpc.bloadbank.u.data.presenter.DetailsHomePresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.DetailsHomeView;
import com.example.manasatpc.bloadbank.u.helper.DrawerLocker;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;
import static com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.article.HomeFragment.POST_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsHomeFragment extends Fragment implements DetailsHomeView {
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
    private DetailsHomePresenter presenter;

    public DetailsHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new DetailsHomeInteractor(this);
        rememberMy = new RememberMy(getActivity());
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((DrawerLocker) getActivity()).setDraweEnabled(false);
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        bundle = getArguments();
        int postId = bundle.getInt(POST_ID);
        if (check_network == false) {
        } else {
            presenter.getPostDetails(rememberMy.getAPIKey(), postId, 1);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showProgress() {
        DetailsHomeFragmentLoginProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        DetailsHomeFragmentLoginProgress.setVisibility(View.GONE);

    }

    @Override
    public void isFavourite() {
        Glide.with(getActivity()).load(R.drawable.favorite_bold)
                .error(R.drawable.no_image).centerCrop().into(DetailsHomeFragmentIMShowFavoriteDetails);
    }

    @Override
    public void isNotFavourite() {
        Glide.with(getActivity()).load(R.drawable.favorite)
                .error(R.drawable.no_image).centerCrop().into(DetailsHomeFragmentIMShowFavoriteDetails);
    }

    @Override
    public void toolbarEmpty() {
        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void loadSuccess(PostDetails post) {
        if (post != null) {
            DetailsHomeFragmentTVShowTitleArticleDetails.setText(post.getData().getTitle());
            toolbar.setTitle(post.getData().getTitle());
            DetailsHomeFragmentTVShowDetails.setText(post.getData().getContent());
            Glide.with(getActivity()).load(post.getData().getThumbnailFullPath())
                    .error(R.drawable.no_image).centerCrop().into(DetailsHomeFragmentIMShowImageDetails);
        }
    }
}
