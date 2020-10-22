package com.divy.prakash.paathsala.bahikhata.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.divy.prakash.paathsala.bahikhata.DatabaseHelper.CustomerDBHelper;
import com.divy.prakash.paathsala.bahikhata.dao.SignupDAO;
import com.divy.prakash.paathsala.bahikhata.dao.SignupDAOImpl;
import com.divy.prakash.paathsala.bahikhata.utils.CustomMethods;
import com.divy.prakash.paathsala.bahikhata.utils.CustomerConstants;
import com.divy.prakash.paathsala.bahikhata.utils.PreferenceUtils;
import com.divy.prakash.paathsala.bahikhata.validate.CustomerDialog;
import com.divy.prakash.paathsala.bahikhata.validate.SignupScreenValidate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import es.dmoral.toasty.Toasty;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DialogCustomerAdd.AddCustomerDialogListener,
        PopupMenu.OnMenuItemClickListener,
        DialogMatchPassword.MatchPasswordDialogListener,
        DialogPayAmount.PayAmountDialogListener,
        DialogAreYouSure.AreYouSureDialogListener,
        DialogUpdateCustomerData.UpdateCustomerDataDialogListener,
        DialogChangeProfile.ChangeProfileDialogListener,
        DialogChangePassword.ChangePasswordDialogListener {
    private static final int TIMEDELAY = 15000;
    private static long BACKPRESS;
    NavigationView navigationView;
    int user_id;                                           /* Used To Set User Id */
    int cust_id;                                           /* Used To Set Customer Id */
    String custname;                                       /* Used To Set Customer Name */
    Double custamount = 0.0;                               /* Used To Set Customer Amount */
    int flag_for_match_password = 0;                       /* Used To Set Value To  Select Match Password Used For*/
    int flag_sort_by = 0;                                  /* Used To Set Value To Sort Recycler View By Different Parameters */
    int flag_add_customer = 0;                             /* Used To Set Value To Add Customer Dialog Box*/
    int flag_pay_amount = 0;                               /* Used To Set Value To Pay Amount Dialog Box*/
    int flag_update_customer_data = 0;                     /* Used To Set Value To Update Customer Data Dialog Box*/
    int flag_change_profile = 0;                           /* Used To Set Value To Change Profile Dialog Box*/
    int flag_change_password = 0;                          /* Used To Set Value To Change Password Dialog Box*/

    String username, contact, address, amount;
    Cursor cursor1;
    String cust_name_str, cust_contact_str, cust_address_str;
    TextView home_screen_nav_header_txt_view, home_screen_shopname_nav_header_txt_view, home_screen_userid_nav_header_txt_view;
    String updatecustname, updatecontact, updateaddress;
    MenuItem sort_name, sort_amount;
    SignupDAO signupDAO;
    String useridstr, shopname_str, useremail_str, usercontact_str, user_password;
    String update_useridstr, update_shopname_str, update_useremail_str, update_usercontact_str, update_password;
    Cursor usercursor;
    private SQLiteDatabase mDatabase;
    private CustomerAdapter customerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Used To Set Color Of Status Bar If Sdk Is > Lollipop */
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);

        View headerview = navigationView.inflateHeaderView(R.layout.nav_header_home_screen);
        /* Get Id Of Nav Header Home Screen And Initialize Components */
        home_screen_nav_header_txt_view = headerview.findViewById(R.id.home_screen_nav_header_txt_view);
        home_screen_shopname_nav_header_txt_view = headerview.findViewById(R.id.home_screen_shopname_nav_header_txt_view);
        home_screen_userid_nav_header_txt_view = headerview.findViewById(R.id.home_screen_userid_nav_header_txt_view);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        flag_sort_by = Integer.parseInt(PreferenceUtils.getFlagSortBy(getApplicationContext()));
        signupDAO = new SignupDAOImpl(getApplicationContext());
        String id = PreferenceUtils.getId(getApplicationContext());
        user_id = Integer.parseInt(id);
        usercursor = signupDAO.getUserData(id);
        shopname_str = usercursor.getString(1);
        useremail_str = usercursor.getString(2);
        usercontact_str = usercursor.getString(3);
        useridstr = usercursor.getString(4);

        /* Get Data When It Is First Time Log In And Show The Shopname, Userid */
        String result = "";
        int count = 0;
        boolean bol = true;
        /* Used To Get Two Character Of Shopname */
        for (int i = 0; i < shopname_str.length(); i++) {
            if (shopname_str.charAt(i) == ' ') {
                bol = true;
            } else if ((shopname_str.charAt(i) != ' ') && (bol == true) && (count <= 2)) {
                result += (shopname_str.charAt(i));
                bol = false;
                count++;
            }

            home_screen_nav_header_txt_view.setText(result.toUpperCase());
            home_screen_shopname_nav_header_txt_view.setText(shopname_str.toUpperCase());
            home_screen_userid_nav_header_txt_view.setText(useridstr);
        }


        /* Recycler View Initialize */
        CustomerDBHelper dbHelper = new CustomerDBHelper(getApplicationContext());
        mDatabase = dbHelper.getWritableDatabase();
        final RecyclerView recyclerView = findViewById(R.id.recycleView_add_customer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customerAdapter = new CustomerAdapter(this, getAllItems());
        recyclerView.setAdapter(customerAdapter);
        /* Define Listener Or Method On Components Of Recycler View */
        customerAdapter.setOnItemClickListener(new CustomerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String id, String name) {
                Cursor custdata = getCustomerData(id);
                Intent intent = new Intent(getApplicationContext(), AddDataScreen.class);
                intent.putExtra(CustomerConstants.Customer_Data._ID, custdata.getString(0));
                intent.putExtra(CustomerConstants.Customer_Data.COLUMN_NAME, custdata.getString(1));
                intent.putExtra(CustomerConstants.Customer_Data.COLUMN_ADDRESS, custdata.getString(3));
                intent.putExtra(CustomerConstants.Customer_Data.COLUMN_CONTACT, custdata.getString(4));
                intent.putExtra(CustomerConstants.Customer_Data.COLUMN_AMOUNT, custdata.getString(5));
                startActivity(intent);
                finish();
            }

            @Override
            public void onView(int position, String id, String name) {
                Toast.makeText(HomeScreen.this, "This is View click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPay(int position, String id, String name) {
                cust_id = Integer.parseInt(id);
                custname = name;
                payAmountDialog();
            }

            @Override
            public void onEdit(int position, View view, String id, String name) {
                /* Show Popup Menu For Edit Or Delete */
                showPopup(view);
                cust_id = Integer.parseInt(id);
                custname = name;

            }


        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.UP | ItemTouchHelper.ANIMATION_TYPE_SWIPE_SUCCESS) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                customerAdapter.swapCursor(getAllItems());
            }
        }).attachToRecyclerView(recyclerView);
        /* Set Event On Floating Button */
        FloatingActionButton fab = findViewById(R.id.fab_add_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomerDialog();
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    /* Used To Exit On Back Press */
    @Override
    public void onBackPressed() {
        BACKPRESS = System.currentTimeMillis();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            Toasty.normal(getApplicationContext(), "Press back again to exit", Toasty.LENGTH_SHORT).show();
        } else if (TIMEDELAY + BACKPRESS > System.currentTimeMillis()) {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate The Menu This Adds Items To The Action Bar If It Is Present */

        getMenuInflater().inflate(R.menu.menu_sort_by, menu);
        sort_name = menu.findItem(R.id.menu_sort_by_name);
        sort_amount = menu.findItem(R.id.menu_sort_by_amount);
        if (flag_sort_by == 0) {
            sort_name.setChecked(true);
        } else if (flag_sort_by == 1) {
            sort_amount.setChecked(true);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_sort_by_name) {

            item.setChecked(true);
            /* Used To Sort Recycler By Name */
            PreferenceUtils.saveFlagSortBy(String.valueOf(0), getApplicationContext());
            flag_sort_by = 0;
            customerAdapter.swapCursor(getAllItems());
            return true;
        } else if (id == R.id.menu_sort_by_amount) {

            item.setChecked(true);
            /* Used To Sort Recycler By Amount */
            PreferenceUtils.saveFlagSortBy(String.valueOf(1), getApplicationContext());
            flag_sort_by = 1;
            customerAdapter.swapCursor(getAllItems());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    /* Used To Define Events Of Navigation Drawer */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        /* Handle Navigation View Item Clicks Here */
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            /* Handle The Camera Action */
        } else if (id == R.id.nav_logout) {
            /* Handle The Logout Action And Set Shared Preferences Values To Null */
            PreferenceUtils.saveId("", getApplicationContext());
            PreferenceUtils.saveShopName("", getApplicationContext());
            PreferenceUtils.saveEmail("", getApplicationContext());
            PreferenceUtils.saveContact("", getApplicationContext());
            PreferenceUtils.saveUserID("", getApplicationContext());
            PreferenceUtils.savePassword("", getApplicationContext());
            Intent intent1 = new Intent(getApplicationContext(), LogINScreen.class);
            startActivity(intent1);
            finish();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_chage_profile) {
            changeProfileDialog();
        } else if (id == R.id.nav_change_password) {
            changePasswordDialog();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void removeItem(long id) {

        mDatabase.delete(CustomerConstants.Customer_Data.TABLE_NAME, CustomerConstants.Customer_Data._ID + "=" + id, null);
        customerAdapter.swapCursor(getAllItems());
    }

    /* Method Used To Call Add Customer Dialog */
    private void addCustomerDialog() {
        if (flag_add_customer == 0) {
            /* If Flag Add Customer Is 0 Then It Is First Time Call Mean There Is No Error Occur So Pass Parameters NULL*/
            DialogCustomerAdd dialogCustomerAdd = new DialogCustomerAdd(null, null, null);
            dialogCustomerAdd.show(getSupportFragmentManager(), "Register Customer");
        } else if (flag_add_customer == 1) {
            /* If Flag Add Customer Is 1 Then It Is Not First Time Call Mean There Is  Error Occur So Pass Value To Dialog Box To Set */
            DialogCustomerAdd dialogCustomerAdd = new DialogCustomerAdd(username, contact, address);
            dialogCustomerAdd.show(getSupportFragmentManager(), "Register Customer");
            /* After Calling Set Value To 0 For Default Mode */
            flag_add_customer = 0;
        }


    }


    /* Used To Create Match Password Dialog Box */
    private void matchPasswordDialog() {
        DialogMatchPassword dialogMatchPassword = new DialogMatchPassword();
        dialogMatchPassword.show(getSupportFragmentManager(), "Delete Customer Data");

    }

    /* Used To Create Pay Amount Dialog Box */
    private void payAmountDialog() {
        if (flag_pay_amount == 0) {
            /* If Flag Pay Amount Is 0 Then It Is First Time Call Mean There Is No Error Occur So Pass Parameters NULL*/
            DialogPayAmount dialogPayAmount = new DialogPayAmount(null);
            dialogPayAmount.show(getSupportFragmentManager(), "Pay Amount");
        } else if (flag_pay_amount == 1) {
            /* If Flag Pay Amount Is 1 Then It Is Not First Time Call Mean There Is  Error Occur So Pass Value To Dialog Box To Set */
            DialogPayAmount dialogPayAmount = new DialogPayAmount(amount);
            dialogPayAmount.show(getSupportFragmentManager(), "Pay Amount");
            /* After Calling Set Value To 0 For Default Mode */
            flag_pay_amount = 0;
        }
    }

    /* Used To Create Change Password Dialog Box */
    private void changePasswordDialog() {
        if (flag_change_password == 0) {
            /* If Flag Change Password Is 0 Then It Is First Time Call Mean There Is No Error Occur So Pass Parameters NULL*/
            DialogChangePassword dialogChangePassword = new DialogChangePassword(null);
            dialogChangePassword.show(getSupportFragmentManager(), "Change Password");
        } else if (flag_change_password == 1) {
            /* If Flag Change Password Is 1 Then It Is Not First Time Call Mean There Is  Error Occur So Pass Value To Dialog Box To Set */
            DialogChangePassword dialogChangePassword = new DialogChangePassword(update_password);
            dialogChangePassword.show(getSupportFragmentManager(), "Change Password");
            /* After Calling Set Value To 0 For Default Mode */
            flag_change_password = 0;
        }

    }

    /* Used To Create Pay Amount Dialog Box */
    private void changeProfileDialog() {
        if (flag_change_profile == 0) {
            /* If Flag Change Profile Is 0 Then It Is First Time Call Mean There Is No Error Occur So Pass Parameters NULL*/
            DialogChangeProfile dialogChangeProfile = new DialogChangeProfile(shopname_str, useridstr, usercontact_str, useremail_str);
            dialogChangeProfile.show(getSupportFragmentManager(), "Change Profile");
        } else if (flag_change_profile == 1) {
            /* If Flag Change Profile  Is 1 Then It Is Not First Time Call Mean There Is  Error Occur So Pass Value To Dialog Box To Set */
            DialogChangeProfile dialogChangeProfile = new DialogChangeProfile(update_shopname_str, update_useridstr, update_usercontact_str, update_useremail_str);
            dialogChangeProfile.show(getSupportFragmentManager(), "Change Profile");
            /* After Calling Set Value To 0 For Default Mode */
            flag_change_profile = 0;
        }


    }

    // method create dialog box for Are You Sure
    public void AreYouSure_Dialog() {
        DialogAreYouSure dialogAreYouSure = new DialogAreYouSure(custname);
        dialogAreYouSure.show(getSupportFragmentManager(), "Are You Sure?");

    }

    // method create dialog box for Update Customer Data
    public void updateCustomerData() {
        if (flag_update_customer_data == 0) {
            /* If Flag Update Customer Data Is 0 Then It Is First Time Call Mean There Is No Error Occur So Pass Parameters NULL*/
            DialogUpdateCustomerData dialogUpdateCustomerData = new DialogUpdateCustomerData(cust_name_str, cust_contact_str, cust_address_str);
            dialogUpdateCustomerData.show(getSupportFragmentManager(), "Update Customer Data");
        } else if (flag_update_customer_data == 1) {
            /* If Flag Update Customer Data Is 1 Then It Is Not First Time Call Mean There Is  Error Occur So Pass Value To Dialog Box To Set */
            DialogUpdateCustomerData dialogUpdateCustomerData = new DialogUpdateCustomerData(updatecustname, updatecontact, updateaddress);
            dialogUpdateCustomerData.show(getSupportFragmentManager(), "Update Customer Data");
            /* After Calling Set Value To 0 For Default Mode */
            flag_update_customer_data = 0;
        }


    }

    /*
    Database Methods
    Method Used To Get All Data From Database Where
    */
    private Cursor getAllItems() {
        /* Based On Flag Sort Value Recycler View Is Sort */
        switch (flag_sort_by) {
            case 0: {
                return mDatabase.query(CustomerConstants.Customer_Data.TABLE_NAME,
                        null,
                        CustomerConstants.Customer_Data.COLUMN_USERID + "=" + user_id,
                        null,
                        null,
                        null,
                        CustomerConstants.Customer_Data.COLUMN_NAME + " ASC"
                );
            }
            case 1: {
                return mDatabase.query(CustomerConstants.Customer_Data.TABLE_NAME,
                        null,
                        CustomerConstants.Customer_Data.COLUMN_USERID + "=" + user_id,
                        null,
                        null,
                        null,
                        CustomerConstants.Customer_Data.COLUMN_AMOUNT + " ASC"
                );
            }
            default: {
                return null;
            }

        }


    }


    /* Method Used To Get Data */
    private Cursor getCustomerData(String id) {
        Cursor cursor = mDatabase.query(true, CustomerConstants.Customer_Data.TABLE_NAME, new String[]{CustomerConstants.Customer_Data._ID, CustomerConstants.Customer_Data.COLUMN_NAME, CustomerConstants.Customer_Data.COLUMN_USERID, CustomerConstants.Customer_Data.COLUMN_ADDRESS, CustomerConstants.Customer_Data.COLUMN_CONTACT, CustomerConstants.Customer_Data.COLUMN_AMOUNT}, CustomerConstants.Customer_Data._ID + "=" + id, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /* Method Used To Update Customer Data */
    private boolean updatedCustomerData(String id, String name, String contact, String address) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CustomerConstants.Customer_Data.COLUMN_NAME, name);
        contentValues.put(CustomerConstants.Customer_Data.COLUMN_CONTACT, contact);
        contentValues.put(CustomerConstants.Customer_Data.COLUMN_ADDRESS, address);
        int up = mDatabase.update(CustomerConstants.Customer_Data.TABLE_NAME, contentValues, CustomerConstants.Customer_Data._ID + "=" + id, null);
        return up > 0;

    }

    /* Method Used To Check Duplicate Customer Name */
    public boolean isCustUserExists(String name) {
        Cursor cursor = mDatabase.query(CustomerConstants.Customer_Data.TABLE_NAME,// Selecting Table
                new String[]{CustomerConstants.Customer_Data._ID, CustomerConstants.Customer_Data.COLUMN_NAME, CustomerConstants.Customer_Data.COLUMN_USERID, CustomerConstants.Customer_Data.COLUMN_ADDRESS, CustomerConstants.Customer_Data.COLUMN_CONTACT, CustomerConstants.Customer_Data.COLUMN_CONTACT},//Selecting columns want to query
                CustomerConstants.Customer_Data.COLUMN_NAME + "=?",
                new String[]{name},//Where clause
                null, null, null);

        return cursor != null && cursor.moveToFirst() && cursor.getCount() > 0;

    }

    /* Method Used To Insert Data In Database */
    @Override
    public void insertData(String username, String contact, String address) {
        if ((username.isEmpty()) || (contact.isEmpty()) || (address.isEmpty())) {
            /* If Input Fields Are Empty Then Show Error Message */
            Toasty.warning(getApplicationContext(), "Input Fields are Empty", Toasty.LENGTH_SHORT).show();
            Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
            /* Set Flag Add Customer Value To 1 So That It Pass Value To Dialog Box To Set Text*/
            flag_add_customer = 1;
            this.username = username;
            this.contact = contact;
            this.address = address;
            addCustomerDialog();
        } else {
            /* Method Used For Validation Contact Number */
            CustomerDialog customer_dialog = new CustomerDialog();
            if (customer_dialog.customerValidateContact(contact) == false) {
                Toasty.warning(getApplicationContext(), "Enter Valid Number", Toasty.LENGTH_LONG).show();
                Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                /* Set Flag Add Customer Value To 1 So That It Pass Value To Dialog Box To Set Text*/
                flag_add_customer = 1;
                this.username = username;
                this.contact = contact;
                this.address = address;
                addCustomerDialog();
            } else {
                /* Method To Check That Is There Customer That Have Same Name As Given Or Check Duplicate Customer And Convert Name In To Title Case */
                CustomMethods customMethods = new CustomMethods();
                if (isCustUserExists(customMethods.convertToTitleCase(username)) == true) {
                    Toasty.warning(getApplicationContext(), username + "is Already Exists", Toasty.LENGTH_SHORT).show();
                    Toasty.error(getApplicationContext(), "Try Another Name ", Toasty.LENGTH_SHORT).show();
                    /* Set Flag Add Customer Value To 1 So That It Pass Value To Dialog Box To Set Text*/
                    flag_add_customer = 1;
                    this.username = username;
                    this.contact = contact;
                    this.address = address;
                    addCustomerDialog();
                } else {
                    /* Insert Data In Customer Table */
                    ContentValues cv = new ContentValues();
                    CustomMethods titleCase = new CustomMethods();
                    cv.put(CustomerConstants.Customer_Data.COLUMN_NAME, titleCase.convertToTitleCase(username));
                    cv.put(CustomerConstants.Customer_Data.COLUMN_CONTACT, contact);
                    cv.put(CustomerConstants.Customer_Data.COLUMN_ADDRESS, address);
                    cv.put(CustomerConstants.Customer_Data.COLUMN_AMOUNT, 0.0);
                    cv.put(CustomerConstants.Customer_Data.COLUMN_USERID, user_id);
                    mDatabase.insert(CustomerConstants.Customer_Data.TABLE_NAME, null, cv);
                    /* Notify Changes In Recyclerview */
                    customerAdapter.swapCursor(getAllItems());
                    Toasty.success(getApplicationContext(), username + " is Added", Toasty.LENGTH_SHORT).show();
                }

            }


        }


    }

    /* Method Used To Update Customer Amount */
    private boolean updatedCustomerAmount(Double Amount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CustomerConstants.Customer_Data.COLUMN_AMOUNT, Amount);
        int up = mDatabase.update(CustomerConstants.Customer_Data.TABLE_NAME, contentValues, CustomerConstants.Customer_Data._ID + "=" + cust_id, null);
        customerAdapter.swapCursor(getAllItems());
        return up > 0;

    }


    /* Method Used To Show Popup Menu */
    public void showPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu_add_customer);
        popupMenu.show();
    }

    /* Pop Up Menu Event */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popup_menu_add_customer_edit: {

                /* Get Data From Database And Set To Input Field */
                cursor1 = getCustomerData(String.valueOf(cust_id));
                cust_name_str = cursor1.getString(1);
                cust_contact_str = cursor1.getString(4);
                cust_address_str = cursor1.getString(3);
                updateCustomerData();
                return true;
            }
            case R.id.popup_menu_add_customer_delete: {
                flag_for_match_password = 1;
                AreYouSure_Dialog();
                return true;
            }

            default: {
                return false;
            }
        }
    }


    @Override
    public void matchPassword(String password) {
        switch (flag_for_match_password) {
            case 1: {
                /* Match Password To Update Data In Table */
                if (PreferenceUtils.getPassword(getApplicationContext()).equals(password)) {
                    removeItem(cust_id);
                    flag_for_match_password = 0;
                    Toasty.success(getApplicationContext(), custname + " Data is Deleted", Toasty.LENGTH_SHORT).show();
                } else {
                    Toasty.warning(getApplicationContext(), "Wrong Password", Toasty.LENGTH_SHORT).show();
                    Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                    /* Call Match Password Dialog */
                    matchPasswordDialog();
                }

            }
            break;
            case 2: {
                /* Match Password To Update Amount In Table */
                if (PreferenceUtils.getPassword(getApplicationContext()).equals(password)) {
                    /* Get Amount From Database To Update Or Pay Amount */
                    Cursor cursor = getCustomerData(String.valueOf(cust_id));
                    double amount = Double.valueOf(cursor.getString(5));
                    amount = amount + custamount;
                    updatedCustomerAmount(amount);
                    flag_for_match_password = 0;
                    Toasty.success(getApplicationContext(), "Payment of " + custamount + " by " + custname + " is Successful.", Toasty.LENGTH_SHORT).show();
                    custamount = 0.0;
                } else {
                    Toasty.warning(getApplicationContext(), "Wrong Password", Toasty.LENGTH_SHORT).show();
                    Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                    /* Call Match Password Dialog */
                    matchPasswordDialog();
                }

            }
            break;
            case 3: {

                /* Match Password To Update Data In Table */
                if (PreferenceUtils.getPassword(getApplicationContext()).equals(password)) {
                    CustomerDialog customer_dialog = new CustomerDialog();
                    CustomMethods customMethods = new CustomMethods();
                    /* Set Message If Edit Text Is Empty */
                    if ((updatecustname.isEmpty()) || (updatecontact.isEmpty()) || (updateaddress.isEmpty())) {
                        Toasty.warning(getApplicationContext(), "Input Fields are Empty", Toasty.LENGTH_SHORT).show();
                        Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                        flag_update_customer_data = 1;
                        updateCustomerData();
                    } else {
                        /* Set Message If Edit Field Data Is Same */
                        if ((updatecustname.equals(cursor1.getString(1))) && (updatecontact.equals(cursor1.getString(4))) && (updateaddress.equals(cursor1.getString(3)))) {
                            Toasty.warning(getApplicationContext(), "Input Fields Contains Same Data as Previous ", Toasty.LENGTH_SHORT).show();
                            Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                            flag_update_customer_data = 1;
                            updateCustomerData();
                        } else {
                            /* Used To Validate Contact Number */
                            if (customer_dialog.customerValidateContact(updatecontact) == false) {
                                Toasty.warning(getApplicationContext(), "Enter Correct Contact Number", Toasty.LENGTH_SHORT).show();
                                Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                                flag_update_customer_data = 1;
                                updateCustomerData();
                            } else {
                                /* Update Data In Table */
                                boolean res = updatedCustomerData(String.valueOf(cust_id), customMethods.convertToTitleCase(updatecustname), updatecontact, updateaddress);
                                if (res == true) {
                                    flag_for_match_password = 0;
                                    customerAdapter.swapCursor(getAllItems());
                                    updatecustname = null;
                                    updatecontact = null;
                                    updateaddress = null;
                                    Toasty.success(getApplicationContext(), updatecustname + " Data is Updated", Toasty.LENGTH_SHORT).show();

                                } else {
                                    Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                                    flag_update_customer_data = 1;
                                    updateCustomerData();
                                }
                            }
                        }
                    }
                } else {
                    Toasty.warning(getApplicationContext(), "Wrong Password", Toasty.LENGTH_SHORT).show();
                    Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                    flag_update_customer_data = 1;
                    updateCustomerData();
                }

            }
            case 4: {
                /* Match Password To Update Data In Table */
                if (PreferenceUtils.getPassword(getApplicationContext()).equals(password)) {
                    /* Set Message If Edit Text Is Empty */
                    if ((update_shopname_str.isEmpty()) || (update_useremail_str.isEmpty()) || (update_usercontact_str.isEmpty()) || (update_useridstr.isEmpty())) {
                        Toasty.warning(getApplicationContext(), "Input Fields are Empty", Toasty.LENGTH_SHORT).show();
                        Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                        flag_change_profile = 1;
                        changeProfileDialog();
                    } else {
                        /* Set Message If Edit Field Data Is Same */
                        if ((update_shopname_str.equals(usercursor.getString(1))) && (update_useremail_str.equals(usercursor.getString(2))) && (update_usercontact_str.equals(usercursor.getString(3))) && (update_useridstr.equals(usercursor.getString(4)))) {
                            Toasty.warning(getApplicationContext(), "Input Fields Contains Same Data as Previous ", Toasty.LENGTH_SHORT).show();
                            Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                            flag_change_profile = 1;
                            changeProfileDialog();
                        } else {
                            SignupScreenValidate signupScreenValidate = new SignupScreenValidate();
                            int email, userid, contact;
                            email = signupScreenValidate.signupvalidateEmail(update_useremail_str);
                            userid = signupScreenValidate.signupvalidateUserid(update_useridstr);
                            contact = signupScreenValidate.signupvalidateContact(update_usercontact_str);
                            /* Check Whether Field Has Correct Type Of Input Is Enter Or Not And Than Show/Set  Message According To It */
                            if (email == 1) {
                                Toasty.warning(getApplicationContext(), "Enter correct emailid", Toasty.LENGTH_SHORT).show();
                                Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                                flag_change_profile = 1;
                                changeProfileDialog();
                            } else if (contact == 1) {
                                Toasty.warning(getApplicationContext(), "Enter valid phone number", Toasty.LENGTH_SHORT).show();
                                Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                                flag_change_profile = 1;
                                changeProfileDialog();
                            } else if (userid == 1) {
                                Toasty.warning(getApplicationContext(), "userid must Contain atleast 1 Letter and Number", Toasty.LENGTH_SHORT).show();
                                Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                                flag_change_profile = 1;
                                changeProfileDialog();
                            } else

                                /* Check Maximum Size Of Character Is Enter In The Field And Than Show/Set Message According To It */
                                if (email == 0) {
                                    Toasty.warning(getApplicationContext(), "email id is to long", Toasty.LENGTH_SHORT).show();
                                    Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                                    flag_change_profile = 1;
                                    changeProfileDialog();
                                } else if (contact == 0) {
                                    Toasty.warning(getApplicationContext(), "phone number has greater than 10 digits", Toasty.LENGTH_SHORT).show();
                                    Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                                    flag_change_profile = 1;
                                    changeProfileDialog();
                                } else if (userid == 0) {
                                    Toasty.warning(getApplicationContext(), "userid is to long", Toasty.LENGTH_SHORT).show();
                                    Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                                    flag_change_profile = 1;
                                    changeProfileDialog();
                                } else

                                    /* Check Minimum Size Of Character Can Be Enter In The Field And Than Show/Set Message According To It */
                                    if (userid == 3) {
                                        Toasty.warning(getApplicationContext(), "user is to short", Toasty.LENGTH_SHORT).show();
                                        Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                                        flag_change_profile = 1;
                                        changeProfileDialog();
                                    } else if (contact == 3) {
                                        Toasty.warning(getApplicationContext(), "phone number has less than 10 digits", Toasty.LENGTH_SHORT).show();
                                        Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                                        flag_change_profile = 1;
                                        changeProfileDialog();
                                    } else /* If All Field Has Proper Type Of Input Than Set Message To Null And Set Data To Sign Vo Class */
                                        if (email == 2 && contact == 2 && userid == 2) {
                                            CustomMethods customMethods = new CustomMethods();
                                            boolean res = signupDAO.changeUserData(String.valueOf(PreferenceUtils.getId(getApplicationContext())), customMethods.convertToTitleCase(update_shopname_str), update_usercontact_str, update_useremail_str, update_useridstr);
                                            if (res == true) {
                                                flag_for_match_password = 0;
                                                PreferenceUtils.saveShopName(update_shopname_str, getApplicationContext());
                                                PreferenceUtils.saveEmail(update_useremail_str, getApplicationContext());
                                                PreferenceUtils.saveContact(update_usercontact_str, getApplicationContext());
                                                PreferenceUtils.saveUserID(update_useridstr, getApplicationContext());
                                                customerAdapter.swapCursor(getAllItems());
                                                update_shopname_str = null;
                                                update_useremail_str = null;
                                                update_usercontact_str = null;
                                                update_useridstr = null;
                                                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                                                startActivity(intent);
                                                finish();
                                                Toasty.success(getApplicationContext(), "Profile is Change Successfully", Toasty.LENGTH_SHORT).show();
                                            } else {
                                                Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                                                flag_change_profile = 1;
                                                changeProfileDialog();
                                            }
                                        }
                        }
                    }

                } else {
                    Toasty.warning(getApplicationContext(), "Wrong Password", Toasty.LENGTH_SHORT).show();
                    Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                    flag_change_profile = 1;
                    changeProfileDialog();
                }
            }
            break;
            case 5: {
                /* Match Password To Update Data In Table */
                if (PreferenceUtils.getPassword(getApplicationContext()).equals(password)) {
                    CustomerDialog customer_dialog = new CustomerDialog();
                    CustomMethods customMethods = new CustomMethods();
                    /* Set Message If Edit Text Is Empty */
                    if ((update_password.isEmpty())) {
                        Toasty.warning(getApplicationContext(), "Input Fields are Empty", Toasty.LENGTH_SHORT).show();
                        Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                        flag_change_password = 1;
                        changePasswordDialog();
                    } else {
                        SignupScreenValidate signupScreenValidate = new SignupScreenValidate();
                        int checkpassword = signupScreenValidate.signupvalidatePassword(update_password);
                        if (checkpassword == 1) {
                            Toasty.warning(getApplicationContext(), "password is to weak", Toasty.LENGTH_SHORT).show();
                            Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                            flag_change_password = 1;
                            changePasswordDialog();

                        } else if (checkpassword == 0) {
                            Toasty.warning(getApplicationContext(), "password is to short", Toasty.LENGTH_SHORT).show();
                            Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                            flag_change_password = 1;
                            changePasswordDialog();

                        } else if (checkpassword == 2) {
                            boolean res = signupDAO.changePassword(String.valueOf(PreferenceUtils.getId(getApplicationContext())), update_password);
                            if (res == true) {
                                flag_for_match_password = 0;
                                PreferenceUtils.savePassword(update_password, getApplicationContext());
                                customerAdapter.swapCursor(getAllItems());
                                update_password = null;
                                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                                startActivity(intent);
                                finish();
                                Toasty.success(getApplicationContext(), "Password is Change Successfully", Toasty.LENGTH_SHORT).show();
                            } else {
                                Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                                flag_change_password = 1;
                                changePasswordDialog();
                            }
                        }

                    }
                } else {
                    Toasty.warning(getApplicationContext(), "Wrong Password", Toasty.LENGTH_SHORT).show();
                    Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                    flag_change_password = 1;
                    changePasswordDialog();
                }
            }
            break;
            default: {
                Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void payAmount(Double amount) {
        if (amount.isNaN() || amount.isInfinite() || amount.equals(0.0)) {
            Toasty.warning(getApplicationContext(), "Enter Valid Amount", Toasty.LENGTH_SHORT).show();
            Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
            /* Set Flag Pay Amount Value To 1 So That It Pass Value To Dialog Box To Set Text*/
            this.amount = String.valueOf(amount);
            flag_pay_amount = 1;
            /* Call Pay Amount Dialog Again */
            payAmountDialog();

        } else {
            /* Set Flag For Match Password Value To 2 To Use Match Password For Pay Amount */
            flag_for_match_password = 2;
            /* Set Custamount To Amount For Update Value In Table */
            custamount = amount;
            /* Call Match Password Dialog */
            matchPasswordDialog();
        }
    }

    @Override
    public void areYouSure() {
        matchPasswordDialog();

    }

    @Override
    public void updateCustomerData(String custname, String contact, String address) {
        flag_for_match_password = 3;
        updatecustname = custname;
        updateaddress = address;
        updatecontact = contact;
        matchPasswordDialog();

    }

    @Override
    public void changeProfile(String shopname, String userid, String contact, String email) {
        flag_for_match_password = 4;
        update_shopname_str = shopname;
        update_useremail_str = email;
        update_usercontact_str = contact;
        update_useridstr = userid;
        matchPasswordDialog();

    }

    @Override
    public void changePassword(String newpassword) {
        flag_for_match_password = 5;
        update_password = newpassword;
        matchPasswordDialog();
    }


}
