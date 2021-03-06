package com.allen.code.downloader;

import com.alibaba.fastjson.JSON;
import com.allen.code.downloader.db.AppDatabase;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;

import java.io.File;

/**
 * 作者：husongzhen on 17/8/15 11:31
 * 邮箱：husongzhen@musikid.com
 */


@Table(database = AppDatabase.class)
public class DownTaskInfo {
    private String dir = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "downloader";
    @PrimaryKey
    @Unique(unique = true)
    public String id;
    @Column
    public String url;
    @Column
    public String path;
    @Column
    public String name;
    @Column
    public String param;
    @Column
    public boolean isFinish;


    public Object holder;


    public byte status;

    public void setPath() {
        this.path = dir + File.separator + name;
    }

    public void setParam(Object o) {
        param = JSON.toJSONString(o);
    }


    public Object getParam(Class clazz) {
        return JSON.parseObject(param, clazz);
    }
}
