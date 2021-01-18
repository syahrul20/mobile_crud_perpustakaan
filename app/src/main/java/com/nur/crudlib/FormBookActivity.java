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
import com.nur.crudlib.Utils.LoadingDialog;
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
    DatabaseReference databaseReference;
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
            btnSubmit.setText(getString(R.string.app_data_save));
            btnDelete.setVisibility(View.GONE);
        } else {
            tvTitle.setText(getString(R.string.app_change_book_data));
            btnSubmit.setText(getString(R.string.app_data_change));
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
        btnback.setOnClickListener(view -> finish());

        btnSubmit.setOnClickListener(view -> {
            String key;
            if (bookField == null) {
                key = databaseReference.push().getKey();
            } else {
                key = bookField.getUid();
            }
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
                if (checkValidation()) {
                    showLoading(true);
                    databaseReference.child(key).setValue(field);
                    handler.postDelayed(() -> {
                        showLoading(false);
                        if (editMode == Mode.ADD) {
                            setResult(HomeActivity.RESULT_ADD);
                        } else {
                            setResult(HomeActivity.RESULT_UPDATE);
                        }
                        finish();
                    }, 500);
                }
            }
        });

        btnDelete.setOnClickListener(view -> {
            showLoading(true);
            databaseReference.child(bookField.getUid()).removeValue();
            handler.postDelayed(() -> {
                showLoading(false);
                setResult(HomeActivity.RESULT_DELETE);
                finish();
            }, 500);
        });
    }

    private Boolean checkValidation() {
        if (edBookTitle.getText().toString().matches("")) {
            inputLayoutBookTitle.setError(getString(R.string.app_book_should_fill_title));
            return false;
        } else {
            inputLayoutBookTitle.setErrorEnabled(false);
        }

        if (edBookCreator.getText().toString().matches("")) {
            inputLayoutBookCreator.setError(getString(R.string.app_book_should_fill_creator));
            return false;
        } else {
            inputLayoutBookCreator.setErrorEnabled(false);
        }

        if (edBookPublished.getText().toString().matches("")) {
            inputLayoutBookPublished.setError(getString(R.string.app_book_should_fill_published));
            return false;
        } else {
            inputLayoutBookPublished.setErrorEnabled(false);
        }

        if (edBookCategory.getText().toString().matches("")) {
            inputLayoutBookCategory.setError(getString(R.string.app_book_should_fill_category));
            return false;
        } else {
            inputLayoutBookCategory.setErrorEnabled(false);
        }

        if (edBookYearPublished.getText().toString().matches("")) {
            inputLayoutBookYearPublished.setError(getString(R.string.app_book_should_fill_year_published));
            return false;
        } else {
            inputLayoutBookCategory.setErrorEnabled(false);
        }

        if (edBookThickness.getText().toString().matches("")) {
            inputLayoutBookThickness.setError(getString(R.string.app_book_should_fill_thickness));
            return false;
        } else {
            inputLayoutBookThickness.setErrorEnabled(false);
        }

        if (edBookIsbn.getText().toString().matches("")) {
            inputLayoutBookIsbn.setError(getString(R.string.app_book_should_fill_isbn));
            return false;
        } else {
            inputLayoutBookIsbn.setErrorEnabled(false);
        }

        return true;
    }

    private void setUpFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference("buku");
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