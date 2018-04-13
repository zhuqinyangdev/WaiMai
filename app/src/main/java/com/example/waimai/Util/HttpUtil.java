package com.example.waimai.Util;

import android.util.Log;

import com.example.waimai.Fields.Operate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Callback;

/**
 * Created by 32033 on 2017/12/5.
 */

public class HttpUtil {
    private static String address;

    private static void initAddress(String operate) {
        address = "http://192.168.0.5:8081";
        if (operate.equals(Operate.SEND_VERIFICATION_CODE)) {
            address += "/user/verification_code";
            return;
        }
        if (operate.equals(Operate.USER_REGISTER)) {
            address += "/user/register";
            return;
        }
        if (operate.equals(Operate.USER_LOGIN)) {
            address += "/user/login";
            return;
        }
        if (operate.equals(Operate.EDIT_PASSWORD)) {
            address += "/user/edit_password";
            return;
        }
        if (operate.equals(Operate.UPLOAD_HEAD_IMAGE)) {
            address += "/file/upload/head_portrait";
        }
        if (operate.equals(Operate.DOWNLOAD_HEAD_IMAGE)) {
            address += "/file/download/head_portrait";
        }
        if(operate.equals(Operate.REGISTER_STORE)){
            address += "/store/register";
        }
        if(operate.equals(Operate.UPLOAD_STORE_IMAGE)){
            address += "/file/upload/store_image";
        }
        if(operate.equals(Operate.QUERY_USER_STORE)){
            address += "/store/queryUserStore";
        }
    }

    /**
     * Get方式发送请求(异步)
     *
     * @param operate  请求的操作
     * @param callback 回调
     */
    public static void sendGetRequest(String operate, Callback callback) {
        initAddress(operate);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * Post方式发送请求（异步）
     *
     * @param operate  请求的操作
     * @param callback 回调
     */
    public static void sendPostRequest(String operate, Callback callback, RequestBody body) {

        initAddress(operate);
        Log.d("HttpUtil", "发送请求地址：" + operate);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        Log.d("HttpUtil", request.toString());
        client.newCall(request).enqueue(callback);
    }

    /**
     * 上传头像
     *
     * @param operate  操作
     * @param callback 回调
     * @param file     上传的文件File
     */
    public static void uploadFile(String operate, Callback callback, File file, String Email) {
        initAddress(operate);
        OkHttpClient client = new OkHttpClient();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(), fileBody)
                .addFormDataPart("Email", Email)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);

    }

    public static void downloadHeadImage(String operate, String Email, Callback callback, RequestBody body) {
        initAddress(operate);
        Log.d("HttpUtil", "发送请求地址：" + operate);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        Log.d("HttpUtil", request.toString());
        client.newCall(request).enqueue(callback);
    }


}
