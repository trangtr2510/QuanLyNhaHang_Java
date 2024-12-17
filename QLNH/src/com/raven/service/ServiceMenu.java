
package com.raven.service;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelMenu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ServiceMenu {
    private final Connection con;
    //Connect tới DataBase       
    public ServiceMenu(){
        con = DatabaseConnection.getInstance().getConnection();
    }
    
    public void insertMenu(ModelMenu menu)throws SQLException{
        String sql_ND = "INSERT INTO qlmenu (mathucdon, tenthucdon, loaithucdon, gia) VALUES (?, ?, ?, ?)";
        PreparedStatement pInsert = con.prepareStatement(sql_ND);
            pInsert.setString(1, menu.getIdTD());
            pInsert.setString(2, menu.getNameTD());
            pInsert.setString(3, menu.getLoaiTD());
            pInsert.setFloat(4, menu.getGia()); 
            pInsert.execute();
            pInsert.close();
    }
    
    public void UpdateMenu(ModelMenu menu) throws SQLException {
        String sql = "UPDATE qlmenu SET tenthucdon=?,loaithucdon=?,gia =? WHERE mathucdon=?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, menu.getNameTD());
        p.setString(2, menu.getLoaiTD());
        p.setFloat(3, menu.getGia());
        p.setString(4, menu.getIdTD());
        
        p.executeUpdate();
        p.close();
    }
    
    /*
        Kiểm tra ma thuc don đã tồn tại trong hệ thống hay chưa
        Trả về : True <- Nếu tồn tại
                 False <- Nếu chưa tồn tại
    */
    public boolean checkDuplicateID(String email) throws SQLException{
        boolean duplicate=false;
        String sql="SELECT * FROM qlmenu WHERE mathucdon=? FETCH FIRST 1 ROWS ONLY";
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
    
    public String getIDMenu() throws SQLException {
        String gtlnMaHD = null;
        String query = "SELECT MAX(mathucdon) AS maxID FROM qlmenu"; //lay gia tri idTD lon nhat trong bang
        try(PreparedStatement pstmt = con.prepareStatement(query);//thuc hien truy van tham so
            ResultSet rs = pstmt.executeQuery()) {//con tro den hang dau cua bang ket qua cua cau truy van query select 
            if (rs.next()) {//di chuyen con tro den hang tiep theo trong bang, neu co hang tiep theo thi thuc thi lenh trong if
                gtlnMaHD = rs.getString("maxID"); // lay gia tri idTD lon nhat tu bang
            }
        } catch (SQLException e) {//xu ly ngoai le 
            e.printStackTrace();
        }
        return gtlnMaHD; // tra ve gia tri idhd lon nhat 
    }
}
