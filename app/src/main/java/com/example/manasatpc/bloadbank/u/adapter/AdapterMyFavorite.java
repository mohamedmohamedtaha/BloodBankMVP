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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterMyFavorite extends ArrayAdapter<DataMyFavouritesTwo> {


    public AdapterMyFavorite(@NonNull Context context, ArrayList<DataMyFavouritesTwo> myFavourites) {
        super(context, 0, myFavourites);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        ViewHolder viewHolder;
        //Check if there is an exsiting list item view (called convertView) that we can reuse,
        //otherwise ,if convertView is null, then inflate anew list item
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_article, parent, false);
        }
        viewHolder = new ViewHolder(listItemView);

        //find the myFavourite at the given position in the list of myFavourite
        DataMyFavouritesTwo currentMyFavourite = getItem(position);


        if (TextUtils.isEmpty(currentMyFavourite.getTitle())) {
            viewHolder.TVShowTitleArticle.setText(R.string.no_title);
        } else {
            viewHolder.TVShowTitleArticle.setText(currentMyFavourite.getTitle());

        }
        if (TextUtils.isEmpty(currentMyFavourite.getThumbnailFullPath())) {
            Glide.with(getContext())
                    .load(R.drawable.no_image)
                    .into(viewHolder.IMShowPicture);
        } else {
            Glide.with(getContext())
                    .load(currentMyFavourite.getThumbnailFullPath())
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .into(viewHolder.IMShowPicture);

        }
        if (currentMyFavourite.getIsFavourite()) {
            Glide.with(getContext())
                    .load(R.drawable.favorite_bold)
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .into(viewHolder.IMFavorite);

        } else {
            Glide.with(getContext())
                    .load(R.drawable.favorite)
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .into(viewHolder.IMFavorite);
        }
        return listItemView;
    }


    static
    class ViewHolder {
        @BindView(R.id.IM_Favorite)
        ImageView IMFavorite;
        @BindView(R.id.TV_Show_Title_Article)
        TextView TVShowTitleArticle;
        @BindView(R.id.IM_Show_Picture)
        ImageView IMShowPicture;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }
    }
}
