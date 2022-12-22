package cc.liyaya.helloworld;

import android.app.Application;
import android.content.Context;

/*
 *@ClassName MyApplication
 *@Description Application控制器
 *@Author B1GGersnow
 *@Date 2022/10/16 17:52
 *@Version 1.0
 **/
public class MyApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        
    }
}