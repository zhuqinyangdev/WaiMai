package com.example.waimai.Adapter;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waimai.R;

import org.w3c.dom.Text;

/**
 * Created by 32033 on 2017/11/21.
 */

public class RecyclerViewInMainAdapter extends RecyclerView.Adapter<RecyclerViewInMainAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView shop_Phote; //recyclerview显示的照片
        TextView price; //销售价格
        TextView sold_number; //销售数量
        TextView rack_rate; //门市价
        public ViewHolder(View view){
            super(view);
            shop_Phote = (ImageView)view.findViewById(R.id.iv_shop_photo);
            price = (TextView)view.findViewById(R.id.tv_price);
            sold_number = (TextView)view.findViewById(R.id.tv_sold_number);
            rack_rate = (TextView)view.findViewById(R.id.tv_rack_rate);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mian_recyclerview_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }
}
