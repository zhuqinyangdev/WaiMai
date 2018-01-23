package com.example.waimai.Tools;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 32033 on 2017/12/12.
 */

public class JSONTools {

    /**
     * 验证码json数据解析
     * @param json json字符串
     * @return
     */
    public static String verification_code(String json){
        String verification_code = "";
        try {
            Log.d("JSONTools",json);
            JSONObject jsonObject = new JSONObject(json);
            verification_code = jsonObject.getString("Result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return verification_code;
    }

    public static String getResult(String json){
        String result = "";
        try{
            Log.d("JSONTools",json);
            JSONObject jsonObject = new JSONObject(json);
            result = jsonObject.getString("Result");
        } catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
