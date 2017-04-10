package com.example.weatherapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by 高静 on 2017/3/2.
 */

public class AlertDialogFragment extends DialogFragment {
    AlertDialog.Builder builder;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        builder= new AlertDialog.Builder(context)
                .setTitle("Oops! Error!")
                .setMessage("Something went wrong!")
                .setPositiveButton("Ok",null);

        AlertDialog dialog= builder.create();
        return dialog;
    }
}
