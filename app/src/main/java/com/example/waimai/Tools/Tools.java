package com.example.waimai.Tools;

import android.content.Context;
import com.example.waimai.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 32033 on 2018/1/1.
 */

public class Tools {
    private static SweetAlertDialog dialogBuilder = null;

    public static SweetAlertDialog showError(Context context, String title, String message){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText("确认");
        sweetAlertDialog.show();
        return sweetAlertDialog;
    }

    public static SweetAlertDialog showSuccess(Context context, String title, String message){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText("确认");
        sweetAlertDialog.show();
        return sweetAlertDialog;
    }

    public static SweetAlertDialog showWarn(Context context, String title, String message){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText("确认");
        sweetAlertDialog.show();
        return sweetAlertDialog;
    }

    public static SweetAlertDialog showBasic(Context context, String title, String message){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText("确认");
        sweetAlertDialog.show();
        return sweetAlertDialog;
    }
}
