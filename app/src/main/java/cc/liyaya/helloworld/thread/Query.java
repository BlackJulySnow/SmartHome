package cc.liyaya.helloworld.thread;

import android.content.Context;

import java.util.List;

import cc.liyaya.helloworld.database.DeviceDatabase;
import cc.liyaya.helloworld.model.Device;

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
        this.devices = DeviceDatabase.getInstance(context).deviceDao().getAll();

    }
}