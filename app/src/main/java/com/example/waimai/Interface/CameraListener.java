package com.example.waimai.Interface;

import com.jph.takephoto.model.TResult;

/**
 * Created by zhuqinyang on 18-2-3.
 */

public interface CameraListener {
    public void takeSuccess(TResult result);
    public void takeFail(TResult result, String msg);
    public void takeCancel();
}
