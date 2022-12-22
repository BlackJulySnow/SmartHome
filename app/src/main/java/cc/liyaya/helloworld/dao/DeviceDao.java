package cc.liyaya.helloworld.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cc.liyaya.helloworld.model.Device;

/*
 *@ClassName DeviceDao
 *@Description 设备的Dao层
 *@Author B1GGersnow
 *@Date 2022/10/16 17:43
 *@Version 1.0
 **/
@Dao
public interface DeviceDao {
    @Query("SELECT * FROM device")
    List<Device> getAll();

    @Query("SELECT * FROM device WHERE id = :id")
    Device get(String id);

    @Delete
    void delete(Device device);

    @Insert
    void insert(Device... devices);

    @Insert
    long insert(Device device);

    @Update
    void update(Device device);
}
