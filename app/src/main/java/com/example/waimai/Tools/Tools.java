package com.example.waimai.Tools;

import android.content.Context;

import com.example.waimai.LoginAndRegister.EditPassword;
import com.example.waimai.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

/**
 * Created by 32033 on 2018/1/1.
 */

public class Tools {

    public static NiftyDialogBuilder showMessage(Context context,String title,String message){
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder.withTitle(title)
                .withButton1Text("确认")
                .withMessage(message)
                .withTitleColor("#000000")
                .withDividerColor("#ededed")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withDuration(500)
                .withButtonDrawable(R.drawable.button_shape)
                .withEffect(Effectstype.SlideBottom)
                .isCancelableOnTouchOutside(false) ;
        return dialogBuilder;
    }
}
