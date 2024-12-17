
package com.raven.model;

import java.util.Date;

public class ModelVoucher {
    private String maVoucher;     
    private String tenVoucher;    
    private String moTa;          
    private float giamGia;       
    private Date ngayBatDau;      
    private Date ngayKetThuc;     
    private String trangThai;
    private int diemTL;

    public ModelVoucher() {
    }

    
    public ModelVoucher(String maVoucher, String tenVoucher, String moTa, float giamGia, Date ngayBatDau, Date ngayKetThuc, String trangThai, int diemTL) {
        this.maVoucher = maVoucher;
        this.tenVoucher = tenVoucher;
        this.moTa = moTa;
        this.giamGia = giamGia;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
        this.diemTL = diemTL;
    }

    public int getDiemTL() {
        return diemTL;
    }

    public void setDiemTL(int diemTL) {
        this.diemTL = diemTL;
    }
    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public float getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(float giamGia) {
        if (giamGia >= 0 && giamGia <= 1) {
            this.giamGia = giamGia;
        } else {
            throw new IllegalArgumentException("Giảm giá phải nằm trong khoảng từ 0 đến 1");
        }
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        if (ngayKetThuc.after(ngayBatDau) || ngayKetThuc.equals(ngayBatDau)) {
            this.ngayKetThuc = ngayKetThuc;
        } else {
            throw new IllegalArgumentException("Ngày kết thúc phải sau hoặc bằng ngày bắt đầu");
        }
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        if (trangThai.equals("Hoat dong") || trangThai.equals("Het han") || trangThai.equals("Tam dung")) {
            this.trangThai = trangThai;
        } else {
            throw new IllegalArgumentException("Trạng thái không hợp lệ");
        }
    }
    
}
