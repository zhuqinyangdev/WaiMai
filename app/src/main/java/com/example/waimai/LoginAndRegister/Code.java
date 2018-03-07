package com.example.waimai.LoginAndRegister;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.waimai.Fields.ActionOperate;
import com.example.waimai.Fields.Operate;
import com.example.waimai.R;

public class Code extends AppCompatActivity implements View.OnClickListener {

    private String LastOperate;
    private String Code;
    private String Email;

    private EditText et_code;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_code_layout);
        Intent param = getIntent();
        Code = param.getStringExtra("Code");
        Email = param.getStringExtra("Email");
        LastOperate = param.getExtras().getString("Operate");
        initView();
        initTest();
        EnterMail.actionList.add(this);
        LoginActivity.actionList.add(this);
    }

    private void initView() {
        et_code = (EditText) findViewById(R.id.et_verification_code);
        btn_next = (Button) findViewById(R.id.btn_next_in_verification_code);
    }

    /**
     * 添加控件事件
     */
    private void initTest() {
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next_in_verification_code:
                if (Code.equals(et_code.getText().toString())) {
                    if (LastOperate.equals(Operate.USER_REGISTER)) {
                        Intent intent = new Intent(Code.this, UserInfo.class);
                        intent.putExtra("Email", Email);
                        startActivity(intent);
                    } else if (LastOperate.equals(ActionOperate.FORGET_PASSWORD)) {
                        Intent intent = new Intent(Code.this, EditPassword.class);
                        intent.putExtra("Email", Email);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(Code.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
