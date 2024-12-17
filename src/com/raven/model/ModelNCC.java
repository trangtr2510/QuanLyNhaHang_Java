
package com.raven.model;

public class ModelNCC {
    private int maNCC;           // Mã nhà cung cấp
    private String nhaCungCap;   // Tên nhà cung cấp
    private String diaChi;       // Địa chỉ nhà cung cấp
    private String sdt;          // Số điện thoại nhà cung cấp
    private String email;        // Email nhà cung cấp
    private String trangThai;    // Trạng thái nhà cung cấp (Hoạt động, Tạm dừng, Ngừng hoạt động)

    public ModelNCC() {
        
    }
    public ModelNCC(int maNCC, String nhaCungCap, String diaChi, String sdt, String email, String trangThai) {
        this.maNCC = maNCC;
        this.nhaCungCap = nhaCungCap;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.trangThai = trangThai;
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "NhaCungCap [maNCC=" + maNCC + ", nhaCungCap=" + nhaCungCap + ", diaChi=" + diaChi + ", sdt=" + sdt
                + ", email=" + email + ", trangThai=" + trangThai + "]";
    }
}

