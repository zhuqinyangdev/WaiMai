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
import com.example.waimai.Interface.BottomSelectListener;
import com.example.waimai.LoginAndRegister.EnterMail;
import com.example.waimai.LoginAndRegister.UserInfo;
import com.example.waimai.OtherActivity.Cooperation2;
import com.example.waimai.R;

/**
 * Created by zhuqinyang on 18-1-31.
 */

public class BottomDialog implements View.OnClickListener {

    //宽高比
    private int aspectX,aspectY;
    //宽高长
    private int outputX,outputY;

    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Dialog dialog;

    //声明要启动活动
    Intent intent = null;

    //声明对象
    private UserInfo userInfo;
    private Cooperation2 cooperation2;

    private String Email, page;

    public BottomDialog(UserInfo userInfo,String Email,int aspectX,int aspectY,int outputX,int outputY) {
        this.userInfo = userInfo;
        this.Email = Email;
        initData(aspectX,aspectY,outputX,outputY);
    }

    public BottomDialog(Cooperation2 cooperation2,String Email,int aspectX,int aspectY,int outputX,int outputY) {
        this.cooperation2 = cooperation2;
        this.Email = Email;
        initData(aspectX,aspectY,outputX,outputY);
    }

    /**
     * 初始化宽高比设置
     * @param aspectX 宽占的比列
     * @param aspectY 高占的比列
     * @param outputX 宽有多长
     * @param outputY 高有多长
     */
    public void initData(int aspectX,int aspectY,int outputX,int outputY){
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        this.outputX = outputX;
        this.outputY = outputY;
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

    //初始化相机Intent
    public void initIntentForCamera(){
        if(cooperation2 != null) {
            intent = new Intent(cooperation2,SimpleCameraForAll.class);
            setIntent();
            cooperation2.startActivityForResult(intent, 0);
        } else if(userInfo != null) {
            intent = new Intent(userInfo,SimpleCameraForAll.class);
            setIntent();
            userInfo.startActivityForResult(intent, 0);
        }
    }

    //初始化图册Intent
    public void initIntentForGallery(){
        if(cooperation2 != null) {
            intent = new Intent(cooperation2,SelectImageFromGallery.class);
            setIntent();
            cooperation2.startActivityForResult(intent, 0);
        } else if(userInfo != null) {
            intent = new Intent(userInfo,SelectImageFromGallery.class);
            setIntent();
            userInfo.startActivityForResult(intent, 0);
        }
    }

    //设置宽高比
    public void setIntent(){
        intent.putExtra(SimpleCameraForAll.APSPECTX,aspectX);
        intent.putExtra(SimpleCameraForAll.APSPECTY,aspectY);
        intent.putExtra(SimpleCameraForAll.OUTPUTX,outputX);
        intent.putExtra(SimpleCameraForAll.OUTPUTY,outputY);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takePhoto:
                initIntentForCamera();
                break;
            case R.id.choosePhoto:
                initIntentForGallery();
                break;
        }
        dialog.dismiss();
    }
}
