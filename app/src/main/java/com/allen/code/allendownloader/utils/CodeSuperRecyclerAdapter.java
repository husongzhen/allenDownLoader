package com.allen.code.allendownloader.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.code.downloader.db.utils.CodeCheck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by husongzhen on 15/7/23.
 */
public abstract class CodeSuperRecyclerAdapter<M> extends RecyclerView.Adapter<CodeSuperRecyclerAdapter.WebViewHolder> {
    private static final int WITH_HEADER = 111;
    private static final int WITH_NONE = 112;

    protected List<M> dataList = new ArrayList<M>();
    protected Context activity;
    private View header;

    public void addHeader(View header) {
        this.header = header;
    }

    public CodeSuperRecyclerAdapter(Context activity) {
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return CodeCheck.isNotNull(header) ? dataList.size() + 1 : dataList.size();
    }

    @Override
    public void onBindViewHolder(WebViewHolder holder, int i) {
        if (getItemViewType(i) == WITH_HEADER) {
            return;
        }
        int index = getRealPosition(holder);
        onBindView(holder.query, dataList.get(index), i);
    }

    protected abstract void onBindView(CodeQuery query, M m, int i);


    @Override
    public WebViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CodeQuery query = viewType == WITH_HEADER ? new CodeQuery(activity).setRoot(header) : onCreateView(parent, viewType);
        return new WebViewHolder(query);
    }

    protected abstract CodeQuery onCreateView(View parent, int p);

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return CodeCheck.isNotNull(header) ? position - 1 : position;
    }


    @Override
    public int getItemViewType(int position) {
        if (CodeCheck.isNotNull(header) && position == 0) {
            return WITH_HEADER;
        } else {
            return getViewType(position);
        }
    }

    public int getViewType(int pos) {
        return WITH_NONE;
    }

    public void reloadData(List<M> data) {
        // TODO Auto-generated method stub
        if (!CodeCheck.isNotNullList(data)) {
            data = new ArrayList<M>();
        }
        dataList = data;
        notifyDataSetChanged();
    }

//    public void reloadData(M data) {
//        // TODO Auto-generated method stub
//        if (!CodeCheck.isNotNull(data)) {
//            if (dataList != null) {
//                dataList.clear();
//                dataList.add(data);
//            } else {
//                dataList = new ArrayList<M>();
//                dataList.add(data);
//            }
//        }
//        notifyDataSetChanged();
//    }

    public void appendData(List<M> data) {
        // TODO Auto-generated method stub

        if (!CodeCheck.isNotNullList(data)) {
            return;
        }
        dataList.addAll(data);
        int startIndex = getItemCount() + 1;
        notifyItemRangeInserted(startIndex, data.size());
    }


    public void notifyData(M m) {
        if (CodeCheck.isNotNull(m)) {
            int index = dataList.indexOf(m);
            if (index != -1) {
                notifyItemChanged(updatePos(index));
            }
        }
    }

    public int updatePos(int index) {
        return CodeCheck.isNotNull(header) ? index + 1 : index;
    }

    protected int downPos(int index) {
        return CodeCheck.isNotNull(header) ? index - 1 : index;
    }


    public void remove(int p) {
        dataList.remove(p);
        notifyItemRemoved(p);
        if (p != dataList.size()) { // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(p, dataList.size() - p);
        }
    }


    public M getItem(int p) {
        return dataList.get(downPos(p));
    }


    public List<M> getDataList() {
        return dataList;
    }

    public int indexOf(M item) {
        int pos = dataList.indexOf(item);
        return CodeCheck.isNotNull(header) ? updatePos(pos) : pos;
    }

    public void addAll(List<M> list) {
        if (!CodeCheck.isNotNullList(list)) {
            return;
        }

        int startPos = getItemCount();
        dataList.addAll(list);
        notifyItemRangeInserted(startPos, list.size());
    }


    public void add(int index, M item) {
        if (!CodeCheck.isNotNull(item)) {
            return;
        }
        dataList.add(index, item);
        notifyItemInserted(index);
    }

    public void add(M item) {
        if (!CodeCheck.isNotNull(item)) {
            return;
        }
        int index = dataList.size();
        dataList.add(item);
        notifyItemInserted(index);
    }

    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }


    public static class WebViewHolder extends RecyclerView.ViewHolder {
        public CodeQuery query;

        public WebViewHolder(CodeQuery query) {
            super(query.getRoot());
            this.query = query;
        }

        public View getItemView() {
            return itemView;
        }
    }

    public CodeQuery inflater(int layout, View parent) {
        View root = LayoutInflater.from(activity).inflate(layout, (ViewGroup) parent, false);
        CodeQuery query = new CodeQuery(activity).setRoot(root);
        return query;
    }

}
