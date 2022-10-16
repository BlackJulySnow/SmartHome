package cc.liyaya.helloworld.ui;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebGet extends Thread{
    private Context context;
    public WebGet(Context context) {
        this.context = context;
    }
    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://113.243.100.93:5000/").build();
        try {
            Response response = client.newCall(request).execute();
            Looper.prepare();
            Toast.makeText(context, response.body().string(), Toast.LENGTH_LONG).show();
            Looper.loop();
            response.body();
        } catch (IOException e) {
//            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

//        User user=new User();
//        user.setFirstName("Liu");
//        user.setLastName("rui");
//        UserDatabase.getInstance(context).userDao().insert(user);
//        List<User> allUsers = UserDatabase
//                .getInstance(context)
//                .userDao()
//                .getAll();
//        Log.e("sqliteSize", String.valueOf(allUsers.size()));
    }
}
