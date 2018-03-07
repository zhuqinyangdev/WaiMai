package com.example.waimai.Tools;

import android.content.Intent;
import android.os.Bundle;

import com.example.waimai.Fields.Error;
import com.example.waimai.Fields.Successful;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.model.TResult;


public class SelectImageFromGallery extends TakePhotoActivity {

    private static final String IMAGE_URI = "Image_Uri";

    private TakePhoto takePhoto;
    /**
     * @Email 用户名
     * @page 由哪张页面调用的
     */
    private String Email,page;

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
        takePhoto.onPickMultiple(1);
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
