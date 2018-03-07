package com.example.waimai.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.waimai.Interface.CameraListener;
import com.jph.takephoto.model.TResult;

/**
 * Created by zhuqinyang on 18-3-5.
 */

public class TakePhoto extends Activity {

    /**
     * yonghuming
     */
    private String Email;
    private Context context;
    private CameraListener cameraListener;

    public TakePhoto(String Email, Context context, CameraListener cameraListener){
        this.Email = Email;
        this.context = context;
        this.cameraListener = cameraListener;
    }

    private void init(){

    }

    public void start(){
        Intent intent = new Intent(context,SimpleCameraForAll.class);
        intent.putExtra("Email", Email);
        context.startActivity(intent);
    }



}
