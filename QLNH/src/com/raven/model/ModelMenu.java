
package com.raven.model;

public class ModelMenu {
    private String idTD;
    private String nameTD;
    private String loaiTD;
    private float gia;

    public ModelMenu() {
    }

    public ModelMenu(String idTD, String nameTD, String loaiTD, float gia) {
        this.idTD = idTD;
        this.nameTD = nameTD;
        this.loaiTD = loaiTD;
        this.gia = gia;
    }

    public String getIdTD() {
        return idTD;
    }

    public void setIdTD(String idTD) {
        this.idTD = idTD;
    }

    public String getNameTD() {
        return nameTD;
    }

    public void setNameTD(String nameTD) {
        this.nameTD = nameTD;
    }

    public String getLoaiTD() {
        return loaiTD;
    }

    public void setLoaiTD(String loaiTD) {
        this.loaiTD = loaiTD;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }
    
}
