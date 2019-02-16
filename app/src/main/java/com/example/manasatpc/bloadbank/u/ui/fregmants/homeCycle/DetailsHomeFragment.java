package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.rest.posts.post.Post;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.HomeFragment.POST_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsHomeFragment extends Fragment {


    @BindView(R.id.IM_Show_Image_Details)
    ImageView IMShowImageDetails;
    @BindView(R.id.IM_Show_Favorite_Details)
    ImageView IMShowFavoriteDetails;
    @BindView(R.id.TV_Show_Title_Article_Details)
    TextView TVShowTitleArticleDetails;
    @BindView(R.id.TV_Show_Details)
    TextView TVShowDetails;
    Unbinder unbinder;
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
        //bundle = getArguments();
       // int aa = bundle.getInt(POST_ID);
        //Toast.makeText(getActivity(), "AA : "  + aa, Toast.LENGTH_SHORT).show();
         apiServices =  getRetrofit().create(APIServices.class);
         apiServices.getPost("8KTqGqCh3ofQvl0DySaPNBw0TZODwgxlfZ0nHmWxigWlrKK3cpVLJQEb0bju",1).enqueue(new Callback<Post>() {
             @Override
             public void onResponse(Call<Post> call, Response<Post> response) {
                 Post post = response.body();
                 if (post.getStatus() == 1){
                     TVShowTitleArticleDetails.setText(post.getDataPost().getTitle());
                     TVShowDetails.setText(post.getDataPost().getContent());
                     Glide.with(getActivity()).load(post.getDataPost().getThumbnailFullPath())
                             .error(R.drawable.no_image).centerCrop().into(IMShowImageDetails);
                 }else {
                     Toast.makeText(getActivity(), post.getMsg(), Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<Post> call, Throwable t) {
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
