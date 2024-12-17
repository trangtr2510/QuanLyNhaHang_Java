
package com.raven.model;

public class ModelNhanVien {
    String maNV;
    String tenNV;
    String SDT;
    String ngayVL;
    String chucVu;
    float luong;

    //get-set: cập nhật, lấy ra giá trị thuộc tính
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getNgayVL() {
        return ngayVL;
    }

    public void setNgayVL(String ngayVL) {
        this.ngayVL = ngayVL;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public float getLuong() {
        return luong;
    }

    public void setLuong(float luong) {
        this.luong = luong;
    }

    //khoi tao doi tuong
    public ModelNhanVien() {
    }

    public ModelNhanVien(String maNV, String tenNV, String SDT, String ngayVL, String chucVu, float luong) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.SDT = SDT;
        this.ngayVL = ngayVL;
        this.chucVu = chucVu;
        this.luong = luong;
    }
    
}
