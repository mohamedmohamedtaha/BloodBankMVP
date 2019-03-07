package com.example.manasatpc.bloadbank.u.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.rest.posts.Data2Posts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterArticle extends RecyclerView.Adapter<AdapterArticle.ArticleViewHolder> {

    private Context context;
    private showDetial mListener ;
    private saveFavorite saveFavorite;
    private ArrayList<Data2Posts> dataPosts = new ArrayList<>();

    public AdapterArticle(Context context, ArrayList<Data2Posts> dataPosts,showDetial mListener,saveFavorite saveFavorite) {
        this.context = context;
        this.dataPosts = dataPosts;
        this.mListener = mListener;
        this.saveFavorite = saveFavorite;
    }



    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_article, parent, false);
       final ArticleViewHolder articleViewHolder = new ArticleViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = articleViewHolder.getAdapterPosition();
                Data2Posts data2Posts = dataPosts.get(position);
                if (mListener !=null)mListener.itemShowDetail(data2Posts);

            }
        });
        return articleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        if (TextUtils.isEmpty(dataPosts.get(position).getTitle())){
            holder.TVShowTitleArticle.setText(R.string.no_title);
        }else {
            holder.TVShowTitleArticle.setText(dataPosts.get(position).getTitle());

        }
        if (dataPosts.get(position).getIsFavourite()){
            Glide.with(context)
                    .load(R.drawable.favorite_bold)
                    .into(holder.IMFavorite);
        }else{
            Glide.with(context)
                    .load(R.drawable.favorite)
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .into(holder.IMFavorite);

        }
        if (TextUtils.isEmpty(dataPosts.get(position).getThumbnailFullPath())){
            Glide.with(context)
                    .load(R.drawable.no_image)
                    .into(holder.IMShowPicture);
        }else{
            Glide.with(context)
                    .load(dataPosts.get(position).getThumbnailFullPath())
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .into(holder.IMShowPicture);

        }

    //    setDataPost(holder, position);
     //   setAction(holder, position);

    }

    @Override
    public int getItemCount() {
        if (dataPosts != null){
            return dataPosts.size();

        }
        return 0;
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.IM_Favorite)
        ImageView IMFavorite;
        @BindView(R.id.TV_Show_Title_Article)
        TextView TVShowTitleArticle;
        @BindView(R.id.IM_Show_Picture)
        ImageView IMShowPicture;

        public ArticleViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this,view);
            IMFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    Data2Posts data2Posts1 = dataPosts.get(position);
                    if (mListener != null) saveFavorite.itemSaveFavorite(data2Posts1);


                }
            });



        }
    }

    private void setData(ArticleViewHolder holder, int position) {

    }

    private void setAction(ArticleViewHolder adapterArticle, int position) {

    }

    public interface showDetial {
        void itemShowDetail(Data2Posts position);
    }
    public interface saveFavorite {
        void itemSaveFavorite(Data2Posts position);
    }


}
