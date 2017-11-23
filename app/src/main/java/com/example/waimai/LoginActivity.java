package com.example.waimai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.waimai.LoginAndRegister.EnterPhone;
import com.example.waimai.MainActivities.MainFirst;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register; //注册新用户
    private Button login_button; //登录按钮

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
        login_button = (Button)findViewById(R.id.login_button);
    }

    /**
     * 按钮单机事件绑定
     */
    private void bindOnClick(){
        register.setOnClickListener(this);
        login_button.setOnClickListener(this);
    }

    /**
     * 控件点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register:
                Intent phoneIntent = new Intent(this, EnterPhone.class);
                startActivity(phoneIntent);
                break;
            case R.id.login_button:
                Intent main1Intent = new Intent(this, MainFirst.class);
                startActivity(main1Intent);
                break;
        }
    }
}
