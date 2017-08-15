package com.allen.code.downloader;

import android.util.SparseArray;

import com.allen.code.downloader.db.dao.impl.DownTaskDao;
import com.allen.code.downloader.db.utils.CodeCheck;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;

import java.util.List;

/**
 * 作者：husongzhen on 17/8/15 13:51
 * 邮箱：husongzhen@musikid.com
 */

public class FileDownLoaderImpl extends FileDownloadListener implements IDownLoader {
    private final DownTaskDao dao;
    private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();
    private SparseArray<DownLoaderListener> downLoaderListeners = new SparseArray<>();
    private SparseArray<DownTaskInfo> downTasks = new SparseArray<>();
    private int listenerCount;

    public FileDownLoaderImpl() {
        dao = new DownTaskDao();
    }

    public void registerDownListener(String key, DownLoaderListener listener) {
        downLoaderListeners.put(key.hashCode(), listener);
        listenerCount = downLoaderListeners.size();
    }

    public void unRegisterDownListener(String key) {
        downLoaderListeners.remove(key.hashCode());
        listenerCount = downLoaderListeners.size();
    }


    @Override
    public List<DownTaskInfo> restoreLoading() {
        List<DownTaskInfo> downTaskInfos = dao.getDownLoading();
        if (!CodeCheck.isNotNullList(downTaskInfos)) {
            return downTaskInfos;
        }
        for (DownTaskInfo info : downTaskInfos) {
            startTask(info.url, info);
            downTasks.put(info.url.hashCode(), info);
        }
        return downTaskInfos;
    }

    @Override
    public List<DownTaskInfo> restoreLoaded() {
        List<DownTaskInfo> downTaskInfos = dao.getDownLoaded();
        for (DownTaskInfo info : downTaskInfos) {
            downTasks.put(info.url.hashCode(), info);
        }
        return downTaskInfos;
    }

    @Override
    public void startTask(String tag, DownTaskInfo info) {
        BaseDownloadTask task = FileDownloader.getImpl().create(info.url)
                .setPath(info.path)
                .setWifiRequired(true)
                .setTag(info)
                .setListener(this);
        setStatus(info, task);
        task.start();
        taskSparseArray.put(tag.hashCode(), task);
        dao.save(info);

    }


    private void setStatus(DownTaskInfo info, BaseDownloadTask task) {

        switch (task.getStatus()) {
            case FileDownloadStatus.pending:
            case FileDownloadStatus.retry:
                info.status = AllenDownStatus.wait;
                break;

            case FileDownloadStatus.started:
                info.status = AllenDownStatus.start;
                break;

            case FileDownloadStatus.paused:
                info.status = AllenDownStatus.pause;
                break;
            case FileDownloadStatus.progress:
            case FileDownloadStatus.blockComplete:
                info.status = AllenDownStatus.progress;
                break;
            case FileDownloadStatus.completed:
                info.status = AllenDownStatus.finish;
                break;
            case FileDownloadStatus.warn:
            case FileDownloadStatus.error:
                info.status = AllenDownStatus.error;
                break;
            default:
                info.status = AllenDownStatus.error;
                break;
        }
    }

    @Override
    public void stopTask(String tag) {
        BaseDownloadTask task = taskSparseArray.get(tag.hashCode());
        if (CodeCheck.isNotNull(task)) {
            FileDownloader.getImpl().pause(task.getId());
        }
    }

    @Override
    public void cancleTask(String tag, DownTaskInfo info) {
        BaseDownloadTask task = taskSparseArray.get(tag.hashCode());
        if (CodeCheck.isNotNull(task)) {
            task.pause();
            taskSparseArray.remove(task.getUrl().hashCode());
            dao.delete(info);
        }
    }


    //    listener
    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        for (int i = 0; i < listenerCount; i++) {
            DownLoaderListener listener = getDownLoaderListener(i);
            listener.onWait(get(task), soFarBytes, totalBytes);
        }
    }


    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        for (int i = 0; i < listenerCount; i++) {
            DownLoaderListener listener = getDownLoaderListener(i);
            listener.onProgress(get(task), soFarBytes, totalBytes);
        }
    }

    @Override
    protected void completed(BaseDownloadTask task) {
        DownTaskInfo info = downTasks.get(task.getUrl().hashCode());
        info.isFinish = true;
        dao.udpate(info);
        for (int i = 0; i < listenerCount; i++) {
            DownLoaderListener listener = getDownLoaderListener(i);
            listener.onFinish(get(task));
        }
    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        for (int i = 0; i < listenerCount; i++) {
            DownLoaderListener listener = getDownLoaderListener(i);
            listener.onPause(get(task), soFarBytes, totalBytes);
        }
    }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {
        for (int i = 0; i < listenerCount; i++) {
            DownLoaderListener listener = getDownLoaderListener(i);
            listener.onError(get(task), e);
        }
    }

    @Override
    protected void warn(BaseDownloadTask task) {
        for (int i = 0; i < listenerCount; i++) {
            DownLoaderListener listener = getDownLoaderListener(i);
            listener.onError(get(task), new Throwable());
        }
    }

    private DownTaskInfo get(BaseDownloadTask task) {
        DownTaskInfo info = downTasks.get(task.getUrl().hashCode());
        setStatus(info, task);
        return info;
    }

    private DownLoaderListener getDownLoaderListener(int i) {
        return downLoaderListeners.get(downLoaderListeners.keyAt(i));
    }
}
