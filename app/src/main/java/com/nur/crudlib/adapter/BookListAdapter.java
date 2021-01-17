package com.nur.crudlib.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nur.crudlib.R;
import com.nur.crudlib.field.BookField;

import java.util.ArrayList;

/**
 * بِسْمِ اللهِ الرَّحْمٰنِ الرَّحِيْمِ
 * Created By Fahmi on 17/01/21
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ItemViewHolder> {

    private LayoutInflater mInflate;
    private ArrayList<BookField> mData;
    private ItemClickListener itemClickListener;

    public BookListAdapter(Context context, ArrayList<BookField> mData) {
        this.mInflate = LayoutInflater.from(context);
        this.mData = mData;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.item_book, parent, false);
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

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvJudul, tvPengarang, tvTahun;
        CardView cardViewItemBuku;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvPengarang = itemView.findViewById(R.id.tv_pengarang);
            tvTahun = itemView.findViewById(R.id.tv_tahun);
            cardViewItemBuku = itemView.findViewById(R.id.cardview_item_buku);
        }

        private void bind(BookField bookData) {
            tvJudul.setText(bookData.getJudul());
            tvPengarang.setText(bookData.getPengarang());
            tvTahun.setText(bookData.getYearPublished());
            cardViewItemBuku.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(bookData, getAdapterPosition());
                }
            });
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(BookField bookData, int pos);
    }
}
