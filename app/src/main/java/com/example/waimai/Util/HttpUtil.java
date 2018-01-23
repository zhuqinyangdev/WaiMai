package com.example.waimai.Util;

import android.util.Log;

import com.example.waimai.Fields.Operate;

import java.io.IOException;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import  okhttp3.Callback;

/**
 * Created by 32033 on 2017/12/5.
 */

public class HttpUtil {
    private static String address ;

    private static void initAddress(String operate){
        address = "http://192.168.1.107:8081";
        if(operate.equals(Operate.SEND_VERIFICATION_CODE)){
            address+="/user/verification_code";
            return;
        }
        if(operate.equals(Operate.USER_REGISTER)){
            address+="/user/register";
            return;
        }
        if(operate.equals(Operate.USER_LOGIN)){
            address+="/user/login";
            return;
        }
        if(operate.equals(Operate.EDIT_PASSWORD)){
            address+="/user/edit_password";
            return;
        }
    }

    /**
     * Get方式发送请求(异步)
     * @param operate 请求的操作
     * @param callback 回调
     */
    public static void sendGetRequest(String operate, Callback callback ){
        initAddress(operate);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * Post方式发送请求（异步）
     * @param operate 请求的操作
     * @param callback 回调
     */
    public static void sendPostRequest(String operate,Callback callback , RequestBody body){

        initAddress(operate);
        Log.d("HttpUtil","发送请求地址："+operate);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        Log.d("HttpUtil",request.toString());
        client.newCall(request).enqueue(callback);
    }

    /**
     * Get方式发送请求(同步)
     * @param operate 请求的操作
     */
    /*public static void sendGetRequestSYN(String operate ){
        try {
        initAddress(operate);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Post(同步)
     * @param operate 相应的操作
     * @param body 操作
     */
    /*public static void sendPostRequestSYN(String operate, RequestBody body){
        try {
        initAddress(operate);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
