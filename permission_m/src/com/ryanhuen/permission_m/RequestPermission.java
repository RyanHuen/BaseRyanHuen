
package com.ryanhuen.permission_m;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by ryanhuenwork on 16-10-13. Request Permission in Marshmallow or
 * higher
 */
public class RequestPermission {
    public static final int CODE_FOR_MULTIPLE_PERMISSION = 10;
    private static RequestPermission sInstance;
    private OnPermissionGranted onPermissionGranted;
    private Activity mActivity;

    public void setOnPermissionGranted(OnPermissionGranted onPermissionGranted) {
        this.onPermissionGranted = onPermissionGranted;
    }

    private RequestPermission(Activity activity) {
        mActivity = activity;
    }

    public static RequestPermission getsInstance(Activity activity) {
        if (sInstance == null) {
            synchronized (RequestPermission.class) {
                if (sInstance == null) {
                    sInstance = new RequestPermission(activity);
                }
            }
        }
        return sInstance;
    }

    public void verifyPermissions(Activity activity) {
        List<String> permissionsNeeded = new ArrayList<>();
        Set<String> CONFIG_PERMISSION_LIST = PermissionConfig.getPermissionList();

        if (Build.VERSION.SDK_INT >= 23) {
            for (String perm : CONFIG_PERMISSION_LIST) {
                // Check if we have write permission
                int permission = ActivityCompat.checkSelfPermission(activity, perm);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    permissionsNeeded.add(perm);
                }
            }
        }

        if (permissionsNeeded.isEmpty()) {
            if (onPermissionGranted != null) {
                onPermissionGranted.onPermissionGranted();
            } else {
                throw new RuntimeException(
                        "It seems your welcome activity didn't implements OnPermissionGranted");
            }
        } else {
            ActivityCompat.requestPermissions(
                    activity,
                    permissionsNeeded.toArray(new String[permissionsNeeded.size()]),
                    CODE_FOR_MULTIPLE_PERMISSION);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) {
        if (requestCode == CODE_FOR_MULTIPLE_PERMISSION) {
            for (int ret : grantResults) {
                if (ret != PackageManager.PERMISSION_GRANTED) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setMessage("应用需要授权后才能使用,您可以在设置中给应用授权!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                final Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                final Uri uri = Uri.fromParts("package", mActivity.getPackageName(),
                                        null);
                                intent.setData(uri);
                                mActivity.startActivity(intent);
                                System.exit(0);
                            } catch (final Exception e) {
                                Toast.makeText(mActivity, "打开设置失败", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
                    builder.setNegativeButton("不用了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    });

                    builder.create();
                    builder.show();

                    return;
                }
            }
            if (onPermissionGranted != null) {
                onPermissionGranted.onPermissionGranted();
            } else {
                throw new RuntimeException(
                        "It seems your welcome activity didn't implements OnPermissionGranted");
            }
        }
    }
}
