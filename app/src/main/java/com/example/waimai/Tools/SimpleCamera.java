package com.example.waimai.Tools;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;

import java.io.File;

/**
 * Created by 32033 on 2018/1/19.
 */

public class SimpleCamera extends TakePhotoActivity {

    private TakePhoto takePhoto;

    private int selectId; //0.头像拍摄 1.照片拍摄

    public SimpleCamera(){
        takePhoto = this.getTakePhoto();
    }

    public void take_head_portrait(){
        selectId = 0;
        File file=new File(Environment.getExternalStorageDirectory(), "/head_portrait/"+System.currentTimeMillis() + ".jpg");
        Uri imageUri = Uri.fromFile(file);
        CropOptions.Builder builder=new CropOptions.Builder()
                .setAspectX(300)
                .setOutputY(300)
                .setAspectX(1)
                .setAspectY(1);
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(20)
                .enableReserveRaw(false)  //是否储存原图
                .setMaxPixel(300)
                .create();
        getTakePhoto().onEnableCompress(config,false);

        this.getTakePhoto().onPickFromCaptureWithCrop(imageUri,builder.create());
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Log.d("SimpleCamera",result.getImage().getCompressPath());
    }

}
