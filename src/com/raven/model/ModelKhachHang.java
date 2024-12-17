package com.raven.model;

public class ModelKhachHang {

    public ModelKhachHang() {
    }

    public ModelKhachHang(int IDKH, String name, String sdt, String gioiTinh, String diaChi, String ngayTG) {
        this.IDKH = IDKH;
        this.name = name;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.ngayTG = ngayTG;
    }

    public int getIDKH() {
        return IDKH;
    }

    public void setIDKH(int IDKH) {
        this.IDKH = IDKH;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgayTG() {
        return ngayTG;
    }

    public void setNgayTG(String ngayTG) {
        this.ngayTG = ngayTG;
    }

    private int IDKH;
    private String name;
    private String sdt;
    private String gioiTinh;
    private String diaChi;
    private String ngayTG;

}
