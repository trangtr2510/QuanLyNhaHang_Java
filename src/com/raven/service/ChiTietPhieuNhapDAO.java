package com.raven.service;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ChiTietPhieuNhap;
import com.raven.model.ModelPhieuNhap;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuNhapDAO {

    private static ChiTietPhieuNhapDAO instance;

    public ChiTietPhieuNhapDAO() {
        
    }

    public static ChiTietPhieuNhapDAO getInstance() {
        if (instance == null) {
            instance = new ChiTietPhieuNhapDAO();
        }
        return instance;
    }

    // Phương thức để thêm chi tiết phiếu nhập vào cơ sở dữ liệu
    public void insertChiTietPhieuNhap(int maPN, int maNL, float soLuong, float donGia) {
        String sql = "INSERT INTO ChiTietPhieuNhap (MaPN, MaNL, SoLuong, DonGia) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); // Mở kết nối ở đây
                 PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPN);
            ps.setInt(2, maNL);
            ps.setFloat(3, soLuong);
            ps.setFloat(4, donGia);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức để lấy danh sách chi tiết phiếu nhập từ cơ sở dữ liệu
    public List<ChiTietPhieuNhap> getChiTietPhieuNhapByMaPN(int maPN) {
        String sql = "SELECT * FROM ChiTietPhieuNhap WHERE MaPN = ?";
        List<ChiTietPhieuNhap> chiTietPhieuNhaps = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPN);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietPhieuNhap chiTiet = new ChiTietPhieuNhap();
                chiTiet.setMaPN(rs.getInt("MaPN"));
                chiTiet.setMaNL(rs.getInt("MaNL"));
                chiTiet.setSoLuong(rs.getFloat("SoLuong"));
                chiTiet.setDonGia(rs.getFloat("DonGia"));
                chiTietPhieuNhaps.add(chiTiet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietPhieuNhaps;
    }

    // Phương thức để xóa chi tiết phiếu nhập
    public int deleteChiTietPhieuNhap(int maPN, int maNL) {
        String sql = "DELETE FROM ChiTietPhieuNhap WHERE MaPN = ? AND MaNL = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPN);
            ps.setInt(2, maNL);

            // Thực thi câu lệnh và trả về số dòng bị ảnh hưởng
            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

   
}
