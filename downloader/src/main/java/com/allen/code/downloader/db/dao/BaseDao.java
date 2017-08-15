package com.allen.code.downloader.db.dao;

import android.support.annotation.NonNull;

import com.allen.code.downloader.db.utils.CodeCheck;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 作者：husongzhen on 17/7/25 11:27
 * 邮箱：husongzhen@musikid.com
 */

public abstract class BaseDao<T> implements IDao<T> {

    protected final ModelAdapter<T> adapter;
    private final Class<T> entityClass;

    public BaseDao() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
        adapter = FlowManager.getModelAdapter(entityClass);
    }

    @Override
    public void save(T t) {
        T item = queryById(t);
        if (CodeCheck.isNotNull(item)) {
            adapter.delete(t);
        }
        adapter.save(t);
    }

    @Override
    public void delete(T t) {
        adapter.delete(t);
    }

    @Override
    public void udpate(T t) {
        adapter.update(t);
    }


    @Override
    public void clear() {
        adapter.deleteAll(queryAll());
    }

    @Override
    public void asyncQuery(final OnQueryResultListener<T> queryResultListener) {
        SQLite.select()
                .from(entityClass)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<T>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<T> tResult) {
                        queryResultListener.onQueryResultListener(tResult);
                    }
                }).execute();
    }

    @Override
    public List<T> queryAll() {
        return SQLite.select().from(entityClass).queryList();
    }


    @Override
    public T queryById(T t) {
        return null;
    }
}
