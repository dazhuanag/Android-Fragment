package com.digitalchina.myfragment;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService  extends Service {

    String tag = "renjwb";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(tag,"调用服务的onCreate（）方法");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(tag,"调用了服务的onBind()方法"+getResources().getString(R.string.app_name));
        int a = intent.getIntExtra("myparam",0);
        if (a!=0){
            a++;
        }
        return new MyBind(a) ;
    }
    public class MyBind extends Binder{
        public int a;
        public MyBind(int a) {
            this.a = a;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(tag,"调用了服务的onStartCommand()方法");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(tag,"调用了服务的onUnbind()方法");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(tag,"调用了服务的onDestroy()方法");
        super.onDestroy();
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(tag,"调用了服务的onRebind()方法");
        super.onRebind(intent);
    }
}
