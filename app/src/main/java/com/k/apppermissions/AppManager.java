package com.k.apppermissions;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AppManager {

    private PackageManager pm;


    public AppManager(Context context){

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
            PermissionInfo info = pm.getPermissionInfo(permissionsInfo, PackageManager.GET_META_DATA);
            if (info.loadDescription(pm) != null) {
                Toast.makeText(context, info.loadDescription(pm), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, permissionsInfo + " is Unknown permission...", Toast.LENGTH_SHORT).show();
            }
        } catch (PackageManager.NameNotFoundException e){
            Toast.makeText(context, permissionsInfo + " is Unknown permission....", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

}
