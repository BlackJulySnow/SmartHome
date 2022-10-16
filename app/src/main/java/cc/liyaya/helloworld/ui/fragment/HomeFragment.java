package cc.liyaya.helloworld.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.common.Barcode;

import java.util.Date;
import java.util.List;

import cc.liyaya.helloworld.MyApplication;
import cc.liyaya.helloworld.R;
import cc.liyaya.helloworld.ui.activity.MainActivity;
import cc.liyaya.helloworld.adapter.DeviceAdapter;
import cc.liyaya.helloworld.dao.DeviceDao;
import cc.liyaya.helloworld.database.DeviceDatabase;
import cc.liyaya.helloworld.databinding.FragmentHomeBinding;
import cc.liyaya.helloworld.model.Device;
import cc.liyaya.helloworld.ui.activity.ScanActivity;
import cc.liyaya.helloworld.utils.TimeUtils;

public class HomeFragment extends Fragment{

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private DeviceAdapter deviceAdapter;
    private DeviceDao deviceDao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.deviceRecycle;
        deviceDao = DeviceDatabase.getInstance(getContext()).deviceDao();

        initMenu();
        initRecycleView();

        return root;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initRecycleView(){
        List<Device> list = deviceDao.getAll();
        //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        //创建适配器，将数据传递给适配器
        deviceAdapter = new DeviceAdapter(list);
        //设置布局管理器
        recyclerView.setLayoutManager(mLayoutManager);
        //设置适配器adapter
        recyclerView.setAdapter(deviceAdapter);
    }



    public void initMenu(){

        getActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                ActionBar actionBar = MainActivity.actionBar;
                actionBar.setBackgroundDrawable(MyApplication.getContext().getDrawable(R.color.blue));
                actionBar.setTitle("家庭设备");
                menu.clear();
                menuInflater.inflate(R.menu.toolbar_home_menu,menu);
            }
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_home_add_device:
                        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_device_add,null);
                        final EditText nameView = dialogView.findViewById(R.id.dialog_device_name);
                        final EditText IPView = dialogView.findViewById(R.id.dialog_device_ip);
                        final Spinner roomView = dialogView.findViewById(R.id.dialog_device_room);
                        final Spinner typeView = dialogView.findViewById(R.id.dialog_device_type);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("添加设备").setIcon(R.drawable.ic_device).setView(dialogView)
                                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
                        builder.setPositiveButton("确定", (dialog, which) -> {
                            Device device = new Device();
                            device.setName(nameView.getText().toString());
                            device.setIp(IPView.getText().toString());
                            device.setRoom(roomView.getSelectedItem().toString());
                            device.setType(typeView.getSelectedItemPosition());
                            device.setTime(TimeUtils.dateToStr(new Date()));
                            device.setStatus(false);
                            Toast.makeText(getContext(),roomView.getSelectedItem().toString() + typeView.getSelectedItem().toString() + "添加成功", Toast.LENGTH_SHORT).show();
                            device.setId(deviceDao.insert(device));
                            deviceAdapter.append(device);
                        });
                        builder.show();
                        return true;
                    case R.id.action_home_refresh:
                        deviceAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.action_home_scan:
                        Toast.makeText(getContext(),"扫描局域网设备", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_home_scan_qrcode:
                        Intent intent = new Intent(getActivity(), ScanActivity.class);
                        startActivity(intent);
                        Toast.makeText(getContext(),"二维码添加设备", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
    }
}