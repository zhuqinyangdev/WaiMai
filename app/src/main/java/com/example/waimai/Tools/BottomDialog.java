package com.example.waimai.Tools;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.waimai.Fields.Activities;
import com.example.waimai.LoginAndRegister.EnterMail;
import com.example.waimai.LoginAndRegister.UserInfo;
import com.example.waimai.OtherActivity.Cooperation2;
import com.example.waimai.R;

/**
 * Created by zhuqinyang on 18-1-31.
 */

public class BottomDialog implements View.OnClickListener {

    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Dialog dialog;

    private UserInfo userInfo;
    private Cooperation2 cooperation2;
    private String Email, page;

    public BottomDialog(UserInfo context, String page,String Email) {
        this.userInfo = context;
        this.page = page;
        this.Email = Email;
    }

    public BottomDialog(Cooperation2 context, String page,String Email) {
        this.cooperation2 = context;
        this.page = page;
        this.Email = Email;
    }

    public void show(Context context) {
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takePhoto:
                /*if (page.equals("")) {
                    Intent intent = new Intent(context, UserInfo.class);
                    context.startActivity(intent);
                    Log.d("BottomDialog", "点击了拍照");
                } else if (page.equals("Cooperation2")) {

                }*/
                if (page.equals(Activities.COOPERATION2)) {
                    Intent intent = new Intent(cooperation2, SimpleCameraForAll.class);
                    cooperation2.startActivityForResult(intent, 0);
                }
                break;
            case R.id.choosePhoto:
                if (page.equals(Activities.COOPERATION2)) {
                    Intent intent = new Intent(cooperation2, SelectImageFromGallery.class);
                    cooperation2.startActivityForResult(intent, 0);
                }
                break;
        }
        dialog.dismiss();
    }
}
