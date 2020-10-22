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
public class DialogChangeProfile extends AppCompatDialogFragment {

    private EditText editTextUserID;
    private EditText editTextContact;
    private EditText editTextEmail;
    private EditText editTextShopname;
    private ChangeProfileDialogListener listener;
    String userid,contact,email,shopname ;

    public DialogChangeProfile(String shopname,String userid, String contact, String email) {
        this.shopname = shopname;
        this.userid = userid;
        this.contact = contact;
        this.email = email;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_change_profile, null);
        builder.setView(view)
                .setTitle("Change Profile")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String shopnamestr = editTextShopname.getText().toString();
                        String useridstr = editTextUserID.getText().toString();
                        String contactstr = editTextContact.getText().toString();
                        String emailstr = editTextEmail.getText().toString();
                        listener.changeProfile(shopnamestr,useridstr, contactstr, emailstr);


                    }
                });
        editTextShopname= view.findViewById(R.id.change_profile_shopname_EditTxt);
        editTextUserID = view.findViewById(R.id.change_profile_user_id_EditTxt);
        editTextContact = view.findViewById(R.id.change_profile_contact_no_EditTxt);
        editTextEmail = view.findViewById(R.id.change_profile_email_id_EditTxt);
        editTextShopname.setText(shopname);
        editTextUserID.setText(userid);
        editTextContact.setText(contact);
        editTextEmail.setText(email);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ChangeProfileDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ChangeProfileDialogListener");
        }
    }

    /* Define Method That Call On Dialog Box Buttons */
    public interface ChangeProfileDialogListener {
        void changeProfile(String shopname, String userid, String contact, String email);
    }

}
