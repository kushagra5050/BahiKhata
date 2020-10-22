package com.divy.prakash.paathsala.bahikhata.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

/* This Class Is Used To Make A Dialog Box To Pay Amount */
public class DialogAreYouSure extends AppCompatDialogFragment {

    private EditText editTextAmount;

    private AreYouSureDialogListener listener;
    String custname;

    public DialogAreYouSure(String custname) {
        this.custname = custname;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Do You Want To Delete "+custname+" Record?")
                .setMessage("This May Delete All the Record of " + custname)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.areYouSure();


                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AreYouSureDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AreYouSureDialogListener");
        }
    }

    /* Define Method That Call On Dialog Box Buttons */
    public interface AreYouSureDialogListener {
        void areYouSure();
    }

}
