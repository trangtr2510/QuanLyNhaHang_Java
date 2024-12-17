package com.raven.service;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelNguyenLieu;
import com.raven.model.ModelPhieuNhap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceNguyenLieu {

    private Connection con;

    public ServiceNguyenLieu() {
        this.con = DatabaseConnection.getInstance().getConnection();
        // Check if the connection is closed and reconnect if needed
        ensureConnectionIsOpen();
    }

    // Ensures the connection is open, reconnecting if necessary
    private void ensureConnectionIsOpen() {
        try {
            if (con == null || con.isClosed()) {
                con = DatabaseConnection.getInstance().getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error ensuring connection is open: " + e.getMessage());
        }
    }

    public void insertNguyenLieu(ModelNguyenLieu nguyenLieu) throws SQLException {
        ensureConnectionIsOpen();  // Ensure the connection is open

        String sql = "INSERT INTO nguyenlieu (MaNL, TenNL, DonViTinh, NhaCungCap, Gia, HanSuDung, TrangThai) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pInsert = con.prepareStatement(sql)) {
            pInsert.setInt(1, nguyenLieu.getMaNL());
            pInsert.setString(2, nguyenLieu.getTenNL());
            pInsert.setString(3, nguyenLieu.getDonViTinh());
            pInsert.setString(4, nguyenLieu.getNhaCungCap());
            pInsert.setDouble(5, nguyenLieu.getGia());
            pInsert.setDate(6, (Date) nguyenLieu.getHanSuDung());
            pInsert.setString(7, nguyenLieu.getTrangThai());

            pInsert.executeUpdate(); // Execute insert query
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Re-throw the exception after logging it
        }
    }

    public void updateNguyenLieu(ModelNguyenLieu nguyenLieu) throws SQLException {
        ensureConnectionIsOpen();  // Ensure the connection is open

        String sql = "UPDATE nguyenlieu SET TenNL=?, DonViTinh=?, NhaCungCap=?, Gia=?, HanSuDung=?, TrangThai=? WHERE MaNL=?";
        try (PreparedStatement pUpdate = con.prepareStatement(sql)) {
            pUpdate.setString(1, nguyenLieu.getTenNL());
            pUpdate.setString(2, nguyenLieu.getDonViTinh());
            pUpdate.setString(3, nguyenLieu.getNhaCungCap());
            pUpdate.setDouble(4, nguyenLieu.getGia());
            pUpdate.setDate(5, (Date) nguyenLieu.getHanSuDung());
            pUpdate.setString(6, nguyenLieu.getTrangThai());
            pUpdate.setInt(7, nguyenLieu.getMaNL());

            pUpdate.executeUpdate(); // Execute update query
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Re-throw the exception after logging it
        }
    }

    public boolean checkDuplicateNL(String email) throws SQLException {
        boolean duplicate = false;
        String sql = "SELECT TOP 1 *\n"
                + "FROM Nguyenlieu\n"
                + "WHERE TenNL = ? ";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, email);
        ResultSet r = p.executeQuery();
        if (r.next()) {
            duplicate = true;
        }
        r.close();
        p.close();
        return duplicate;
    }

    public void deleteNguyenLieu(String maNL) throws SQLException {
        ensureConnectionIsOpen();  // Ensure the connection is open

        String sql = "DELETE FROM nguyenlieu WHERE MaNL = ?";
        try (PreparedStatement pDelete = con.prepareStatement(sql)) {
            pDelete.setString(1, maNL);
            pDelete.executeUpdate(); // Execute delete query
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Re-throw the exception after logging it
        }
    }

    public String getLatestNguyenLieu() throws SQLException {
        ensureConnectionIsOpen();  // Ensure the connection is open

        String gtlnMaHD = null;
        String query = "SELECT MAX(MaNL) AS maxMaNL FROM nguyenlieu";
        try (PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                gtlnMaHD = rs.getString("maxMaNL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Re-throw the exception after logging it
        }
        return gtlnMaHD;
    }

    public String getLatestPhieuXuat() throws SQLException {
        ensureConnectionIsOpen();  // Ensure the connection is open

        String gtlnMaHD = null;
        String query = "SELECT MAX(MaPX) AS maxMaPX FROM PhieuXuat";
        try (PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                gtlnMaHD = rs.getString("maxMaPX");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Re-throw the exception after logging it
        }
        return gtlnMaHD;
    }

    public String getLatestPhieuNhap() throws SQLException {
        ensureConnectionIsOpen();  // Ensure the connection is open

        String gtlnMaHD = null;
        String query = "SELECT MAX(MaPN) AS maxMaPN FROM PhieuNhap";
        try (PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                gtlnMaHD = rs.getString("maxMaPN");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Re-throw the exception after logging it
        }
        return gtlnMaHD;
    }

    public ModelPhieuNhap selectById(String t) {
        ensureConnectionIsOpen();  // Ensure the connection is open

        ModelPhieuNhap ketQua = null;
        String sql = "SELECT * FROM PhieuNhap WHERE MaPN = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(t));  // Gán giá trị tham số
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int maPN = rs.getInt("MaPN");
                    Date ngayNhap = rs.getDate("NgayNhap");
                    int idNV = rs.getInt("ID_ND");
                    float tongTien = rs.getFloat("TongTien");

                    // Tạo đối tượng ModelPhieuNhap từ dữ liệu truy vấn
                    ketQua = new ModelPhieuNhap(maPN, ngayNhap, idNV, tongTien);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public void deletePhieuNhap(int maPN) throws SQLException {
        ensureConnectionIsOpen();
        try {
            con.setAutoCommit(false);
            // Step 1: Delete from ChiTietPhieuNhap
            String deleteChiTietSql = "DELETE FROM ChiTietPhieuNhap WHERE MaPN = ?";
            try (PreparedStatement pstChiTiet = con.prepareStatement(deleteChiTietSql)) {
                pstChiTiet.setInt(1, maPN);
                pstChiTiet.executeUpdate();
            }
            // Step 2: Delete from PhieuNhap
            String deletePhieuNhapSql = "DELETE FROM PhieuNhap WHERE MaPN = ?";
            try (PreparedStatement pstPhieuNhap = con.prepareStatement(deletePhieuNhapSql)) {
                pstPhieuNhap.setInt(1, maPN);
                pstPhieuNhap.executeUpdate();
            }
            con.commit();
            System.out.println("Successfully deleted PhieuNhap and its ChiTietPhieuNhap.");

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                    System.err.println("Transaction rolled back due to: " + e.getMessage());
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw e;

        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public float getSLbyID(int maNL) {
        ensureConnectionIsOpen(); // Ensure the connection is open

        String sql = "SELECT SoLuong FROM Kho WHERE MaNL = ?";
        float totalQuantity = 0;

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, maNL); // Set the material ID in the query

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    totalQuantity = rs.getFloat("SoLuong");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error fetching total quantity for MaNL " + maNL + ": " + e.getMessage());
        }

        return totalQuantity;
    }

    public ModelPhieuNhap selectByIdPX(String t) {
        ensureConnectionIsOpen();  // Ensure the connection is open

        ModelPhieuNhap ketQua = null;
        String sql = "SELECT * FROM PhieuXuat WHERE MaPX = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(t));  // Gán giá trị tham số
            try (ResultSet rs = pst.executeQuery()) {
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
            e.printStackTrace();
        }
        return ketQua;
    }

    public int getSoLuongNguyenLieuTheoTrangThai(String trangThai) {
        int soLuong = 0;
        ensureConnectionIsOpen();

        String query = "SELECT COUNT(*) AS SoLuong FROM NguyenLieu WHERE TrangThai = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, trangThai);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    soLuong = rs.getInt("SoLuong");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy số lượng cho TrangThai = " + trangThai + ": " + e.getMessage());
        }
        return soLuong;
    }

    public int getTongSoLuongNguyenLieu() {
        int totalNguyenLieu = 0;
        String query = "SELECT COUNT(*) FROM NguyenLieu";
        try {
            ensureConnectionIsOpen(); // Đảm bảo kết nối được mở
            try (PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    totalNguyenLieu = rs.getInt(1); // Lấy giá trị đếm từ cột đầu tiên
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Loi NguyenLieu: " + e.getMessage());
        }

        return totalNguyenLieu;
    }

    public int getSoLanNhapHangTheoThang(int thang, int nam) {
        int soLanNhap = 0;
        ensureConnectionIsOpen();
        String query = "SELECT COUNT(*) AS SoLanNhap FROM PhieuNhap WHERE MONTH(NgayNhap) = ? AND YEAR(NgayNhap) = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, thang);
            pstmt.setInt(2, nam);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    soLanNhap = rs.getInt("SoLanNhap");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy số lần nhập hàng cho tháng = " + thang + " năm = " + nam + ": " + e.getMessage());
        }
        return soLanNhap;
    }

    public int getSoLanXuatHangTheoThang(int thang, int nam) {
        int soLanXuat = 0;
        ensureConnectionIsOpen();
        String query = "SELECT COUNT(*) AS SoLanXuat FROM PhieuXuat WHERE MONTH(NgayXuat) = ? AND YEAR(NgayXuat) = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, thang);
            pstmt.setInt(2, nam);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    soLanXuat = rs.getInt("SoLanXuat");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy số lần xuất hàng cho tháng = " + thang + " năm = " + nam + ": " + e.getMessage());
        }
        return soLanXuat;
    }

    public double getTongTienNhapHangTheoThang(int thang, int nam) {
        double tongTienNhap = 0;
        ensureConnectionIsOpen();
        String query = "SELECT SUM(TongTien) AS TongTienNhap FROM PhieuNhap WHERE MONTH(NgayNhap) = ? AND YEAR(NgayNhap) = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, thang);
            pstmt.setInt(2, nam);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    tongTienNhap = rs.getDouble("TongTienNhap");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy tổng tiền nhập hàng cho tháng = " + thang + " năm = " + nam + ": " + e.getMessage());
        }
        return tongTienNhap;
    }

    public double getTongTienXuatHangTheoThang(int thang, int nam) {
        double tongTienXuat = 0;
        ensureConnectionIsOpen();
        String query = "SELECT SUM(TongTien) AS TongTienXuat FROM PhieuXuat WHERE MONTH(NgayXuat) = ? AND YEAR(NgayXuat) = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, thang);
            pstmt.setInt(2, nam);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    tongTienXuat = rs.getDouble("TongTienXuat");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy tổng tiền xuất hàng cho tháng = " + thang + " năm = " + nam + ": " + e.getMessage());
        }
        return tongTienXuat;
    }

    public List<Integer> getAllYearsFromNgayNhap() {
        List<Integer> years = new ArrayList<>();
        // Đảm bảo kết nối được mở
        ensureConnectionIsOpen();
        String query = "SELECT DISTINCT YEAR(NgayNhap) AS Year FROM PhieuNhap ORDER BY Year DESC"; // Lấy tất cả các năm duy nhất và sắp xếp giảm dần
        try (PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                years.add(rs.getInt("Year")); // Thêm năm vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy năm từ cột NgayNhap: " + e.getMessage());
        }
        return years;
    }

    public List<Integer> getAllYearsFromNgayXuat() {
        List<Integer> years = new ArrayList<>();
        // Đảm bảo kết nối được mở
        ensureConnectionIsOpen();
        String query = "SELECT DISTINCT YEAR(NgayXuat) AS Year FROM PhieuXuat ORDER BY Year DESC"; // Lấy tất cả các năm duy nhất và sắp xếp giảm dần
        try (PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                years.add(rs.getInt("Year")); // Thêm năm vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy năm từ cột NgayXuat: " + e.getMessage());
        }
        return years;
    }

    public boolean giamSoLuongTonKho(String maNL, int soLuong) {
        ensureConnectionIsOpen();
        String sql = "UPDATE Kho SET SoLuong = SoLuong - ? WHERE MaNL = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameters
            ps.setInt(1, soLuong);
            ps.setString(2, maNL);

            // Execute update
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean tangSoLuongTonKho(String maNL, int soLuong) {
        ensureConnectionIsOpen();
        String sql = "UPDATE Kho SET SoLuong = SoLuong + ? WHERE MaNL = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameters
            ps.setInt(1, soLuong);
            ps.setString(2, maNL);

            // Execute update
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSoLuong(String maNL, int soLuong) {
        ensureConnectionIsOpen();
        String sql = "UPDATE Kho SET SoLuong = ? WHERE MaNL = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameters
            ps.setInt(1, soLuong);
            ps.setString(2, maNL);

            // Execute update
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getActiveSuppliers() throws SQLException {
        List<String> supplierNames = new ArrayList<>();
        String sql = "SELECT NhaCungCap FROM NhaCungCap WHERE TrangThai = 'Hoat dong'";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            supplierNames.add(rs.getString("NhaCungCap"));
        }
        rs.close();
        pstmt.close();
        return supplierNames;
    }

}
