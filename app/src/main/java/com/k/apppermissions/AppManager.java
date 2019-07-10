package com.k.apppermissions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;


public class AppManager {

    private PackageManager pm;
    private PermissionInfo info;


    public AppManager() {
        super();
    }

    public List<AppInfo> getAppInfos(Context context) {
        List<AppInfo> listApps = new ArrayList<>();
        List<ApplicationInfo> applicationInfos;
        pm = context.getPackageManager();

        applicationInfos = pm.getInstalledApplications(0);

        for (ApplicationInfo info : applicationInfos){
            AppInfo appInfo = new AppInfo();

            Drawable icon = info.loadIcon(pm);
            appInfo.setApp_icon(icon);
            String appName = info.loadLabel(pm).toString();
            appInfo.setApp_name(appName);

            String packName = info.packageName;
            appInfo.setPack_name(packName);

            try{
                PackageInfo packageInfo = pm.getPackageInfo(packName, PackageManager.GET_PERMISSIONS);
                String[] permissions = packageInfo.requestedPermissions;


                if (permissions != null) {
                    for (int i = 0; i < permissions.length; i++) {
                        appInfo.setAppPermissions(permissions);
                    }
                }

            } catch (PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }

            listApps.add(appInfo);

        }

        return listApps;
    }

    public void showPermissionsInfo(Context context, String permissionsInfo){


        try {
            info = pm.getPermissionInfo(permissionsInfo, PackageManager.GET_META_DATA);
            if (info.loadLabel(pm).equals(permissionsInfo)) {
                Toast.makeText(context, info.loadLabel(pm), Toast.LENGTH_SHORT).show();
                Log.d("APPManager info: ", info.name + " + " + info.loadDescription(pm));
            } else {
                //Toast.makeText(context, info.loadDescription(pm), Toast.LENGTH_SHORT).show();
                AlertDialog showPermission = new AlertDialog.Builder(context).create();

                showPermission.setTitle(info.loadLabel(pm));
                showPermission.setMessage(info.loadDescription(pm));
                showPermission.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                showPermission.show();
            }
        } catch (PackageManager.NameNotFoundException e){
            Toast.makeText(context, permissionsInfo, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }
}