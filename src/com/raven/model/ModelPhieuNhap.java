
package com.raven.model;

import java.sql.Date;
import java.util.Objects;

public class ModelPhieuNhap {
    private int maPN;         // Mã phiếu nhập
    private Date ngayNhap;    // Ngày nhập
    private int idND;      // ID nhân viên thực hiện nhập
    private float tongTien;   // Tổng tiền nhập

    // Constructor không tham số
    public ModelPhieuNhap() {
    }

    // Constructor đầy đủ tham số
    public ModelPhieuNhap(int maPN, Date ngayNhap, int idND, float tongTien) {
        this.maPN = maPN;
        this.ngayNhap = ngayNhap;
        this.idND = idND;
        this.tongTien = tongTien;
    }

    // Getter và Setter
    public int getMaPN() {
        return maPN;
    }

    public void setMaPN(int maPN) {
        this.maPN = maPN;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getIdND() {
        return idND;
    }

    public void setIdND(int idND) {
        this.idND = idND;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        if (tongTien >= 0) {
            this.tongTien = tongTien;
        } else {
            throw new IllegalArgumentException("Tổng tiền phải lớn hơn hoặc bằng 0");
        }
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(maPN, ngayNhap, idND, tongTien);
    }

    // Override equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ModelPhieuNhap other = (ModelPhieuNhap) obj;
        return maPN == other.maPN
                && Float.compare(other.tongTien, tongTien) == 0
                && Objects.equals(ngayNhap, other.ngayNhap)
                && Objects.equals(idND, other.idND);
    }
    @Override
    public String toString() {
        return "PhieuNhap{" +
                "maPN=" + maPN +
                ", ngayNhap=" + ngayNhap +
                ", idNV='" + idND + '\'' +
                ", tongTien=" + tongTien +
                '}';
    }
}
