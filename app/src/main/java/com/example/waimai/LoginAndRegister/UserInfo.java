package com.example.waimai.LoginAndRegister;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waimai.Fields.Operate;
import com.example.waimai.Fields.Path;
import com.example.waimai.Fields.Successful;
import com.example.waimai.Interface.DatePickListener;
import com.example.waimai.Interface.SingleDialogListener;
import com.example.waimai.R;
import com.example.waimai.Tools.BottomDialog;
import com.example.waimai.Tools.JSONTools;
import com.example.waimai.Tools.SimpleCamera;
import com.example.waimai.Tools.SingleDialog;
import com.example.waimai.Tools.DatePick;
import com.example.waimai.Tools.Tools;
import com.example.waimai.Util.HttpUtil;

import java.io.IOException;
import java.sql.Date;
import java.util.TooManyListenersException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserInfo extends AppCompatActivity implements View.OnClickListener {

    private String Email;

    private CircleImageView head_image;
    private EditText userPwd, userName, userPhone, userIdCard, userAddress, userDescription;
    private RelativeLayout select_gender, select_birthday;
    private TextView gender, birthday;
    private Button next;

    private SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_layout);
        Intent intent = getIntent();
        Email = intent.getStringExtra("Email");
        String PageName = intent.getExtras().getString("PageName");
        initView();
        if (PageName.equals("Main3")) {
            head_image();
            initTest();
            final String savePath = Path.HEAD_IMAGE_PATH + "HeadImage" + Email + ".jpg";
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(savePath);
                head_image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            initTest();
        }
        EnterMail.actionList.add(this);
    }

    private void initView() {
        head_image = (CircleImageView) findViewById(R.id.user_image);
        userPwd = (EditText) findViewById(R.id.et_userPassWord);
        userName = (EditText) findViewById(R.id.et_userName);
        userPhone = (EditText) findViewById(R.id.et_userPhone);
        userIdCard = (EditText) findViewById(R.id.et_IdCard);
        userAddress = (EditText) findViewById(R.id.et_userAddress);
        userDescription = (EditText) findViewById(R.id.et_userDescription);
        select_gender = (RelativeLayout) findViewById(R.id.ll_gender);
        select_birthday = (RelativeLayout) findViewById(R.id.ll_birthday);
        gender = (TextView) findViewById(R.id.tv_gender_text);
        birthday = (TextView) findViewById(R.id.tv_birthday_date);
        next = (Button) findViewById(R.id.btn_next_in_userinfo);
    }

    private void initTest() {
        select_gender.setOnClickListener(this);
        select_birthday.setOnClickListener(this);
        next.setOnClickListener(this);
        head_image.setOnClickListener(this);
    }

    private void head_image() {
        head_image.setVisibility(View.VISIBLE);

    }

    /**
     * 身份证验证
     *
     * @param IdCard 身份证号
     * @return
     */
    public boolean isIdCard(String IdCard) {
        String str = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(IdCard);

        return m.matches();
    }

    private boolean check() {
        if (userName.getText().toString().equals("") || userPwd.getText().toString().equals("") ||
                userAddress.getText().toString().equals("") || userDescription.getText().toString().equals("") ||
                userIdCard.getText().toString().equals("") || userPhone.getText().toString().equals("") ||
                gender.getText().toString().equals("") || birthday.getText().toString().equals("")) {
            Toast.makeText(UserInfo.this, "请务必填写完整每一项", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userPhone.getText().toString().length() != 11) {
            Toast.makeText(UserInfo.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isIdCard(userIdCard.getText().toString())) {
            Toast.makeText(UserInfo.this, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_gender:
                final String[] items = {"男", "女"};
                SingleDialog.showDialog(this, items, new SingleDialogListener() {
                    @Override
                    public void onSelect(int which) {
                        Log.d("UserInfo", "选择了" + items[which]);
                        gender.setText(items[which]);
                    }
                });
                break;
            case R.id.ll_birthday:
                DatePick.showDatePick(this, new DatePickListener() {
                    @Override
                    public void onSelectDate(String date) {
                        birthday.setText(date);
                    }
                });
                break;
            case R.id.btn_next_in_userinfo:
                if (check()) {
                    RequestBody requestBody = new FormBody.Builder()
                            .add("UserCode", Email)
                            .add("UserPassword", userPwd.getText().toString())
                            .add("UserName", userName.getText().toString())
                            .add("UserPhone", userPhone.getText().toString())
                            .add("UserBirthday", birthday.getText().toString())
                            .add("UserAddress", userAddress.getText().toString())
                            .add("UserIdCard", userIdCard.getText().toString())
                            .add("UserGender", gender.getText().toString().equals("男") ? "1" : "2")
                            .add("Description", userDescription.getText().toString())
                            .build();
                    HttpUtil.sendPostRequest(Operate.USER_REGISTER, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String json = response.body().string();
                            String result = JSONTools.getResult(json);
                            if (result.equals(Successful.SUCCEED)) {
                                UserInfo.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Tools tools = new Tools();
                                        sweetAlertDialog = tools.showSuccess(UserInfo.this, "提示", "注册成功");
                                        sweetAlertDialog.show();
                                    }
                                });
                            }
                        }
                    }, requestBody);
                }
                break;
            case R.id.user_image:
                /*BottomDialog dialog = new BottomDialog(UserInfo.this);
                dialog.show(UserInfo.this);*/
                try {
                    Intent intent = new Intent(UserInfo.this, SimpleCamera.class);
                    intent.putExtra("Email", Email);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
