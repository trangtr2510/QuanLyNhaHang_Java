package com.raven.service;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.DAOInterface;
import com.raven.model.ModelPhieuNhap;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PhieuXuatDAO implements DAOInterface<ModelPhieuNhap> {

    public static PhieuXuatDAO getInstance() {
        return new PhieuXuatDAO();
    }

    @Override
    public int insert(ModelPhieuNhap t) {
        int ketQua = 0;
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String sql = "INSERT INTO PhieuXuat (MaPX, NgayXuat, ID_ND, TongTien) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMaPN());
            pst.setDate(2, t.getNgayNhap());
            pst.setInt(3, t.getIdND());
            pst.setFloat(4, t.getTongTien());
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(ModelPhieuNhap t) {
        int ketQua = 0;
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String sql = "UPDATE PhieuXuat SET NgayXuat = ?, ID_ND = ?, TongTien = ? WHERE MaPX = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, t.getNgayNhap());
            pst.setInt(2, t.getIdND());
            pst.setFloat(3, t.getTongTien());
            pst.setInt(4, t.getMaPN());
            ketQua = pst.executeUpdate();
            //DatabaseConnection.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(ModelPhieuNhap t) {
        int ketQua = 0;
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String sql = "DELETE FROM PhieuXuat WHERE MaPX = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMaPN());
            ketQua = pst.executeUpdate();
            //DatabaseConnection.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<ModelPhieuNhap> selectAll() {
        ArrayList<ModelPhieuNhap> ketQua = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT * FROM PhieuXuat ORDER BY NgayXuat DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int maPN = rs.getInt("MaPX");
                Date ngayNhap = rs.getDate("NgayXuat");
                int idNV = rs.getInt("ID_ND");
                float tongTien = rs.getFloat("TongTien");
                ModelPhieuNhap pn = new ModelPhieuNhap(maPN, ngayNhap, idNV, tongTien);
                ketQua.add(pn);
            }
            //DatabaseConnection.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ModelPhieuNhap selectById(String t) {
        ModelPhieuNhap ketQua = null;
        String sql = "SELECT * FROM PhieuXuat WHERE MaPX = ?";
        try (Connection con = DatabaseConnection.getInstance().getConnection(); // Lấy kết nối
                 PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(t)); // Gán giá trị tham số
            try (ResultSet rs = pst.executeQuery()) { // Thực thi truy vấn và quản lý ResultSet
                if (rs.next()) {
                    int maPN = rs.getInt("MaPX");
                    Date ngayNhap = rs.getDate("NgayXuat");
                    int idNV = rs.getInt("ID_ND");
                    float tongTien = rs.getFloat("TongTien");

                    // Tạo đối tượng ModelPhieuNhap từ dữ liệu truy vấn
                    ketQua = new ModelPhieuNhap(maPN, ngayNhap, idNV, tongTien);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console
        }
        return ketQua; // Trả về kết quả
    }

    // Phương thức bổ sung nếu cần, như select theo điều kiện đặc biệt, v.v.
}
