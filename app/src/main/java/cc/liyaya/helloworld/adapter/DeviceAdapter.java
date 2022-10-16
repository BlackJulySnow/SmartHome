package cc.liyaya.helloworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cc.liyaya.helloworld.MyApplication;
import cc.liyaya.helloworld.R;
import cc.liyaya.helloworld.constant.IconEnum;
import cc.liyaya.helloworld.dao.DeviceDao;
import cc.liyaya.helloworld.database.DeviceDatabase;
import cc.liyaya.helloworld.model.Device;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyHolder> {
    private List<Device> devices;
    private DeviceDao deviceDao;
    public DeviceAdapter(List<Device> devices){
        this.devices = devices;
        deviceDao = DeviceDatabase.getInstance(MyApplication.getContext()).deviceDao();
    }

    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        Device device = devices.get(i);
        holder.deviceText.setText(device.getRoom() + IconEnum.icon_name[device.getType()]);
        holder.deviceTime.setText(device.getTime());
        holder.deviceIcon.setImageResource(IconEnum.icon_id[device.getType()]);
        if (device.isStatus()){
            holder.deviceOpen.setVisibility(View.VISIBLE);
            holder.deviceShutdown.setBackgroundResource(R.drawable.ic_shutdown_red);
        }else{
            holder.deviceOpen.setVisibility(View.INVISIBLE);
            holder.deviceShutdown.setBackgroundResource(R.drawable.ic_shutdown_black);
        }
        /*
         * 配置开关功能
         */
        holder.deviceShutdown.setOnClickListener(view -> {
            if (device.isStatus()){
                holder.deviceOpen.setVisibility(View.INVISIBLE);
                holder.deviceShutdown.setBackgroundResource(R.drawable.ic_shutdown_black);
            }else{
                holder.deviceOpen.setVisibility(View.VISIBLE);
                holder.deviceShutdown.setBackgroundResource(R.drawable.ic_shutdown_red);
            }
            device.setStatus(!device.isStatus());
            DeviceDatabase.getInstance(view.getContext()).deviceDao().update(device);
        });
        /*
         * 编辑功能
         */
        holder.cardView.setOnClickListener(view -> {
            final View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_device_add,null);
            final EditText nameView = dialogView.findViewById(R.id.dialog_device_name);
            final EditText IPView = dialogView.findViewById(R.id.dialog_device_ip);
            final Spinner roomView = dialogView.findViewById(R.id.dialog_device_room);
            final Spinner typeView = dialogView.findViewById(R.id.dialog_device_type);
            nameView.setText(device.getName());
            IPView.setText(device.getIp());
            int k = roomView.getAdapter().getCount();
            for (int j = 0;j < k;j++){
                if (device.getRoom().equals(roomView.getItemAtPosition(j)))
                    roomView.setSelection(j);
            }
            typeView.setSelection(device.getType());

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("编辑" + device.getName()).setIcon(R.drawable.ic_device).setView(dialogView)
                    .setNegativeButton("删除", (dialog, which) -> {
                        //删除数据库里面的内容
                        deviceDao.delete(device);
                        //删除devices里的内容
                        devices.remove(i);
                        //删除动画
                        notifyItemRemoved(i);
                        //调整Position
                        notifyItemRangeChanged(i, getItemCount());
                        dialog.dismiss();
                    });
            builder.setPositiveButton("确定", (dialog, which) -> {
                device.setName(nameView.getText().toString());
                device.setIp(IPView.getText().toString());
                device.setRoom(roomView.getSelectedItem().toString());
                device.setType(typeView.getSelectedItemPosition());
                deviceDao.update(device);
                notifyItemChanged(i);
                Toast.makeText(view.getContext(),roomView.getSelectedItem().toString() + typeView.getSelectedItem().toString() + "修改成功", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
            builder.show();
        });

    }

    public void append(Device device){
        devices.add(device);
        notifyItemInserted(devices.size());
    }

    @Override
    public int getItemCount() {
        if (devices != null)
            return devices.size();
        return 0;
    }
    static class MyHolder extends RecyclerView.ViewHolder {

        TextView deviceText,deviceTime;
        ImageView deviceIcon,deviceOpen;
        ImageButton deviceShutdown;
        CardView cardView;

        public MyHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.device_card);
            deviceText = itemView.findViewById(R.id.device_text);
            deviceTime = itemView.findViewById(R.id.device_time);
            deviceIcon = itemView.findViewById(R.id.device_icon);
            deviceOpen = itemView.findViewById(R.id.device_open);
            deviceShutdown = itemView.findViewById(R.id.device_shutdown);
        }
    }
}
