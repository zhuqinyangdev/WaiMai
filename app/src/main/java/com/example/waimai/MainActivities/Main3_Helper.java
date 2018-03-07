package com.example.waimai.MainActivities;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.waimai.Fragments.Main3;
import com.example.waimai.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhuqinyang on 18-1-28.
 */

public class Main3_Helper{

    private CircleImageView circleImageView;

    public void init(Main3 main3){
        try {
            circleImageView = main3.getView().findViewById(R.id.user_image);
            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Main3_Helper","点击了头像");
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
