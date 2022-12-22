package cc.liyaya.helloworld.thread;

import android.content.Context;

import java.util.List;

import cc.liyaya.helloworld.database.DatabaseUsage;
import cc.liyaya.helloworld.model.Device;

/*
 *@ClassName Query
 *@Description 查询线程
 *@Author B1GGersnow
 *@Date 2022/10/16 17:47
 *@Version 1.0
 **/
public class Query extends Thread{
    private List<Device> devices;
    private Context context;
    public Query(Context context){
        this.context = context;
    }

    public List<Device> getDevices() {
        return devices;
    }

    @Override
    public void run() {
        this.devices = DatabaseUsage.getInstance(context).deviceDao().getAll();

    }
}