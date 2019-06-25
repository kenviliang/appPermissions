package com.k.apppermissions;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class MyExtenableListViewAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<AppInfo> appInfos;

    public MyExtenableListViewAdapter(Context context, List<AppInfo> appInfos){
        this.appInfos = appInfos;
        this.mContext = context;
    }


    public void flashData(List<AppInfo> data){
        this.appInfos = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return appInfos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String[] permissions = appInfos.get(groupPosition).getAppPermissions();
        if (permissions != null) {
            return permissions.length;
        }
        return 0;

    }

    @Override
    public Object getGroup(int groupPosition) {
        return appInfos.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return appInfos.get(groupPosition).getAppPermissions()[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        AppInfoViewHolder appInfoViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.parent_item, parent, false);
            appInfoViewHolder = new AppInfoViewHolder();
            appInfoViewHolder.appIcon = (ImageView) convertView.findViewById(R.id.appIcon);
            appInfoViewHolder.appName = (TextView) convertView.findViewById(R.id.appName);
            appInfoViewHolder.permissionscount = (TextView) convertView.findViewById(R.id.permissioncount);
            convertView.setTag(appInfoViewHolder);
        } else {
            appInfoViewHolder = (AppInfoViewHolder) convertView.getTag();
        }

        appInfoViewHolder.appIcon.setImageDrawable(appInfos.get(groupPosition).getApp_icon());
        appInfoViewHolder.appName.setText(appInfos.get(groupPosition).getApp_name());
        try {
            appInfoViewHolder.permissionscount.setText(String.valueOf(getChildrenCount(groupPosition)));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        AppPermissionViewHolder appPermissionViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.child_item, parent, false);
            appPermissionViewHolder = new AppPermissionViewHolder();
            appPermissionViewHolder.appPermission = (TextView) convertView.findViewById(R.id.appPermission);
            convertView.setTag(appPermissionViewHolder);
        } else {
            appPermissionViewHolder = (AppPermissionViewHolder) convertView.getTag();
        }

        String aPermissions = appInfos.get(groupPosition).getAppPermissions()[childPosition];

        try {
            appPermissionViewHolder.appPermission.setText(aPermissions);
        } catch (NullPointerException e){
            appPermissionViewHolder.appPermission.setText("No permissions");
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class AppInfoViewHolder{
        TextView appName;
        TextView permissionscount;
        ImageView appIcon;
    }

    static class AppPermissionViewHolder{
        TextView appPermission;
    }
}
