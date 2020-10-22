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

/* This Class Is Used To Make A Dialog Box To Pay Amount */
public class DialogChangePassword extends AppCompatDialogFragment {

    private EditText editTextPassword;

    private ChangePasswordDialogListener listener;
    String password ;

    public DialogChangePassword(String amount) {
        this.password = amount;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_change_password, null);
        builder.setView(view)
                .setTitle("Enter New Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String passwordstr = editTextPassword.getText().toString();


                        listener.changePassword(passwordstr);


                    }
                });
        editTextPassword = view.findViewById(R.id.dialog_change_password_name_EditTxt);
        editTextPassword.setText(password);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ChangePasswordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ChangePasswordDialogListener");
        }
    }

    /* Define Method That Call On Dialog Box Buttons */
    public interface ChangePasswordDialogListener {
        void changePassword(String newpassword);
    }

}
