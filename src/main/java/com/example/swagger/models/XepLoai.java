package com.example.swagger.models;

public enum XepLoai {
    GIOI("Gioi"), KHA("Kha"), TRUNG_BINH("Trung binh"), YEU("Yeu");
    private String xl;

    XepLoai(String xl){
        this.xl = xl;
    }
    public String getXl() {
        return xl;
    }
}