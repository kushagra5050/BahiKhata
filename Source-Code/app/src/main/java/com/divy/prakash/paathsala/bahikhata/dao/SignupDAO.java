package com.divy.prakash.paathsala.bahikhata.dao;

import android.database.Cursor;

import com.divy.prakash.paathsala.bahikhata.bo.SignupBO;
import com.divy.prakash.paathsala.bahikhata.utils.CustomerConstants;
import com.divy.prakash.paathsala.bahikhata.utils.UserConstants;

/* This Interface Is Used To Declare Signup Daoimpl Methods */
public interface SignupDAO {
    long insertSignup_Detail(SignupBO signupBO);

    UserConstants Authenticate(UserConstants userConstants);

    boolean isUserExists(String user);

    UserConstants forgotPassword(UserConstants userConstants);
     Cursor getUserData(String id) ;

     boolean changeUserData(String id, String shopname, String usercontact, String useremail,String userid);
    boolean  changePassword(String id, String newpassword);


}
