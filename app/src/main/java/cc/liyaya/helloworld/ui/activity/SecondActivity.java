package cc.liyaya.helloworld.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;
import cc.liyaya.helloworld.R;
import cc.liyaya.helloworld.adapter.DeviceAdapter;
import cc.liyaya.helloworld.database.DatabaseUsage;
import cc.liyaya.helloworld.model.Device;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button button = findViewById(R.id.second_button);
        button.setOnClickListener(view -> {
            finish();
        });
        RecyclerView recyclerView = findViewById(R.id.device_recycle);
        List<Device> list= DatabaseUsage.getInstance(getApplicationContext()).deviceDao().getAll();
        //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        //创建适配器，将数据传递给适配器
        DeviceAdapter mAdapter = new DeviceAdapter(list);
        //设置布局管理器
        recyclerView.setLayoutManager(mLayoutManager);
        //设置适配器adapter
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
