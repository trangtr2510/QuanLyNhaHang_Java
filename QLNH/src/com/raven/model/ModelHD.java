
package com.raven.model;


public class ModelHD {
    private String idHD;
    private String idBan;
    private String tang;
    private String ngayTT;
    float gia;

    public String getIdHD() {
        return idHD;
    }

    public void setIdHD(String idHD) {
        this.idHD = idHD;
    }

    public String getIdBan() {
        return idBan;
    }

    public void setIdBan(String idBan) {
        this.idBan = idBan;
    }

    public String getTang() {
        return tang;
    }

    public void setTang(String tang) {
        this.tang = tang;
    }

    public String getNgayTT() {
        return ngayTT;
    }

    public void setNgayTT(String ngayTT) {
        this.ngayTT = ngayTT;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }
    
    
    public ModelHD() {
    }

    public ModelHD(String idHD, String idBan, String tang, String ngayTT, float gia) {
        this.idHD = idHD;
        this.idBan = idBan;
        this.tang = tang;
        this.ngayTT = ngayTT;
        this.gia = gia;
    }
    
}
