package com.nur.crudlib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nur.crudlib.dialog.LoadingDialog;
import com.nur.crudlib.field.BookField;

public class FormBookActivity extends AppCompatActivity {

    public enum Mode {
        ADD, UPDATE
    }

    AppCompatImageView btnback;
    AppCompatButton btnSubmit, btnDelete;
    AppCompatTextView tvTitle;
    TextInputLayout inputLayoutBookTitle, inputLayoutBookPublished, inputLayoutBookCreator,
            inputLayoutBookCategory, inputLayoutBookYearPublished,
            inputLayoutBookThickness, inputLayoutBookIsbn;
    AppCompatEditText edBookTitle, edBookPublished, edBookCreator, edBookCategory,
            edBookYearPublished, edBookThickness, edBookIsbn;
    DatabaseReference myRef;
    BookField bookField;
    final Handler handler = new Handler(Looper.getMainLooper());
    Mode editMode = Mode.ADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_book);

        setUpView();
        setUpExtra();
        setUpFirebase();
        setUpListener();
    }

    private void setUpExtra() {
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                editMode = Mode.UPDATE;
                bookField = (BookField) getIntent().getSerializableExtra("DATA");
                edBookTitle.setText(bookField.getJudul());
                edBookCreator.setText(bookField.getPengarang());
                edBookPublished.setText(bookField.getPenerbit());
                edBookCategory.setText(bookField.getKategori());
                edBookYearPublished.setText(bookField.getYearPublished());
                edBookThickness.setText(bookField.getTebalBuku());
                edBookIsbn.setText(bookField.getIsbn());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (editMode == Mode.ADD) {
            tvTitle.setText(getString(R.string.app_add_book_data));
            btnSubmit.setText("Simpan");
            btnDelete.setVisibility(View.GONE);
        } else {
            tvTitle.setText(getString(R.string.app_change_book_data));
            btnSubmit.setText("Ubah");
            btnDelete.setVisibility(View.VISIBLE);
        }
    }

    private void setUpView() {
        inputLayoutBookTitle = findViewById(R.id.input_layout_book_title);
        inputLayoutBookCreator = findViewById(R.id.input_layout_book_creator);
        inputLayoutBookPublished = findViewById(R.id.input_layout_book_published);
        inputLayoutBookCategory = findViewById(R.id.input_layout_book_category);
        inputLayoutBookYearPublished = findViewById(R.id.input_layout_book_year_published);
        inputLayoutBookThickness = findViewById(R.id.input_layout_book_thickness);
        inputLayoutBookIsbn = findViewById(R.id.input_layout_book_isbn);

        edBookTitle = findViewById(R.id.ed_book_title);
        edBookCreator = findViewById(R.id.ed_book_creator);
        edBookPublished = findViewById(R.id.ed_book_publsihed);
        edBookCategory = findViewById(R.id.ed_book_category);
        edBookYearPublished = findViewById(R.id.ed_book_year_published);
        edBookThickness = findViewById(R.id.ed_book_thickness);
        edBookIsbn = findViewById(R.id.ed_book_isbn);

        btnback = findViewById(R.id.btn_back);
        btnSubmit = findViewById(R.id.btn_submit);
        btnDelete = findViewById(R.id.btn_delete);
        tvTitle = findViewById(R.id.tv_title);
    }

    private void setUpListener() {
        btnback.setOnClickListener(view -> {
            finish();
        });

        btnSubmit.setOnClickListener(view -> {
            String key = myRef.push().getKey();
            String bookTitle = edBookTitle.getText().toString();
            String bookCreator = edBookCreator.getText().toString();
            String bookPublished = edBookPublished.getText().toString();
            String bookCategory = edBookCategory.getText().toString();
            String bookYearPublished = edBookYearPublished.getText().toString();
            String bookThickness = edBookThickness.getText().toString();
            String bookIsbn = edBookIsbn.getText().toString();
            BookField field = new BookField(
                    key,
                    bookTitle,
                    bookCreator,
                    bookPublished,
                    bookCategory,
                    bookYearPublished,
                    bookThickness,
                    bookIsbn
            );
            if (key != null) {
                showLoading(true);
                if (editMode == Mode.ADD) {
                    myRef.child(key).setValue(field);
                } else {
                    myRef.child(bookField.getUid()).setValue(field);
                }
                handler.postDelayed(() -> {
                    showLoading(false);
                    if (editMode == Mode.ADD) {
                        setResult(MainActivity.RESULT_ADD);
                    } else {
                        setResult(MainActivity.RESULT_UPDATE);
                    }
                    finish();
                }, 500);
            }
        });

        btnDelete.setOnClickListener(view -> {
            showLoading(true);
            myRef.child(bookField.getUid()).removeValue();
            handler.postDelayed(() -> {
                showLoading(false);
                setResult(MainActivity.RESULT_DELETE);
                finish();
            }, 500);
        });
    }

    private void setUpFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("buku");
    }

    public void showLoading(Boolean isShow) {
        LoadingDialog loadingDialog = new LoadingDialog();
        if (isShow) {
            loadingDialog.setUpDialog(this).show();
        } else {
            loadingDialog.setUpDialog(this).dismiss();
        }
    }
}