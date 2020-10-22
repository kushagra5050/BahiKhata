package com.divy.prakash.paathsala.bahikhata.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.List;

/* This Class Is Used To Make A Dialog Box To Get Password */
public class DialogAddItem extends AppCompatDialogFragment  {

    String productname_str, productmeasuingunit_str,productquantity,productprice;

    private EditText editTextProductName;
    private TextView textViewProductMeasuringUnit;
    private EditText editTextProductQuantity;
    private EditText editTextProductPrice;
    private Spinner spinner_Munit;
    private AddItemDialogListener listener;
    List<String> list ;

    public DialogAddItem(String productname_str, String productmeasuingunit_str, String productquantity, String productprice) {
        this.productname_str = productname_str;
        this.productmeasuingunit_str = productmeasuingunit_str;
        this.productquantity = productquantity;
        this.productprice = productprice;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_add_item, null);
        builder.setView(view)
                .setTitle("Add Item")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String productname = editTextProductName.getText().toString();
                        String quantity = editTextProductQuantity.getText().toString();
                        String price = editTextProductPrice.getText().toString();
                        String unit = textViewProductMeasuringUnit.getText().toString();
                        if (quantity.isEmpty())
                        {
                            quantity = "0";
                        }
                        if (price.isEmpty())
                        {
                            price="0";
                        }
                        listener.addItem(productname, unit, Double.parseDouble(quantity),Double.parseDouble(price));


                    }
                });
        editTextProductName = view.findViewById(R.id.dialog_add_item_product_name_EditTxt);
        textViewProductMeasuringUnit = view.findViewById(R.id.dialog_add_item_product_measuring_unit_EditTxt);
        editTextProductPrice = view.findViewById(R.id.dialog_add_item_amount_EditTxt);
        editTextProductQuantity = view.findViewById(R.id.dialog_add_item_product_quantity_EditTxt);
        spinner_Munit = view.findViewById(R.id.dialog_add_item_spinner_measuring_unit);
        spinner_Munit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  Munit = (String) spinner_Munit.getSelectedItem();
                textViewProductMeasuringUnit.setText(Munit);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                  spinner_Munit.setSelection(1);
            }
        });
        editTextProductName.setText(productname_str);
        editTextProductQuantity.setText(productquantity);
        editTextProductPrice.setText(productprice);
        textViewProductMeasuringUnit.setText(productmeasuingunit_str);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddItemDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddItemDialogListener");
        }
    }



    /* Define Method That Call On Dialog Box Buttons */
    public interface AddItemDialogListener {
        void addItem(String productname, String productmeasuingunit, double quantity, double price);
    }

}
