
package com.raven.model;

import java.util.Date;


public class ModelNguyenLieu {
    private int maNL;          // Mã nguyên liệu
    private String tenNL;      // Tên nguyên liệu
    private String donViTinh;  // Đơn vị tính
    private String nhaCungCap; // Nhà cung cấp
    private double gia;        // Giá nguyên liệu
    private Date hanSuDung;    // Hạn sử dụng
    private String trangThai;  // Trạng thái (Hoạt động, Hết hạn, Tạm dừng)

    public ModelNguyenLieu() {
    }

    public ModelNguyenLieu(int maNL, String tenNL, String donViTinh, String nhaCungCap, double gia, Date hanSuDung, String trangThai) {
        this.maNL = maNL;
        this.tenNL = tenNL;
        this.donViTinh = donViTinh;
        this.nhaCungCap = nhaCungCap;
        this.gia = gia;
        this.hanSuDung = hanSuDung;
        this.trangThai = trangThai;
    }

    public int getMaNL() {
        return maNL;
    }

    public void setMaNL(int maNL) {
        this.maNL = maNL;
    }

    public String getTenNL() {
        return tenNL;
    }

    public void setTenNL(String tenNL) {
        this.tenNL = tenNL;
    }


    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(Date hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    

}
