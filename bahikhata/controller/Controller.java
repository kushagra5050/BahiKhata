package com.divy.prakash.paathsala.bahikhata.controller;

import android.content.Context;

import com.divy.prakash.paathsala.bahikhata.dto.SignupDTO;
import com.divy.prakash.paathsala.bahikhata.service.SignupServiceImpl;
import com.divy.prakash.paathsala.bahikhata.vo.SignupVO;

/* This Class Is Used To Control The Flow Of Data */
public class Controller {
    SignupServiceImpl service;
    SignupDTO dto;

    public String getResult(Context context, SignupVO vo) {
        service = new SignupServiceImpl(context);
        dto = new SignupDTO();
        dto.setShopname(vo.getShopname());
        dto.setEmail(vo.getEmail());
        dto.setContactno(Long.parseLong(vo.getContactno()));
        dto.setUserid(vo.getUserid());
        dto.setPassword(vo.getPassword());
        return service.getInsertInfo(dto);
    }
}
