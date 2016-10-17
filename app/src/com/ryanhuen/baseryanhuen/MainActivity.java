
package com.ryanhuen.baseryanhuen;

import com.ryanhuen.permission_m.PermissionConfig;
import com.ryanhuen.permission_m.RequestPermission;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
    }

    private void initPermission() {
        String[] permissionList = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE
        };
        PermissionConfig.addToPermissionList(permissionList);
        RequestPermission.verifyPermissions(this);
    }
}
