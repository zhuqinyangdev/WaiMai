package com.example.waimai.Tools;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.waimai.Interface.SingleDialogListener;
import com.example.waimai.LoginAndRegister.EnterMail;
import com.example.waimai.LoginAndRegister.UserInfo;

/**
 * Created by 32033 on 2017/12/16.
 */

public class SingleDialog {

    private static int select = 0;

    public static void showDialog(final UserInfo userInfo, final String []items, final SingleDialogListener singleDialogListener){
        userInfo.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showSingleChoiceDialog(userInfo,items,singleDialogListener);
            }
        });
    }

    private static void showSingleChoiceDialog(Context context, String []item, final SingleDialogListener singleDialogListener){
        select = 0;
         String[] items = item;
         AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(context);
        singleChoiceDialog.setTitle("我是一个单选Dialog");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        select = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (select != -1) {
                            singleDialogListener.onSelect(select);
                        }
                    }
                });
        singleChoiceDialog.show();
    }

}
