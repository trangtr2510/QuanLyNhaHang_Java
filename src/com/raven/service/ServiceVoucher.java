package com.raven.service;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelDatBan;
import com.raven.model.ModelNhanVien;
import com.raven.model.ModelVoucher;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class ServiceVoucher {

    private final Connection con;
    DatabaseConnection cn = new DatabaseConnection();

    //Connect tới DataBase       
    public ServiceVoucher() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    //them
    public void insertVoucher(ModelVoucher mdb) throws SQLException {
        String sql_ND = "INSERT INTO Voucher (MaVoucher, TenVoucher, MoTa, GiamGia, NgayBatDau, NgayKetThuc, TrangThai, DiemTL) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pInsert = con.prepareStatement(sql_ND);
        pInsert.setString(1, mdb.getMaVoucher());
        pInsert.setString(2, mdb.getTenVoucher());
        pInsert.setString(3, mdb.getMoTa());
        pInsert.setFloat(4, mdb.getGiamGia());
        java.sql.Date ngayBatDau = (mdb.getNgayBatDau() != null) ? new java.sql.Date(mdb.getNgayBatDau().getTime()) : null;
        java.sql.Date ngayKetThuc = (mdb.getNgayKetThuc() != null) ? new java.sql.Date(mdb.getNgayKetThuc().getTime()) : null;
        pInsert.setDate(5, ngayBatDau);
        pInsert.setDate(6, ngayKetThuc);
        pInsert.setString(7, mdb.getTrangThai());
        pInsert.setInt(8, mdb.getDiemTL());

        pInsert.execute();
        pInsert.close();
    }

    public void insertVoucherKH(ModelVoucher mdb, int idkh) throws SQLException {
        String sql_ND = "INSERT INTO DSVoucherKH (IDKH, MaVoucher, MoTa, GiamGia, TrangThai) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pInsert = con.prepareStatement(sql_ND);
        pInsert.setInt(1, idkh);
        pInsert.setString(2, mdb.getMaVoucher());
        pInsert.setString(3, mdb.getMoTa());
        pInsert.setFloat(4, mdb.getGiamGia());
        pInsert.setString(5, mdb.getTrangThai());

        pInsert.execute();
        pInsert.close();
    }

    //sua
    public void UpdateVoucher(ModelVoucher mdb) throws SQLException {
        String sql = "UPDATE Voucher SET TenVoucher=?, MoTa=?, GiamGia=?, NgayBatDau= ?, NgayKetThuc=?, TrangThai=?, DiemTL=? WHERE MaVoucher=?";
        PreparedStatement pInsert = con.prepareStatement(sql);

        pInsert.setString(1, mdb.getTenVoucher());
        pInsert.setString(2, mdb.getMoTa());
        pInsert.setFloat(3, mdb.getGiamGia());
        java.sql.Date ngayBatDau = (mdb.getNgayBatDau() != null) ? new java.sql.Date(mdb.getNgayBatDau().getTime()) : null;
        java.sql.Date ngayKetThuc = (mdb.getNgayKetThuc() != null) ? new java.sql.Date(mdb.getNgayKetThuc().getTime()) : null;
        pInsert.setDate(4, ngayBatDau);
        pInsert.setDate(5, ngayKetThuc);
        pInsert.setString(6, mdb.getTrangThai());
        pInsert.setInt(7, mdb.getDiemTL());
        pInsert.setString(8, mdb.getMaVoucher());

        pInsert.execute();
        pInsert.close();
    }

    //lay idVC lon nhat
    public String getLatestVoucher() throws SQLException {
        String gtlnMaHD = null;
        String query = "SELECT MAX(MaVoucher) AS maxMaVoucher FROM Voucher"; //lay gia tri idhd lon nhat trong bang
        try (PreparedStatement pstmt = con.prepareStatement(query);//thuc hien truy van tham so
                 ResultSet rs = pstmt.executeQuery()) {//con tro den hang dau cua bang ket qua cua cau query select 
            if (rs.next()) {//di chuyen con tro den hang tiep theo trong bang, neu co hang tiep theo thi thuc thi lenh trong if
                gtlnMaHD = rs.getString("maxMaVoucher"); // lay gia tri idhd lon nhat tu bang
            }
        } catch (SQLException e) {//xu ly ngoai le 
            e.printStackTrace();
        }
        return gtlnMaHD; // tra ve gia tri idhd lon nhat 
    }

    public void deleteVoucher(ModelVoucher mdb) throws SQLException {
        String sql = "Delete Voucher where MaVoucher = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, mdb.getMaVoucher());

        p.executeUpdate();
        p.close();
    }

    public boolean checkDuplicateVoucher(String email) throws SQLException {
        boolean duplicate = false;
        String sql = "SELECT TOP 1 *\n"
                + "FROM Voucher\n"
                + "WHERE TenVoucher = ? ";
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

    public float getVoucherDiscount(String maVoucher) {
        float giamGia = 0.0f;
        try {
            String sql = "SELECT giamGia FROM Voucher WHERE maVoucher = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maVoucher);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                giamGia = rs.getFloat("giamGia");
                BigDecimal roundedGiamGia = new BigDecimal(giamGia).setScale(2, RoundingMode.HALF_UP);
                giamGia = roundedGiamGia.floatValue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return giamGia; // Return the rounded discount value
    }

    public List<Integer> getDTLByIdKH() {
        List<Integer> diemTLList = new ArrayList<>();
        String sql = "SELECT DiemTL FROM Voucher WHERE Trangthai = 'Hoat dong'";

        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            // Thêm từng DiemTL vào danh sách
            while (rs.next()) {
                Integer diemTL = rs.getInt("DiemTL");
                diemTLList.add(diemTL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return diemTLList;
    }

    public List<ModelVoucher> getAllVouchers() {
        List<ModelVoucher> vouchers = new ArrayList<>();
        String sql = "SELECT MaVoucher, TenVoucher, MoTa, GiamGia, NgayBatDau, NgayKetThuc, Trangthai, DiemTL FROM Voucher";

        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ModelVoucher voucher = new ModelVoucher();
                voucher.setMaVoucher(rs.getString("MaVoucher"));
                voucher.setTenVoucher(rs.getString("TenVoucher"));
                voucher.setMoTa(rs.getString("MoTa"));
                voucher.setGiamGia(rs.getFloat("GiamGia"));
                voucher.setNgayBatDau(rs.getDate("NgayBatDau"));
                voucher.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                voucher.setTrangThai(rs.getString("Trangthai"));
                voucher.setDiemTL(rs.getInt("DiemTL"));

                vouchers.add(voucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vouchers;
    }

    //dem so lam dat ban
    public int demSoLanDB(int idKH) {
        int bookingCount = 0;
        String getNgayBatDauQuery = "SELECT ngaybatdaudem FROM QLKhachHang WHERE idkh = ?";
        String countBookingsQuery = "SELECT COUNT(*) FROM qlhoadon WHERE idkh = ? AND ngaytt >= ? AND trangthai = 'Da thanh toan'";


        try (Connection conn = cn.getConnection()) {
            // Lấy ngày bắt đầu đếm từ bảng QLKhachHang
            try (PreparedStatement ps = conn.prepareStatement(getNgayBatDauQuery)) {
                ps.setInt(1, idKH); // Gán idKH vào câu lệnh SQL

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Lấy giá trị ngày bắt đầu đếm từ ResultSet
                        Date ngayBatDau = rs.getDate("ngaybatdaudem"); // Lấy giá trị ngày bắt đầu đếm

                        // Kiểm tra nếu ngày bắt đầu đếm không phải là NULL
                        if (ngayBatDau != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            String date2 = sdf.format(ngayBatDau);
                            // Tiến hành đếm số lần đặt bàn từ ngày bắt đầu đếm
                            try (PreparedStatement countPs = conn.prepareStatement(countBookingsQuery)) {
                                countPs.setInt(1, idKH); // Gán idKH vào câu lệnh SQL

                                // Chuyển đổi ngày bắt đầu đếm thành java.sql.Date nếu cần
                                countPs.setString(2, date2); // Gán ngày bắt đầu đếm vào câu lệnh SQL

                                try (ResultSet countRs = countPs.executeQuery()) {
                                    if (countRs.next()) {
                                        bookingCount = countRs.getInt(1); // Lấy kết quả đếm từ cột đầu tiên
                                    }
                                }
                            }
                        } else {
                            System.out.println("Ngày bắt đầu đếm là NULL.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookingCount;
    }

//    public int demSoLanDB(int idKH) {
//        int bookingCount = 0;
//        String checkSDTQuery = "SELECT COUNT(*) FROM dsban WHERE idkh = ?";
//
//        try (PreparedStatement ps = con.prepareStatement(checkSDTQuery)) {
//            ps.setInt(1, idKH); // Gán giá trị idKH vào câu lệnh SQL
//
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    bookingCount = rs.getInt(1); // Lấy kết quả đếm từ cột đầu tiên
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return bookingCount;
//    }
    public String getMoTaByMaVoucher(String maVoucher) {
        String moTa = "Không tìm thấy mô tả";
        String sql = "SELECT MoTa FROM Voucher WHERE MaVoucher = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // Gán giá trị cho tham số trong câu lệnh SQL
            ps.setString(1, maVoucher);

            try (ResultSet rs = ps.executeQuery()) {
                // Kiểm tra nếu có kết quả trả về
                if (rs.next()) {
                    // Lấy mô tả từ ResultSet
                    moTa = rs.getString("MoTa");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Trả về mô tả hoặc thông báo lỗi nếu không tìm thấy
        return moTa;
    }

    public float getGiamGiaByMaVoucher(String maVoucher) {
        float giamGia = 0.0f;  // Giá trị mặc định nếu không tìm thấy giảm giá

        // Câu lệnh SQL để lấy giá trị giảm giá từ bảng Voucher theo MaVoucher
        String sql = "SELECT GiamGia FROM Voucher WHERE MaVoucher = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // Gán giá trị cho tham số trong câu lệnh SQL
            ps.setString(1, maVoucher);

            try (ResultSet rs = ps.executeQuery()) {
                // Kiểm tra nếu có kết quả trả về
                if (rs.next()) {
                    // Lấy giá trị giảm giá từ ResultSet
                    giamGia = rs.getFloat("GiamGia");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Trả về giá trị giảm giá hoặc -1 nếu có lỗi
        return giamGia;
    }

    public String getTrangThaiByMaVoucher(String maVoucher) {
        String trangThai = null;  // Mặc định là null nếu không tìm thấy trạng thái

        // Câu lệnh SQL để lấy giá trị TrangThai từ bảng Voucher theo MaVoucher
        String sql = "SELECT TrangThai FROM Voucher WHERE MaVoucher = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // Gán giá trị cho tham số trong câu lệnh SQL
            ps.setString(1, maVoucher);

            try (ResultSet rs = ps.executeQuery()) {
                // Kiểm tra nếu có kết quả trả về
                if (rs.next()) {
                    // Lấy giá trị TrangThai từ ResultSet
                    trangThai = rs.getString("TrangThai");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Trả về trạng thái voucher hoặc "Lỗi" nếu có lỗi
        return trangThai;
    }

    public int getDiemTLByMaVoucher(String maVoucher) {
        int diemTL = 0;  // Mặc định là 0 nếu không tìm thấy DiemTL

        // Câu lệnh SQL để lấy giá trị DiemTL từ bảng Voucher theo MaVoucher
        String sql = "SELECT DiemTL FROM Voucher WHERE MaVoucher = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // Gán giá trị cho tham số trong câu lệnh SQL
            ps.setString(1, maVoucher);

            try (ResultSet rs = ps.executeQuery()) {
                // Kiểm tra nếu có kết quả trả về
                if (rs.next()) {
                    // Lấy giá trị DiemTL từ ResultSet và gán vào biến diemTL
                    diemTL = rs.getInt("DiemTL");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Trả về giá trị DiemTL hoặc -1 nếu có lỗi
        return diemTL;
    }

    public void updateNgayBatDauDemSoLanDB(int idkhBan) {
        // Chuỗi kết nối với cơ sở dữ liệu
        String query = "UPDATE QLKhachHang SET ngaybatdaudem = ? WHERE idkh = ?";

        try (Connection conn = cn.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Lấy ngày hiện tại
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

            // Cập nhật ngày bắt đầu đếm số lần đặt bàn
            pstmt.setDate(1, currentDate);
            pstmt.setInt(2, idkhBan);

            // Thực thi câu lệnh cập nhật
            int rowsUpdated = pstmt.executeUpdate();

            // Kiểm tra xem có cập nhật được bản ghi nào không
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật thành công ngày bắt đầu đếm số lần đặt bàn cho khách hàng: " + idkhBan);
            } else {
                System.out.println("Không tìm thấy khách hàng với ID: " + idkhBan);
            }

        } catch (SQLException e) {
            // Xử lý ngoại lệ nếu có lỗi kết nối hoặc thực thi câu lệnh
            System.out.println("Lỗi khi cập nhật ngày bắt đầu đếm số lần đặt bàn: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateSoLanDatBanTuNgayDoiThanhCong(int idkhBan) {
        // Cập nhật lại số lần đặt bàn bắt đầu từ ngày đổi thành công
        // Giả sử bạn có phương thức trong service để cập nhật ngày bắt đầu đếm số lần đặt bàn từ ngày đổi thành công.
        String query = "UPDATE dsban SET NgayDat = ? WHERE idkh = ?"; // Cập nhật ngày
        try (Connection conn = cn.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));  // Ngày hiện tại
            stmt.setInt(2, idkhBan);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteVC(ModelVoucher mdb, int idkh) throws SQLException {
        String sql = "Delete DSVoucherKH where MaVoucher = ? And idkh = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, mdb.getMaVoucher());
        p.setInt(2, idkh);

        p.executeUpdate();
        p.close();
    }

}
