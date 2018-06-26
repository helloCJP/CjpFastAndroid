package com.cjp.recycleview;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjp.R;

import java.util.ArrayList;

/**
 * Created by 蔡金品 on 2017/4/17.
 * email : caijinpin@zhexinit.com
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> data = new ArrayList<>();
    private Activity activity;

    int colors[]={Color.MAGENTA,Color.BLUE, Color.CYAN, Color.RED, Color.GRAY, Color.GREEN};

    public RecycleAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), android.R.layout.simple_list_item_1, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).mTextView.setText(data.get(position));
        ((ViewHolder) holder).mTextView.setBackgroundColor(colors[position%colors.length]);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setData(ArrayList<String> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<String> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        /**
         * @Description: TODO
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }
}
