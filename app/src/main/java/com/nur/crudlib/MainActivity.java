package com.nur.crudlib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nur.crudlib.adapter.BookListAdapter;
import com.nur.crudlib.field.BookField;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btnUbah;
    AppCompatEditText edJudul, edPenerbit;
    RecyclerView rvBook;
    DatabaseReference myRef;
    BookListAdapter bookListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpFirebase();
        setUpView();
        setUpListener();
    }

    private void setUpFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("buku");

        loadData();
    }

    public void setUpView() {
        edJudul = findViewById(R.id.ed_judul);
        edPenerbit = findViewById(R.id.ed_penerbit);
        btnUbah = findViewById(R.id.btn_submit);
        rvBook = findViewById(R.id.recyclerView);
    }

    public void setUpListener() {
        btnUbah.setOnClickListener(view -> {
            String key = myRef.push().getKey();
            BookField field = new BookField(key, edJudul.getText().toString(), edPenerbit.getText().toString());
            if (key != null) {
                myRef.child(key).setValue(field);
            }
        });
    }

    private void loadData() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            final ArrayList<BookField> bookFieldArrayList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookFieldArrayList.clear();
                for (DataSnapshot bookSnapShot : snapshot.getChildren()) {
                    if (!Arrays.asList(bookFieldArrayList).contains(bookSnapShot.getValue(BookField.class))) {
                        BookField bookField = bookSnapShot.getValue(BookField.class);
                        bookFieldArrayList.add(bookField);
                    }
                }
                setUpRecyclerView(bookFieldArrayList);
                Log.i("ZXCVD", "data" + bookFieldArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("ZXC", "loadErrorCause: ", error.toException());
            }
        };

        myRef.orderByKey().addValueEventListener(valueEventListener);
    }


    private void setUpRecyclerView(ArrayList<BookField> mArrayList) {
        bookListAdapter = new BookListAdapter(this, mArrayList);
        rvBook.setAdapter(bookListAdapter);
    }
}