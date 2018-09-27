package com.remjeyinc.remjeyliu.beautifulbulldog;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class BulldogListFragment extends Fragment {

    private RecyclerView bulldogList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter bulldogAdapter;

    public BulldogListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bulldog_list, container, false);

        bulldogList = (RecyclerView) view.findViewById(R.id.bulldog_list);
        layoutManager = new LinearLayoutManager(getContext());
        bulldogList.setLayoutManager(layoutManager);
        refreshList();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }
    private void refreshList() {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Bulldog> bulldogs = realm.where(Bulldog.class).findAll();

        final MainActivity mainActivity = (MainActivity) this.getActivity();

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bulldog bulldog = (Bulldog) bulldogs.get(position);
                Intent intent = new Intent(view.getContext(), BulldogActivity.class);
                intent.putExtra("username", mainActivity.user.getUsername());
                intent.putExtra("bulldog", bulldog.getId());
                startActivity(intent);
            }
        };

        BulldogAdapter adapter = new BulldogAdapter(getActivity(), bulldogs, listener);
        bulldogList.setAdapter(adapter);
    }

}
