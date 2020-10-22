package com.divy.prakash.paathsala.bahikhata.service;

import android.content.Context;

import com.divy.prakash.paathsala.bahikhata.bo.SignupBO;
import com.divy.prakash.paathsala.bahikhata.dao.SignupDAO;
import com.divy.prakash.paathsala.bahikhata.dao.SignupDAOImpl;
import com.divy.prakash.paathsala.bahikhata.dto.SignupDTO;

/* This Class Is Used For Calculation And Set And Get Data */
public class SignupServiceImpl {
    private SignupDAO dao;
    private SignupBO bo;

    /* Constructor */
    public SignupServiceImpl(Context context) {
        dao = new SignupDAOImpl(context);
        bo = new SignupBO();
    }

    /* To Get Ack About Insert Of Data In Database*/
    public String getInsertInfo(SignupDTO dto) {
        bo.setShopname(dto.getShopname());
        bo.setEmail(dto.getEmail());
        bo.setContactno(dto.getContactno());
        bo.setUserid(dto.getUserid());
        bo.setPassword(dto.getPassword());
        long result = dao.insertSignup_Detail(bo);
        if (result > 0) {
            return "Data Inserted";
        } else {
            return "Not Inserted";
        }
    }
}
