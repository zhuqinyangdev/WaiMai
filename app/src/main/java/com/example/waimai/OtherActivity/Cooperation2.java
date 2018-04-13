package com.example.waimai.OtherActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.waimai.Fields.Activities;
import com.example.waimai.Fields.Error;
import com.example.waimai.Fields.Operate;
import com.example.waimai.Fields.Successful;
import com.example.waimai.LoginAndRegister.EnterMail;
import com.example.waimai.R;
import com.example.waimai.Tools.BottomDialog;
import com.example.waimai.Tools.SimpleCameraForAll;
import com.example.waimai.Tools.Tools;
import com.example.waimai.Util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Cooperation2 extends AppCompatActivity {

    //dialog
    private SweetAlertDialog sweetAlertDialog;
    //进度条型的dialog（转圈圈）
    private SweetAlertDialog processAlertDialog;
    private Button upload, btn_next;
    private String Email;
    /**
     * 存放商铺信息
     */
    private RequestBody requestBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation2_layout);
        init();
        initView();
        initTest();
    }

    private void init() {
        try {
            Intent intent = this.getIntent();
            Email = intent.getExtras().getString("Email", null);
            requestBody = new FormBody.Builder()
                    .add("Email", Email)
                    .add("storename", intent.getExtras().getString("StoreName"))
                    .add("storeaddress", intent.getExtras().getString("StoreAddress"))
                    .add("storelatitude", intent.getExtras().getString("StoreLatitude"))
                    .add("storelongitude", intent.getExtras().getString("StoreLongitude"))
                    .add("storephone", intent.getExtras().getString("StorePhone"))
                    .add("storeproperty", intent.getExtras().getString("StoreProperty"))
                    .add("storetype", intent.getExtras().getString("StoreType"))
                    //平均价
                    .add("middlerate", intent.getExtras().getString("MiddleRate"))
                    //门市价
                    .add("counterprice", intent.getExtras().getString("CounterPrice"))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                BottomDialog bottomDialog = new BottomDialog(Cooperation2.this, Email,0,0,0,0);
                bottomDialog.show(Cooperation2.this);
            }
        });
    }


    /**
     * 上传头像
     * @param imagePath 图片路径
     */
    private void uploadImage(String imagePath){
        File file = new File(imagePath);
        HttpUtil.uploadFile(Operate.UPLOAD_STORE_IMAGE, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Cooperation2.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processAlertDialog.dismiss();
                        sweetAlertDialog = Tools.showError(Cooperation2.this,"提示","网络连接失败，请检查你的网络");
                        sweetAlertDialog.show();
                    }
                });

                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                Log.d("Cooperation2","照片"+text);
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    if(jsonObject.getString("Result").equals(Successful.SUCCEED)){
                        processAlertDialog.dismiss();
                        sweetAlertDialog = Tools.showSuccess(Cooperation2.this,"提示","注册成功！");
                        sweetAlertDialog.show();
                    } else {
                        processAlertDialog.dismiss();
                        sweetAlertDialog = Tools.showError(Cooperation2.this,"提示","注册失败，请检查你是否选择正确的照片！");
                        sweetAlertDialog.show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        },file,Email);
    }
    /**
     * 注册商铺的方法
     */
    private void registerStore(final String imagepath) {
        //上传基本商铺信息
        HttpUtil.sendPostRequest(Operate.REGISTER_STORE, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Cooperation2.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processAlertDialog.dismiss();
                        sweetAlertDialog = Tools.showError(Cooperation2.this, "提示", "信息上传失败\n请检查你的网络或重新尝试");
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

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String text = response.body().string();
                    JSONObject jsonObject = new JSONObject(text);
                    String result = jsonObject.getString("Result");
                    Log.d("Cooperation2", text);
                    if (result.equals(Successful.SUCCEED)) {
                        Cooperation2.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                processAlertDialog.dismiss();
                                processAlertDialog = Tools.progress(Cooperation2.this, "正在上传图片..");
                                processAlertDialog.show();
                                uploadImage(imagepath);
                            }
                        });


                    } else if (result.equals(Error.STORE_EXIST)){
                        Cooperation2.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                processAlertDialog.dismiss();
                                sweetAlertDialog = Tools.showError(Cooperation2.this, "提示", "相应的的账号只能注册一个商铺！");
                                sweetAlertDialog.show();
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, requestBody);
    }

    /**
     * 监听照片拍摄事件
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        Log.d("Cooperation2","拍摄完成路径为："+data.getExtras().getString(SimpleCameraForAll.IMAGE_URI)+"结果："+resultCode);
        switch (requestCode) {
            //相机拍摄
            case 0:
                //0:拍照成功 1:拍照失败 2:拍照取消
                if (resultCode == 0) {
                    sweetAlertDialog = Tools.showSuccess(Cooperation2.this, "提示", "操作成功，点击确认完成商铺注册！");
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            try{
                                processAlertDialog = Tools.progress(Cooperation2.this, "正在上传信息..");
                                processAlertDialog.show();
                                registerStore(data.getExtras().getString(SimpleCameraForAll.IMAGE_URI));

                            } catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    });
                    sweetAlertDialog.show();
                } else if (resultCode == 1) {
                    sweetAlertDialog = Tools.showError(Cooperation2.this, "警告", "错误的操作,无法完成!");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    });
                    sweetAlertDialog.show();
                } else if (resultCode == 2) {
                    sweetAlertDialog = Tools.showError(Cooperation2.this, "提示", "您取消了该操作!");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    });
                    sweetAlertDialog.show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
