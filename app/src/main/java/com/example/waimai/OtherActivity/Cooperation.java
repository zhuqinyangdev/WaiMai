package com.example.waimai.OtherActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.waimai.R;
import com.example.waimai.Tools.Tools;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Cooperation extends AppCompatActivity implements View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener {

    /**
     * 活动
     */
    private Intent intent;
    /**
     * store_average_price为平均价,store_counter_price为门市价
     */
    private EditText store_name, store_address, store_phone, store_property, store_type, store_average_price, store_counter_price;
    /**
     * 问号按钮
     */
    private ImageButton store_name_help, store_address_help, store_phone_help, store_property_help, store_type_help, store_average_price_help, store_counter_price_help;

    private Button btn_next;

    /**
     * Longitude:商铺经度, Latitude:商铺纬度
     */
    private String Email, Longitude, Latitude;
    /**
     * 提示框
     */
    private SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_layout);
        init();
        initView();
        initTest();
    }

    private void init() {
        try {
            Intent intent1 = this.getIntent();
            this.Email = intent1.getExtras().getString("Email", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
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
        store_counter_price_help = findViewById(R.id.store_counter_price_help);

    }

    private void initTest() {
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
     * 开始查询该地址的经度和纬度（高德地图SDK）
     */
    private void queryAddress() {
        GeocodeSearch geocoderSearch = new GeocodeSearch(Cooperation.this);
        geocoderSearch.setOnGeocodeSearchListener(Cooperation.this);
        GeocodeQuery query = new GeocodeQuery(store_address.getText().toString(), null);
        geocoderSearch.getFromLocationNameAsyn(query);
    }

    /**
     * 检查必填项是否为空
     */
    private boolean checknull() {
        if (store_name.getText().toString().equals("") || store_address.getText().toString().equals("") || store_phone.getText().toString().equals("") || store_property.getText().toString().equals("") ||
                store_type.getText().toString().equals("") || store_average_price.getText().toString().equals("") || store_counter_price.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    /**
     * 当经纬度获取失败的时候进行提示（在用户单击下一步时会进行查询,并返回相应的结果）
     */
    private void addressfaild() {
        sweetAlertDialog = Tools.showError(Cooperation.this, "提示", "经纬度获取失败！\n请核对你输入的地址是否正确！");
        sweetAlertDialog.setCancelText("取消");
        sweetAlertDialog.setConfirmText("重试");
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                addressfaild();
                sweetAlertDialog.dismiss();
            }
        });
        sweetAlertDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:

                if (checknull()) {
                    queryAddress();
                } else {
                    sweetAlertDialog = Tools.showWarn(Cooperation.this, "提示", "请务必填写完每一项！");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    });
                    sweetAlertDialog.show();
                }
                break;
            case R.id.store_name_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this, "提示", "此处为店铺名请正确填写");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.show();
                break;
            case R.id.store_address_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this, "提示", "此处请填写店铺地址,请输入标准化的地址结构（省/市/区或县/乡/村或社区/商圈/街道/门牌号/POI）进行各个地址级别的匹配");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.show();
                break;
            case R.id.store_phone_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this, "提示", "此处请填写正确的店铺联系电话,顾客可根据电话来联系相应商家");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.show();
                break;
            case R.id.store_property_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this, "提示", "此处请选择相应出现的店铺性质");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.show();
                break;
            case R.id.store_type_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this, "提示", "此处请选择相应出现的店铺类型");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.show();
                break;
            case R.id.store_average_price_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this, "提示", "此处填写此店铺的平均价(平均价不能低于最低的菜谱价格,一经发现将给予严重警告!)");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.show();
                break;
            case R.id.store_counter_price_help:
                sweetAlertDialog = Tools.showBasic(Cooperation.this, "提示", "此处填写门市价(即实体店的价格(非优惠价))");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.show();
                break;
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        try {
            if (i == 1000) {
                Longitude = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLongitude() + "";
                Latitude = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLatitude() + "";
                Log.d("Cooperation", "经度：" + Longitude + " 纬度：" + Latitude);
                /**
                 * 存放给下一个活动的数据
                 */
                intent = new Intent(Cooperation.this, Cooperation2.class);
                intent.putExtra("Email", Email);
                intent.putExtra("StoreLongitude", Longitude);
                intent.putExtra("StoreLatitude", Latitude);
                intent.putExtra("StoreName", store_name.getText().toString());
                intent.putExtra("StoreAddress", store_address.getText().toString());
                intent.putExtra("StorePhone", store_phone.getText().toString());
                intent.putExtra("StoreProperty", store_property.getText().toString());
                intent.putExtra("StoreType", store_type.getText().toString());
                intent.putExtra("MiddleRate", store_average_price.getText().toString());
                intent.putExtra("CounterPrice", store_counter_price.getText().toString());
                startActivity(intent);

            } else {
                Log.d("Cooperation", "经纬度获取失败！"+i);
                addressfaild();
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            addressfaild();
        }
    }
}
