package com.example.waimai.Tools;

import android.content.Context;
import android.graphics.Color;

import com.example.waimai.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 32033 on 2018/1/1.
 */

public class Tools {

    public static SweetAlertDialog showError(Context context, String title, String message){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText("确认");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        return sweetAlertDialog;
    }

    public static SweetAlertDialog showSuccess(Context context, String title, String message){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText("确认");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        return sweetAlertDialog;
    }

    public static SweetAlertDialog showWarn(Context context, String title, String message){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText("确认");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        return sweetAlertDialog;
    }

    public static SweetAlertDialog showBasic(Context context, String title, String message){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText("确认");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        return sweetAlertDialog;
    }

    public static SweetAlertDialog progress(Context context,String message){
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(message);
        pDialog.setCancelable(true);
        return pDialog;
    }
}
