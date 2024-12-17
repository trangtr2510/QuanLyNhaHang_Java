
package com.raven.service;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelKhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
