package cc.liyaya.helloworld.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cc.liyaya.helloworld.databinding.ActivityInfoBinding;

/*
 *@ClassName UserInfoActivity
 *@Description 用户信息页面
 *@Author B1GGersnow
 *@Date 2022/10/16 17:49
 *@Version 1.0
 **/
public class UserInfoActivity extends AppCompatActivity {
    private ActivityInfoBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = binding.toolbarInfo;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
