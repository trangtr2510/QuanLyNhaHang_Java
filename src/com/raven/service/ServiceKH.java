package com.raven.service;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelKhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceKH {

    private final Connection con;

    //Connect tới DataBase       
    public ServiceKH() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    //sua thong tin cua khach hang
    public void UpdateKH(ModelKhachHang data) throws SQLException {
        String sql = "UPDATE qlkhachhang SET tenkh=?, gioitinh=?,SDT=?,diachi=? WHERE idkh=?";
        PreparedStatement p = con.prepareStatement(sql);//interace(giao thuc) thực thi câu lệnh truy vấn tham số SQL trong JDBC
        p.setString(1, data.getName());
        p.setString(2, data.getGioiTinh());
        p.setString(3, data.getSdt());
        p.setString(4, data.getDiaChi());
        p.setInt(5, data.getIDKH());

        p.executeUpdate();//thuc thi cau truy van duoc chi dinh update
        p.close();
    }

    public int getSoLuongKHTheoThang(int month, int year) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS Total FROM QLKhachHang WHERE MONTH(Ngaythamgia) = ? AND YEAR(Ngaythamgia) = ?";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, month);
            p.setInt(2, year);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("Total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getSoLuongKHTTTheoThang(int month, int year) {
        int count = 0;
        String sql = "SELECT COUNT(DISTINCT kh.idkh) AS KQ "
                + "FROM qlkhachhang kh "
                + "JOIN qlhoadon hd ON kh.idkh = hd.idkh "
                + "WHERE kh.idkh != 0 "
                + "AND hd.TrangThai = 'Da thanh toan' "
                + "AND MONTH(kh.Ngaythamgia) = ? "
                + "AND YEAR(kh.Ngaythamgia) = ? "
                + "GROUP BY kh.idkh "
                + "HAVING COUNT(hd.idhd) > 10";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, month);
            p.setInt(2, year);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("KQ");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Integer> getAllYearsFromNgayThamGia() {
        List<Integer> years = new ArrayList<>();
        String query = "SELECT DISTINCT YEAR(Ngaythamgia) AS Year FROM QLKhachHang ORDER BY Year DESC"; 
        try (PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                years.add(rs.getInt("Year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy năm từ cột Ngaythamgia: " + e.getMessage());
        }
        return years;
    }

}
