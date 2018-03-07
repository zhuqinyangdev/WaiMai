package com.example.waimai.LoginAndRegister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.waimai.Fields.Error;
import com.example.waimai.Fields.Operate;
import com.example.waimai.Fields.Successful;
import com.example.waimai.R;
import com.example.waimai.Tools.JSONTools;
import com.example.waimai.Tools.Tools;
import com.example.waimai.Util.HttpUtil;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditPassword extends AppCompatActivity {

    private String Email;
    private EditText oldPassword, newPassword, confirmPasswrod;
    private Button next;
    private SweetAlertDialog sweetAlertDialog; //提示框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pwd_layout);
        Intent intent = getIntent();
        Email = intent.getExtras().getString("Email");
        initView();
        bindTest();
        EnterMail.actionList.add(this);
        LoginActivity.actionList.add(this);
    }

    private void initView() {
        oldPassword = (EditText) findViewById(R.id.old_password);
        newPassword = (EditText) findViewById(R.id.new_password);
        confirmPasswrod = (EditText) findViewById(R.id.confirm_password);
        next = (Button) findViewById(R.id.btn_next_in_editpwd);
    }

    private void bindTest() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    RequestBody requestBody = new FormBody.Builder()
                            .add("UserCode", Email)
                            .add("oldPWD", oldPassword.getText().toString())
                            .add("newPWD", newPassword.getText().toString())
                            .build();
                    HttpUtil.sendPostRequest(Operate.EDIT_PASSWORD, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            Toast.makeText(EditPassword.this, "发生未知错误", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = JSONTools.getResult(response.body().string());
                            showDialog(result);
                        }
                    }, requestBody);
                }
            }
        });
    }

    /**
     * 显示提示框
     */
    String message = ""; //要显示的信息

    private void showDialog(final String result) {
        if (result.equals(Successful.SUCCEED)) {
            message = "密码修改成功";
        } else if (result.equals(Error.ERROR_PASSWORD)) {
            message = "密码与账号不匹配";
        } else if (result.equals(Error.ERROR)) {
            message = "发生未知错误";
        }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sweetAlertDialog = Tools.showError(EditPassword.this, "提示", message);
                if (result.equals(Successful.SUCCEED)) {
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            EnterMail.closeAllAction();
                            EditPassword.this.finish();
                        }
                    });
                }
                if (result.equals(Error.ERROR) || result.equals(Error.ERROR_PASSWORD)) {
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            if (sweetAlertDialog.isShowing()) {
                                sweetAlertDialog.dismiss();
                            }
                        }
                    });
                }
                sweetAlertDialog.show();
            }
        });
    }

    /**
     * 检查文本框是否符合要求
     *
     * @return
     */
    private boolean check() {
        if (!newPassword.getText().toString().equals(confirmPasswrod.getText().toString())) {
            sweetAlertDialog = Tools.showBasic(EditPassword.this, "警告", "两次输入的密码不一致请重新输入");
            return false;
        }
        return true;
    }
}
