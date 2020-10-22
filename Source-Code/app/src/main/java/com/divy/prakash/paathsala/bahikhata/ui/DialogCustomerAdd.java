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
public class DialogCustomerAdd extends AppCompatDialogFragment {

    private EditText editTextUsername;
    private EditText editTextContact;
    private EditText editTextAddress;
    String username, contact, address;

    private AddCustomerDialogListener listener;

    public DialogCustomerAdd(String username, String contact, String address) {
        this.username = username;
        this.contact = contact;
        this.address = address;


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_add_customer, null);
        builder.setView(view)
                .setTitle("Register a New Customer")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String usernamestr = editTextUsername.getText().toString();
                        String contactstr = editTextContact.getText().toString();
                        String addressstr = editTextAddress.getText().toString();
                        listener.insertData(usernamestr, contactstr, addressstr);

                    }
                });
        editTextUsername = view.findViewById(R.id.dialog_add_customer_name_EditTxt);
        editTextContact = view.findViewById(R.id.dialog_add_customer_Contact_EditTxt);
        editTextAddress = view.findViewById(R.id.dialog_add_customer_address_EditTxt);
        editTextUsername.setText(username);
        editTextContact.setText(contact);
        editTextAddress.setText(address);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddCustomerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddCustomerDialogListener");
        }
    }

    /* Define Method That Call On Dialog Box Buttons */
    public interface AddCustomerDialogListener {
        void insertData(String username, String contact, String address);
    }
}
