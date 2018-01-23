package com.example.waimai.Tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.example.waimai.Interface.DatePickListener;
import com.example.waimai.MainActivities.MainActivity;

/**
 * Created by 32033 on 2017/12/18.
 */

public class DatePick {
    public static void showDatePick(final Context context, final DatePickListener datePickListener){
        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(context, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                datePickListener.onSelectDate(dateDesc);
            }
        }).textConfirm("确认") //text of confirm button
                .textCancel("取消") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(35) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                .minYear(1990) //min year in loop
                .maxYear(2550) // max year in loop
                .dateChose("1997-04-28") // date chose when init popwindow
                .build();
        pickerPopWin.showPopWin((AppCompatActivity) context);
    }
}
