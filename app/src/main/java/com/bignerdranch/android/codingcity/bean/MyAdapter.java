package com.bignerdranch.android.codingcity.bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.android.codingcity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This is Adapter using for list view
 *
 * @author Ruize Nie
 */
public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList = new ArrayList<>();
    private onItemClickListener mOnItemClickListener;

    public MyAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.list_lesson_item, null);
            viewHolder.mTextView = view.findViewById(R.id.tv_title);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextView.setText(mList.get(position));
        viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onLessonClick(position);
            }
        });
        return view;
    }

    public void setOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface onItemClickListener {
        void onLessonClick(int i);
    }

    class ViewHolder {
        TextView mTextView;
        Button mButton;
    }
}
