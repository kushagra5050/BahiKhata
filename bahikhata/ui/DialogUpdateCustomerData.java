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
public class DialogUpdateCustomerData extends AppCompatDialogFragment {

    private EditText edit_cust_name, edit_cust_contact, edit_cust_address;

    String name, contact, address;
    private UpdateCustomerDataDialogListener listener;


    public DialogUpdateCustomerData(String name, String contact, String address) {
        this.name = name;
        this.contact = contact;
        this.address = address;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_update_customer_data, null);
        builder.setView(view)
                .setTitle("Update")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String custnamestr = edit_cust_name.getText().toString();
                        String contactstr = edit_cust_contact.getText().toString();
                        String addressstr = edit_cust_address.getText().toString();


                        listener.updateCustomerData(custnamestr, contactstr, addressstr);


                    }
                });
        edit_cust_name = view.findViewById(R.id.update_customer_name_EditTxt);
        edit_cust_contact = view.findViewById(R.id.update_customer_contact_EditTxt);
        edit_cust_address = view.findViewById(R.id.update_customer_address_EditTxt);
        edit_cust_name.setText(name);
        edit_cust_contact.setText(contact);
        edit_cust_address.setText(address);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (UpdateCustomerDataDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement UpdateCustomerDataDialogListener");
        }
    }

    /* Define Method That Call On Dialog Box Buttons */
    public interface UpdateCustomerDataDialogListener {
        void updateCustomerData(String custname, String contact, String address);
    }

}
