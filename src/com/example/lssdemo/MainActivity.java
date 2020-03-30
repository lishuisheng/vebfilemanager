package com.example.lssdemo;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.UserManager;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Process;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import android.app.ActivityManager;
import com.example.lssdemo.R;
import android.os.UserHandle;
import static android.app.admin.DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE;
import static android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity{

    private Button parent ;
    private ListView listview ;
    private TextView textview ;
    private File currentParent ;    //记录当前文件的父文件夹
    private File[] currentFiles ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         init();
    }

   private void init() {
        // TODO Auto-generated method stub
        this.parent = (Button)findViewById(R.id.parent) ;
        this.listview = (ListView)findViewById(R.id.listview) ;
        this.textview = (TextView)findViewById(R.id.textview) ;
        File root = new File(Environment.getExternalStorageDirectory().getPath()) ;

        if(root.exists()) {
            currentParent = root ;
            currentFiles = root.listFiles() ;

            inflateListView(currentFiles) ;
        }
        this.parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {

                    if(currentParent.getPath().equals(Environment.getExternalStorageDirectory().getPath())){
                        Toast.makeText(getApplicationContext(), "这是顶层目录",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!currentParent.getCanonicalFile().equals(Environment.getExternalStorageDirectory().getPath())) {
                        currentParent = currentParent.getParentFile() ; //获取上级目录
                        currentFiles = currentParent.listFiles() ; //取得当前层所有文件

                        Log.i("GGGGG", "path:" + currentParent.getPath());
                        inflateListView(currentFiles); //更新列表
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                if(currentFiles[position].isFile()) {
                    Toast.makeText(getApplicationContext(), "点击了文件，不做处理",Toast.LENGTH_LONG).show();
                    return ;
                }
                File[] temp = currentFiles[position].listFiles() ;
                if(temp == null /*|| temp.length == 0*/) {
                    Toast.makeText(getApplicationContext(), "此文件夹不可用或者文件夹为空",Toast.LENGTH_LONG).show();
                    return ;
                }

                if(temp.length == 0){
                    Toast.makeText(getApplicationContext(), "此文件夹为空",Toast.LENGTH_LONG).show();
                }
                currentParent = currentFiles[position] ;
                currentFiles = temp ;
                inflateListView(currentFiles);

            }
        }) ;
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        try {
            if(currentParent.getPath().equals(Environment.getExternalStorageDirectory().getPath())){
                Toast.makeText(getApplicationContext(), "这是顶层目录",Toast.LENGTH_LONG).show();
                return;
            }
            if(!currentParent.getCanonicalFile().equals(Environment.getExternalStorageDirectory().getPath())) {
                currentParent = currentParent.getParentFile() ;
                currentFiles = currentParent.listFiles() ;
                inflateListView(currentFiles);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * 更新listview
     */
    private void inflateListView(File[] files) {
        // TODO Auto-generated method stub
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>() ;
        for(int i = 0 ;i<files.length ;i++) {
            Map<String,Object> item= new HashMap<String, Object>() ;
            if(files[i].isDirectory()) {
                item.put("icon", R.drawable.folder) ;
            }else {
                item.put("icon", R.drawable.file) ;
            }
            item.put("name", files[i].getName()) ;

            if(currentParent.getPath().equals(Environment.getExternalStorageDirectory().getPath()) && files[i].getName().equals("veb")){
                list.add(item) ;
                currentFiles[0] = files[i];
            }

            if(!currentParent.getPath().equals(Environment.getExternalStorageDirectory().getPath())){
                list.add(item) ;
            }
        }
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),list,R.layout.listview_item, new String[]{"icon","name"}, new int[]{R.id.icon,R.id.name}) ;
        this.listview.setAdapter(adapter);
        try{
            textview.setText(currentParent.getCanonicalPath());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}





















































