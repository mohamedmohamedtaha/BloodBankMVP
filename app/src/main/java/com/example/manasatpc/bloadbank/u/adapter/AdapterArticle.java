package com.example.manasatpc.bloadbank.u.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.posts.Data2Posts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterArticle extends RecyclerView.Adapter<AdapterArticle.ArticleViewHolder> implements Filterable {

    private Context context;
    private showDetial mListener ;
    private saveFavorite saveFavorite;
    private ArrayList<Data2Posts> dataPosts = new ArrayList<>();
    //list for filtered Seach
    ArrayList<Data2Posts> postsArrayListSearch = new ArrayList<>();

    public AdapterArticle(Context context, ArrayList<Data2Posts> dataPosts,showDetial mListener,saveFavorite saveFavorite) {
        this.context = context;
        this.dataPosts = dataPosts;
        this.mListener = mListener;
        this.saveFavorite = saveFavorite;
        this.postsArrayListSearch =dataPosts;
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
                Data2Posts data2Posts = postsArrayListSearch.get(position);
                if (mListener !=null)mListener.itemShowDetail(data2Posts);

            }
        });
        return articleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Data2Posts data2Posts = postsArrayListSearch.get(position);

        if (TextUtils.isEmpty(data2Posts.getTitle())){
            holder.TVShowTitleArticle.setText(R.string.no_title);
        }else {
            holder.TVShowTitleArticle.setText(data2Posts.getTitle());

        }
        if (data2Posts.getIsFavourite()){
//            Glide.with(context)
//                    .load(R.drawable.favorite_bold)
//                    .into(holder.IMFavorite);
            holder.IMFavorite.setImageResource(R.drawable.favorite_bold);
        }else{
//            Glide.with(context)
//                    .load(R.drawable.favorite)
//                    .error(R.drawable.no_image)
//                    .centerCrop()
//                    .into(holder.IMFavorite);

            holder.IMFavorite.setImageResource(R.drawable.favorite);

        }

        if (TextUtils.isEmpty(data2Posts.getThumbnailFullPath())){
            Glide.with(context)
                    .load(R.drawable.no_image)
                    .into(holder.IMShowPicture);
        }else{
            Glide.with(context)
                    .load(data2Posts.getThumbnailFullPath())
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .into(holder.IMShowPicture);

        }

    //    setDataPost(holder, position);
     //   setAction(holder, position);

    }

    @Override
    public int getItemCount() {
        if (postsArrayListSearch != null){
            return postsArrayListSearch.size();

        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()){
                    postsArrayListSearch = dataPosts;
                }else {
                    ArrayList filterdList = new ArrayList();
                    for (Data2Posts row : dataPosts){
                        //change this to filter according
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())){
                            filterdList.add(row);
                        }
                    }
                    postsArrayListSearch = filterdList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = postsArrayListSearch;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                postsArrayListSearch = (ArrayList)results.values;
                notifyDataSetChanged();

            }
        };
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
