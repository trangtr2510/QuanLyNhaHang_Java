
package com.raven.model;

public class ModelBuffet {
    private int maBuffet;
    private String tenBuffet;
    private String moTa;
    private double gia;

    public ModelBuffet() {
    }
    
    public ModelBuffet(int maBuffet, String tenBuffet, String moTa, double gia) {
        this.maBuffet = maBuffet;
        this.tenBuffet = tenBuffet;
        this.moTa = moTa;
        this.gia = gia;
    }

    public int getMaBuffet() {
        return maBuffet;
    }

    public void setMaBuffet(int maBuffet) {
        this.maBuffet = maBuffet;
    }

    public String getTenBuffet() {
        return tenBuffet;
    }

    public void setTenBuffet(String tenBuffet) {
        this.tenBuffet = tenBuffet;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
    
    
}
