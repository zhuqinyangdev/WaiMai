package com.example.waimai.Fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.waimai.Adapter.RecyclerViewInMainAdapter;
import com.example.waimai.Fields.Operate;
import com.example.waimai.Fields.Path;
import com.example.waimai.Fields.Successful;
import com.example.waimai.LoginAndRegister.UserInfo;
import com.example.waimai.MainActivities.MainActivity;
import com.example.waimai.OtherActivity.Cooperation;
import com.example.waimai.OtherActivity.Cooperation2;
import com.example.waimai.R;
import com.example.waimai.Tools.JSONTools;
import com.example.waimai.Tools.Tools;
import com.example.waimai.Util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 32033 on 2017/11/23.
 */

@SuppressLint("ValidFragment")
public class Main3 extends Fragment implements View.OnClickListener {

    private String Email;//登录者

    private LinearLayout cooperation;

    private MainActivity context;

    private CircleImageView circleImageView;
    //声明提示框对象
    SweetAlertDialog sweetAlertDialog;

    public Main3(MainActivity context, String Email) {
        this.context = context;
        this.Email = Email;
    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main3_layout, container, false);
        initView();
        init();
        initTest();
        //loadHeadImage();
        Glide.with(this).load("http://192.168.0.5:8081/file/download/head_portrait?Email="+Email).into(circleImageView);
        return view;
    }

    private void initView() {
        circleImageView = (CircleImageView) view.findViewById(R.id.user_image);
        cooperation = (LinearLayout) view.findViewById(R.id.cooperation);

    }

    private void initTest() {
        circleImageView.setOnClickListener(this);
        cooperation.setOnClickListener(this);
    }

    private void init() {
        circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.no_head_image));
    }

    /**
     * 加载头像
     */
    private void loadHeadImage() {
        final String savePath = Path.HEAD_IMAGE_PATH + "HeadImage" + Email + ".jpg";
        File file = new File(savePath);
        if (file.exists()) {
            try {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = BitmapFactory.decodeFile(savePath);
                        circleImageView.setImageBitmap(bitmap);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("Email", Email)
                .build();
        HttpUtil.downloadHeadImage(Operate.DOWNLOAD_HEAD_IMAGE, Email, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Main3", "访问失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("Main3", "访问成功");
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                //储存下载文件的目录
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                    }
                    fos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bitmap = BitmapFactory.decodeFile(savePath);
                                circleImageView.setImageBitmap(bitmap);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, requestBody);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_image:
                Intent intent = new Intent(context, UserInfo.class);
                intent.putExtra("PageName", "Main3");
                intent.putExtra("Email", Email);
                startActivity(intent);
                break;
            case R.id.cooperation:
                try {
                    RequestBody requestBody = new FormBody.Builder()
                            .add("Email", Email)
                            .build();
                    HttpUtil.sendPostRequest(Operate.QUERY_USER_STORE, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    sweetAlertDialog = Tools.showError(context, "提示", "网络连接失败，请检查你的网络!");
                                    sweetAlertDialog.show();
                                }
                            });
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (JSONTools.getResult(jsonObject.toString()) == Successful.SUCCEED) {
                                    Intent cooperation_intent = new Intent(context, Cooperation.class);
                                    cooperation_intent.putExtra("Email", Email);
                                    startActivity(cooperation_intent);
                                } else {
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            sweetAlertDialog = Tools.showWarn(context, "提示", "你已注册过商铺，相应的账户只能拥有一个商铺。");
                                            sweetAlertDialog.show();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, requestBody);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
