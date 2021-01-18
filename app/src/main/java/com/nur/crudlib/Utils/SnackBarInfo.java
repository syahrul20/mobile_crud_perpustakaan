package com.nur.crudlib.Utils;

import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.nur.crudlib.R;

/**
 * بِسْمِ اللهِ الرَّحْمٰنِ الرَّحِيْمِ
 * Created By Fahmi on 18/01/21
 */

public class SnackBarInfo {
    public void setUpSnackBar(Context context, View view, String message) {
        Snackbar snackbar;
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }
}
