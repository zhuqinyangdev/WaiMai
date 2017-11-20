package com.example.waimai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.waimai.Activitys.LoginAndRegister.EnterPhone;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register; //注册新用户

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
        bindOnClick();
    }

    /**
     * 初始化控件
     */
    private void init(){
        register = (TextView)findViewById(R.id.register);
    }

    /**
     * 按钮单机事件绑定
     */
    private void bindOnClick(){
        register.setOnClickListener(this);
    }

    /**
     * 控件点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register:
                Intent intent = new Intent(this, EnterPhone.class);
                startActivity(intent);
                break;
        }
    }
}
