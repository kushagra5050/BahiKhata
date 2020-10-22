package com.divy.prakash.paathsala.bahikhata.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

/* This Class Is Used To Make A Dialog Box To Add Customer */
public class DialogMatchPassword extends AppCompatDialogFragment {

    private EditText editTextPassword;
    private MatchPasswordDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_match_password, null);
        builder.setView(view)
                .setTitle("Enter Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String password = editTextPassword.getText().toString();


                        listener.matchPassword(password);


                    }
                });
        editTextPassword = view.findViewById(R.id.dialog_match_password_name_EditTxt);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (MatchPasswordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ChangePasswordDialogListener");
        }
    }

    /* Define Method That Call On Dialog Box Buttons */
    public interface MatchPasswordDialogListener {
        void matchPassword(String password);
    }

}
