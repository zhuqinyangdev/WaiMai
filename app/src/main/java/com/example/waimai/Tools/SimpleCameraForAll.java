package com.example.waimai.Tools;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import com.example.waimai.Fields.Error;
import com.example.waimai.Fields.Successful;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;

import java.io.File;

/**
 * Created by zhuqinyang on 18-3-5.
 */

public class SimpleCameraForAll extends TakePhotoActivity {

    private TakePhoto takePhoto;

    private String page;

    private static final String IMAGE_URI = "Image_Uri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        start();
    }

    private void init(){
        takePhoto = this.getTakePhoto();
    }

    private void start(){
        String savePath = Environment.getExternalStorageDirectory()+"/kuqi/image/"+System.currentTimeMillis()+ ".jpg";
        File file = new File(savePath);
        Uri imageUri = Uri.fromFile(file);
        takePhoto.onPickFromCapture(imageUri);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        Intent intent = new Intent();
        intent.putExtra("result", Error.CANCEL);
        setResult(2, intent);
        this.finish();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Intent intent = new Intent();
        intent.putExtra("result", Error.ERROR);
        setResult(1, intent);
        this.finish();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Intent intent = new Intent();
        intent.putExtra("result", Successful.SUCCEED);
        intent.putExtra(SimpleCameraForAll.IMAGE_URI, result.getImage().getCompressPath());
        setResult(0, intent);
        this.finish();
    }
}
