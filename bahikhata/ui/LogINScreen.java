package com.divy.prakash.paathsala.bahikhata.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.divy.prakash.paathsala.bahikhata.dao.SignupDAO;
import com.divy.prakash.paathsala.bahikhata.dao.SignupDAOImpl;
import com.divy.prakash.paathsala.bahikhata.utils.PreferenceUtils;
import com.divy.prakash.paathsala.bahikhata.utils.UserConstants;
import com.google.android.material.textfield.TextInputEditText;

import es.dmoral.toasty.Toasty;


public class LogINScreen extends AppCompatActivity implements DialogForgotPassword.ForgotPasswordDialogListener {

    TextInputEditText login_user_id_EditTxt, login_password_EditTxt;
    Button login_login_btn;
    TextView login_signup_txtView, login_forgotpassword_txtView;
    CheckBox login_remember_me;
    SignupDAO signupDAO;
    String userid, contact, email;
    int flag_forgot_password = 0;                          /* Used To Set Value To Forgot Password Dialog Box*/
    /*
    This Function Is Used To Watch Wheather A Edittext Is Empty Or Not
    If Empty Then Login Btn Is Not Enable And Else It Is Enable
    */
    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            /* Get Text Form Edit Text And Store It */
            String login_userid_str = login_user_id_EditTxt.getText().toString().trim();
            String login_password_str = login_password_EditTxt.getText().toString().trim();

            /* Toast Warning If Field Characters Are Greater Than 15 */
            if (login_userid_str.length() > 15) {
                Toasty.warning(getApplicationContext(), "Userid field contains only 15 characters", Toasty.LENGTH_SHORT).show();
            }
            if (login_password_str.length() > 15) {
                Toasty.warning(getApplicationContext(), "Password field contains only 15 characters", Toasty.LENGTH_SHORT).show();
            }
            if (!login_userid_str.isEmpty() && !login_password_str.isEmpty()) {
                login_login_btn.setBackgroundColor(Color.rgb(103, 58, 183));
                login_login_btn.setEnabled(true);
            } else {
                login_login_btn.setBackgroundColor(Color.parseColor("#23000000"));
                login_login_btn.setEnabled(false);
            }


        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Used To Set Color Of Status Bar If Sdk Is > Lollipop */
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_log_in_screen);
        signupDAO = new SignupDAOImpl(this);
        /* Component Intialization */
        login_user_id_EditTxt = findViewById(R.id.signup_email_id_EditTxt);
        login_password_EditTxt = findViewById(R.id.login_password_EditTxt);
        login_login_btn = findViewById(R.id.login_login_btn);
        login_signup_txtView = findViewById(R.id.login_signup_txtView);
        login_forgotpassword_txtView = findViewById(R.id.login_forgotpassword_txtView);
        login_remember_me = findViewById(R.id.log_in_remember_me);
        /* Check Shared Preferences Value Is Not Null Then Move To Homescreen */
        if ((PreferenceUtils.getUserid(getApplicationContext()) == null) || (PreferenceUtils.getUserid(getApplicationContext()).equals(""))) {

        } else {
            Intent i = new Intent(getApplicationContext(), HomeScreen.class);
            startActivity(i);
            finish();
        }
        /* Function Is Called To Check Edittext Is Empty Or Not */
        login_user_id_EditTxt.addTextChangedListener(loginTextWatcher);
        login_password_EditTxt.addTextChangedListener(loginTextWatcher);
        login_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id_str = login_user_id_EditTxt.getText().toString().trim();
                String password_str = login_password_EditTxt.getText().toString().trim();
                /* Authenticate User */
                UserConstants userConstants = signupDAO.Authenticate(new UserConstants(null, null, null, null, user_id_str, password_str));

                /* Check Authentication Is Successful Or Not */
                if (userConstants != null) {
                    /* Empty All Input Edit Textfield */
                    emptyEditText();
                    if (login_remember_me.isChecked()) {
                        /* Save Value In Shared Preference */

                        PreferenceUtils.saveShopName(userConstants.shopName, getApplicationContext());
                        PreferenceUtils.saveEmail(userConstants.email, getApplicationContext());
                        PreferenceUtils.saveContact(userConstants.contact, getApplicationContext());
                        PreferenceUtils.saveUserID(userConstants.userID, getApplicationContext());

                    }
                    PreferenceUtils.savePassword(userConstants.password, getApplicationContext());
                    PreferenceUtils.saveId(userConstants.id, getApplicationContext());
                    PreferenceUtils.saveFlagSortBy(String.valueOf(0), getApplicationContext());
                    Toasty.success(getApplicationContext(), "Successfully Login" + userConstants.userID, Toasty.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), HomeScreen.class);
                    startActivity(i);
                    finish();
                } else {
                    /* User Logged In Failed */
                    Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();

                }


            }
        });
        String text = "Don't have BahiKhata account? \n REGISTER";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                emptyEditText();
                /* Intent Is Used To Switch From Login Activity To Sign Upscreen */
                Intent i = new Intent(getApplicationContext(), SignUPScreen.class);
                /* Invoke The Second Activity */
                startActivity(i);


            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 32, 40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        login_signup_txtView.setText(ss);
        login_signup_txtView.setMovementMethod(LinkMovementMethod.getInstance());

        login_forgotpassword_txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPasswordDialog();
            }
        });
    }

    /* Function Used To Set Empty All Edit Text Field */
    private void emptyEditText() {
        login_user_id_EditTxt.setText(null);
        login_password_EditTxt.setText(null);
    }

    /* Function To Open Dialogbox */
    private void forgotPasswordDialog() {
        /* If Flag Forgot Password Is 0 Then It Is First Time Call Mean There Is No Error Occur So Pass Parameters NULL*/
        if (flag_forgot_password == 0) {
            DialogForgotPassword dialog = new DialogForgotPassword(null, null, null);
            dialog.show(getSupportFragmentManager(), "Forgot Password");
        } else if (flag_forgot_password == 1) {
            /* If Flag Forgot Password Is 1 Then It Is Not First Time Call Mean There Is  Error Occur So Pass Value To Dialog Box To Set */
            DialogForgotPassword dialog = new DialogForgotPassword(userid, contact, email);
            dialog.show(getSupportFragmentManager(), "Forgot Password");
            /* After Calling Set Value To 0 For Default Mode */
            flag_forgot_password = 0;
        }


    }

    /* Function To Get Password */
    @Override
    public void forgotPassword(String userid, String contact, String email) {
        UserConstants userConstants = signupDAO.forgotPassword(new UserConstants(null, null, null, null, userid, null));
        if ((userid.isEmpty()) || (contact.isEmpty()) || (email.isEmpty())) {
            Toasty.warning(getApplicationContext(), "Input Fields are Empty", Toasty.LENGTH_SHORT).show();
            Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
            /* Set Flag Forgot Password Value To 1 So That It Pass Value To Dialog Box To Set Text*/
            flag_forgot_password = 1;
            this.userid = userid;
            this.contact = contact;
            this.email = email;
            forgotPasswordDialog();
        } else if (userConstants != null) {
            if ((userConstants.userID.equals(userid)) && (userConstants.email.equals(email)) && (userConstants.contact).equals(contact)) {
                Toasty.success(getApplicationContext(), "Your Password is:- " + userConstants.password, 80000).show();
            } else {
                Toasty.warning(getApplicationContext(), "Enter Correct Values", Toasty.LENGTH_SHORT).show();
                Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
                /* Set Flag Forgot Password Value To 1 So That It Pass Value To Dialog Box To Set Text*/
                flag_forgot_password = 1;
                this.userid = userid;
                this.contact = contact;
                this.email = email;
                forgotPasswordDialog();
            }
        } else {
            Toasty.warning(getApplicationContext(), "Data are not matches", Toasty.LENGTH_SHORT).show();
            Toasty.error(getApplicationContext(), "Try Again", Toasty.LENGTH_SHORT).show();
            /* Set Flag Forgot Password Value To 1 So That It Pass Value To Dialog Box To Set Text*/
            flag_forgot_password = 1;
            this.userid = userid;
            this.contact = contact;
            this.email = email;
            forgotPasswordDialog();
        }

    }


}
