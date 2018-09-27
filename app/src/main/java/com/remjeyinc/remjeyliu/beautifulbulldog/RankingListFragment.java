package com.remjeyinc.remjeyliu.beautifulbulldog;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankingListFragment extends Fragment {

    private RecyclerView RankingList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter RankingAdapter;

    public RankingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rankings, container, false);

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Vote> rankings = realm.where(Vote.class).findAll();

        RankingList = (RecyclerView) view.findViewById(R.id.ranking_list);

        layoutManager = new LinearLayoutManager(getContext());
        RankingList.setLayoutManager(layoutManager);

        final MainActivity mainActivity = (MainActivity) this.getActivity();

        RankingAdapter = new RankingAdapter(getContext(), rankings);
        RankingList.setAdapter(RankingAdapter);

        return view;
    }

}
