package com.divy.prakash.paathsala.bahikhata.utils;

/* This Class Used To Create Custom Methods */
public class CustomMethods {
    /* This Method Is Used To Convert A Given String In Title Case */
    public String convertToTitleCase(String text) {
        /* Return Text Is Text Is Null */
        if (text == null || text.isEmpty()) {
            return text;
        }
        StringBuilder converted = new StringBuilder();
        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            /* Check Whether A Character Is Space If So Then Convert Next Is True */
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else
                /* If Convert Next Is True Then Set Character In Uppercase Else In Lowercase */
                if (convertNext) {
                    ch = Character.toTitleCase(ch);
                    convertNext = false;
                } else {
                    ch = Character.toLowerCase(ch);
                }
            converted.append(ch);
        }
        /* Return Converted Text */
        return converted.toString();
    }
}
