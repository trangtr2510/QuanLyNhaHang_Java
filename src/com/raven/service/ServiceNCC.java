package com.raven.service;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelNCC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceNCC {

    private final Connection con;

    // Connect to the database
    public ServiceNCC() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    // Insert a new supplier
    public void insertNCC(ModelNCC nhaCungCap) throws SQLException {
        String sql = "INSERT INTO NhaCungCap (MaNCC, NhaCungCap, DiaChi, SDT, Email, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pInsert = con.prepareStatement(sql);
        pInsert.setInt(1, nhaCungCap.getMaNCC());
        pInsert.setString(2, nhaCungCap.getNhaCungCap());
        pInsert.setString(3, nhaCungCap.getDiaChi());
        pInsert.setString(4, nhaCungCap.getSdt());
        pInsert.setString(5, nhaCungCap.getEmail());
        pInsert.setString(6, nhaCungCap.getTrangThai());
        pInsert.execute();
        pInsert.close();
    }

    // Update supplier details
    public void updateNCC(ModelNCC nhaCungCap) throws SQLException {
        String sql = "UPDATE NhaCungCap SET NhaCungCap = ?, DiaChi = ?, SDT = ?, Email = ?, TrangThai = ? WHERE MaNCC = ?";
        PreparedStatement pUpdate = con.prepareStatement(sql);
        pUpdate.setString(1, nhaCungCap.getNhaCungCap());
        pUpdate.setString(2, nhaCungCap.getDiaChi());
        pUpdate.setString(3, nhaCungCap.getSdt());
        pUpdate.setString(4, nhaCungCap.getEmail());
        pUpdate.setString(5, nhaCungCap.getTrangThai());
        pUpdate.setInt(6, nhaCungCap.getMaNCC());
        pUpdate.executeUpdate();
        pUpdate.close();
    }

    // Delete a supplier
    public void deleteNCC(int maNCC) throws SQLException {
        String sql = "DELETE FROM NhaCungCap WHERE MaNCC = ?";
        PreparedStatement pDelete = con.prepareStatement(sql);
        pDelete.setInt(1, maNCC);
        pDelete.executeUpdate();
        pDelete.close();
    }

    public boolean checkDuplicateNCCByName(String tenNCC) throws SQLException {
        boolean duplicate = false;
        String sql = "SELECT 1 FROM NguyenLieu WHERE NhaCungCap = ?";

        try (PreparedStatement pCheck = con.prepareStatement(sql)) {
            pCheck.setString(1, tenNCC); 
            try (ResultSet r = pCheck.executeQuery()) {
                if (r.next()) {
                    duplicate = true; // Có dữ liệu trả về => Tên đã tồn tại
                }
            }
        }
        return duplicate; 
    }

    // Check if a supplier already exists by MaNCC
    public boolean checkDuplicateNCC(int maNCC) throws SQLException {
        boolean duplicate = false;
        String sql = "SELECT 1 FROM NhaCungCap WHERE MaNCC = ?";
        PreparedStatement pCheck = con.prepareStatement(sql);
        pCheck.setInt(1, maNCC);
        ResultSet r = pCheck.executeQuery();
        if (r.next()) {
            duplicate = true;
        }
        r.close();
        pCheck.close();
        return duplicate;
    }

    // Get the maximum MaNCC
    public int getMaxMaNCC() throws SQLException {
        int maxMaNCC = 0;
        String sql = "SELECT MAX(MaNCC) AS maxMaNCC FROM NhaCungCap";
        PreparedStatement pMax = con.prepareStatement(sql);
        ResultSet r = pMax.executeQuery();
        if (r.next()) {
            maxMaNCC = r.getInt("maxMaNCC");
        }
        r.close();
        pMax.close();
        return maxMaNCC;
    }
}
