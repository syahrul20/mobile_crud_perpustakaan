package com.nur.crudlib.field;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
@Keep
public class BookField implements Serializable {
    private String uid;
    private String judul;
    private String pengarang;
    private String penerbit;
    private String kategori;
    private String yearPublished;
    private String tebalBuku;
    private String isbn;

    public BookField() {
    }

    public BookField(
            String uid,
            String judul,
            String pengarang,
            String penerbit,
            String kategori,
            String yearPublished,
            String tebalBuku,
            String isbn
    ) {
        this.uid = uid;
        this.judul = judul;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.kategori = kategori;
        this.yearPublished = yearPublished;
        this.tebalBuku = tebalBuku;
        this.isbn = isbn;
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

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(String yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getTebalBuku() {
        return tebalBuku;
    }

    public void setTebalBuku(String tebalBuku) {
        this.tebalBuku = tebalBuku;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
