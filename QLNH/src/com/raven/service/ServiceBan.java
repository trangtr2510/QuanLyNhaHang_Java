
package com.raven.service;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelDatBan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ServiceBan {
     private final Connection con;
    //Connect tới DataBase       
    public ServiceBan() {
        con = DatabaseConnection.getInstance().getConnection();
    }
    
    //them
    public void insertBan(ModelDatBan mdb)throws SQLException{
        String sql_ND = "INSERT INTO dsban (idban, tang, tenkh, sdt, slnguoi, ngaydat, giodat, trangthai) VALUES (?, ?, ?, ?, ?, ?, ?, 'Da Dat')";
        PreparedStatement pInsert = con.prepareStatement(sql_ND);
            pInsert.setString(1, mdb.getIDBan());
            pInsert.setString(2, mdb.getTang());
            pInsert.setString(3, mdb.getNameKH());
            pInsert.setString(4, mdb.getSDT());
            pInsert.setInt(5, mdb.getSLNguoi()); 
            pInsert.setString(6, mdb.getDate());
            pInsert.setString(7, mdb.getTime());
            
            pInsert.execute();
            pInsert.close();
            mdb.setTrangThai("Da Dat"); // Cập nhật trạng thái của bàn
    }
    //sua
    public void UpdateBan(ModelDatBan mdb)throws SQLException{
        String sql = "UPDATE datban SET tenkh=?, SDT=?,slnguoi=?,ngaydat= ?,giodat=?, trangthai='Da Dat' WHERE idban=? and tang=?";
        PreparedStatement pInsert = con.prepareStatement(sql);
            pInsert.setString(1, mdb.getNameKH());
            pInsert.setString(2, mdb.getSDT());
            pInsert.setInt(3, mdb.getSLNguoi()); 
            pInsert.setString(4, mdb.getDate());
            pInsert.setString(5, mdb.getTime());
            pInsert.setString(6, mdb.getIDBan());
            pInsert.setString(7, mdb.getTang());
            
            pInsert.executeUpdate();
            pInsert.close();
            mdb.setTrangThai("Da Dat"); // Cập nhật trạng thái của bàn
    }
    //xoa
     public void HUYBan(ModelDatBan mdb)throws SQLException{
        String sql = "UPDATE datban\n" +
                    "SET tenkh = NULL, \n" +
                    "    SDT = NULL, \n" +
                    "    slnguoi = NULL, \n" +
                    "    ngaydat = NULL, \n" +
                    "    giodat = NULL, \n" +
                    "    trangthai = 'Con Trong'\n" +
                    "WHERE idban = ? \n" +
                    "  AND tang = ?";
        PreparedStatement pInsert = con.prepareStatement(sql);
            
            pInsert.setString(1, mdb.getIDBan());
            pInsert.setString(2, mdb.getTang());
            
            pInsert.executeUpdate();
            pInsert.close();
            mdb.setTrangThai("Con Trong"); // Cập nhật trạng thái của bàn
    }
    //ktra ban da dat hay chua
    public boolean checkDuplicateBan(String email, String tang) throws SQLException{
        boolean duplicate=false;
        String sql="SELECT * FROM datban WHERE idban=? AND tang =? AND Trangthai='Da Dat' FETCH FIRST 1 ROWS ONLY";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, email);
        p.setString(2, tang);
        ResultSet r=p.executeQuery();
        if(r.next()){
            duplicate=true;
        }
        r.close();
        p.close();
        return duplicate;
    }
    
    //ktra sdt 
    public boolean checkSDT(String email, String tang, String sdt) throws SQLException{
        boolean duplicate=false;
        String sql="SELECT * FROM datban WHERE idban=? AND tang =? AND Trangthai='Da Dat' AND sdt=? FETCH FIRST 1 ROWS ONLY";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, email);
        p.setString(2, tang);
        p.setString(3, sdt);
        ResultSet r=p.executeQuery();
        if(r.next()){
            duplicate=true;
        }
        r.close();
        p.close();
        return duplicate;
    }
}
