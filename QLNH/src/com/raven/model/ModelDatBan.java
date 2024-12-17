
package com.raven.model;


public class ModelDatBan {
   
    
    //cap nhat, luu tru thong tin
    public String getIDBan() {
        return IDBan;
    }

    public void setIDBan(String IDBan) {
        this.IDBan = IDBan;
    }

    public String getTang() {
        return Tang;
    }

    public void setTang(String Tang) {
        this.Tang = Tang;
    }

    public String getNameKH() {
        return NameKH;
    }

    public void setNameKH(String NameKH) {
        this.NameKH = NameKH;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public int getSLNguoi() {
        return SLNguoi;
    }

    public void setSLNguoi(int SLNguoi) {
        this.SLNguoi = SLNguoi;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }
    
    public ModelDatBan() {
    }
    
    public ModelDatBan(String IDBan, String Tang, String NameKH, String SDT, int SLNguoi, String Date, String Time, String TrangThai) {
        this.IDBan = IDBan;
        this.Tang = Tang;
        this.NameKH = NameKH;
        this.SDT = SDT;
        this.SLNguoi = SLNguoi;
        this.Date = Date;
        this.Time = Time;
        this.TrangThai = TrangThai;
    }

    
    String IDBan;
    String Tang;
    String NameKH;
    String SDT;
    int SLNguoi;
    String Date;
    String Time;
    String TrangThai;
}
