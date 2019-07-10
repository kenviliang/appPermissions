package com.k.apppermissions;

import android.graphics.drawable.Drawable;


public class AppInfo {
    private Drawable app_icon;
    private String app_name;
    private String pack_name;
    private String[] appPermissions;
    private String[] AppService;


    public String[] getAppService() {
        return AppService;
    }

    public void setAppService(String[] appService) {
        AppService = appService;
    }

    public AppInfo(){
        super();
    }

    public Drawable getApp_icon() {
        return app_icon;
    }

    public void setApp_icon(Drawable app_icon) {
        this.app_icon = app_icon;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getPack_name() {
        return pack_name;
    }

    public void setPack_name(String pack_name) {
        this.pack_name = pack_name;
    }

    public String[] getAppPermissions() {
        return appPermissions;
    }

    public void setAppPermissions(String[] appPermissions) {
        this.appPermissions = appPermissions;
    }


}