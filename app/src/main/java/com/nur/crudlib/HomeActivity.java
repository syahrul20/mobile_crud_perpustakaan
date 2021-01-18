package com.nur.crudlib;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nur.crudlib.Utils.SnackBarInfo;
import com.nur.crudlib.adapter.BookListAdapter;
import com.nur.crudlib.field.BookField;

import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements BookListAdapter.ItemClickListener {

    public static final String DATA = "DATA";
    public static final int RESULT_ADD = 10;
    public static final int RESULT_UPDATE = 11;
    public static final int RESULT_DELETE = 12;

    RecyclerView rvBook;
    FloatingActionButton fabAdd;
    CoordinatorLayout container;
    LinearLayoutCompat containerEmpty;
    DatabaseReference myRef;
    ProgressBar progressLoading;
    BookListAdapter bookListAdapter;
    ActivityResultLauncher<Intent> mStartForResult;
    SnackBarInfo snackbar = new SnackBarInfo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpView();
        setUpActivityForResult();
    }

    private void setUpFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("buku");

        loadData();
    }

    public void setUpView() {
        container = findViewById(R.id.container);
        rvBook = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fab_add);
        containerEmpty = findViewById(R.id.container_empty);
        progressLoading = findViewById(R.id.progressLoading);

        setUpListener();
        setUpFirebase();
    }

    public void setUpListener() {
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, FormBookActivity.class);
            mStartForResult.launch(intent);
        });
    }

    private void loadData() {
        showLoading(true);
        ValueEventListener valueEventListener = new ValueEventListener() {
            final ArrayList<BookField> bookFieldArrayList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookFieldArrayList.clear();
                for (DataSnapshot bookSnapShot : snapshot.getChildren()) {
                    if (!Objects.equals(bookFieldArrayList, bookSnapShot.getValue(BookField.class))) {
                        BookField bookField = bookSnapShot.getValue(BookField.class);
                        bookFieldArrayList.add(bookField);
                    }
                }
                showLoading(false);
                setUpRecyclerView(bookFieldArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar snackbar;
                snackbar = Snackbar.make(container, "Gagal memuat data", Snackbar.LENGTH_SHORT);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

            }
        };

        myRef.orderByKey().addValueEventListener(valueEventListener);
    }


    private void setUpRecyclerView(ArrayList<BookField> mArrayList) {
        bookListAdapter = new BookListAdapter(this, mArrayList);
        rvBook.setAdapter(bookListAdapter);
        bookListAdapter.setClickListener(this);
        if (bookListAdapter.getItemCount() == 0) {
            containerEmpty.setVisibility(View.VISIBLE);
            rvBook.setVisibility(View.GONE);
        } else {
            containerEmpty.setVisibility(View.GONE);
            rvBook.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(BookField bookData, int pos) {
        Intent intent = new Intent(this, FormBookActivity.class);
        intent.putExtra(DATA, bookData);
        mStartForResult.launch(intent);
    }

    private void setUpActivityForResult() {
        mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_ADD) {
                        snackbar.setUpSnackBar(this, container, "Data berhasil ditambahkan");
                    } else if (result.getResultCode() == RESULT_UPDATE) {
                        snackbar.setUpSnackBar(this, container, "Data berhasil diubah");
                    } else if (result.getResultCode() == RESULT_DELETE) {
                        snackbar.setUpSnackBar(this, container, "Data berhasil dihapus");
                    }
                });
    }

    private void showLoading(Boolean isShow) {
        if (isShow) {
            progressLoading.setVisibility(View.VISIBLE);
        } else {
            progressLoading.setVisibility(View.GONE);
        }
    }
}