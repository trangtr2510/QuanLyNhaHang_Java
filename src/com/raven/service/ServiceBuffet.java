
package com.raven.service;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelBuffet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceBuffet {
     private final Connection con;
     
      //Connect tới DataBase       
    public ServiceBuffet() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    // Thêm buffet
    public void insertBuffet(ModelBuffet buffet) throws SQLException {
        String sql = "INSERT INTO QLBuffet (TenBuffet, MoTa, Gia) VALUES (?, ?, ?)";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, buffet.getTenBuffet());
        p.setString(2, buffet.getMoTa());
        p.setDouble(3, buffet.getGia());
        p.execute();
        p.close();
    }

    // Cập nhật buffet
    public void updateBuffet(ModelBuffet buffet) throws SQLException {
        String sql = "UPDATE QLBuffet SET TenBuffet = ?, MoTa = ?, Gia = ? WHERE MaBuffet = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, buffet.getTenBuffet());
        p.setString(2, buffet.getMoTa());
        p.setDouble(3, buffet.getGia());
        p.setInt(4, buffet.getMaBuffet());
        p.executeUpdate();
        p.close();
    }

    // Kiểm tra MaBuffet đã tồn tại hay chưa
    public boolean checkDuplicateBuffetID(int maBuffet) throws SQLException {
        boolean duplicate = false;
        String sql = "SELECT 1 FROM QLBuffet WHERE MaBuffet = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, maBuffet);
        ResultSet r = p.executeQuery();
        if (r.next()) {
            duplicate = true;
        }
        r.close();
        p.close();
        return duplicate;
    }
    
    public void deleteBuffet(int maBuffet) throws SQLException {
        String sql = "DELETE FROM QLBuffet WHERE MaBuffet = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, maBuffet);
        p.executeUpdate();
        p.close();
    }

    
    public String getIDBuffet() throws SQLException {
        String gtlnMaHD = null;
        String query = "SELECT MAX(MaBuffet) AS maxID FROM QLBuffet"; //lay gia tri idTD lon nhat trong bang
        try (PreparedStatement pstmt = con.prepareStatement(query);//thuc hien truy van tham so
                 ResultSet rs = pstmt.executeQuery()) {//con tro den hang dau cua bang ket qua cua cau truy van query select 
            if (rs.next()) {//di chuyen con tro den hang tiep theo trong bang, neu co hang tiep theo thi thuc thi lenh trong if
                gtlnMaHD = rs.getString("maxID"); // lay gia tri idTD lon nhat tu bang
            }
        } catch (SQLException e) {//xu ly ngoai le 
            e.printStackTrace();
        }
        return gtlnMaHD; // tra ve gia tri idhd lon nhat 
    }
    
    public List<String> getAllTenBuffet() throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT TenBuffet FROM QLBuffet";
        PreparedStatement p = con.prepareStatement(sql);
        ResultSet rs = p.executeQuery();
        while (rs.next()) {
            list.add(rs.getString("TenBuffet"));
        }
        rs.close();
        p.close();
        return list;
    }
    
    public double getGiaBuffetByTen(String tenBuffet) throws SQLException {
        double gia = 0;
        String sql = "SELECT Gia FROM QLBuffet WHERE TenBuffet = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, tenBuffet);
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            gia = rs.getDouble("Gia");
        }
        rs.close();
        p.close();
        return gia;
    }

    
}
