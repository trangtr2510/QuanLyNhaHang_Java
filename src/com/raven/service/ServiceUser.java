package com.raven.service;

import com.raven.model.ModelUser;
import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelLogin;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

// Controller Đăng ký/Đăng nhập vào hệ thống
public class ServiceUser {

    private final Connection con;

    //Connect tới DataBase       
    public ServiceUser() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    /*
        Kiểm tra thông tin đăng nhập
        Trả về : null <- Nếu Thông tin đăng nhập sai
                 ModelNguoiDung <- Nếu thông tin đăng nhập đúng
     */
    public ModelUser login(ModelLogin login) throws SQLException {
        ModelUser user = null;
        String sql = "SELECT TOP 1 * FROM NguoiDung WHERE Email = ? AND Matkhau = ? AND Trangthai = 'Verified'";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, login.getEmail());
        p.setString(2, login.getPassword());
        ResultSet r = p.executeQuery();//con tro den hang dau cua bang ket qua cua cau query select 
        if (r.next()) { //di chuyen con tro den hang tiep theo trong bang,neu co hang tiep theo thi thuc thi lenh trong if
            int UserID = r.getInt("ID_ND");
            String email = r.getString("Email");
            String password = r.getString("Matkhau");
            String role = r.getString("Vaitro");
            user = new ModelUser(UserID, email, password, role);
        }
        r.close();
        p.close();
        return user;
    }

    /*
        Phần đăng ký chỉ dành cho khách hàng, sau khi đăng ký thành công:
        Thêm thông tin Người dùng gồm email, mật khẩu, verifycode với
          Vai trò mặc định là 'Khach Hang' xuống bảng NguoiDung.
     */
    public void insertUser(ModelUser user) throws SQLException {
        //Lấy ID của User tiếp theo 
        PreparedStatement p1 = con.prepareStatement("SELECT MAX(ID_ND) as ID_ND FROM NguoiDung");
        ResultSet r = p1.executeQuery();
        r.next();
        int userID = r.getInt("ID_ND") + 1;

        //Thêm Người Dùng
        String sql_ND = "INSERT INTO NguoiDung (ID_ND,Email, MatKhau, VerifyCode,Vaitro) VALUES (?,?, ?, ?,'Khach Hang')";
        PreparedStatement p = con.prepareStatement(sql_ND);
        String code = generateVerifiyCode();
        p.setInt(1, userID);
        p.setString(2, user.getEmail());
        p.setString(3, user.getPassword());
        p.setString(4, code);
        p.execute();

        r.close();
        p.close();
        p1.close();

        user.setUserID(userID);
        user.setVerifyCode(code);
        user.setRole("Khach Hang");
    }

    public void insertND(ModelUser user) throws SQLException {
        //Lấy ID của User tiếp theo 
        PreparedStatement p1 = con.prepareStatement("SELECT MAX(ID_ND) as ID_ND FROM NguoiDung");
        ResultSet r = p1.executeQuery();
        r.next();
        int userID = r.getInt("ID_ND") + 1;

        //Thêm Người Dùng
        String sql_ND = "INSERT INTO NguoiDung (ID_ND,Email, MatKhau, Vaitro) VALUES (?,?, ?, 'Chua phan quyen')";
        PreparedStatement p = con.prepareStatement(sql_ND);
        String code = generateVerifiyCode();
        p.setInt(1, userID);
        p.setString(2, user.getEmail());
        p.setString(3, user.getPassword());
        p.execute();

        r.close();
        p.close();
        p1.close();

        user.setUserID(userID);
        user.setRole("Chua phan quyen");
    }

    public void updateUserStatus(int userID, String newRole) throws SQLException {
        String sqlUpdate = "UPDATE NguoiDung SET TrangThai = 'Verified', Vaitro = ? WHERE ID_ND = ?";
        PreparedStatement p = con.prepareStatement(sqlUpdate);
        p.setString(1, newRole);
        p.setInt(2, userID);
        int rowsUpdated = p.executeUpdate();

        if (rowsUpdated > 0) {
            if (newRole.equalsIgnoreCase("Nhan Vien") || newRole.equalsIgnoreCase("Nhan Vien Kho")) {
                String chucVu = newRole.equalsIgnoreCase("Nhan Vien") ? "Le tan" : "Nhan Vien Kho";
                addNewEmployee(userID, chucVu);
            }
        } else {
            System.out.println("Không tìm thấy người dùng với ID " + userID + ".");
        }

        p.close();
    }

    private void addNewEmployee(int userID, String chucVu) throws SQLException {
        String sqlGetMaxID = "SELECT MAX(IDNV) as MaxID FROM QLNhanVien2";
        PreparedStatement getMaxID = con.prepareStatement(sqlGetMaxID);
        ResultSet rs = getMaxID.executeQuery();

        String newIDNV;
        if (rs.next() && rs.getString("MaxID") != null) {
            int maxID = Integer.parseInt(rs.getString("MaxID").substring(2)) + 1;
            newIDNV = String.format("NV%03d", maxID);
        } else {
            newIDNV = "NV001";
        }
        String sqlInsert = "INSERT INTO QLNhanVien2 (IDNV, TenNV, SDT, NgayVL, Chucvu, Luong, ID_ND) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement insertEmployee = con.prepareStatement(sqlInsert);
        insertEmployee.setString(1, newIDNV);
        insertEmployee.setString(2, "Nhan Vien moi phan quyen");
        insertEmployee.setString(3, "0000000000");
        insertEmployee.setDate(4, new java.sql.Date(System.currentTimeMillis()));
        insertEmployee.setString(5, chucVu);
        insertEmployee.setFloat(6, 0.0f);
        insertEmployee.setInt(7, userID);
        insertEmployee.executeUpdate();
        rs.close();
        getMaxID.close();
        insertEmployee.close();
    }

    public void deleteUser(int userID) throws SQLException {
        con.setAutoCommit(false);
        try {
            String deleteKHSQL = "DELETE FROM QLKhachHang WHERE ID_ND = ?";
            PreparedStatement deleteKHStmt = con.prepareStatement(deleteKHSQL);
            deleteKHStmt.setInt(1, userID);
            deleteKHStmt.executeUpdate();
            deleteKHStmt.close();
            
            String deleteEmployeeSQL = "DELETE FROM QLNhanVien2 WHERE ID_ND = ?";
            PreparedStatement deleteEmployeeStmt = con.prepareStatement(deleteEmployeeSQL);
            deleteEmployeeStmt.setInt(1, userID);
            deleteEmployeeStmt.executeUpdate();
            deleteEmployeeStmt.close();

            String deleteUserSQL = "DELETE FROM NguoiDung WHERE ID_ND = ?";
            PreparedStatement deleteUserStmt = con.prepareStatement(deleteUserSQL);
            deleteUserStmt.setInt(1, userID);
            deleteUserStmt.executeUpdate();
            deleteUserStmt.close();
            con.commit();
        } catch (SQLException e) {
            con.rollback();
        } finally {
            con.setAutoCommit(true);
        }
    }

    //Tạo random Mã xác minh
    public String generateVerifiyCode() throws SQLException {
        DecimalFormat df = new DecimalFormat("000000");
        Random ran = new Random();
        String code = df.format(ran.nextInt(1000000));  //  Random from 0 to 999999
        while (checkDuplicateCode(code)) {
            code = df.format(ran.nextInt(1000000));
        }
        return code;
    }

    //Kiểm tra Mã trùng 
    private boolean checkDuplicateCode(String code) throws SQLException {
        boolean duplicate = false;
        String sql = "SELECT TOP 1 *\n"
                + "FROM NguoiDung\n"
                + "WHERE VerifyCode = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, code);
        ResultSet r = p.executeQuery();
        if (r.next()) {
            duplicate = true;
        }
        r.close();
        p.close();
        return duplicate;
    }

    /*
        Kiểm tra Email đã tồn tại trong hệ thống hay chưa
        Trả về : True <- Nếu tồn tại
                 False <- Nếu chưa tồn tại
     */
    public boolean checkDuplicateEmail(String email) throws SQLException {
        boolean duplicate = false;
        String sql = "SELECT TOP 1 *\n"
                + "FROM NguoiDung\n"
                + "WHERE Email = ? \n"
                + "  AND Trangthai = 'Verified'";
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

    /*
        Sau khi Hoàn tất xác minh tài khoản:
        1.Cập nhật VerifyCode= '' và Trangthai của Người dùng thành Verified
        2. Thêm mới một khách hàng vào bảng KhachHang với các thông tin:
        - Tên KH : lấy từ phần đăng ký
        - Ngày tham gia: ngày hiện tại đăng ký
        - Doanh số, điểm tích lũy mặc định là 0
        - ID_ND lấy từ Người dùng vừa tạo
     */
    public void doneVerify(int userID, String name) throws SQLException {
        //Cập nhật NguoiDung
        String sql_ND = "UPDATE NguoiDung SET VerifyCode='', Trangthai='Verified' WHERE ID_ND=?";
        PreparedStatement p1 = con.prepareStatement(sql_ND);
        p1.setInt(1, userID);
        p1.execute();
        //Lấy id của Khách Hàng tiếp theo
        int id = 0;
        String sql_ID = "SELECT MAX(IDKH) as ID FROM QLKhachHang";
        PreparedStatement p_id = con.prepareStatement(sql_ID);
        ResultSet r = p_id.executeQuery();
        if (r.next()) {
            id = r.getInt("ID") + 1;
        }

        //Thêm KH mới
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");

        String sql_KH = "INSERT INTO QLKhachHang (IDKH, TenKH, Ngaythamgia, ID_ND, Ngaybatdaudem) VALUES (?, ?, CONVERT(DATE, ?, 105), ?, CONVERT(DATE, ?, 105))";
        PreparedStatement p2 = con.prepareStatement(sql_KH);
        p2.setInt(1, id);
        p2.setString(2, name);
        p2.setString(3, simpleDateFormat.format(new Date()));
        p2.setInt(4, userID);
        p2.setString(5, simpleDateFormat.format(new Date()));
        p2.execute();

        p1.close();
        p_id.close();
        p2.close();
    }

    /* 
       Kiểm trả Verify Code của người dùng nhập vào với 
       Verify Code của người dùng đó được lưu trên DB
       Trả về : True <- Nếu Mã xác minh đúng
                False <- Nếu nhập sai
     */
    public boolean verifyCodeWithUser(int userID, String code) throws SQLException {
        boolean verify = false;
        String sql = "SELECT COUNT(ID_ND) as CountID FROM NguoiDung WHERE ID_ND=? AND VerifyCode=?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, userID);
        p.setString(2, code);
        ResultSet r = p.executeQuery();
        if (r.next()) {
            if (r.getInt("CountID") > 0) {
                verify = true;
            }
        }
        r.close();
        p.close();
        return verify;
    }

    //Thay đổi mật khẩu tài khoản
    public void changePassword(int userID, String newPass) throws SQLException {
        String sql = "UPDATE NguoiDung SET MatKhau = ? WHERE ID_ND = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, newPass);
        p.setInt(2, userID);
        p.execute();
        p.close();
    }
}
