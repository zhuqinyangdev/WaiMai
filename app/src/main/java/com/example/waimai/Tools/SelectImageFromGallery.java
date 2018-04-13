package com.example.waimai.Tools;

import android.content.Intent;
import android.os.Bundle;

import com.example.waimai.Fields.Error;
import com.example.waimai.Fields.Successful;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;


public class SelectImageFromGallery extends TakePhotoActivity {
    public static final String APSPECTX = "AspectX";
    public static final String APSPECTY = "AspectY";
    public static final String OUTPUTX = "OutputX";
    public static final String OUTPUTY = "OutputY";
    //宽高比
    private int aspectX,aspectY;
    //宽高长
    private int outputX,outputY;

    //用于后来去图片的地址
    private static final String IMAGE_URI = "Image_Uri";

    private TakePhoto takePhoto;
    /**
     * @Email 用户名
     * @page 由哪张页面调用的
     */
    private String Email,page;

    private CropOptions.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获得宽高比
        Intent intent = getIntent();
        try{
            aspectX = Integer.parseInt(intent.getExtras().getString(this.APSPECTX,"-1"));
            aspectY = Integer.parseInt(intent.getExtras().getString(this.APSPECTY,"-1"));
            outputX = Integer.parseInt(intent.getExtras().getString(this.OUTPUTX,"-1"));
            outputY = Integer.parseInt(intent.getExtras().getString(this.OUTPUTY,"-1"));
        }catch (Exception e){
            e.printStackTrace();
        }
        init();
        start();
    }

    private void init(){
        takePhoto = this.getTakePhoto();
        CompressConfig config = new CompressConfig.Builder()
                .enableReserveRaw(true)  //是否储存原图
                .create();
        takePhoto.onEnableCompress(config, false);
        builder = new CropOptions.Builder();
        if(aspectX != -1 && aspectY != -1){
            builder.setAspectX(aspectX);
            builder.setAspectY(aspectY);
        }
        if(outputX != -1 && outputY != -1){
            builder.setOutputX(outputX);
            builder.setOutputY(outputY);
        }
        TakePhotoOptions.Builder builder1 = new TakePhotoOptions.Builder();
        builder1.setWithOwnGallery(true);
        takePhoto.setTakePhotoOptions(builder1.create());
    }

    private void start(){
        takePhoto.onPickMultipleWithCrop(1,builder.create());
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Intent intent = new Intent();
        intent.putExtra("result", Successful.SUCCEED);
        intent.putExtra(SelectImageFromGallery.IMAGE_URI, result.getImage().getCompressPath());
        setResult(0, intent);
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
    public void takeCancel() {
        super.takeCancel();
        Intent intent = new Intent();
        intent.putExtra("result", Error.CANCEL);
        setResult(2, intent);
        this.finish();
    }
}
