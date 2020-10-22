package com.divy.prakash.paathsala.bahikhata.validate;

import android.util.Patterns;

import java.util.regex.Pattern;

/* This Class Is Used To Validate All Field Of Singup Screen By Regular Expression(Re) */
public class SignupScreenValidate {

    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +                                            /* Contain At Least 1 Digit */
                    "(?=.*[a-z])" +                                            /* Contain At Least 1 Lower Case Letter */
                    "(?=.*[A-Z])" +                                            /* Contain At Least 1 Upper Case Letter */
                    "(?=.*[a-zA-Z])" +                                         /* Contain Any Letter */
                    "(?=.*[@#$%^&+=])" +                                       /* Contain At Least 1 Special Character */
                    "(?=\\S+$)" +                                              /* Contain No White Spaces */
                    ".{8,20}" +                                                /* Contain At Least 8 Characters */
                    "$");
    public static final Pattern USERID_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +                                            /* Contain At Least 1 Digit */
                    "(?=.*[a-zA-Z])" +                                         /* Contain Any Letter */
                    "(?=\\S+$)" +                                              /* Contain No White Spaces */
                    ".{6,20}" +                                                /* Contain At Least 6 Characters */
                    "$");
    public static final Pattern CONTACT_PATTERN =
            Pattern.compile("-?\\d+(\\\\d+)?");                                /* Contains Only Digits  */

    public int signupvalidateEmail(String emailInput) {

        if (emailInput.length() > 50)                                          /* Check Maximum Characters */ {
            return 0;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())      /* Check Pattern Of Emailid */ {
            return 1;
        }
        return 2;                                               /* Default Value */

    }

    /* Used To Validate Signup Screen Userid Field */
    public int signupvalidateUserid(String usernameInput) {
        if (usernameInput.length() > 20)                                      /* Check Maximum Characters */ {
            return 0;
        } else if (usernameInput.length() < 6)                                 /* Check Minimum Characters */ {
            return 3;
        } else if (!USERID_PATTERN.matcher(usernameInput).matches())            /* Check Pattern Of Userid */ {
            return 1;
        }
        return 2;                                               /* Default Value */

    }

    /* Used To Validate Signup Screen Password Field */
    public int signupvalidatePassword(String passwordInput) {

        if (passwordInput.length() < 8)                                        /* Check Minimum Characters */ {
            return 0;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches())         /* Check Pattern Of Password */ {
            return 1;
        }
        return 2;                                              /* Default Value */

    }

    /* Used To Validate Signup Screen Contactnumber Field */
    public int signupvalidateContact(String contactInput) {

        if (contactInput.length() > 10)                                          /* Check Maximum Characters */ {
            return 0;
        } else if (contactInput.length() < 10)                                    /* Check Minimum Characters */ {
            return 3;
        } else if (!CONTACT_PATTERN.matcher(contactInput).matches())             /* Check Pattern Of Phone Number */ {
            return 1;
        }
        return 2;                                             /* Default Value */


    }

}
