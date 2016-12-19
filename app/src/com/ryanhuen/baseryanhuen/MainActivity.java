
package com.ryanhuen.baseryanhuen;

import com.ryanhuen.permission_m.OnPermissionGranted;
import com.ryanhuen.permission_m.PermissionConfig;
import com.ryanhuen.permission_m.RequestPermission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnPermissionGranted {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermission();
    }

    private void initPermission() {
        //add your permission list
        String[] permissionList = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE
        };
        PermissionConfig.addToPermissionList(permissionList);
        RequestPermission.getsInstance(this).setOnPermissionGranted(this);
        RequestPermission.getsInstance(this).verifyPermissions(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        RequestPermission.getsInstance(this).onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionGranted() {
        //TODO : init your application right here
        init();
    }

    private void init() {
        setContentView(R.layout.activity_main);
    }
}
