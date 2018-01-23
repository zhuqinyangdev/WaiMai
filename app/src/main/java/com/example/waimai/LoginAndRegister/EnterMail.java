package com.example.waimai.LoginAndRegister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waimai.Fields.ActionOperate;
import com.example.waimai.Fields.Operate;
import com.example.waimai.R;
import com.example.waimai.Tools.JSONTools;
import com.example.waimai.Util.HttpUtil;
import com.example.waimai.Fields.Error;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EnterMail extends AppCompatActivity implements View.OnClickListener{

    private String LastOperate;

    private Button btn_next;
    private EditText et_Mail;
    private LinearLayout tiaokuan;
    private RelativeLayout load;

    private int i;//当连接失败时重新连接的次数

    public static List<AppCompatActivity> actionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_mail_layout);
        Intent intent = getIntent();
        LastOperate = intent.getExtras().getString("Operate");
        initView();
        initTest();
        if(!LastOperate.equals(Operate.SEND_VERIFICATION_CODE)){
            tiaokuan.setVisibility(View.INVISIBLE);
        }
        actionList.add(this);
        LoginActivity.actionList.add(this);
    }

    public static void closeAllAction(){
        for(AppCompatActivity activity:EnterMail.actionList){
            activity.finish();
        }
    }

    /**
     * 初始化控件
     */
    private void initView(){
        btn_next = (Button)findViewById(R.id.btn_next_email);
        et_Mail = (EditText)findViewById(R.id.et_phone_number);
        load = (RelativeLayout)findViewById(R.id.loading);
        tiaokuan = (LinearLayout)findViewById(R.id.tiaokuan);
    }
    /**
     * 添加控件的事件
     */
    private void initTest(){
        btn_next.setOnClickListener(this);
        //btn_next.setClickable(false);
        et_Mail.addTextChangedListener(new TextWatcher() {



            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }




            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    if(isEmail(s.toString())) {
                        btn_next.setBackground(EnterMail.this.getResources().getDrawable(R.drawable.button_shape));
                        btn_next.setTextColor(EnterMail.this.getResources().getColor(R.color.black));
                        btn_next.setClickable(true);
                    } else {
                        btn_next.setBackground(EnterMail.this.getResources().getDrawable(R.drawable.button_shape_gray));
                        btn_next.setTextColor(EnterMail.this.getResources().getColor(R.color.gray_text));
                        //btn_next.setClickable(false);
                    }
            }
            @Override
            public void afterTextChanged(Editable e) {
            }
        });
    }

    /**
     * 验证邮箱格式是否正确
     * @param email 邮箱
     * @return
     */
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next_email:
                load.getBackground().setAlpha(10);
                load.setVisibility(View.VISIBLE);
                i = 0;
                getCode();
                break;

        }
    }

    /**
     * 发起okhttp请求向服务器获得验证码
     */
    private void getCode(){
        RequestBody requestBody = new FormBody.Builder()
                .add("Email",et_Mail.getText().toString())
                .build();
        HttpUtil.sendPostRequest(Operate.SEND_VERIFICATION_CODE, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                if(i<3) {
                    i++;
                    getCode();
                } else {
                    Toast.makeText(EnterMail.this,"请求服务器失败，请稍后重新尝试",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String code = response.body().string();
                //String code = JSONTools.verification_code(response.body().string());
                if(!code.equals(Error.ERROR)){
                        Intent intent = new Intent(EnterMail.this, Code.class);
                        intent.putExtra("Email", et_Mail.getText().toString());
                        intent.putExtra("Operate",LastOperate);
                        intent.putExtra("Code","");
                        Log.d("EnterMail",code);
                        startActivity(intent);
                } else {
                    Toast.makeText(EnterMail.this,"验证码获取失败",Toast.LENGTH_SHORT).show();
                }
            }
        },requestBody);
    }

    @Override
    protected void onRestart() {
        if(load.getVisibility() == View.VISIBLE){
            load.setVisibility(View.GONE);
        }
        super.onRestart();
    }
}
