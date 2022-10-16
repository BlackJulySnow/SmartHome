package cc.liyaya.helloworld.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

import cc.liyaya.helloworld.MyApplication;
import cc.liyaya.helloworld.R;
import cc.liyaya.helloworld.ui.activity.MainActivity;
import cc.liyaya.helloworld.databinding.FragmentCenterBinding;
import cc.liyaya.helloworld.ui.activity.UserInfoActivity;

public class CenterFragment extends Fragment {

    private FragmentCenterBinding binding;
    private String TAG = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCenterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initMenu();
        binding.centerCardviewInfo.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), UserInfoActivity.class);
            startActivity(intent);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        Log.e(TAG,"onDestroyView");
    }

    public void initMenu(){


        getActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                ActionBar actionBar = MainActivity.actionBar;
                actionBar.setBackgroundDrawable(MyApplication.getContext().getDrawable(R.color.white));
                actionBar.setTitle("");
                menu.clear();
            }
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return true;
            }
        });
    }

}