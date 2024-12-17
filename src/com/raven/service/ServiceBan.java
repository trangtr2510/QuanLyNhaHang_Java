package com.raven.service;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelDatBan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceBan {

    private final Connection con;
    DatabaseConnection cn = new DatabaseConnection();

    //Connect tới DataBase       
    public ServiceBan() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    //them
    public void insertBan(ModelDatBan mdb) throws SQLException {
        String sql_ND = "INSERT INTO dsban (idban, tang, tenkh, sdt, slnguoi, ngaydat, giodat, idkh) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pInsert = con.prepareStatement(sql_ND);
        pInsert.setString(1, mdb.getIDBan());
        pInsert.setString(2, mdb.getTang());
        pInsert.setString(3, mdb.getNameKH());
        pInsert.setString(4, mdb.getSDT());
        pInsert.setInt(5, mdb.getSLNguoi());
        pInsert.setString(6, mdb.getDate());
        pInsert.setString(7, mdb.getTime());
        pInsert.setInt(8, mdb.getIDKH());

        pInsert.execute();
        pInsert.close();
    }

    //sua
    public void UpdateBan(ModelDatBan mdb) throws SQLException {
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

    public void HUYBan2(ModelDatBan mdb) throws SQLException {
        String sql = "UPDATE datban\n"
                + "SET tenkh = NULL, \n"
                + "    SDT = NULL, \n"
                + "    slnguoi = NULL, \n"
                + "    ngaydat = NULL, \n"
                + "    giodat = NULL, \n"
                + "    trangthai = 'Con Trong'\n"
                + "WHERE idban = ? \n"
                + "  AND tang = ?";
        String sqlDeleteDsBan = "DELETE FROM dsban WHERE idban = ? and sdt =?";
        // Execute the update first, then the delete.
        try (Connection conn = cn.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Update `datban` table
            try (PreparedStatement stmtUpdate = conn.prepareStatement(sql)) {
                stmtUpdate.setString(1, mdb.getIDBan());
                stmtUpdate.setString(2, mdb.getTang());
                stmtUpdate.executeUpdate();
                mdb.setTrangThai("Con Trong");
            }

            // Delete from `dsban` table
            try (PreparedStatement stmtDelete = conn.prepareStatement(sqlDeleteDsBan)) {
                stmtDelete.setString(1, mdb.getIDBan());
                stmtDelete.setString(2, mdb.getSDT());
                stmtDelete.executeUpdate();
            }

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally handle transaction rollback if needed
        }
    }

    //xoa
    public void HUYBan(ModelDatBan mdb) throws SQLException {
        String sql = "UPDATE datban\n"
                + "SET tenkh = NULL, \n"
                + "    SDT = NULL, \n"
                + "    slnguoi = NULL, \n"
                + "    ngaydat = NULL, \n"
                + "    giodat = NULL, \n"
                + "    trangthai = 'Con Trong'\n"
                + "WHERE idban = ? \n"
                + "  AND tang = ?";
        PreparedStatement pInsert = con.prepareStatement(sql);

        pInsert.setString(1, mdb.getIDBan());
        pInsert.setString(2, mdb.getTang());

        pInsert.executeUpdate();
        pInsert.close();
        mdb.setTrangThai("Con Trong"); // Cập nhật trạng thái của bàn
    }

    //ktra ban da dat hay chua
    public boolean checkDuplicateBanTrong(String email, String tang) throws SQLException {
        boolean duplicate = false;
        String sql = "SELECT *\n"
                + "FROM datban\n"
                + "WHERE idban = ? \n"
                + "  AND tang = ? \n"
                + "  AND Trangthai = 'Con Trong'";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, email);
        p.setString(2, tang);
        ResultSet r = p.executeQuery();
        if (r.next()) {
            duplicate = true;
        }
        r.close();
        p.close();
        return duplicate;
    }

    //ktra ban da dat hay chua
    public boolean checkDuplicateBan(String email, String tang) throws SQLException {
        boolean duplicate = false;
        String sql = "SELECT TOP 1 *\n"
                + "FROM datban\n"
                + "WHERE idban = ? \n"
                + "  AND tang = ? \n"
                + "  AND Trangthai = 'Da Dat'";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, email);
        p.setString(2, tang);
        ResultSet r = p.executeQuery();
        if (r.next()) {
            duplicate = true;
        }
        r.close();
        p.close();
        return duplicate;
    }

    //ktra da dat ban lan nao hay chua
    public boolean checkDuplicateBan2(String email) throws SQLException {
        boolean duplicate = false;
        String sql = "SELECT TOP 1 *\n"
                + "FROM dsban\n"
                + "WHERE sdt = ? \n"
                + "  AND Trangthai = 'Da Dat'";
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

    //ktra sdt 
    public boolean checkSDT(String email, String tang, String sdt) throws SQLException {
        boolean duplicate = false;
        String sql = "SELECT TOP 1 *\n"
                + "FROM datban\n"
                + "WHERE idban = ? \n"
                + "  AND tang = ? \n"
                + "  AND Trangthai = 'Da Dat'\n"
                + "  AND sdt = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, email);
        p.setString(2, tang);
        p.setString(3, sdt);
        ResultSet r = p.executeQuery();
        if (r.next()) {
            duplicate = true;
        }
        r.close();
        p.close();
        return duplicate;
    }

    public String getIdKhByIdNd(int idNd) throws SQLException {
        String idKh = null;
        String sql = "SELECT kh.idkh FROM qlkhachhang kh JOIN nguoidung nd ON kh.ID_ND = nd.ID_ND WHERE nd.ID_ND = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idNd);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            idKh = rs.getString("idkh");
        }

        rs.close();
        ps.close();
        return idKh;
    }

    public List<String> getMaVouchersByIdKH(int idKH) {
        List<String> maVoucherList = new ArrayList<>();
        String sql = "SELECT mavoucher FROM DSVoucherKH WHERE idkh = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idKH); // Gán giá trị idKH vào câu lệnh SQL
            try (ResultSet rs = ps.executeQuery()) {
                // Thêm từng mavoucher vào danh sách
                while (rs.next()) {
                    String mavoucher = rs.getString("mavoucher");
                    maVoucherList.add(mavoucher);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maVoucherList;
    }

    public String getMoTaByMaVoucher(String maVoucher) {
        String moTa = null;
        String sql = "SELECT mota FROM Voucher WHERE mavoucher = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maVoucher); // Gán giá trị mavoucher vào câu lệnh SQL
            try (ResultSet rs = ps.executeQuery()) {
                // Kiểm tra kết quả và lấy giá trị mota
                if (rs.next()) {
                    moTa = rs.getString("mota");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return moTa; // Trả về mô tả hoặc null nếu không tìm thấy
    }

}
