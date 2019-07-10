package com.k.apppermissions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AppInfo> appInfos = new ArrayList<>();
    private ExpandableListView expandableListView;
    private MyExtenableListViewAdapter adapter;
    private AppManager appManager = new AppManager();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = findViewById(R.id.listapp);

        if (appInfos != null) {
            appInfos.clear();
            appInfos = appManager.getAppInfos(MainActivity.this);
        }

        if (appInfos != null) {
            adapter = new MyExtenableListViewAdapter(getApplicationContext(), appInfos);
        }

        expandableListView.setAdapter(adapter);

        adapter.refresh();

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                appManager.showPermissionsInfo(MainActivity.this, appInfos.get(groupPosition).getAppPermissions()[childPosition]);

                return true;
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int i, long l) {
                if (appInfos.get(i).getAppPermissions() == null || parent.isGroupExpanded(i)){
                    return  false;
                }


                //parent.collapseGroup(i);
                parent.expandGroup(i);
                //parent.expandGroup(i);

                return true;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = adapter.getGroupCount();
                for (int i = 0; i < count; i++){
                    if (groupPosition != i) {
                        expandableListView.collapseGroup(i);
                        //expandableListView.expandGroup(i);
                        //adapter.refresh();
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                //Toast.makeText(this, "version:1.0", Toast.LENGTH_SHORT).show();
                final AlertDialog about = new AlertDialog.Builder(MainActivity.this).create();
                about.setTitle("关于");
                about.setMessage("版本:1.01,       作者: K");
                about.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        about.dismiss();
                    }
                });
                about.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}