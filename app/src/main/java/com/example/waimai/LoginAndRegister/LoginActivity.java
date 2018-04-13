package com.example.waimai.LoginAndRegister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waimai.Fields.ActionOperate;
import com.example.waimai.Fields.Error;
import com.example.waimai.Fields.Operate;
import com.example.waimai.Fields.Successful;
import com.example.waimai.MainActivities.MainActivity;
import com.example.waimai.R;
import com.example.waimai.Tools.JSONTools;
import com.example.waimai.Tools.Tools;
import com.example.waimai.Util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static List<AppCompatActivity> actionList = new ArrayList<>();

    private EditText userCode, userPwd;
    private TextView register, forgetPWD; //注册新用户，忘记密码
    private Button login_button; //登录按钮
    private ImageView show, unshow;

    private boolean isshow = false; //是否显示密码

    private SweetAlertDialog sweetAlertDialog; //dialog

    private SharedPreferences.Editor editor;  //储存用户名和密码
    private SharedPreferences shared; //查看用户名和密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
        bindOnClick();
        LoginActivity.actionList.add(this);
        fillname();
    }

    public static void closeAllAction() {
        for (AppCompatActivity activity : EnterMail.actionList) {
            activity.finish();
        }
    }

    /**
     * 自动填充账号密码
     */
    private void fillname() {
        String last_name;
        String password;
        if ((last_name = shared.getString("last", null)) != null) {
            password = shared.getString(last_name, "error!");
            userCode.setText(last_name);
            userPwd.setText(password);
        }
    }

    /**
     * 初始化控件
     */
    private void init() {
        register = (TextView) findViewById(R.id.tv_register);
        forgetPWD = (TextView) findViewById(R.id.tv_forget_pwd);
        login_button = (Button) findViewById(R.id.btn_login_button);
        userCode = (EditText) findViewById(R.id.et_UserCode);
        userPwd = (EditText) findViewById(R.id.et_UserPwd);
        show = (ImageView) findViewById(R.id.im_show);
        unshow = (ImageView) findViewById(R.id.im_unshow);
        userPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        shared = PreferenceManager.getDefaultSharedPreferences(this);
    }

    /**
     * 按钮单机事件绑定
     */
    private void bindOnClick() {
        register.setOnClickListener(this);
        forgetPWD.setOnClickListener(this);
        login_button.setOnClickListener(this);
        show.setOnClickListener(this);
        unshow.setOnClickListener(this);
        userPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() { //密码框焦点监听事件
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    if (isshow) {
                        show.setVisibility(View.VISIBLE);
                        unshow.setVisibility(View.GONE);
                    } else {
                        show.setVisibility(View.GONE);
                        unshow.setVisibility(View.VISIBLE);
                    }
                } else {
                    show.setVisibility(View.GONE);
                    unshow.setVisibility(View.GONE);
                    userPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isshow = false;
                }
            }
        });
    }

    /**
     * 设置密码是否显示
     */
    public void setshow() {
        if (isshow) {
            isshow = false;
            show.setVisibility(View.GONE);
            unshow.setVisibility(View.VISIBLE);
            userPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            isshow = true;
            show.setVisibility(View.VISIBLE);
            unshow.setVisibility(View.GONE);
            userPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register: //注册按钮
                Intent phoneIntent = new Intent(this, EnterMail.class);
                phoneIntent.putExtra("Operate", ActionOperate.REGISTER);
                startActivity(phoneIntent);
                break;
            case R.id.tv_forget_pwd:
                Intent forgetPWDIntent = new Intent(this, EnterMail.class);
                forgetPWDIntent.putExtra("Operate", ActionOperate.FORGET_PASSWORD);
                startActivity(forgetPWDIntent);
                break;
            case R.id.btn_login_button:  //登录按钮
                RequestBody requestBody = new FormBody.Builder()
                        .add("UserCode", userCode.getText().toString())
                        .add("UserPassword", userPwd.getText().toString())
                        .build();
                HttpUtil.sendPostRequest(Operate.USER_LOGIN, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sweetAlertDialog = Tools.showError(LoginActivity.this, "提示", "无法连接服务器，请检查您的网络是否正确！");
                                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                });
                                sweetAlertDialog.show();
                            }
                        });
                        e.printStackTrace();

                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String request = JSONTools.getResult(response.body().string());
                        if (request.equals(Successful.SUCCEED)) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("Email", userCode.getText().toString());
                            startActivity(intent);
                            //记住密码
                            editor.putString(userCode.getText().toString(), userPwd.getText().toString());
                            editor.putString("last", userCode.getText().toString());
                            editor.apply();
                            LoginActivity.closeAllAction();
                        } else if (request.equals(Error.ERROR)) {
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    sweetAlertDialog = Tools.showError(LoginActivity.this, "提示", "用户名或密码错误");
                                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    });
                                    sweetAlertDialog.show();
                                }
                            });
                        }
                    }
                }, requestBody);
                break;
            case R.id.im_show:
                setshow();
                break;
            case R.id.im_unshow:
                setshow();
                break;
        }
    }
}
