package com.divy.prakash.paathsala.bahikhata.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.divy.prakash.paathsala.bahikhata.controller.Controller;
import com.divy.prakash.paathsala.bahikhata.dao.SignupDAO;
import com.divy.prakash.paathsala.bahikhata.dao.SignupDAOImpl;
import com.divy.prakash.paathsala.bahikhata.utils.CustomMethods;
import com.divy.prakash.paathsala.bahikhata.validate.SignupScreenValidate;
import com.divy.prakash.paathsala.bahikhata.vo.SignupVO;
import com.google.android.material.textfield.TextInputEditText;

import es.dmoral.toasty.Toasty;


public class SignUPScreen extends AppCompatActivity {
    TextInputEditText signupShopnameEditTxt, signupEmailIdEditTxt, signupContactNoEditTxt, signupUserIdEditTxt, signupPasswordEditTxt;
    Button signupSignupBtn;
    SignupVO signupVO;
    SignupDAO signupDAO;

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
        getSupportActionBar().setTitle("Register");
        setContentView(R.layout.activity_sign_up_screen);

        /* Component Intialization */
        signupShopnameEditTxt = findViewById(R.id.signup_shopname_EditTxt);
        signupEmailIdEditTxt = findViewById(R.id.signup_email_id_EditTxt);
        signupContactNoEditTxt = findViewById(R.id.signup_contact_no_EditTxt);
        signupUserIdEditTxt = findViewById(R.id.signup_user_id_EditTxt);
        signupPasswordEditTxt = findViewById(R.id.signup_password_EditTxt);
        signupSignupBtn = findViewById(R.id.signup_signup_btn);

        /* Function Is Called To Check Edit Text Is Empty Or Not By Calling Text Watcher Listener */
        signupShopnameEditTxt.addTextChangedListener(signupTextWatcher);
        signupEmailIdEditTxt.addTextChangedListener(signupTextWatcher);
        signupContactNoEditTxt.addTextChangedListener(signupTextWatcher);
        signupUserIdEditTxt.addTextChangedListener(signupTextWatcher);
        signupPasswordEditTxt.addTextChangedListener(signupTextWatcher);
        signupSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Get Text From Sign Up Fields */
                signupVO = new SignupVO();
                signupDAO = new SignupDAOImpl(getApplicationContext());
                String signup_shopname_str = signupShopnameEditTxt.getText().toString().trim();
                String signup_email_str = signupEmailIdEditTxt.getText().toString().trim();
                String signup_contact_no_str = signupContactNoEditTxt.getText().toString().trim();
                String signup_user_id_str = signupUserIdEditTxt.getText().toString().trim();
                String signup_password_str = signupPasswordEditTxt.getText().toString().trim();

                /* Validate This Fields By Using RE */
                SignupScreenValidate signupScreenValidate = new SignupScreenValidate();
                int email, contact, userid, password;
                email = signupScreenValidate.signupvalidateEmail(signup_email_str);
                contact = signupScreenValidate.signupvalidateContact(signup_contact_no_str);
                userid = signupScreenValidate.signupvalidateUserid(signup_user_id_str);
                password = signupScreenValidate.signupvalidatePassword(signup_password_str);

                /* Check Whether Field Has Correct Type Of Input Is Enter Or Not And Than Show/Set  Message According To It */
                if (email == 1) {
                    signupEmailIdEditTxt.setError("Enter correct emailid");
                }
                if (contact == 1) {
                    signupContactNoEditTxt.setError("Enter valid phone number");
                }
                if (userid == 1) {
                    signupUserIdEditTxt.setError("userid must Contain atleast 1 Letter and Number");
                }
                if (password == 1) {
                    signupPasswordEditTxt.setError("password must contains aleast 1 Capital and Small Letter 1 Speical Symbol Like @,#,$,%,^,&,+,=, and 1 Number");
                }

                /* Check Maximum Size Of Character Is Enter In The Field And Than Show/Set Message According To It */
                if (email == 0) {
                    signupEmailIdEditTxt.setError("email id is to long");
                }
                if (contact == 0) {
                    signupContactNoEditTxt.setError("phone number has greater than 10 digits");
                }
                if (userid == 0) {
                    signupUserIdEditTxt.setError("userid is to long");
                }
                if (password == 0) {
                    signupPasswordEditTxt.setError("password is to short");
                }

                /* Check Minimum Size Of Character Can Be Enter In The Field And Than Show/Set Message According To It */
                if (userid == 3) {
                    signupUserIdEditTxt.setError("user is to short");
                }
                if (contact == 3) {
                    signupContactNoEditTxt.setError("phone number has less than 10 digits");
                }

                /* If All Field Has Proper Type Of Input Than Set Message To Null And Set Data To Sign Vo Class */
                if (email == 2 && contact == 2 && userid == 2 && password == 2) {
                    Boolean check = signupDAO.isUserExists(signup_user_id_str);
                    if (check == false) {
                        /* Method Used To Convert ShopName In Title Case */
                        CustomMethods customMethods = new CustomMethods();
                        signupVO.setShopname(customMethods.convertToTitleCase(signup_shopname_str));
                        signupVO.setEmail(signup_email_str);
                        signupVO.setContactno(signup_contact_no_str);
                        signupVO.setUserid(signup_user_id_str);
                        signupVO.setPassword(signup_password_str);

                        /* Empty All Edit Text Field After Set Data To Sign Vo Class */
                        emptyEditText();
                        Controller controller = new Controller();
                        String result = controller.getResult(getApplicationContext(), signupVO);
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        Toasty.success(getApplicationContext(), "Welcome To Bahi Khata ", Toasty.LENGTH_SHORT).show();
                        Toasty.success(getApplicationContext(), "You Have Successfully Create Account", Toasty.LENGTH_SHORT).show();

                        /* After Creating Account Back To Login Screen */
                        //Intent intent = new Intent(getApplicationContext(), LogINScreen.class);
                       // startActivity(intent);
                        finish();
                    }
                    /* Show Message If Userid Is Already Created In Database */
                    else if (check == true) {
                        Toasty.warning(getApplicationContext(), "This UserID Already Exists", Toasty.LENGTH_SHORT).show();
                    }
                }
                /* Show Error Message If Input Type Is Not Correct */
                else {
                    Toasty.error(getApplicationContext(), "Fill correct value in fields", Toasty.LENGTH_SHORT).show();
                }

            }
        });
    }

    /* Used To Go Back To Previous Activity On Click*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /* Used To Go Back To Previous Activity On Back Press */
    @Override
    public void onBackPressed() {

        finish();
    }

    /*
    This Function Is Used To Watch Whether A Edit Text Is Empty Or Not
    If Empty Then Signup Btn Is Not Enable And Else It Is Enable
    */
    private TextWatcher signupTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            /* Get Text Form Edit Text And Store It */
            String signup_shopname_EditTxt_str = signupShopnameEditTxt.getText().toString().trim();
            String signup_email_id_EditTxt_str = signupEmailIdEditTxt.getText().toString().trim();
            String signup_contact_no_EditTxt_str = signupContactNoEditTxt.getText().toString().trim();
            String signup_user_id_EditTxt_str = signupUserIdEditTxt.getText().toString().trim();
            String signup_password_EditTxt_str = signupPasswordEditTxt.getText().toString().trim();

            /* Used To Check Field Is Empty And Change Background Color According To It */
            if (!signup_shopname_EditTxt_str.isEmpty() && !signup_email_id_EditTxt_str.isEmpty() && !signup_contact_no_EditTxt_str.isEmpty() && !signup_user_id_EditTxt_str.isEmpty() && !signup_password_EditTxt_str.isEmpty()) {
                signupSignupBtn.setBackgroundColor(Color.rgb(103, 58, 183));
                signupSignupBtn.setEnabled(true);
            } else {
                signupSignupBtn.setBackgroundColor(Color.parseColor("#23000000"));
                signupSignupBtn.setEnabled(false);
            }
            /* Used To Validated Input Field Value By RE */
            SignupScreenValidate signup_validate_email = new SignupScreenValidate();
            SignupScreenValidate signup_validate_contact = new SignupScreenValidate();
            SignupScreenValidate signup_validate_userid = new SignupScreenValidate();
            SignupScreenValidate signup_validate_password = new SignupScreenValidate();
            int email, contact, userid, password;
            email = signup_validate_email.signupvalidateEmail(signupEmailIdEditTxt.getText().toString().trim());
            contact = signup_validate_contact.signupvalidateContact(signupContactNoEditTxt.getText().toString().trim());
            userid = signup_validate_userid.signupvalidateUserid(signupUserIdEditTxt.getText().toString().trim());
            password = signup_validate_password.signupvalidatePassword(signupPasswordEditTxt.getText().toString().trim());

            /* If All Field Has Proper Input Than Set Error Message To Null */
            if (email == 2) {
                signupEmailIdEditTxt.setError(null);
            }
            if (contact == 2) {
                signupContactNoEditTxt.setError(null);
            }
            if (userid == 2) {
                signupUserIdEditTxt.setError(null);
            }
            if (password == 2) {
                signupPasswordEditTxt.setError(null);
            }

            /* Used To Check Maximum Character Can Be Enter */
            if (signup_shopname_EditTxt_str.length() > 30) {
                Toasty.warning(getApplicationContext(), "Shop Name field contains only 30 characters", Toasty.LENGTH_SHORT).show();
            }
            if (signup_email_id_EditTxt_str.length() > 30) {
                Toasty.warning(getApplicationContext(), "Email field contains only 30 characters", Toasty.LENGTH_SHORT).show();
            }
            if (signup_contact_no_EditTxt_str.length() > 10) {
                Toasty.warning(getApplicationContext(), "Contact field contains only 10 characters", Toasty.LENGTH_SHORT).show();
            }
            if (signup_user_id_EditTxt_str.length() > 15) {
                Toasty.warning(getApplicationContext(), "Userid field contains only 15 characters", Toasty.LENGTH_SHORT).show();
            }
            if (signup_password_EditTxt_str.length() > 15) {
                Toasty.warning(getApplicationContext(), "Password field contains only 15 characters", Toasty.LENGTH_SHORT).show();
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    /* Function Used To Set Empty All Edit Text Field */
    private void emptyEditText() {
        signupShopnameEditTxt.setText(null);
        signupEmailIdEditTxt.setText(null);
        signupContactNoEditTxt.setText(null);
        signupUserIdEditTxt.setText(null);
        signupPasswordEditTxt.setText(null);
    }
}
