package cc.liyaya.helloworld.thread;

import android.content.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cc.liyaya.helloworld.database.DeviceDatabase;
import cc.liyaya.helloworld.model.Device;

public class Insert extends Thread{
    private Context context;
    public Insert(Context context){
        this.context = context;
    }
    @Override
    public void run() {
        Device device = new Device();
        device.setIp("192.168.1." + (int)(Math.random() * 1000) % 255);
        switch ((int)(Math.random() * 1000) % 3){
            case 0:
                device.setRoom("卧室");
                break;
            case 1:
                device.setRoom("客厅");
                break;
            case 2:
                device.setRoom("大门");
                break;
        }
        switch ((int)(Math.random() * 1000) % 3){
            case 0:
                device.setName("摄像头");
                break;
            case 1:
                device.setName("开关");
                break;
            case 2:
                device.setName("空调");
                break;
        }
        device.setStatus((int) (Math.random() * 1000) % 2 == 0);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        device.setTime(dtf.format(LocalDateTime.now()));
        device.setType((int) (Math.random() * 1000) % 5);
        DeviceDatabase.getInstance(context).deviceDao().insert(device);

//        User user=new User();
//        user.setFirstName(String.valueOf(Math.random()));
//        user.setLastName(String.valueOf(Math.random()));
//        UserDatabase.getInstance(context).userDao().insert(user);
    }
}
