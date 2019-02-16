package com.example.manasatpc.bloadbank.u.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.rest.posts.my_favourites.DataMyFavouritesTwo;
import com.example.manasatpc.bloadbank.u.data.rest.posts.my_favourites.MyFavourites;

import java.util.ArrayList;

import butterknife.BindView;

public class AdapterMyFavorite extends ArrayAdapter<DataMyFavouritesTwo> {
    @BindView(R.id.IM_Favorite)
    ImageView IMFavorite;
    @BindView(R.id.TV_Show_Title_Article)
    TextView TVShowTitleArticle;
    @BindView(R.id.IM_Show_Picture)
    ImageView IMShowPicture;
    private Context context;

    public AdapterMyFavorite(@NonNull Context context, ArrayList<DataMyFavouritesTwo> myFavourites) {
        super(context, 0, myFavourites);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        //Check if there is an exsiting list item view (called convertView) that we can reuse,
        //otherwise ,if convertView is null, then inflate anew list item
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_article, parent, false);

        }

        //find the myFavourite at the given position in the list of myFavourite
        DataMyFavouritesTwo currentMyFavourite = getItem(position);


        if (TextUtils.isEmpty(currentMyFavourite.getTitle())) {
            TVShowTitleArticle.setText(R.string.no_title);
        } else {
            TVShowTitleArticle.setText(currentMyFavourite.getTitle());

        }
        if (TextUtils.isEmpty(currentMyFavourite.getThumbnailFullPath())) {
            Glide.with(context)
                    .load(R.drawable.no_image)
                    .into(IMShowPicture);
        } else {
            Glide.with(context)
                    .load(currentMyFavourite.getThumbnailFullPath())
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .into(IMShowPicture);

        }
        if (currentMyFavourite.getIsFavourite() == true){
            Glide.with(context)
                    .load(R.drawable.favorite_bold)
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .into(IMShowPicture);

        }else {
            Glide.with(context)
                    .load(R.drawable.favorite)
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .into(IMShowPicture);
        }
        return listItemView;
    }


}
