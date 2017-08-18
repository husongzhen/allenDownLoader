package com.allen.code.allendownloader;

import android.content.Context;
import android.view.View;

import com.allen.code.allendownloader.utils.CodeQuery;
import com.allen.code.allendownloader.utils.CodeSuperRecyclerAdapter;
import com.allen.code.downloader.AllenDownStatus;
import com.allen.code.downloader.DownLoaderListener;
import com.allen.code.downloader.DownLoaderManager;
import com.allen.code.downloader.DownTaskInfo;
import com.allen.code.downloader.db.utils.CodeCheck;

/**
 * 作者：husongzhen on 17/8/15 15:26
 * 邮箱：husongzhen@musikid.com
 */

public class DownAdapter extends CodeSuperRecyclerAdapter<DownTaskInfo> implements DownLoaderListener, View.OnClickListener {


    public DownAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void onBindView(CodeQuery query, DownTaskInfo info, int i) {
        info.holder = query;
        query.id(R.id.name).text(info.name);
        setStatus(query, info);
        query.id(R.id.button).setTag(info).click(this);
    }


    @Override
    protected CodeQuery onCreateView(View parent, int p) {
        return inflater(R.layout.down_item, parent);
    }


    @Override
    public void onClick(View view) {
        DownTaskInfo info = (DownTaskInfo) view.getTag();
        CodeQuery query = (CodeQuery) info.holder;
        switch (info.status) {
            case AllenDownStatus.error:
            case AllenDownStatus.pause:
                query.id(R.id.button).text("start");
                DownLoaderManager.news().getDownLoader().startTask(info.url, info);
                break;
            case AllenDownStatus.progress:
            case AllenDownStatus.start:
            case AllenDownStatus.wait:
                query.id(R.id.button).text("stop");
                DownLoaderManager.news().getDownLoader().stopTask(info.url);
                break;
            case AllenDownStatus.finish:
                break;

        }
    }


    @Override
    public void onWait(DownTaskInfo tag, int downsize, int totalSize) {
        CodeQuery query = (CodeQuery) tag.holder;
        if (!CodeCheck.isNotNull(query)) {
            return;
        }
        setStatus(query, tag);
    }


//    /storage/emulated/0/Android/data/com.allen.code.allendownloader/cache/downloader/name5

    @Override
    public void onProgress(DownTaskInfo tag, int soFarBytes, int totalBytes) {
        CodeQuery query = (CodeQuery) tag.holder;
        if (!CodeCheck.isNotNull(query)) {
            return;
        }

        int progress = (int) (soFarBytes / (totalBytes * 1f) * 100);
        query.id(R.id.progress).text(progress + " , speed = " + tag.getSpeed());
        setStatus(query, tag);
    }

    @Override
    public void onPause(DownTaskInfo tag, int soFarBytes, int totalBytes) {
        CodeQuery query = (CodeQuery) tag.holder;
        if (!CodeCheck.isNotNull(query)) {
            return;
        }
        setStatus(query, tag);
    }

    @Override
    public void onError(DownTaskInfo tag, Throwable e) {
        CodeQuery query = (CodeQuery) tag.holder;
        if (!CodeCheck.isNotNull(query)) {
            return;
        }
        setStatus(query, tag);
    }

    @Override
    public void onFinish(DownTaskInfo tag) {
        CodeQuery query = (CodeQuery) tag.holder;
        if (!CodeCheck.isNotNull(query)) {
            return;
        }
        setStatus(query, tag);
    }



    private void setStatus(CodeQuery query, DownTaskInfo info) {
        switch (info.status) {
            case AllenDownStatus.start:
                query.id(R.id.status).text("start");
                query.id(R.id.button).text("start");
                break;

            case AllenDownStatus.pause:
                query.id(R.id.status).text("pause");
                query.id(R.id.button).text("pause");
                break;

            case AllenDownStatus.wait:
                query.id(R.id.status).text("wait");
                query.id(R.id.button).text("wait");
                break;

            case AllenDownStatus.progress:
                query.id(R.id.status).text("progress");
                query.id(R.id.button).text("loading");
                break;

            case AllenDownStatus.finish:
                query.id(R.id.status).text("finish");
                query.id(R.id.button).text("finish");
                break;

            case AllenDownStatus.error:
                query.id(R.id.status).text("errors");
                query.id(R.id.button).text("start");
                break;
        }
    }


}


