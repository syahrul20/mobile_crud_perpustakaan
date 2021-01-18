package com.nur.crudlib.Utils;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import com.nur.crudlib.R;

/**
 * بِسْمِ اللهِ الرَّحْمٰنِ الرَّحِيْمِ
 * Created By Fahmi on 17/01/21
 */

public class LoadingDialog {
    AlertDialog dialog;

    public AlertDialog setUpDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.dialog_loading);
        dialog = builder.create();
        return dialog;
    }
}
