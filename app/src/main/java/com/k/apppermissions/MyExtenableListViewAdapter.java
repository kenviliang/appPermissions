package com.k.apppermissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import static android.content.pm.PackageManager.*;

public class MyExtenableListViewAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<AppInfo> appInfos;
    private Handler mHandler;


    public MyExtenableListViewAdapter(Context context, List<AppInfo> appInfos) {
        this.appInfos = appInfos;
        this.mContext = context;
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                notifyDataSetChanged();
                return true;
            }
        });
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
        return getCombinedChildId(groupPosition, childPosition);
    }


    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return super.getCombinedChildId(groupId, childId);
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //AppInfoViewHolder appInfoViewHolder;
/*
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.parent_item, parent, false);

            appInfoViewHolder = new AppInfoViewHolder();
            appInfoViewHolder.appName =  (TextView) convertView.findViewById(R.id.appName);
            appInfoViewHolder.permissionscount = (TextView) convertView.findViewById(R.id.permissioncount);
            appInfoViewHolder.appIcon = (ImageView) convertView.findViewById(R.id.appIcon);
            convertView.setTag(appInfoViewHolder);
        } else {
            appInfoViewHolder = (AppInfoViewHolder) convertView.getTag();
        }
*/

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.parent_item, parent, false);
        }

        ImageView appIcon = (ImageView) ViewHolder.get(convertView, R.id.appIcon);
        TextView appName = (TextView) ViewHolder.get(convertView, R.id.appName);
         TextView appCount = (TextView) ViewHolder.get(convertView, R.id.permissioncount);

         appIcon.setImageDrawable(appInfos.get(groupPosition).getApp_icon());
         appName.setText(appInfos.get(groupPosition).getApp_name());
         appCount.setText(String.valueOf(getChildrenCount(groupPosition)));

/*
        appInfoViewHolder.appIcon.setImageDrawable(appInfos.get(groupPosition).getApp_icon());
        appInfoViewHolder.appName.setText(appInfos.get(groupPosition).getApp_name());
        appInfoViewHolder.permissionscount.setText(String.valueOf(getChildrenCount(groupPosition)));
*/
        return convertView;
    }

    //@SuppressLint("WrongConstant")
    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PackageManager pm;
        final PermissionInfo info;
        //AppPermissionViewHolder appPermissionViewHolder;
/*
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.child_item, parent, false);

            appPermissionViewHolder = new AppPermissionViewHolder();
            appPermissionViewHolder.appPermission = (TextView) convertView.findViewById(R.id.appPermission);
            appPermissionViewHolder.constraintLayout = (ConstraintLayout) convertView.findViewById(R.id.childItem);
            appPermissionViewHolder.aSwitch = (Switch) convertView.findViewById(R.id.switch1);
            convertView.setTag(appPermissionViewHolder);
        } else {
            appPermissionViewHolder = (AppPermissionViewHolder) convertView.getTag();
        }

        if (appInfos != null){
            String aPermission = appInfos.get(groupPosition).getAppPermissions()[childPosition];
            String packname = appInfos.get(groupPosition).getPack_name();
            pm = mContext.getPackageManager();
            int color = appPermissionViewHolder.constraintLayout.getSolidColor();

            String dis = "无权限识别或者自定义权限" + "\n" + "(" + aPermission + ")";
            //String text = info.loadLabel(pm)+"\n" + "(" + aPermission + ")";

            if (pm.checkPermission(aPermission, packname) == PERMISSION_GRANTED){
                appPermissionViewHolder.aSwitch.setChecked(true);
            } else {
                appPermissionViewHolder.aSwitch.setChecked(false);
            }

            //appPermissionViewHolder.aSwitch.setClickable(true);

            //appPermissionViewHolder

            try {
                info = pm.getPermissionInfo(aPermission, GET_META_DATA);
                String permission = info.loadLabel(pm)+"\n" + "(" + aPermission + ")";
                //String dis = "无权限识别或者自定义权限" + "\n" + "(" + aPermission + ")";


                if (info.loadLabel(pm).equals(aPermission)){
                    appPermissionViewHolder.appPermission.setText(dis);
                } else {
                    appPermissionViewHolder.appPermission.setText(permission);
                }

                if (Build.VERSION.SDK_INT > 27){
                    if (info.getProtection() == PermissionInfo.PROTECTION_DANGEROUS){
                        appPermissionViewHolder.constraintLayout.setBackgroundColor(Color.RED);
                    } else {
                        appPermissionViewHolder.constraintLayout.setBackgroundColor(color);
                    }
                } else if (Build.VERSION.SDK_INT >= 23) {
                    if (info.protectionLevel == PermissionInfo.PROTECTION_DANGEROUS) {
                        appPermissionViewHolder.constraintLayout.setBackgroundColor(Color.RED);
                    } else {
                        appPermissionViewHolder.constraintLayout.setBackgroundColor(color);
                    }
                }

            } catch (NameNotFoundException e){
                appPermissionViewHolder.appPermission.setText(dis);
                appPermissionViewHolder.constraintLayout.setBackgroundColor(color);
                e.printStackTrace();
            }

        }
*/

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.child_item, parent, false);
        }

        TextView appPermissions = (TextView) ViewHolder.get(convertView, R.id.appPermission);
        Switch switch1 = (Switch) ViewHolder.get(convertView, R.id.switch1);
        ConstraintLayout constraintLayout = (ConstraintLayout) ViewHolder.get(convertView, R.id.childItem);


        if (appInfos != null ) {
            String aPermissions = appInfos.get(groupPosition).getAppPermissions()[childPosition];
            String packname = appInfos.get(groupPosition).getPack_name();
            pm = mContext.getPackageManager();

            if (pm.checkPermission(aPermissions, packname) == PERMISSION_GRANTED){
                switch1.setChecked(true);
            } else {
                switch1.setChecked(false);
            }

            //String dis = "无权限识别或者自定义权限" + "\n" + aPermissions;
            int color = constraintLayout.getSolidColor();

            try {
                info = pm.getPermissionInfo(aPermissions, PackageManager.GET_META_DATA);
                String text = info.loadLabel(pm)+"\n" + "(" + aPermissions + ")";

                if (info.loadLabel(pm).equals(aPermissions)) {
                    appPermissions.setText(aPermissions);
                } else {
                    appPermissions.setText(text);
                }

                //int color = constraintLayout.getSolidColor();
                if (Build.VERSION.SDK_INT > 27){
                    if (info.getProtection() == PermissionInfo.PROTECTION_DANGEROUS){
                        constraintLayout.setBackgroundColor(Color.RED);
                    } else if (info.getProtection() == PermissionInfo.PROTECTION_SIGNATURE){
                        constraintLayout.setBackgroundColor(Color.GRAY);
                    } else {
                        constraintLayout.setBackgroundColor(color);
                    }
                } if (Build.VERSION.SDK_INT >= 23) {
                    if (info.protectionLevel == PermissionInfo.PROTECTION_DANGEROUS){
                        constraintLayout.setBackgroundColor(Color.RED);
                    } else if (info.protectionLevel == PermissionInfo.PROTECTION_SIGNATURE || info.protectionLevel == PermissionInfo.PROTECTION_FLAG_PRIVILEGED){
                        constraintLayout.setBackgroundColor(Color.GRAY);
                    } else {
                        constraintLayout.setBackgroundColor(color);
                    }
                } //else {
                //    constraintLayout.setBackgroundColor(Color.GRAY);
                //}
            } catch (PackageManager.NameNotFoundException e){
                appPermissions.setText(aPermissions);
                constraintLayout.setBackgroundColor(color);
                e.printStackTrace();
            }
        }



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void refresh() {
        mHandler.sendMessage(new Message());

    }

/*
    static class AppInfoViewHolder {
        TextView appName;
        TextView permissionscount;
        ImageView appIcon;
    }

    static class AppPermissionViewHolder {
        TextView appPermission;
        Switch aSwitch;
        ConstraintLayout constraintLayout;
    }
*/

    public static class ViewHolder {

        public ViewHolder() {
            super();
        }

        @SuppressWarnings("unchecked")
        static View get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }

            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }

            return childView;
        }

    }

}