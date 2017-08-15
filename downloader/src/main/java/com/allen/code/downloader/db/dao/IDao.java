package com.allen.code.downloader.db.dao;

import java.util.List;

/**
 * 作者：husongzhen on 17/7/25 11:26
 * 邮箱：husongzhen@musikid.com
 */

public interface IDao<T> {
    void save(T t);

    void delete(T t);

    void udpate(T t);

    void clear();

    void asyncQuery(OnQueryResultListener<T> listener);

    List<T> queryAll();

    T queryById(T t);

    interface OnQueryResultListener<M> {
        void onQueryResultListener(List<M> tResult);
    }
}
