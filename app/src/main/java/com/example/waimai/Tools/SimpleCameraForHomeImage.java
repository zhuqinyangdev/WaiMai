package com.example.waimai.Tools;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.waimai.Fields.Error;
import com.example.waimai.Fields.Operate;
import com.example.waimai.Fields.Successful;
import com.example.waimai.Util.HttpUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 32033 on 2018/1/19.
 */

public class SimpleCameraForHomeImage extends TakePhotoActivity {

    private TakePhoto takePhoto;
    private String Email;

    private int selectId = 0; //0.头像拍摄 1.照片拍摄

    public SimpleCameraForHomeImage(){
        takePhoto = this.getTakePhoto();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Email = intent.getExtras().getString("Email");
        take_head_portrait();
    }

    public void take_head_portrait(){
        try {
            selectId = 0;
            File file=new File(Environment.getExternalStorageDirectory(), "/HomeImage/"+"HeadImage"+Email+ ".jpg");
            if (!file.getParentFile().exists())file.getParentFile().mkdirs();
            Uri imageUri = Uri.fromFile(file);
            CropOptions.Builder builder = new CropOptions.Builder()
                    .setOutputY(200)
                    .setOutputX(200)
                    .setAspectX(1)
                    .setAspectY(1);
            CompressConfig config = new CompressConfig.Builder()
                    .enableReserveRaw(true)  //是否储存原图
                    .create();
            takePhoto.onEnableCompress(config, false);
            takePhoto.onPickFromCaptureWithCrop(imageUri, builder.create());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void update_head_image(TResult result){
        try {
            File file = new File(result.getImage().getCompressPath());
            if (file.exists()) {
                Toast.makeText(SimpleCameraForHomeImage.this, "文件存在,文件名：" + file.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SimpleCameraForHomeImage.this, "上传失败,文件不存在", Toast.LENGTH_SHORT).show();
            }
            HttpUtil.uploadFile(Operate.UPLOAD_HEAD_IMAGE, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //Toast.makeText(SimpleCamera.this, "上传失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("SimpleCamera","上传失败："+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //Toast.makeText(SimpleCamera.this, "上传成功", Toast.LENGTH_SHORT).show();
                    Log.d("SimpleCamera","上传成功"+response.body().string());
                }
            }, file,Email);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        this.finish();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Intent intent = new Intent();
        intent.putExtra("result", Error.ERROR);
        setResult(1,intent);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        update_head_image(result);
        Intent intent = new Intent();
        intent.putExtra("result", Successful.SUCCEED);
        intent.putExtra("image_url",result.getImage().getCompressPath());
        setResult(0,intent);
        //this.finish();
    }

}
