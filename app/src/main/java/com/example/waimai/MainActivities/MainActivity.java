package com.example.waimai.MainActivities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waimai.Fragments.Main1;
import com.example.waimai.Fragments.Main3;
import com.example.waimai.R;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private Context context;
    private TextView home,indent,account;

    private Main1 main1; //首页碎片
    private Main3 main3; //账户碎片

    private FragmentTransaction transaction;//碎片事务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_layout);
        initView();

        transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        if(main1 == null){
            main1 = new Main1(context);
            transaction.add(R.id.fl_main,main1);
        } else {
            transaction.show(main1);
        }
        transaction.commit();
    }

    /**
     * 初始化控件
     */
    private void initView(){
        context = this;
        home = (TextView)findViewById(R.id.tv_home);
        indent = (TextView)findViewById(R.id.tv_indent);
        account = (TextView)findViewById(R.id.tv_account);
        change();
        home.setOnClickListener(this);
        indent.setOnClickListener(this);
        account.setOnClickListener(this);
    }

    /**
     * 隐藏所有的碎片
     * @param transaction 碎片的事物管理
     */
    public void hideAllFragment(FragmentTransaction transaction){
        if(main1!=null){
            transaction.hide(main1);
        }
    }


    /**
     * 将所有的导航的按钮设置未不选中
     */
    public void selected(){
        home.setSelected(false);
        indent.setSelected(false);
        account.setSelected(false);
    }

    /**
     * 改变底部导航栏图片大小
     */
    public void change(){
        Drawable dra1= getResources().getDrawable(R.mipmap.ic_launcher_home);
        dra1.setBounds( 0, 0, 80,80);
        home.setCompoundDrawables(null,dra1,null,null);
        Drawable dra2= getResources().getDrawable(R.mipmap.ic_launcher_indent);
        dra2.setBounds( 0, 0, 80,80);
        indent.setCompoundDrawables(null,dra2,null,null);
        Drawable dra3= getResources().getDrawable(R.mipmap.ic_launcher_account);
        dra3.setBounds( 0, 0, 80,80);
        account.setCompoundDrawables(null,dra3,null,null);
    }

    @Override
    public void onClick(View view) {
        transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(view.getId()){
            case R.id.tv_home:
                if(main1 == null){
                    main1 = new Main1(context);
                    transaction.add(R.id.fl_main,main1);
                } else {
                    transaction.show(main1);
                }

                break;
            case R.id.tv_indent:
                break;
            case R.id.tv_account:
                if(main3 == null){
                    main3 = new Main3(context);
                    transaction.add(R.id.fl_main,main3);
                } else {
                    transaction.show(main3);
                }
                break;
        }
        transaction.commit();
    }
}
