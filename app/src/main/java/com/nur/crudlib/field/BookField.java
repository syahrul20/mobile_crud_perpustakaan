package com.nur.crudlib.field;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
@Keep
public class BookField {
    private String uid;
    private String penerbit;
    private String judul;

    public BookField() {}

    public BookField(String uid, String judul, String penerbit) {
        this.uid = uid;
        this.judul = judul;
        this.penerbit = penerbit;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getJudul() {
        return judul;
    }
}
