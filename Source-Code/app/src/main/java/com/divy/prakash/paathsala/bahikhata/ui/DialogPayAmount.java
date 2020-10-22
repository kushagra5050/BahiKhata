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
public class DialogPayAmount extends AppCompatDialogFragment {

    private EditText editTextAmount;

    private PayAmountDialogListener listener;
    String amount ;

    public DialogPayAmount(String amount) {
        this.amount = amount;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_pay_amount, null);
        builder.setView(view)
                .setTitle("Enter Amount You Want to Pay")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String amountstr = editTextAmount.getText().toString();


                        listener.payAmount(Double.parseDouble(amountstr));


                    }
                });
        editTextAmount = view.findViewById(R.id.dialog_pay_amount_amount_EditTxt);
        editTextAmount.setText(amount);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (PayAmountDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ChangePasswordDialogListener");
        }
    }

    /* Define Method That Call On Dialog Box Buttons */
    public interface PayAmountDialogListener {
        void payAmount(Double amount);
    }

}
