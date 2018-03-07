package com.example.waimai.OtherActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.waimai.Fields.Activities;
import com.example.waimai.LoginAndRegister.EnterMail;
import com.example.waimai.R;
import com.example.waimai.Tools.BottomDialog;
import com.example.waimai.Tools.Tools;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Cooperation2 extends AppCompatActivity {

    private SweetAlertDialog sweetAlertDialog; //dialog
    private Button upload, btn_next;
    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation2_layout);
        initView();
        initTest();
    }

    private void initView() {
        upload = (Button) findViewById(R.id.upload_image);
        btn_next = (Button) findViewById(R.id.btn_next);
    }

    /**
     * 设置单击事件
     */
    private void initTest() {
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialog bottomDialog = new BottomDialog(Cooperation2.this, Activities.COOPERATION2, Email);
                bottomDialog.show(Cooperation2.this);
            }
        });
    }

    /**
     * 监听照片拍摄事件
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //相机拍摄
            case 0:
                //0:拍照成功 1:拍照失败 2:拍照取消
                if (resultCode == 0) {
                    sweetAlertDialog = Tools.showSuccess(Cooperation2.this,"提示","操作成功，点击确认完成商铺注册！");
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    });
                } else if (resultCode == 1) {
                    sweetAlertDialog = Tools.showError(Cooperation2.this,"警告","错误的操作,无法完成!");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    });
                } else if (resultCode == 2) {
                    sweetAlertDialog = Tools.showError(Cooperation2.this,"提示","您已取消该操作!");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    });
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
