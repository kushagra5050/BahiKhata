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

/* This Class Is Used To Make A Dialog Box To Get Password */
public class DialogForgotPassword extends AppCompatDialogFragment {

    private EditText editTextUserID;
    private EditText editTextContact;
    private EditText editTextEmail;
    private ForgotPasswordDialogListener listener;
    String userid,contact,email ;

    public DialogForgotPassword(String userid, String contact, String email) {
        this.userid = userid;
        this.contact = contact;
        this.email = email;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_forgot_password, null);
        builder.setView(view)
                .setTitle("Forgot Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String useridstr = editTextUserID.getText().toString();
                        String contactstr = editTextContact.getText().toString();
                        String emailstr = editTextEmail.getText().toString();


                        listener.forgotPassword(useridstr, contactstr, emailstr);


                    }
                });
        editTextUserID = view.findViewById(R.id.dialog_forgot_password_ID_EditTxt);
        editTextContact = view.findViewById(R.id.dialog_forgot_password_contact_EditTxt);
        editTextEmail = view.findViewById(R.id.dialog_forgot_password_email_EditTxt);
        editTextUserID.setText(userid);
        editTextContact.setText(contact);
        editTextEmail.setText(email);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ForgotPasswordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement UpdateCustomerDataDialogListener");
        }
    }

    /* Define Method That Call On Dialog Box Buttons */
    public interface ForgotPasswordDialogListener {
        void forgotPassword(String userid, String contact, String email);
    }

}
