
package com.raven.model;

public class ChiTietPhieuNhap {
    private int maPN;         // Mã phiếu nhập
    private int maNL;         // Mã nguyên liệu
    private float soLuong;    // Số lượng nhập
    private float donGia;     // Đơn giá nhập

    // Constructor không tham số
    public ChiTietPhieuNhap() {
    }

    // Constructor đầy đủ tham số
    public ChiTietPhieuNhap(int maPN, int maNL, float soLuong, float donGia) {
        this.maPN = maPN;
        this.maNL = maNL;
        setSoLuong(soLuong); // Áp dụng kiểm tra ràng buộc
        setDonGia(donGia);   // Áp dụng kiểm tra ràng buộc
    }

    // Getter và Setter
    public int getMaPN() {
        return maPN;
    }

    public void setMaPN(int maPN) {
        this.maPN = maPN;
    }

    public int getMaNL() {
        return maNL;
    }

    public void setMaNL(int maNL) {
        this.maNL = maNL;
    }

    public float getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(float soLuong) {
        if (soLuong > 0) {
            this.soLuong = soLuong;
        } else {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        if (donGia > 0) {
            this.donGia = donGia;
        } else {
            throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
        }
    }

    @Override
    public String toString() {
        return "ChiTietPhieuNhap{" +
                "maPN=" + maPN +
                ", maNL=" + maNL +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                '}';
    }
}

