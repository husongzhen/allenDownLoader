package com.allen.code.downloader.db.dao.impl;

import com.allen.code.downloader.DownTaskInfo;
import com.allen.code.downloader.DownTaskInfo_Table;
import com.allen.code.downloader.db.dao.BaseDao;
import com.allen.code.downloader.db.utils.CodeCheck;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * 作者：husongzhen on 17/8/15 11:52
 * 邮箱：husongzhen@musikid.com
 */

public class DownTaskDao extends BaseDao<DownTaskInfo> {

    @Override
    public void save(DownTaskInfo t) {
        DownTaskInfo item = SQLite.select().from(DownTaskInfo.class).where(DownTaskInfo_Table.url.is(t.url)).querySingle();
        if (CodeCheck.isNotNull(item)) {
            return;
        }
        adapter.save(t);
    }


    public List<DownTaskInfo> getAllDownTask() {
        List<DownTaskInfo> list = SQLite.select()
                .from(DownTaskInfo.class)
                .queryList();
        return list;
    }

    public List<DownTaskInfo> getDownLoading() {
        List<DownTaskInfo> list = SQLite.select()
                .from(DownTaskInfo.class)
                .where(DownTaskInfo_Table.isFinish.is(false))
                .queryList();
        return list;
    }

    public List<DownTaskInfo> getDownLoaded() {
        List<DownTaskInfo> list = SQLite.select()
                .from(DownTaskInfo.class)
                .where(DownTaskInfo_Table.isFinish.is(true))
                .queryList();
        return list;
    }
}
