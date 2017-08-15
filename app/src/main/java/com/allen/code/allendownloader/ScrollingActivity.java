package com.allen.code.allendownloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.allen.code.downloader.DownLoaderManager;
import com.allen.code.downloader.DownTaskInfo;

import java.util.List;

import static com.allen.code.downloader.DownLoaderManager.news;

public class ScrollingActivity extends AppCompatActivity {


    private RecyclerView listView;
    private DownAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        adapter = new DownAdapter(this);
        listView = (RecyclerView) findViewById(R.id.listview);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
        String[] strings = Constant.BIG_FILE_URLS;
        for (int i = 0; i < strings.length; i++) {
            String url = strings[i];
            DownTaskInfo info = new DownTaskInfo();
            info.name = url;
            info.url = url;
            info.setPath();
            news().getDownLoader().startTask(url, info);
        }
        List<DownTaskInfo> list = DownLoaderManager.news().getDownLoader().restoreLoading();
        adapter.reloadData(list);
        news().getDownLoader().registerDownListener(getClass().getName(), adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        news().getDownLoader().unRegisterDownListener(getClass().getName());
    }
}
