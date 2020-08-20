package com.digitalchina.myfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.digitalchina.myfragment.Fragment.Fragment1;
import com.digitalchina.myfragment.Fragment.Fragment2;
import com.digitalchina.myfragment.Fragment.Fragment3;

public class MainActivity extends AppCompatActivity   implements RadioGroup.OnCheckedChangeListener {


    //
    private Fragment1 fragment1;
    private Fragment2 fragment2 ;
    private Fragment3 fragment3;

    private RadioGroup radioGroup;
    private RadioButton contactBtn,messageBtn,profileBtn;

    private FragmentManager fragmentManager;
    private FrameLayout content;

    //service组件
    private Button button1,button2,button3,button4;
    private ServiceConnection connection;
    private int a = 1;
    //打印日志的tag
    String tag = "renjwb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentManager = getSupportFragmentManager();
        initView();
        initData();
        //service服务
        button1 = findViewById(R.id.main_bt1);
        button2 = findViewById(R.id.main_bt2);
        button3 = findViewById(R.id.main_bt3);
        button4 = findViewById(R.id.main_bt4);

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d(tag, "与服务产生连接");
                MyService.MyBind myBind =(MyService.MyBind) iBinder;
                int a = myBind.a;
                Log.d(tag, "service处理完后的a的值是"+a+" "+componentName.toString());
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d(tag, "与服务断开连接");
            }
        };

        //启动service 采用startService方式
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //指明启动的服务
                Intent intent = new Intent();
                intent.setAction("android.intent.action.myservice");
                intent.setPackage(getPackageName());
                //启动服务
                MainActivity.this.startService(intent);
            }
        });

        //关闭service
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //指明关闭的服务
                Intent intent=new Intent();
                intent.setAction("android.intent.action.myservice");
                intent.setPackage(getPackageName());
                //关闭服务
                MainActivity.this.stopService(intent);
            }
        });

        //bind方式启动服务
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //指明打开的服务
                Intent intent=new Intent();
                intent.setAction("android.intent.action.myservice");
                intent.putExtra("myparam", a);
                intent.setPackage(getPackageName());
                //启动服务
                MainActivity.this.bindService(intent, connection, Service.BIND_AUTO_CREATE);

            }
        });

        //unbind关闭service
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //指明关闭的服务
                Intent intent=new Intent();
                intent.setAction("android.intent.action.myservice");
                intent.setPackage(getPackageName());
                //关闭服务
                MainActivity.this.unbindService(connection);
            }
        });

    }

    public void initView() {
        radioGroup = findViewById(R.id.radioGroup);
        content = findViewById(R.id.fragment);
        contactBtn = findViewById(R.id.radioBtn1);
        messageBtn = findViewById(R.id.radioBtn2);
        profileBtn = findViewById(R.id.radioBtn3);
        radioGroup.setOnCheckedChangeListener(this);

/*
       Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_tab_contact,null);
            drawable.setBounds(10,10,40,40);
            contactBtn.setCompoundDrawables(null,drawable,null,null);*/
      /*  Drawable drawable1 = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_tab_contact_h,null);
        drawable1.setBounds(0,0,50,50);
        contactBtn.setCompoundDrawables(null,drawable1,null,null);*/

    }

    public void initData() {
        //1-创建fragment管理器
        fragmentManager = getSupportFragmentManager();
        radioGroup.check(R.id.radioBtn1);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        //2.通过fragment管理器得到fragment事物
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //隐藏所有fragment
        hideAllFragment(fragmentTransaction);

        switch (checkId){
            case R.id.radioBtn1:
                //往事务添加fragment
                if (fragment1==null){
                    fragment1 = new Fragment1();
                    fragmentTransaction.add(R.id.fragment,fragment1);
                }
                fragmentTransaction.show(fragment1);
                break;
            case R.id.radioBtn2:
                //往事务添加fragment
                if (fragment2==null){
                    fragment2 = new Fragment2();
                    fragmentTransaction.add(R.id.fragment,fragment2);
                }
                fragmentTransaction.show(fragment2);
                break;
            case R.id.radioBtn3:
                //往事务添加fragment
                if (fragment3==null){
                    fragment3 = new Fragment3();
                    fragmentTransaction.add(R.id.fragment,fragment3);
                }
                fragmentTransaction.show(fragment3);
                break;
        }
        fragmentTransaction.commit();
    }


    public void hideAllFragment(FragmentTransaction fragmentTransaction){
        if (fragment1!=null)fragmentTransaction.hide(fragment1);
        if (fragment2!=null)fragmentTransaction.hide(fragment2);
        if (fragment3!=null)fragmentTransaction.hide(fragment3);
    }

}
