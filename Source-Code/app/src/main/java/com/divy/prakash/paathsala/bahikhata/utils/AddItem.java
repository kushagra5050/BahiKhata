package com.divy.prakash.paathsala.bahikhata.utils;

public class AddItem {
    private String productname ;
    private String productmunit;
    private String quantity;
    private String price;
    private String totalprice;

    public AddItem(String productname, String productmunit, String quantity, String price, String totalprice) {
        this.productname = productname;
        this.productmunit = productmunit;
        this.quantity = quantity;
        this.price = price;
        this.totalprice = totalprice;
    }

    public String getProductname() {
        return productname;
    }

    public String getProductmunit() {
        return productmunit;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getTotalprice() {
        return totalprice;
    }
}
