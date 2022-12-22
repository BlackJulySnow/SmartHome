package cc.liyaya.helloworld.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import cc.liyaya.helloworld.dao.DeviceDao;
import cc.liyaya.helloworld.dao.UserDao;
import cc.liyaya.helloworld.model.Device;
import cc.liyaya.helloworld.model.User;

/*
 *@ClassName DatabaseUsage
 *@Description 数据库实例化类
 *@Author B1GGersnow
 *@Date 2022/10/16 17:52
 *@Version 1.0
 **/
@Database(entities = {Device.class, User.class}, version = 1, exportSchema = false)
public abstract class DatabaseUsage extends RoomDatabase {
    public abstract DeviceDao deviceDao();
    public abstract UserDao userDao();
    private static final String DB_NAME = "AppDatabase.db";
    private static volatile DatabaseUsage instance;

    public static synchronized DatabaseUsage getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static DatabaseUsage create(final Context context) {
        return Room.databaseBuilder(
                context,
                DatabaseUsage.class,
                DB_NAME).allowMainThreadQueries().build();
    }
}
