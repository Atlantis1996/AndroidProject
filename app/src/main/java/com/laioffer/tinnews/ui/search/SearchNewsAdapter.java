package com.laioffer.tinnews.ui.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.model.Article;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;


public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {
    private List<Article> articles = new LinkedList<>();
    private LikeListener likeListener;

    interface LikeListener {
        void onLike(Article article);
        void onClick(Article article);
    }

    public void setLikeListener(LikeListener likeListener) {
        this.likeListener = likeListener;
    }

    public void setArticles(List<Article> newsList) {
        this.articles.clear();
        this.articles.addAll(newsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        SearchNewsViewHolder searchNewsViewHolder = new SearchNewsViewHolder(view);
        Log.d("Test", "create " + searchNewsViewHolder.toString());
        return searchNewsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        if (article.urlToImage == null) {
            holder.newsImage.setImageResource(R.drawable.ic_empty_image);
        } else {
            Picasso.get().load(article.urlToImage).into(holder.newsImage);
        }


        if (article.favorite) {
            holder.favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
            holder.favorite.setOnClickListener(null);
        } else {
            holder.favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            holder.favorite.setOnClickListener(v -> {
                article.favorite = true;
                likeListener.onLike(article);
            });
        }
        holder.itemView.setOnClickListener(v -> {
            likeListener.onClick(article);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder  {
        ImageView newsImage;
        ImageView favorite;
        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.image);
            favorite = itemView.findViewById(R.id.favorite);
        }
    }
}
