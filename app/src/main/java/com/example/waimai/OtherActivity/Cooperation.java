package com.example.waimai.OtherActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.waimai.R;
import com.example.waimai.Tools.Tools;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Cooperation extends AppCompatActivity implements View.OnClickListener {

    //store_average_price为平均价,store_counter_price为门市价
    private EditText store_name,store_address,store_phone,store_property,store_type,store_average_price,store_counter_price;

    private ImageButton store_name_help,store_address_help,store_phone_help,store_property_help,store_type_help,store_average_price_help,store_counter_price_help;

    private Button btn_next;

    private String Email;
    //提示框
    private SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_layout);
        init();
        initView();
        initTest();
    }

    private void init(){
        Intent intent = this.getIntent();
        this.Email = intent.getExtras().getString("Email","");
    }

    /**
     * 初始化控件
     */
    private void initView(){
        btn_next = findViewById(R.id.btn_next);
        //文本框空间初始化
        store_name = findViewById(R.id.store_name);
        store_address = findViewById(R.id.store_address);
        store_phone = findViewById(R.id.store_phone);
        store_property = findViewById(R.id.store_property);
        store_type = findViewById(R.id.store_type);
        store_average_price = findViewById(R.id.store_average_price);
        store_counter_price = findViewById(R.id.store_counter_price);
        //图片按钮控件初始化
        store_name_help = findViewById(R.id.store_name_help);
        store_address_help = findViewById(R.id.store_address_help);
        store_phone_help = findViewById(R.id.store_phone_help);
        store_property_help = findViewById(R.id.store_property_help);
        store_type_help = findViewById(R.id.store_type_help);
        store_average_price_help = findViewById(R.id.store_average_price_help);
        store_counter_price_help= findViewById(R.id.store_counter_price_help);

    }

    private void initTest(){
        btn_next.setOnClickListener(this);
        store_name_help.setOnClickListener(this);
        store_address_help.setOnClickListener(this);
        store_phone_help.setOnClickListener(this);
        store_property_help.setOnClickListener(this);
        store_type_help.setOnClickListener(this);
        store_average_price_help.setOnClickListener(this);
        store_counter_price_help.setOnClickListener(this);
    }

    /**
     * 检查必填项是否为空
     */
    private boolean checknull(){
        if(store_name.getText().toString().equals("")||store_address.getText().toString().equals("")||store_phone.getText().toString().equals("")||store_property.getText().toString().equals("")||
                store_type.getText().toString().equals("")||store_average_price.getText().toString().equals("")||store_counter_price.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                if(checknull()) {
                    Intent intent = new Intent(Cooperation.this, Cooperation2.class);
                    intent.putExtra("Email", Email);
                    startActivity(intent);
                } else {
                    sweetAlertDialog = Tools.showWarn(Cooperation.this,"提示","请务必填写完每一项！");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    });
                }
                break;
            case R.id.store_name_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this,"提示","此处为店铺名请正确填写");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                break;
            case R.id.store_address_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this,"提示","此处请填写店铺地址,请输入标准化的地址结构（省/市/区或县/乡/村或社区/商圈/街道/门牌号/POI）进行各个地址级别的匹配");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                break;
            case R.id.store_phone_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this,"提示","此处请填写正确的店铺联系电话,顾客可根据电话来联系相应商家");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                break;
            case R.id.store_property_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this,"提示","此处请选择相应出现的店铺性质");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                break;
            case R.id.store_type_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this,"提示","此处请选择相应出现的店铺类型");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                break;
            case R.id.store_average_price_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this,"提示","此处填写此店铺的平均价(平均价不能低于最低的菜谱价格,一经发现将给予严重警告!)");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                break;
            case R.id.store_counter_price_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this,"提示","此处填写门市价(即实体店的价格(非酷奇上的价))");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                break;
        }
    }
}
