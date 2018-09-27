package com.remjeyinc.remjeyliu.beautifulbulldog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmResults;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.BulldogViewHolder> {
private Context context;
private RealmResults<Vote> rankings;
private RecyclerViewClickListener mListener;

    public RankingAdapter(Context context, RealmResults<Vote> dataSet) {
        this.context = context;
        this.rankings = dataSet;
    }

    public static class BulldogViewHolder extends RecyclerView.ViewHolder{
        public TextView nameView;
        public TextView ownerView;
        public TextView ratingView;

        private RecyclerViewClickListener mListener;
        public BulldogViewHolder(View v) {
            super(v);
            nameView = v.findViewById(R.id.name_view);
            ownerView = v.findViewById(R.id.owner_view);
            ratingView = v.findViewById(R.id.rating_view);
        }
    }
    @Override
    public int getItemCount() {
        return rankings.size();
    }

    @Override
    public RankingAdapter.BulldogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bulldog_cell, parent, false);
        BulldogViewHolder vh = new BulldogViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(BulldogViewHolder holder, int position) {
        holder.nameView.setText(rankings.get(position).getBulldog().getName());
        holder.ownerView.setText(rankings.get(position).getOwner().getUsername());
        holder.ratingView.setText(rankings.get(position).getRating());


    }
}
