
package com.raven.service;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelNhanVien;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTextField;


public class ServiceNV {
     private final Connection con;
    //Connect tới DataBase       
    public ServiceNV() {
        con = DatabaseConnection.getInstance().getConnection();
    }
    
    
    public void insertNV(ModelNhanVien nhanVien)throws SQLException{
        String sql_ND = "INSERT INTO qlnhanvien2 (idnv, tennv, sdt, ngayvl, chucvu, luong) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pInsert = con.prepareStatement(sql_ND);
            pInsert.setString(1, nhanVien.getMaNV());
            pInsert.setString(2, nhanVien.getTenNV());
            pInsert.setString(3, nhanVien.getSDT());
            pInsert.setString(4, nhanVien.getNgayVL());
            pInsert.setString(5, nhanVien.getChucVu());
            pInsert.setFloat(6, nhanVien.getLuong()); 
            pInsert.execute();
            pInsert.close();
    }
    
    public void UpdateNV(ModelNhanVien data) throws SQLException {
        String sql = "UPDATE qlnhanvien2 SET tennv=?,SDT=?,ngayvl =?,Chucvu=?,luong= ? WHERE idnv=?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, data.getTenNV());
        p.setString(2, data.getSDT());
        p.setString(3, data.getNgayVL());
        p.setString(4, data.getChucVu());
        p.setFloat(5, data.getLuong());
        p.setString(6, data.getMaNV());
        
        p.executeUpdate();
        p.close();
    }
    
    public void deleteNV(ModelNhanVien data)throws SQLException {
        String sql = "Delete qlnhanvien2 where idnv = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, data.getMaNV());
        
        p.executeUpdate();
        p.close();
    }
    
    /*
        Kiểm tra Email đã tồn tại trong hệ thống hay chưa
        Trả về : True <- Nếu tồn tại
                 False <- Nếu chưa tồn tại
    */
    
    public boolean checkDuplicateNV(String email) throws SQLException{
        boolean duplicate=false;
        String sql="SELECT * FROM qlnhanvien2 WHERE idnv=? FETCH FIRST 1 ROWS ONLY";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, email);
        ResultSet r=p.executeQuery();
        if(r.next()){
            duplicate=true;
        }
        r.close();
        p.close();
        return duplicate;
    }
    
    public String gtriIDNV() throws SQLException {
        String gtlnMaHD = null;
        String query = "SELECT MAX(idnv) AS maxIDNV FROM qlnhanvien2"; //lay gia tri idnv lon nhat trong bang
        try(PreparedStatement pstmt = con.prepareStatement(query);//thuc hien truy van tham so
            ResultSet rs = pstmt.executeQuery()) {//con tro den hang dau cua bang ket qua cua cau query select 
            if (rs.next()) {//di chuyen con tro den hang tiep theo trong bang, neu co hang tiep theo thi thuc thi lenh trong if
                gtlnMaHD = rs.getString("maxIDNV"); // lay gia tri idnv lon nhat tu bang
            }
        } catch (SQLException e) {//xu ly ngoai le 
            e.printStackTrace();
        }
        return gtlnMaHD; // tra ve gia tri idhd lon nhat 
    }
}
