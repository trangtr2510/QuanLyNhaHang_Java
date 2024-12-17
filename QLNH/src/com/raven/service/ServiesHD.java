
package com.raven.service;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelHD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ServiesHD {
     private final Connection con;
    //Ket noi toi DataBase       
    public ServiesHD() {
        con = DatabaseConnection.getInstance().getConnection();
    }
    
    
    public void insertHD(ModelHD mdb)throws SQLException{
        String sql_ND = "INSERT INTO qlhoadon (idhd, idban, tang,ngaytt, gia) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pInsert = con.prepareStatement(sql_ND);
            pInsert.setString(1, mdb.getIdHD());
            pInsert.setString(2, mdb.getIdBan());
            pInsert.setString(3, mdb.getTang());
            pInsert.setString(4, mdb.getNgayTT());
            pInsert.setFloat(5, mdb.getGia()); 
            
            pInsert.execute();
            pInsert.close();
    }
    
      public void UpdateHD(ModelHD mdb)throws SQLException{
        String sql = "UPDATE qlhoadon SET idban=?, tang=?, ngaytt=?,gia= ? WHERE idhd=?";
        PreparedStatement pInsert = con.prepareStatement(sql);
            
            pInsert.setString(1, mdb.getIdBan());
            pInsert.setString(2, mdb.getTang());
            pInsert.setString(3, mdb.getNgayTT());
            pInsert.setFloat(4, mdb.getGia());
            pInsert.setString(5, mdb.getIdHD());
            
            pInsert.executeUpdate();//thuc thi cau truy van update, insert, delete,...
            pInsert.close();
    }
    
    public boolean checkDuplicateHD(String email) throws SQLException{
        boolean duplicate=false;
        String sql="SELECT * FROM qlhoadon WHERE idhd=? FETCH FIRST 1 ROWS ONLY";//lay gia tri idhd cua tung dong
        PreparedStatement p = con.prepareStatement(sql);//interace thực thi câu lệnh truy vấn SQL trong JDBC
        p.setString(1, email);
        ResultSet r = p.executeQuery();//con tro den hang dau cua bang ket qua cua cau query select 
        if(r.next()){
            duplicate=true;
        }
        r.close();
        p.close();
        return duplicate;
    }
    //lay idhd lon nhat
    public String getLatestMaHD() throws SQLException {
        String gtlnMaHD = null;
        String query = "SELECT MAX(idhd) AS maxMaHD FROM qlhoadon"; //lay gia tri idhd lon nhat trong bang
        try(PreparedStatement pstmt = con.prepareStatement(query);//thuc hien truy van tham so
            ResultSet rs = pstmt.executeQuery()) {//con tro den hang dau cua bang ket qua cua cau query select 
            if (rs.next()) {//di chuyen con tro den hang tiep theo trong bang, neu co hang tiep theo thi thuc thi lenh trong if
                gtlnMaHD = rs.getString("maxMaHD"); // lay gia tri idhd lon nhat tu bang
            }
        } catch (SQLException e) {//xu ly ngoai le 
            e.printStackTrace();
        }
        return gtlnMaHD; // tra ve gia tri idhd lon nhat 
    }
    
}
