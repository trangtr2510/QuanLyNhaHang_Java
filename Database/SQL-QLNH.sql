create database QLNHang
USE QLNHang;
CREATE TABLE NguoiDung(
    ID_ND INT PRIMARY KEY,
    Email VARCHAR(50) NOT NULL,
    MatKhau VARCHAR(20) NOT NULL,
    VerifyCode VARCHAR(10) DEFAULT NULL,
    TrangThai VARCHAR(10) DEFAULT '',
    VaiTro VARCHAR(20) NOT NULL
);

CREATE TABLE QLNhanVien2 (
    IDNV VARCHAR(50) PRIMARY KEY, -- Changed to INT and added NOT NULL
    TenNV VARCHAR(50) NOT NULL, -- Added NOT NULL
	SDT VARCHAR(50) NOT NULL, -- Added NOT NULL
    NgayVL DATE NOT NULL, -- Added NOT NULL
    Chucvu VARCHAR(50) NOT NULL, -- Added NOT NULL
    Luong FLOAT NOT NULL -- Changed to FLOAT and added NOT NULL
);
alter table qlnhanvien2 add ID_ND int;
-- Create QLKhachHang table
CREATE TABLE QLKhachHang (
    IDKH INT NOT NULL, -- Changed to INT and added NOT NULL
    TenKH VARCHAR(50) NOT NULL, -- Added NOT NULL
	Gioitinh VARCHAR(50), -- Keep optional
	Sdt VARCHAR(50), -- Keep optional
    Diachi VARCHAR(50), -- Keep optional
    ID_ND INT NOT NULL, -- Changed to INT and added NOT NULL
    Ngaythamgia DATE NOT NULL -- Added NOT NULL
);
alter table QLKhachHang add Ngaybatdaudem DATE;

-- Add primary key constraint
ALTER TABLE QLKhachHang
    ADD CONSTRAINT KhachHang_PK PRIMARY KEY (IDKH);

-- Add foreign key constraint
ALTER TABLE QLKhachHang
    ADD CONSTRAINT KH_fk_idND FOREIGN KEY (ID_ND) REFERENCES NguoiDung(ID_ND);


-- Drop the table if it exists (uncomment if needed)
-- DROP TABLE QLMenu;

-- Create QLMenu table
CREATE TABLE QLMenu (
    MaThucdon INT NOT NULL, -- Changed to INT and added NOT NULL
    TenThucdon VARCHAR(50) NOT NULL, -- Added NOT NULL
    LoaiThucdon VARCHAR(50) NOT NULL -- Added NOT NULL
);
-- Add primary key constraint
ALTER TABLE QLMenu
    ADD CONSTRAINT ThucDon_PK PRIMARY KEY (MaThucdon);


-- Drop the table if it exists (uncomment if needed)
-- DROP TABLE datban;

-- Create datban table
CREATE TABLE datban (
    IDBan VARCHAR(50) NOT NULL, -- Added NOT NULL
    Tang VARCHAR(50) NOT NULL, -- Added NOT NULL
    TenKH VARCHAR(50), -- Optional
    Sdt VARCHAR(50), -- Optional
    SLNguoi INT CHECK (SLNguoi >= 0 AND SLNguoi <= 99), -- Changed to INT and added range check
    Ngaydat VARCHAR(50), -- Optional
    Giodat VARCHAR(50), -- Optional
    Trangthai VARCHAR(50) NOT NULL -- Added NOT NULL
);
-- Add primary key constraint
ALTER TABLE datban
    ADD CONSTRAINT datban_PK PRIMARY KEY (IDBan);


-- Create datban table
CREATE TABLE dsban (
    IDBan VARCHAR(50) NOT NULL, -- Added NOT NULL
    Tang VARCHAR(50) NOT NULL, -- Added NOT NULL
    TenKH VARCHAR(50), -- Optional
    Sdt VARCHAR(50), -- Optional
    SLNguoi INT CHECK (SLNguoi >= 0 AND SLNguoi <= 99), -- Changed to INT and added range check
    Ngaydat VARCHAR(50), -- Optional
    Giodat VARCHAR(50), -- Optional
    IDKH INT,
	FOREIGN KEY (IDKH) REFERENCES QLKhachHang(IDKH)-- Added NOT NULL
);



-- Drop the table if it exists (uncomment if needed)
-- DROP TABLE QLHD;

-- Create QLHD table
CREATE TABLE qlhoadon (
    IDHD VARCHAR(50) NOT NULL, -- Added NOT NULL
	IDBan VARCHAR(50) NOT NULL, -- Added NOT NULL
    Tang VARCHAR(50), -- Optional
    Ngaytt VARCHAR(50) NOT NULL, -- Added NOT NULL
    Gia FLOAT NOT NULL -- Changed to FLOAT and added NOT NULL
);
alter table qlhoadon add IDKH INT;
alter table qlhoadon add Trangthai varchar(50);
-- Add CHECK constraint for QLHD
ALTER TABLE qlhoadon
    ADD CONSTRAINT HD_Ngaytt_NNULL CHECK (Gia IS NOT NULL);

-- Add primary key constraint
ALTER TABLE qlhoadon
    ADD CONSTRAINT HD_PK PRIMARY KEY (IDHD);

-- Add foreign key constraint
ALTER TABLE qlhoadon
    ADD CONSTRAINT HD_fk_idBan FOREIGN KEY (IDBan) REFERENCES datban(IDBan);

-- Tạo bảng Voucher
CREATE TABLE Voucher (
    MaVoucher VARCHAR(20) PRIMARY KEY, -- Mã khuyến mại là khóa chính
    TenVoucher VARCHAR(50) NOT NULL, -- Tên của voucher
    MoTa VARCHAR(100), -- Mô tả chi tiết về voucher (tuỳ chọn)
    GiamGia FLOAT CHECK (GiamGia >= 0 AND GiamGia <= 1), -- Tỷ lệ giảm giá (từ 0 đến 1, ví dụ: 0.2 là giảm 20%)
    NgayBatDau DATE NOT NULL, -- Ngày bắt đầu có hiệu lực của voucher
    NgayKetThuc DATE NOT NULL, -- Ngày hết hạn của voucher
    TrangThai VARCHAR(20) DEFAULT 'Hoat dong' CHECK (TrangThai IN ('Hoat dong', 'Het han', 'Tam dung')) -- Trạng thái của voucher
);
alter table Voucher add DiemTL INT;
-- Ràng buộc CHECK đảm bảo rằng NgàyKếtThúc phải sau NgàyBatDau
ALTER TABLE Voucher
    ADD CONSTRAINT Voucher_Ngay_Check CHECK (NgayKetThuc >= NgayBatDau);
--cap nhat trang thai voucher
-- Trigger để cập nhật trạng thái của voucher khi ngày kết thúc đã qua
-- Xóa trigger cũ nếu tồn tại
DROP TRIGGER IF EXISTS trg_UpdateVoucherStatus;
GO

-- Tạo PROCEDURE mới để tu dong xử lý cập nhật trạng thái voucher
CREATE PROCEDURE sp_UpdateVoucherStatus
AS
BEGIN
    SET NOCOUNT ON;

    -- Cập nhật trạng thái thành 'Het han' cho các voucher có NgayKetThuc nhỏ hơn ngày hiện tại
    UPDATE Voucher
    SET TrangThai = 'Het han'
    WHERE NgayKetThuc < CAST(GETDATE() AS DATE)
      AND TrangThai != 'Het han';

    -- Cập nhật trạng thái thành 'Hoat dong' nếu NgayKetThuc lớn hơn hoặc bằng ngày hiện tại
    UPDATE Voucher
    SET TrangThai = 'Hoat dong'
    WHERE NgayKetThuc >= CAST(GETDATE() AS DATE)
      AND TrangThai != 'Hoat dong';
END;
GO
-- Thuc hien PROCEDURE Cập nhật trạng thái voucher 
EXEC sp_UpdateVoucherStatus;


-- Tạo trigger mới để xử lý cả hai trường hợp, cập nhật trạng thái voucher khi them moi 
CREATE TRIGGER trg_UpdateVoucherStatus
ON Voucher
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    -- Cập nhật trạng thái thành 'Het han' cho các voucher có NgayKetThuc nhỏ hơn ngày hiện tại
    UPDATE v
    SET TrangThai = 'Het han'
    FROM Voucher v
    WHERE NgayKetThuc < CAST(GETDATE() AS DATE)
      AND TrangThai != 'Het han';

    -- Cập nhật trạng thái thành 'Hoat dong' nếu NgayKetThuc lớn hơn hoặc bằng ngày hiện tại
    UPDATE v
    SET TrangThai = 'Hoat dong'
    FROM Voucher v
    WHERE NgayKetThuc >= CAST(GETDATE() AS DATE)
      AND TrangThai != 'Hoat dong';
END;
GO



CREATE TABLE DSVoucherKH (
	IDKH INT ,
	MaVoucher VARCHAR(20),
	Mota VARCHAR(100),
	GiamGia FLOAT,
	TrangThai VARCHAR(20),
	FOREIGN KEY (MaVoucher) REFERENCES Voucher(MaVoucher),
	FOREIGN KEY (IDKH) REFERENCES QLKhachHang(IDKH)
);

-- Create NhaCungCap table
CREATE TABLE NhaCungCap (
    MaNCC INT PRIMARY KEY, -- Mã nhà cung cấp
    NhaCungCap VARCHAR(50) NOT NULL, -- Tên nhà cung cấp
    DiaChi VARCHAR(200), -- Địa chỉ nhà cung cấp
    SDT VARCHAR(15) CHECK (LEN(SDT) >= 10), -- Số điện thoại nhà cung cấp (ít nhất 10 chữ số)
    Email VARCHAR(50), -- Email nhà cung cấp
    TrangThai VARCHAR(20) DEFAULT 'Hoat dong' CHECK (TrangThai IN ('Hoat dong', 'Tam dung', 'Ngung hoat dong')) -- Trạng thái nhà cung cấp
);

-- Create NguyenLieu table
CREATE TABLE NguyenLieu (
    MaNL INT PRIMARY KEY, -- Mã nguyên liệu, là khóa chính
    TenNL VARCHAR(50) NOT NULL, -- Tên nguyên liệu
    DonViTinh VARCHAR(20) NOT NULL, -- Đơn vị tính (ví dụ: kg, lít)
    NhaCungCap VARCHAR(50), -- Nhà cung cấp (tùy chọn)
    Gia FLOAT NOT NULL, -- Giá của nguyên liệu
    HanSuDung DATE, -- Hạn sử dụng
    TrangThai VARCHAR(20) DEFAULT 'Hoat dong' CHECK (TrangThai IN ('Hoat dong', 'Het han', 'Tam dung')) -- Trạng thái của nguyên liệu
);

-- Optional: Add CHECK constraint for Gia to ensure it is non-negative
ALTER TABLE NguyenLieu
    ADD CONSTRAINT Gia_NL_Check CHECK (Gia >= 0);
go

-- Tạo PROCEDURE mới để xử lý cập nhật trạng thái nguyên liệu 
CREATE PROCEDURE sp_UpdateNguyenLieuStatus
AS
BEGIN
    -- Cập nhật trạng thái thành 'Het han' nếu hạn sử dụng đã qua
    UPDATE NguyenLieu
    SET TrangThai = 'Het han'
    WHERE HanSuDung < CAST(GETDATE() AS DATE)
      AND TrangThai != 'Het han';

    -- Cập nhật trạng thái thành 'Hoat dong' nếu hạn sử dụng vẫn còn
    UPDATE NguyenLieu
    SET TrangThai = 'Hoat dong'
    WHERE HanSuDung >= CAST(GETDATE() AS DATE)
      AND TrangThai != 'Hoat dong';
END;
GO
--thuc hienn PROCEDURE
EXEC sp_UpdateNguyenLieuStatus;


-- Tạo trigger mới để xử lý cả hai trường hợp, cập nhật trạng thái nguyên liệu 
CREATE TRIGGER trg_AutoUpdateNguyenLieuStatus
ON NguyenLieu
AFTER INSERT, UPDATE
AS
BEGIN
    -- Cập nhật trạng thái thành 'Het han' nếu hạn sử dụng đã qua
    UPDATE NguyenLieu
    SET TrangThai = 'Het han'
    WHERE HanSuDung < CAST(GETDATE() AS DATE)
      AND TrangThai != 'Het han';

    -- Cập nhật trạng thái thành 'Hoat dong' nếu hạn sử dụng vẫn còn
    UPDATE NguyenLieu
    SET TrangThai = 'Hoat dong'
    WHERE HanSuDung >= CAST(GETDATE() AS DATE)
      AND TrangThai != 'Hoat dong';
END;
GO

CREATE TABLE PhieuNhap (
    MaPN INT PRIMARY KEY, -- Mã phiếu nhập
    NgayNhap DATE NOT NULL, -- Ngày nhập
    ID_ND INT, -- ID nhân viên thực hiện nhập
    TongTien FLOAT NOT NULL CHECK (TongTien >= 0), -- Tổng tiền nhập
    FOREIGN KEY (ID_ND) REFERENCES nguoidung(ID_ND) -- Khóa ngoại đến bảng nguoi dung
);

CREATE TABLE ChiTietPhieuNhap (
    MaPN INT, -- Mã phiếu nhập
    MaNL INT, -- Mã nguyên liệu
    SoLuong FLOAT NOT NULL CHECK (SoLuong > 0), -- Số lượng nhập
    DonGia FLOAT NOT NULL CHECK (DonGia > 0), -- Đơn giá nhập
    PRIMARY KEY (MaPN, MaNL), -- Khóa chính bao gồm mã phiếu nhập và mã nguyên liệu
    FOREIGN KEY (MaPN) REFERENCES PhieuNhap(MaPN), -- Khóa ngoại đến phiếu nhập
    FOREIGN KEY (MaNL) REFERENCES NguyenLieu(MaNL) -- Khóa ngoại đến nguyên liệu
);

CREATE TABLE PhieuXuat (
    MaPX INT PRIMARY KEY, -- Mã phiếu xuất
    NgayXuat DATE NOT NULL, -- Ngày xuất
    ID_ND INT, -- ID nhân viên thực hiện xuất
    TongTien FLOAT NOT NULL CHECK (TongTien >= 0), -- Tổng tiền xuất
    FOREIGN KEY (ID_ND) REFERENCES nguoidung(ID_ND) -- Khóa ngoại đến bảng nhân viên
);

CREATE TABLE ChiTietPhieuXuat (
    MaPX INT, -- Mã phiếu xuất
    MaNL INT, -- Mã nguyên liệu
    SoLuong FLOAT NOT NULL CHECK (SoLuong > 0), -- Số lượng xuất
    DonGia FLOAT NOT NULL CHECK (DonGia > 0), -- Đơn giá xuất
    PRIMARY KEY (MaPX, MaNL), -- Khóa chính bao gồm mã phiếu xuất và mã nguyên liệu
    FOREIGN KEY (MaPX) REFERENCES PhieuXuat(MaPX), -- Khóa ngoại đến phiếu xuất
    FOREIGN KEY (MaNL) REFERENCES NguyenLieu(MaNL) -- Khóa ngoại đến nguyên liệu
);
CREATE TABLE Kho (
    MaNL INT PRIMARY KEY,          -- Mã nguyên liệu (khóa chính)
    TenNL NVARCHAR(255) NOT NULL,  -- Tên nguyên liệu
    DonViTinh NVARCHAR(50),        -- Đơn vị tính (ví dụ: kg, lít, cái)
    SoLuong FLOAT NOT NULL CHECK (SoLuong >= 0),  -- Số lượng tồn kho, không được âm
    GiaNhap FLOAT NOT NULL CHECK (GiaNhap >= 0),  -- Giá nhập của nguyên liệu
    NgayNhap DATE NOT NULL          -- Ngày nhập gần nhất
);

--trigger tự động cập nhật Kho
CREATE TRIGGER trg_UpdateKhoAfterInsert
ON ChiTietPhieuNhap
AFTER INSERT
AS
BEGIN
    -- Cập nhật số lượng, giá nhập và ngày nhập cho các nguyên liệu đã tồn tại trong bảng Kho
    UPDATE k
    SET 
        k.SoLuong = k.SoLuong + i.SoLuong,  -- Cộng thêm số lượng
        k.GiaNhap = i.DonGia,               -- Cập nhật giá nhập
        k.NgayNhap = pn.NgayNhap            -- Cập nhật ngày nhập
    FROM Kho k
    INNER JOIN inserted i ON k.MaNL = i.MaNL  -- Nối với bảng inserted theo MaNL
    INNER JOIN PhieuNhap pn ON pn.MaPN = i.MaPN  -- Lấy NgayNhap từ bảng PhieuNhap
    WHERE k.MaNL = i.MaNL;                    -- Điều kiện cập nhật khi MaNL trùng

    -- Thêm mới nguyên liệu vào bảng Kho nếu nguyên liệu chưa tồn tại
    INSERT INTO Kho (MaNL, TenNL, DonViTinh, SoLuong, GiaNhap, NgayNhap)
    SELECT 
        i.MaNL, 
        nl.TenNL, 
        nl.DonViTinh, 
        i.SoLuong, 
        i.DonGia, 
        pn.NgayNhap
    FROM inserted i
    INNER JOIN NguyenLieu nl ON nl.MaNL = i.MaNL  -- Lấy thông tin nguyên liệu từ bảng NguyenLieu
    INNER JOIN PhieuNhap pn ON pn.MaPN = i.MaPN   -- Lấy NgayNhap từ bảng PhieuNhap
    WHERE NOT EXISTS (SELECT 1 FROM Kho k WHERE k.MaNL = i.MaNL);  -- Chỉ thêm mới nếu nguyên liệu chưa tồn tại trong Kho
END;

--PROCEDURE cap nhat kho neu nguyen lieu het han 
CREATE PROCEDURE sp_UpdateNguyenLieuVaKho
AS
BEGIN
    -- Bắt đầu giao dịch để đảm bảo tính nhất quán
    BEGIN TRANSACTION;

    BEGIN TRY
        -- Cập nhật trạng thái thành 'Het han' nếu hạn sử dụng đã qua
        UPDATE NguyenLieu
        SET TrangThai = 'Het han'
        WHERE HanSuDung < CAST(GETDATE() AS DATE)
          AND TrangThai != 'Het han';

        -- Đặt số lượng về 0 trong bảng Kho đối với nguyên liệu hết hạn
        UPDATE Kho
        SET SoLuong = 0
        WHERE MaNL IN (
            SELECT MaNL
            FROM NguyenLieu
            WHERE HanSuDung < CAST(GETDATE() AS DATE)
              AND TrangThai = 'Het han'
        );

        -- Xác nhận giao dịch nếu không có lỗi
        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        -- Rollback giao dịch nếu có lỗi
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH;
END;
GO
--thuc hien PROCEDURE
EXEC sp_UpdateNguyenLieuVaKho;


--Them data cho Bang NguoiDung
--Nhan vien
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (000,'Khachhangkhongcotk','123','00000','KHKTK');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (100,'changcherchou@gmail.com','123','Verified','Quan Ly');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (101,'NVHoangPhuc@gmail.com','123','Verified','Nhan Vien');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (115,'NVKho@gmail.com','123','Verified','Nhan Vien Kho');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (102,'NVAnhHong@gmail.com','123','Verified','Khach Hang');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (103,'NVQuangDinh@gmail.com','123','Verified','Khach Hang');
--Khach Hang
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (104,'KHThaoDuong@gmail.com','123','Verified','Khach Hang');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (105,'KHTanHieu@gmail.com','123','Verified','Khach Hang');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (106,'KHQuocThinh@gmail.com','123','Verified','Khach Hang');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (107,'KHNhuMai@gmail.com','123','Verified','Khach Hang');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (108,'KHBichHao@gmail.com','123','Verified','Khach Hang');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (109,'KHMaiQuynh@gmail.com','123','Verified','Khach Hang');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (110,'KHMinhQuang@gmail.com','123','Verified','Khach Hang');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (111,'KHThanhHang@gmail.com','123','Verified','Khach Hang');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (112,'KHThanhNhan@gmail.com','123','Verified','Khach Hang');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (113,'KHPhucNguyen@gmail.com','123','Verified','Khach Hang');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (114,'KHPhucHa@gmail.com','123','Verified','Khach Hang');

--Them data cho bang Nhan Vien
INSERT INTO QlNhanVien2(IDNV,TenNV,NgayVL,SDT,Chucvu,Luong, ID_ND) VALUES ('NV001','Ha Thao Duong','10-05-2023','0838033232','Quan ly',10000000, 100);
INSERT INTO QlNhanVien2(IDNV,TenNV,NgayVL,SDT,Chucvu,Luong, ID_ND) VALUES ('NV002','Nguyen Quoc Thinh','11-05-2023','0838033734','Nhan Vien Kho',5000000, 115);
INSERT INTO QlNhanVien2(IDNV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV003','Truong Tan Hieu','12-05-2023','0838033834','Phuc vu',4000000);
INSERT INTO QlNhanVien2(IDNV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV004','Nguyen Thai Bao','10-05-2023','0838093234','Phuc vu',5500000);
INSERT INTO QlNhanVien2(IDNV,TenNV,NgayVL,SDT,Chucvu,Luong, ID_ND) VALUES ('NV005','Tran Nhat Khang','11-05-2023','0838133234','Thu ngan',6000000, 101);
INSERT INTO QlNhanVien2(IDNV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV006','Nguyen Ngoc Luong','12-05-2023','0834033234','Bep',80000000);

--Them data cho bang KhachHang
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (000,'Khach hang khong co tk','10-05-2023',000);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (100,'Ha Thao Duong','10-05-2023',102);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (101,'Truong Tan Hieu','10/05/2023',103);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (102,'Nguyen Quoc Thinh','10/05/2023',104);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (103,'Ha Thao Duong','10/05/2023',105);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (104,'Truong Tan Hieu','10/05/2023',106);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (105,'Nguyen Quoc Thinh','10/05/2023',107);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (106,'Tran Nhu Mai','10/05/2023',108);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (107,'Nguyen Thi Bich Hao','10/05/2023',109);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (108,'Nguyen Mai Quynh','11/05/2023',110);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (109,'Hoang Minh Quang','11/05/2023',111);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (110,'Nguyen Thanh Hang','12/05/2023',112);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (111,'Nguyen Ngoc Thanh Nhan','11/05/2023',113);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (112,'Hoang Thi Phuc Nguyen','12/05/2023',114);

--Them data cho bang MonAn
--Aries
insert into QLMenu(MaThucdon,TenThucdon,LoaiThucdon) values(1,'DUI CUU NUONG XE NHO', 'Mon dac san');
insert into QLMenu(MaThucdon,TenThucdon,LoaiThucdon) values(2,'BE SUON CUU NUONG GIAY BAC MONG CO', 'Mon nhung');
insert into QLMenu(MaThucdon,TenThucdon,LoaiThucdon) values(3,'DUI CUU NUONG TRUNG DONG', 'Mon xao');
insert into QLMenu(MaThucdon,TenThucdon,LoaiThucdon) values(4,'CUU XOC LA CA RI', 'Mon nuong');
insert into QLMenu(MaThucdon,TenThucdon,LoaiThucdon) values(5,'CUU KUNGBAO', 'Mon Au');
insert into QLMenu(MaThucdon,TenThucdon,LoaiThucdon) values(6,'BAP CUU NUONG CAY', 'Hai san');
insert into QLMenu(MaThucdon,TenThucdon,LoaiThucdon) values(7,'CUU VIEN HAM CAY', 'Mon chien');
insert into QLMenu(MaThucdon,TenThucdon,LoaiThucdon) values(8,'SUON CONG NUONG MONG CO', 'Mon an man');
insert into QLMenu(MaThucdon,TenThucdon,LoaiThucdon) values(9,'DUI CUU LON NUONG TAI BAN', 'Mon an man');
insert into QLMenu(MaThucdon,TenThucdon,LoaiThucdon) values(10,'SUONG CUU NUONG SOT NAM', 'Mon an man');
insert into QLMenu(MaThucdon,TenThucdon,LoaiThucdon) values(11,'DUI CUU NUONG TIEU XANH', 'Mon đac san');
insert into QLMenu(MaThucdon,TenThucdon,LoaiThucdon) values(12,'SUON CUU SOT PHO MAI', 'Mon đac san');

--Them data cho bang Ban
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban1T1','Tang 1',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban2T1','Tang 1',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban3T1','Tang 1',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban4T1','Tang 1',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban1T2','Tang 2',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban2T2','Tang 2',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban3T2','Tang 2',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban4T2','Tang 2',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban1T3','Tang 3',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban2T3','Tang 3',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban3T3','Tang 3',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban4T3','Tang 3',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban1T4','Tang 4',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban2T4','Tang 4',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban3T4','Tang 4',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban4T4','Tang 4',null,null,null,null,null,'Con trong');
