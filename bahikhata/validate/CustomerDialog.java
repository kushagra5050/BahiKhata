package com.divy.prakash.paathsala.bahikhata.validate;

import java.util.regex.Pattern;

/* This Class Is Used To Validate All Field Of Add Customer Fields By Regular Expression(Re) */
public class CustomerDialog {


    public static final Pattern CONTACT_PATTERN =
            Pattern.compile("-?\\d+(\\\\d+)?");                                /* Contains Only Digits  */

    /* This Method Is Used To Validated Customer Contact Number */
    public boolean customerValidateContact(String contactInput) {

        if (contactInput.length() > 10)                                          /* Check Maximum Length */ {
            return false;
        } else if (contactInput.length() < 10)                                    /* Check Minimum Length */ {
            return false;
        } else if (!CONTACT_PATTERN.matcher(contactInput).matches())             /* Check Pattern Of Phone Number */ {
            return false;
        }
        return true;                                          /* Default Value */


    }

}
