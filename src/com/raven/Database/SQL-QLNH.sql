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
    NgayVL DATE NOT NULL, -- Added NOT NULL
    SDT VARCHAR(50) NOT NULL, -- Added NOT NULL
    Chucvu VARCHAR(50) NOT NULL, -- Added NOT NULL
    Luong FLOAT NOT NULL -- Changed to FLOAT and added NOT NULL
);
-- Create QLKhachHang table
CREATE TABLE QLKhachHang (
    IDKH INT NOT NULL, -- Changed to INT and added NOT NULL
    TenKH VARCHAR(50) NOT NULL, -- Added NOT NULL
    Ngaythamgia DATE NOT NULL, -- Added NOT NULL
    Gioitinh VARCHAR(50), -- Keep optional
    Sdt VARCHAR(50), -- Keep optional
    Diachi VARCHAR(50), -- Keep optional
    ID_ND INT NOT NULL -- Changed to INT and added NOT NULL
);

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
    Gia INT NOT NULL, -- Changed to INT and added NOT NULL
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
    Ngaydat DATE, -- Optional
    Giodat VARCHAR(50), -- Optional
    Trangthai VARCHAR(50) NOT NULL -- Added NOT NULL
);
-- Add primary key constraint
ALTER TABLE datban
    ADD CONSTRAINT datban_PK PRIMARY KEY (IDBan);


-- Drop the table if it exists (uncomment if needed)
-- DROP TABLE QLHD;

-- Create QLHD table
CREATE TABLE QLHD(
    IDHD VARCHAR(50) NOT NULL, -- Added NOT NULL
    Tang VARCHAR(50), -- Optional
    IDBan VARCHAR(50) NOT NULL, -- Added NOT NULL
    Ngaytt DATE NOT NULL, -- Added NOT NULL
    Gia FLOAT NOT NULL -- Changed to FLOAT and added NOT NULL
);

-- Add CHECK constraint for QLHD
ALTER TABLE QLHD
    ADD CONSTRAINT HD_Ngaytt_NNULL CHECK (Gia IS NOT NULL);

-- Add primary key constraint
ALTER TABLE QLHD
    ADD CONSTRAINT HD_PK PRIMARY KEY (IDHD);

-- Add foreign key constraint
ALTER TABLE QLHD
    ADD CONSTRAINT HD_fk_idBan FOREIGN KEY (IDBan) REFERENCES datban(IDBan);
--Them data cho Bang NguoiDung
--Nhan vien
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (100,'changcherchou@gmail.com','123','Verified','Quan Ly');
INSERT INTO NguoiDung(ID_ND,Email,MatKhau,Trangthai,Vaitro) VALUES (101,'NVHoangPhuc@gmail.com','123','Verified','Khach Hang');
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

--Them data cho bang Nhan Vien
INSERT INTO QlNhanVien2(IDNV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV001','Ha Thao Duong','10-05-2023','0838033232','Quan ly',10000000);
INSERT INTO QlNhanVien2(IDNV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV002','Nguyen Quoc Thinh','11-05-2023','0838033734','Phuc vu',5000000);
INSERT INTO QlNhanVien2(IDNV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV003','Truong Tan Hieu','12-05-2023','0838033834','Phuc vu',4000000);
INSERT INTO QlNhanVien2(IDNV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV004','Nguyen Thai Bao','10-05-2023','0838093234','Phuc vu',5500000);
INSERT INTO QlNhanVien2(IDNV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV005','Tran Nhat Khang','11-05-2023','0838133234','Thu ngan',6000000);
INSERT INTO QlNhanVien2(IDNV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV006','Nguyen Ngoc Luong','12-05-2023','0834033234','Bep',80000000);

--Them data cho bang KhachHang
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (100,'Ha Thao Duong','10-05-2023',101);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (101,'Truong Tan Hieu','10/05/2023',102);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (102,'Nguyen Quoc Thinh','10/05/2023',103);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (103,'Ha Thao Duong','10/05/2023',104);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (104,'Truong Tan Hieu','10/05/2023',105);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (105,'Nguyen Quoc Thinh','10/05/2023',106);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (106,'Tran Nhu Mai','10/05/2023',107);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (107,'Nguyen Thi Bich Hao','10/05/2023',108);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (108,'Nguyen Mai Quynh','11/05/2023',109);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (109,'Hoang Minh Quang','11/05/2023',110);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (110,'Nguyen Thanh Hang','12/05/2023',111);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (111,'Nguyen Ngoc Thanh Nhan','11/05/2023',112);
INSERT INTO QLKhachHang(IDKH,TenKH,Ngaythamgia,ID_ND) VALUES (112,'Hoang Thi Phuc Nguyen','12/05/2023',113);

--Them data cho bang MonAn
--Aries
insert into QLMenu(MaThucdon,TenThucdon,Gia,LoaiThucdon) values(1,'DUI CUU NUONG XE NHO', 250000,'Aries');
insert into QLMenu(MaThucdon,TenThucdon,Gia,LoaiThucdon) values(2,'BE SUON CUU NUONG GIAY BAC MONG CO', 230000,'Aries');
insert into QLMenu(MaThucdon,TenThucdon,Gia,LoaiThucdon) values(3,'DUI CUU NUONG TRUNG DONG', 350000,'Aries');
insert into QLMenu(MaThucdon,TenThucdon,Gia,LoaiThucdon) values(4,'CUU XOC LA CA RI', 129000,'Aries');
insert into QLMenu(MaThucdon,TenThucdon,Gia,LoaiThucdon) values(5,'CUU KUNGBAO', 250000,'Aries');
insert into QLMenu(MaThucdon,TenThucdon,Gia,LoaiThucdon) values(6,'BAP CUU NUONG CAY', 250000,'Aries');
insert into QLMenu(MaThucdon,TenThucdon,Gia,LoaiThucdon) values(7,'CUU VIEN HAM CAY', 19000,'Aries');
insert into QLMenu(MaThucdon,TenThucdon,Gia,LoaiThucdon) values(8,'SUON CONG NUONG MONG CO', 250000,'Aries');
insert into QLMenu(MaThucdon,TenThucdon,Gia,LoaiThucdon) values(9,'DUI CUU LON NUONG TAI BAN', 750000,'Aries');
insert into QLMenu(MaThucdon,TenThucdon,Gia,LoaiThucdon) values(10,'SUONG CUU NUONG SOT NAM', 450000,'Aries');
insert into QLMenu(MaThucdon,TenThucdon,Gia,LoaiThucdon) values(11,'DUI CUU NUONG TIEU XANH', 285000,'Aries');
insert into QLMenu(MaThucdon,TenThucdon,Gia,LoaiThucdon) values(12,'SUON CUU SOT PHO MAI', 450000,'Aries');

--Them data cho bang Ban
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban1','Tang 1',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban2','Tang 1',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban3','Tang 1',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban4','Tang 1',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban1','Tang 2',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban2','Tang 2',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban3','Tang 2',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban4','Tang 2',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban1','Tang 3',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban2','Tang 3',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban3','Tang 3',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban4','Tang 3',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban1','Tang 4',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban2','Tang 4',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban3','Tang 4',null,null,null,null,null,'Con trong');
insert into datban(IDBan,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban4','Tang 4',null,null,null,null,null,'Con trong');
