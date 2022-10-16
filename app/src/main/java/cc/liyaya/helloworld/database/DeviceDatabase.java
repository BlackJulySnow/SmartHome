package cc.liyaya.helloworld.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import cc.liyaya.helloworld.dao.DeviceDao;
import cc.liyaya.helloworld.model.Device;

@Database(entities = {Device.class}, version = 1, exportSchema = false)
public abstract class DeviceDatabase extends RoomDatabase {
    public abstract DeviceDao deviceDao();
    private static final String DB_NAME = "AppDatabase.db";
    private static volatile DeviceDatabase instance;

    public static synchronized DeviceDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static DeviceDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                DeviceDatabase.class,
                DB_NAME).allowMainThreadQueries().build();
    }
}
