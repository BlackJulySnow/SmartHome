package cc.liyaya.helloworld.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import cc.liyaya.helloworld.R;
import cc.liyaya.helloworld.databinding.ActivityMainBinding;
import cc.liyaya.helloworld.ui.WebGet;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private BottomNavigationView navView;
    public static ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        initBottomNavigationView();



    }

    public void initBottomNavigationView() {
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_center)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
//            if (navDestination.getId() == R.id.navigation_center){
//                getSupportActionBar().hide();
//            }else {
//                getSupportActionBar().show();
//            }
//        });
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
//        navView.setOnItemSelectedListener((NavigationBarView.OnItemSelectedListener) item -> {
//            if (item.getItemId() == R.id.navigation_dashboard){
//                Toast.makeText(getApplicationContext(),"打开个人中心",Toast.LENGTH_SHORT).show();
//            }
//            return true;
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}