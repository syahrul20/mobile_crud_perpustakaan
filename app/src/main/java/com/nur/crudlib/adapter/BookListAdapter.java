package com.nur.crudlib.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.nur.crudlib.R;
import com.nur.crudlib.field.BookField;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * بِسْمِ اللهِ الرَّحْمٰنِ الرَّحِيْمِ
 * Created By Fahmi on 17/01/21
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ItemViewHolder> {

    private LayoutInflater mInflate;
    private ArrayList<BookField> mData;

    public BookListAdapter(Context context, ArrayList<BookField> mData) {
        this.mInflate = LayoutInflater.from(context);
        this.mData = mData;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.item_recyclerview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        BookField bookData = mData.get(position);
        holder.bind(bookData);
    }

    @Override
    public int getItemCount() {
        Log.i("ZXC", "cause " + mData.size());
        return mData.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvRecycler;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecycler = itemView.findViewById(R.id.tv_recycler);
        }

        private void bind(BookField bookData) {
            tvRecycler.setText(bookData.getJudul());
        }
    }
}
