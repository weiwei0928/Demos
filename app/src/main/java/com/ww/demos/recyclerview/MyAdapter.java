package com.ww.demos.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ww.demos.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<ItemData> dataList;
    private final AsyncListDiffer<ItemData> differ;

    public MyAdapter() {
        differ = new AsyncListDiffer<>(this, new DiffUtil.ItemCallback<ItemData>() {
            @Override
            public boolean areItemsTheSame(@NonNull ItemData oldItem, @NonNull ItemData newItem) {
                // 如果两个对象代表相同的Item返回true
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull ItemData oldItem, @NonNull ItemData newItem) {
                // 如果两个对象的内容相同返回true
                return oldItem.getContent().equals(newItem.getContent());
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ItemData item = differ.getCurrentList().get(position);
        holder.textView.setText(item.getContent());

        holder.itemView.setOnClickListener(v -> {
            // 更新数据模型的内容
            item.setContent("Clicked - " + item.getContent());
            // 无需手动调用notifyItemChanged，AsyncListDiffer会在适当的时候自动调用
//            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(List<ItemData> list) {
        differ.submitList(list);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_item);
        }
    }
}
