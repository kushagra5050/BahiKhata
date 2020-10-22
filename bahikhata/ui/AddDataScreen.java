package com.divy.prakash.paathsala.bahikhata.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.divy.prakash.paathsala.bahikhata.DatabaseHelper.CustomerDBHelper;
import com.divy.prakash.paathsala.bahikhata.utils.AddItem;
import com.divy.prakash.paathsala.bahikhata.utils.CustomerConstants;
import com.divy.prakash.paathsala.bahikhata.utils.PreferenceUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class AddDataScreen extends AppCompatActivity implements DialogAddItem.AddItemDialogListener,
        DialogMatchPassword.MatchPasswordDialogListener {
    String custname, cust_address, cust_amount, cust_contact, cust_id;
    TextView add_item_name_txtview, add_item_address_txtview, add_contact_txtview, add_price_txtview, add_total_price_txtview;
    int flag_add_item = 0;                                 /* Used To Set Value To Add Item Dialog Box*/
    String procuctname, productunit;
    double productquantity;
    double productprice, ctotalprice = 0.0, ptotalprice = 0.0;
    Cursor custcursor;
    CoordinatorLayout rootLayout;
    private ArrayList<AddItem> arrayList;
    private RecyclerView mRecyclerView;
    private AddItemAdapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private SQLiteDatabase mDatabase;
    private CustomerAdapter customerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Used To Enable Back Button On Action Button */
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(30);

        /* Used To Set Color Of Status Bar If Sdk Is > Lollipop */
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        /* If Action Bar Is Null Then Enable Action Bar */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        /* Set Title Of Action Bar */
        getSupportActionBar().setTitle("Add Product Details");

        setContentView(R.layout.activity_add__item__screen);


        add_contact_txtview = findViewById(R.id.add_item_contact_txtview);
        add_item_name_txtview = findViewById(R.id.add_item_name_txtview);
        add_item_address_txtview = findViewById(R.id.add_item_Address_txtview);
        add_price_txtview = findViewById(R.id.add_item_price_txtview);
        add_total_price_txtview = findViewById(R.id.add_item_total_price_txtview);
        rootLayout = findViewById(R.id.coordinatorLayout);
        cust_id = getIntent().getExtras().getString(CustomerConstants.Customer_Data._ID);
        custname = getIntent().getExtras().getString(CustomerConstants.Customer_Data.COLUMN_NAME);
        cust_address = getIntent().getExtras().getString(CustomerConstants.Customer_Data.COLUMN_ADDRESS);
        cust_contact = getIntent().getExtras().getString(CustomerConstants.Customer_Data.COLUMN_CONTACT);
        cust_amount = getIntent().getExtras().getString(CustomerConstants.Customer_Data.COLUMN_AMOUNT);

        add_item_name_txtview.setText(custname);
        add_item_address_txtview.setText(cust_address);
        add_contact_txtview.setText(cust_contact);
        add_price_txtview.setText("Total Price:" + ctotalprice);

        CustomerDBHelper dbHelper = new CustomerDBHelper(getApplicationContext());
        mDatabase = dbHelper.getWritableDatabase();

        /* Set Event On Floating Button */
        FloatingActionButton fab = findViewById(R.id.fab_add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemDialog();

            }
        });
        arrayList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycleView_add_item);
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(getApplicationContext());
        mAdapter = new AddItemAdapter(arrayList);
        mRecyclerView.setLayoutManager(mlayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        arrayList.add(new AddItem("Product Name", "Measuing Unit", "Quantity", "Price/Unit", "Total Price"));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (viewHolder.getAdapterPosition() == 0) {
                    mAdapter.notifyDataSetChanged();
                    Toasty.warning(getApplicationContext(), "This can not be deleted", Toasty.LENGTH_SHORT).show();
                } else {
                    removeItem(viewHolder.getAdapterPosition());
                    showSnackbar();
                }


            }
        }).attachToRecyclerView(mRecyclerView);


    }


    /* Used To Go Back To Previous Activity On Click*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menu_save) {
            double tempprice = 0.0;
            for (int i = 1; i < arrayList.size(); i++) {
                AddItem addItem = arrayList.get(i);
                tempprice = tempprice + Double.parseDouble(addItem.getTotalprice());
            }
            if (ctotalprice <= 0.0) {
                Toasty.warning(getApplicationContext(), "Total Amount is Zero", Toasty.LENGTH_SHORT).show();
                Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();

            } else if (tempprice == ctotalprice) {
                matchPasswordDialog();
            } else {

                Toasty.warning(getApplicationContext(), "Goto Home Screen", Toasty.LENGTH_SHORT).show();
                Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity(intent);
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* Used To Go Back To Previous Activity On Back Press */
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate The Menu This Adds Items To The Action Bar If It Is Present */

        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    /* Method Used To Call Add Customer Dialog */
    private void addItemDialog() {
        if (flag_add_item == 0) {
            /* If Flag Add Item Is 0 Then It Is First Time Call Mean There Is No Error Occur So Pass Parameters NULL*/
            DialogAddItem dialogAddItem = new DialogAddItem(null, null, null, null);
            dialogAddItem.show(getSupportFragmentManager(), "Add Item");
        } else if (flag_add_item == 1) {
            /* If Flag Add Item Is 1 Then It Is Not First Time Call Mean There Is  Error Occur So Pass Value To Dialog Box To Set */
            DialogAddItem dialogAddItem = new DialogAddItem(procuctname, productunit, String.valueOf(productquantity), String.valueOf(productprice));
            dialogAddItem.show(getSupportFragmentManager(), "Add Item");
            /* After Calling Set Value To 0 For Default Mode */
            flag_add_item = 0;
        }


    }

    // this is used to show snack bar
    public void showSnackbar() {

        Snackbar snackbar = Snackbar.make(rootLayout, "Item Deleted", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setDuration(5000)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        arrayList.add(Integer.parseInt(PreferenceUtils.getPosition(getApplicationContext())),new AddItem(
                                PreferenceUtils.getProductName(getApplicationContext()),
                                PreferenceUtils.getMeasuringUnit(getApplicationContext()),
                                PreferenceUtils.getQuantity(getApplicationContext()),
                                PreferenceUtils.getPricePerUnit(getApplicationContext()),
                                PreferenceUtils.getTotalPrice(getApplicationContext())));
                        ptotalprice = Double.parseDouble(add_total_price_txtview.getText().toString());
                        ctotalprice = ptotalprice + Double.parseDouble(PreferenceUtils.getTotalPrice(getApplicationContext()));
                        add_total_price_txtview.setText(String.valueOf(ctotalprice));
                        add_price_txtview.setText("Total Price:" + ctotalprice);
                        mAdapter.notifyDataSetChanged();
                    }
                })
                ;

        snackbar.show();
    }

    @Override
    public void addItem(String product_name, String productmeasuingunit, double quantity, double price) {

        if ((product_name.isEmpty()) || (productmeasuingunit.isEmpty() || (quantity == 0) || (price == 0.0))) {
            Toasty.warning(getApplicationContext(), "Input Fields are Empty", Toasty.LENGTH_SHORT).show();
            Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
            flag_add_item = 1;
            procuctname = product_name;
            productunit = productmeasuingunit;
            productquantity = quantity;
            productprice = price;
            addItemDialog();
        } else if ((quantity == 0) || (price == 0.0)) {
            Toasty.warning(getApplicationContext(), "Enter Valid Value", Toasty.LENGTH_SHORT).show();
            Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
            flag_add_item = 1;
            procuctname = product_name;
            productunit = productmeasuingunit;
            productquantity = quantity;
            productprice = price;
            addItemDialog();
        } else {
            Double totalprice = 0.0;
            if (productmeasuingunit.equals("Kilo Gram(Kg)")) {
                totalprice = price * quantity;
            } else if (productmeasuingunit.equals("Liter(L)")) {
                totalprice = price * quantity;
            } else if (productmeasuingunit.equals("Dozen")) {
                totalprice = price * quantity;
            } else if (productmeasuingunit.equals("Pieces")) {
                totalprice = price * quantity;
            } else if (productmeasuingunit.equals("Unit")) {
                totalprice = price * quantity;
            }
            arrayList.add(new AddItem(product_name, productmeasuingunit, String.valueOf(quantity), String.valueOf(price), String.valueOf(totalprice)));
            ptotalprice = Double.parseDouble(add_total_price_txtview.getText().toString());
            ctotalprice = ptotalprice + totalprice;
            add_total_price_txtview.setText(String.valueOf(ctotalprice));
            add_price_txtview.setText("Total Price:" + ctotalprice);
            mAdapter.notifyDataSetChanged();


        }


    }

    /* Method Used To Update Customer Amount */
    private boolean updatedCustomerAmount(Double Amount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CustomerConstants.Customer_Data.COLUMN_AMOUNT, Amount);
        int up = mDatabase.update(CustomerConstants.Customer_Data.TABLE_NAME, contentValues, CustomerConstants.Customer_Data._ID + "=" + cust_id, null);

        return up > 0;

    }

    public void removeItem(int position) {


        AddItem addItem = arrayList.get(position);
        String productnamestr = addItem.getProductname();
        String quantitystr = addItem.getQuantity();
        String measuringunitstr = addItem.getProductmunit();
        String priceperunitstr = addItem.getPrice();
        String totalpricestr = addItem.getTotalprice();
        PreferenceUtils.savePosition(String.valueOf(position),getApplicationContext());
        PreferenceUtils.saveProductName(productnamestr, getApplicationContext());
        PreferenceUtils.saveQuantity(quantitystr, getApplicationContext());
        PreferenceUtils.saveMeasuringUnit(measuringunitstr, getApplicationContext());
        PreferenceUtils.savePricePerUnit(priceperunitstr, getApplicationContext());
        PreferenceUtils.saveTotalPrice(totalpricestr, getApplicationContext());
        double price = Double.parseDouble(addItem.getTotalprice());
        arrayList.remove(position);
        if (ctotalprice > 0) {
            ctotalprice = ctotalprice - price;
        }
        add_total_price_txtview.setText(String.valueOf(ctotalprice));
        add_price_txtview.setText("Total Price:" + ctotalprice);
        // Adapter.notifyDataSetChanged();
        mAdapter.notifyItemRemoved(position);

    }


    /* Method Used To Get Data */
    private Cursor getCustomerData(String id) {
        Cursor cursor = mDatabase.query(true, CustomerConstants.Customer_Data.TABLE_NAME, new String[]{CustomerConstants.Customer_Data._ID, CustomerConstants.Customer_Data.COLUMN_NAME, CustomerConstants.Customer_Data.COLUMN_USERID, CustomerConstants.Customer_Data.COLUMN_ADDRESS, CustomerConstants.Customer_Data.COLUMN_CONTACT, CustomerConstants.Customer_Data.COLUMN_AMOUNT}, CustomerConstants.Customer_Data._ID + "=" + id, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /* Used To Create Match Password Dialog Box */
    private void matchPasswordDialog() {
        DialogMatchPassword dialogMatchPassword = new DialogMatchPassword();
        dialogMatchPassword.show(getSupportFragmentManager(), "Give Credit");

    }

    @Override
    public void matchPassword(String password) {
        {
            /* Match Password To Update Amount In Table */
            if (PreferenceUtils.getPassword(getApplicationContext()).equals(password)) {
                /* Get Amount From Database To Update Or Pay Amount */
                Cursor cursor = getCustomerData(String.valueOf(cust_id));
                double amount = Double.valueOf(cursor.getString(5));
                amount = amount - ctotalprice;
                updatedCustomerAmount(amount);
                Toasty.success(getApplicationContext(), "Data is Successfully Added", Toasty.LENGTH_SHORT).show();
                ctotalprice = 0.0;
                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity(intent);
                finish();
            } else {
                Toasty.warning(getApplicationContext(), "Wrong Password", Toasty.LENGTH_SHORT).show();
                Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                /* Call Match Password Dialog */
                matchPasswordDialog();
            }
        }
    }
}

