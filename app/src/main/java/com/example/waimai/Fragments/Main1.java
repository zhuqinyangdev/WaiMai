package com.example.waimai.Fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.waimai.Adapter.RecyclerViewInMainAdapter;
import com.example.waimai.R;

/**
 * Created by 32033 on 2017/11/23.
 */

@SuppressLint("ValidFragment")
public class Main1 extends Fragment {

    private Context context;
    private RecyclerView shop_recyclerView; //主页面1中的RecyclerView;
    private RecyclerViewInMainAdapter recyclerViewAdapter; //shop_recyclerView的适配器

    public Main1(Context context){
        this.context = context;
    }

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_layout,container,false);
        init();
        initRecyclerView();
        return view;
    }

    private void init(){
        shop_recyclerView = (RecyclerView)view.findViewById(R.id.rv_main1_Recycler);
        recyclerViewAdapter = new RecyclerViewInMainAdapter();
    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        shop_recyclerView.setLayoutManager(layoutManager);
        shop_recyclerView.setAdapter(recyclerViewAdapter);
    }


}
