package com.example.waimai.MainActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.waimai.Adapter.RecyclerViewInMainAdapter;
import com.example.waimai.R;

public class MainFirst extends AppCompatActivity {

    private RecyclerView shop_recyclerView; //主页面1中的RecyclerView;
    private RecyclerViewInMainAdapter recyclerViewAdapter; //shop_recyclerView的适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        init();
        initRecyclerView();
    }

    /**
     * 初始化控件
     */
    private void init(){
        shop_recyclerView = (RecyclerView)findViewById(R.id.main1_Recycler);
        recyclerViewAdapter = new RecyclerViewInMainAdapter();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        shop_recyclerView.setLayoutManager(layoutManager);
        shop_recyclerView.setAdapter(recyclerViewAdapter);

    }

}
