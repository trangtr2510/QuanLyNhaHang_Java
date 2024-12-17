--DROP table NguoiDung;
--Tao bang NguoiDung
create table NguoiDung(
    ID_ND NUMBER(8,0),
    Email varchar2(50),
    Matkhau varchar2(20),
    VerifyCode varchar2(10)DEFAULT NULL,
    Trangthai varchar2(10) DEFAULT '',
    Vaitro varchar2(20)
);
---Them rang buoc
alter table NguoiDung
    add constraint ND_Email_NNULL check ('Email' is not null)
    add constraint ND_Matkhau_NNULL check ('Matkhau' is not null)
    add constraint ND_Vaitro_Ten check (Vaitro in ('Khach Hang','Nhan Vien','Nhan Vien Kho','Quan Ly'));

---Them khoa chinh
alter table NguoiDung
    add constraint NguoiDung_PK PRIMARY KEY (ID_ND);
    
--Tao bang NhanVien
--drop table NhanVien;
create table QLNhanVien(
    ID_NV NUMBER(8,0),
    TenNV VARCHAR2(50),
    NgayVL DATE ,
    SDT VARCHAR2(50),
    Chucvu VARCHAR2(50),
	Luong double
);
--Them rang buoc cho bang NhanVien
--Them Check Constraint
alter table QLNhanVien
    add constraint NV_TenNV_NNULL check ('TenNV' is not null)
    add constraint NV_SDT_NNULL check ('SDT' is not null)
    add constraint NV_NgayVL_NNULL check ('NgayVL' is not null)
    add constraint NV_Chucvu_Thuoc check (Chucvu IN ('Phuc vu','Tiep tan','Thu ngan','Bep','Kho','Quan ly'));

--Them khoa chinh
alter table QLNhanVien
    add constraint NV_PK PRIMARY KEY (ID_NV);


--Tao bang KhachHang
--drop table KhachHang;
create table QLKhachHang(
    ID_KH NUMBER(8,0),
    TenKH varchar2(50), 
    Ngaythamgia date, 
    Gioitinh varchar2(50), 
    Sdt varchar2(50),
	Diachi varchar2(50),
    ID_ND NUMBER(8,0)
);
--Them Check Constraint
alter table QLKhachHang
    add constraint KH_TenKH_NNULL check ('TenKH' is not null)
    add constraint KH_Ngaythamgia_NNULL check ('Ngaythamgia' is not null)
    add constraint KH_IDND_NNULL check ('ID_ND' is not null);

---Them khoa chinh
alter table QLKhachHang
    add constraint KhachHang_PK PRIMARY KEY (ID_KH);
    
---Them khoa ngoai
ALTER TABLE QLKhachHang
 ADD CONSTRAINT KH_fk_idND FOREIGN KEY 
 (ID_ND) REFERENCES NguoiDung(ID_ND);

--Tao bang MonAn
--drop table MonAn;
create table QLThucDon(
    ID_TD NUMBER(8,0),
    TenTD varchar2(50), 
    Gia number(8,0),
    Loai varchar2(50)
);
--Them Check Constraint
alter table QLThucDon
    add constraint MA_TenTD_NNULL check ('TenMon' is not null)
    add constraint MA_Gia_NNULL check ('Dongia' is not null)
    add constraint MA_Loai_Ten check (Loai in ('Aries','Taurus','Gemini','Cancer','Leo','Virgo'
                                                 ,'Libra','Scorpio','Sagittarius','Capricorn','Aquarius','Pisces'));                          

--Them khoa chinh
alter table MonAn
    add constraint MonAn_PK PRIMARY KEY (ID_MonAn);


--Tao bang Ban
--drop table Ban;
create table datban(
    ID_Ban varchar2(50),
    Tang varchar2(50), 
    tenKH varchar2(50), 
	Sdt varchar2(50),
	SLNguoi number(2,0),
	Ngaydat date, 
	Giodat varchar2(50), 
    Trangthai varchar2(50)
);
--Them Check Constraint
alter table datban
    add constraint Ban_TenBan_NNULL check ('TenBan' is not null)
    add constraint Ban_Tang_NNULL check ('Vitri' is not null)
    add constraint Ban_Trangthai_Ten check (Trangthai in ('Con trong','Da dat'));


--Them khoa chinh
alter table datban
    add constraint datban PRIMARY KEY (ID_Ban);


--Tao bang Voucher

--Tao bang HoaDon
--drop table HoaDon;

create table QLHoaDon(
    ID_HoaDon varchar2(50),
    Tang varchar2(50),
    ID_Ban varchar2(50),
    Ngaytt date,
    Tongtien double
);

--Them Check Constraint
alter table HoaDon
    add constraint HD_Ngaytt_NNULL check ('Tongtien' is not null);

--Them khoa chinh
alter table QLHoaDon
    add constraint HD_PK PRIMARY KEY (ID_HoaDon);

ALTER TABLE HoaDon
 ADD CONSTRAINT HD_fk_idBan FOREIGN KEY 
 (ID_Ban) REFERENCES Ban(ID_Ban);
 
--Procedure
--Procudure them mot khach hang moi voi cac thong tin tenKH , NgayTG va ID_ND
CREATE OR REPLACE PROCEDURE KH_ThemKH(tenKH QLKHACHHANG.TenKH%TYPE, NgayTG QLKHACHHANG.Ngaythamgia%TYPE,
ID_ND QLKHACHHANG.ID_ND%TYPE)
IS
    v_ID_KH QLKHACHHANG.ID_KH%TYPE;
IS 
BEGIN
    --Them ma KH tiep theo
    SELECT MIN(ID_KH)+1
    INTO v_ID_KH
    FROM QLKHACHHANG
    WHERE ID_KH + 1 NOT IN(SELECT ID_KH FROM QLKHACHHANG);
    
    INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (v_ID_KH,tenKH,TO_DATE(NgayTG,'dd-MM-YYYY'),ID_ND);
    EXCEPTION WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR('Thong tin khong hop le');
END;
/
--Procudure them mot nhan vien moi voi cac thong tin tenNV, SDT, Chucvu, NgayVL, Luong
CREATE OR REPLACE PROCEDURE NV_ThemNV(tenNV QLNHANVIEN.TenNV%TYPE, NgayVL QLNHANVIEN.NgayVL%TYPE, SDT QLNHANVIEN.SDT%TYPE,
Chucvu QLNHANVIEN.Chucvu%TYPE, luong NHANVIEN.Luong%TYPE)
IS
    v_ID_NV QLNHANVIEN.ID_NV%TYPE;
IS 
BEGIN
    --Them ma KH tiep theo
    SELECT MIN(ID_NV)+1
    INTO v_ID_NV
    FROM QLNHANVIEN
    WHERE ID_NV + 1 NOT IN(SELECT ID_NV FROM QLNHANVIEN);
    
    INSERT INTO NhanVien(ID_NV,TenNV,NgayVL,SDT,Chucvu,Luong) 
    VALUES (v_ID_NV,tenNV,TO_DATE(NgayVL,'dd-MM-YYYY'),SDT,Chucvu,Luong);
    EXCEPTION WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR('Thong tin khong hop le');
END;
/
-- Procudure xoa mot NHANVIEN voi idNV
CREATE OR REPLACE PROCEDURE NV_XoaNV(idNV QLNHANVIEN.ID_NV%TYPE)
IS
    v_count NUMBER;
    idNQL QLNHANVIEN.ID_NQL%TYPE;
BEGIN 
    SELECT COUNT(ID_NV),ID_NQL
    INTO v_count,ID_NQL
    FROM QLNHANVIEN
    WHERE ID_NV=idNV;
    
    IF(v_count>0) THEN
        IF (id_NV = idNQL) THEN
            RAISE_APPLICATION_ERROR(-20000,'Khong the xoa QUAN LY');
        ELSE
            DELETE FROM NHANVIEN WHERE ID_NV=idNV;
        END IF;
    ELSE 
        RAISE_APPLICATION_ERROR(-20000,'Nhan vien khong ton tai');
    END IF;
END;
/
-- Procudure xoa mot KHACHHANG voi idKH
CREATE OR REPLACE PROCEDURE KH_XoaKH(idKH QLKHACHHANG.ID_KH%TYPE)
IS
    v_count NUMBER;
BEGIN 
    SELECT COUNT(*)
    INTO v_count
    FROM QLKHACHHANG
    WHERE ID_KH=idKH;
    
    IF(v_count>0) THEN
        DELETE FROM QLKHACHHANG WHERE ID_KH=idKH;
    ELSE 
        RAISE_APPLICATION_ERROR(-20000,'Khach hang khong ton tai');
    END IF;
END;
/

-- Procedure xem thong tin KHACHHANG voi thong tin idKH
CREATE OR REPLACE PROCEDURE KH_XemTT(idKH QLKHACHHANG.ID_KH%TYPE)
IS
BEGIN 
    FOR cur IN (SELECT TenKH,Ngaythamgia,Sdt,Gioitinh, Diachi,ID_ND
    FROM QLKHACHHANG WHERE ID_KH=idKH;
    )
    LOOP
        DBMS_OUTPUT.PUT_LINE('Ma khach hang: '||idKH);
        DBMS_OUTPUT.PUT_LINE('Ten khach hang: '||cur.TenKH);
        DBMS_OUTPUT.PUT_LINE('Ngay tham gia: '||TO_CHAR(cur.Ngaythamgia,'dd-MM-YYYY');
        DBMS_OUTPUT.PUT_LINE('Gioi tinh: '||cur.Gioitinh);
        DBMS_OUTPUT.PUT_LINE('So dien thoai: '||cur.Sdt);
        DBMS_OUTPUT.PUT_LINE('Dia chi: '||cur.Diachi);
        DBMS_OUTPUT.PUT_LINE('Ma nguoi dung: '||cur.ID_ND);
        
        EXCEPTION WHEN NO_DATA_FOUND THEN
             RAISE_APPLICATION_ERROR(-20000,'Khach hang khong ton tai');
    END LOOP;
END;
/
-- Procedure xem thong tin NHANVIEN voi thong tin idNV
CREATE OR REPLACE PROCEDURE NV_XemTT(idNV QLNHANVIEN.ID_NV%TYPE)
IS
BEGIN 
    FOR cur IN (SELECT TenKH,NgayVL,SDT,Chucvu,Luong   
    FROM QLNHANVIEN WHERE ID_NV=idNV;
    )
    LOOP
        DBMS_OUTPUT.PUT_LINE('Ma nhan vien: '||idNV);
        DBMS_OUTPUT.PUT_LINE('Ten nhan vien: '||cur.TenNV);
        DBMS_OUTPUT.PUT_LINE('Ngay vao lam: '||TO_CHAR(cur.NgayVL,'dd-MM-YYYY');
        DBMS_OUTPUT.PUT_LINE('Chuc vu: '||cur.Chucvu);
        DBMS_OUTPUT.PUT_LINE('Luong: '||cur.Luong);
        
        EXCEPTION WHEN NO_DATA_FOUND THEN
             RAISE_APPLICATION_ERROR(-20000,'Nhan vien khong ton tai');
    END LOOP;
END;
/

--Them data
ALTER SESSION SET NLS_DATE_FORMAT = 'dd-MM-YYYY';
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
ALTER SESSION SET NLS_DATE_FORMAT = 'dd-MM-YYYY';
INSERT INTO QlNhanVien(ID_NV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV001','Ha Thao Duong','10/05/2023','0838033232','Quan ly',10000000);
INSERT INTO QlNhanVien(ID_NV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV002','Nguyen Quoc Thinh','11/05/2023','0838033734','Phuc vu',5000000);
INSERT INTO QlNhanVien(ID_NV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV003','Truong Tan Hieu','12/05/2023','0838033834','Phuc vu',4000000);
INSERT INTO QlNhanVien(ID_NV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV004','Nguyen Thai Bao','10/05/2023','0838093234','Phuc vu',5500000);
INSERT INTO QlNhanVien(ID_NV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV005','Tran Nhat Khang','11/05/2023','0838133234','Thu ngan',6000000);
INSERT INTO QlNhanVien(ID_NV,TenNV,NgayVL,SDT,Chucvu,Luong) VALUES ('NV006','Nguyen Ngoc Luong','12/05/2023','0834033234','Bep',80000000);

--Them data cho bang KhachHang
INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (100,'Ha Thao Duong','10/05/2023',101);
INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (101,'Truong Tan Hieu','10/05/2023',102);
INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (102,'Nguyen Quoc Thinh','10/05/2023',103);
INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (103,'Ha Thao Duong','10/05/2023',104);
INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (104,'Truong Tan Hieu','10/05/2023',105);
INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (105,'Nguyen Quoc Thinh','10/05/2023',106);
INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (106,'Tran Nhu Mai','10/05/2023',107);
INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (107,'Nguyen Thi Bich Hao','10/05/2023',108);
INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (108,'Nguyen Mai Quynh','11/05/2023',109);
INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (109,'Hoang Minh Quang','11/05/2023',110);
INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (110,'Nguyen Thanh Hang','12/05/2023',111);
INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (111,'Nguyen Ngoc Thanh Nhan','11/05/2023',112);
INSERT INTO QLKhachHang(ID_KH,TenKH,Ngaythamgia,ID_ND) VALUES (112,'Hoang Thi Phuc Nguyen','12/05/2023',113);

--Them data cho bang MonAn
--Aries
insert into QLThucDon(ID_TD,TenTD,Gia,Loai) values(1,'DUI CUU NUONG XE NHO', 250000,'Aries');
insert into QLThucDon(ID_TD,TenTD,Gia,Loai) values(2,'BE SUON CUU NUONG GIAY BAC MONG CO', 230000,'Aries');
insert into QLThucDon(ID_TD,TenTD,Gia,Loai) values(3,'DUI CUU NUONG TRUNG DONG', 350000,'Aries');
insert into QLThucDon(ID_TD,TenTD,Gia,Loai) values(4,'CUU XOC LA CA RI', 129000,'Aries');
insert into QLThucDon(ID_TD,TenTD,Gia,Loai) values(5,'CUU KUNGBAO', 250000,'Aries');
insert into QLThucDon(ID_TD,TenTD,Gia,Loai) values(6,'BAP CUU NUONG CAY', 250000,'Aries');
insert into QLThucDon(ID_TD,TenTD,Gia,Loai) values(7,'CUU VIEN HAM CAY', 19000,'Aries');
insert into QLThucDon(ID_TD,TenTD,Gia,Loai) values(8,'SUON CONG NUONG MONG CO', 250000,'Aries');
insert into QLThucDon(ID_TD,TenTD,Gia,Loai) values(9,'DUI CUU LON NUONG TAI BAN', 750000,'Aries');
insert into QLThucDon(ID_TD,TenTD,Gia,Loai) values(10,'SUONG CUU NUONG SOT NAM', 450000,'Aries');
insert into QLThucDon(ID_TD,TenTD,Gia,Loai) values(11,'DUI CUU NUONG TIEU XANH', 285000,'Aries');
insert into QLThucDon(ID_TD,TenTD,Gia,Loai) values(12,'SUON CUU SOT PHO MAI', 450000,'Aries');

--Them data cho bang Ban
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban1','Tang 1',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban2','Tang 1',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban3','Tang 1',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban4','Tang 1',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban1','Tang 2',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban2','Tang 2',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban3','Tang 2',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban4','Tang 2',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban1','Tang 3',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban2','Tang 3',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban3','Tang 3',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban4','Tang 3',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban1','Tang 4',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban2','Tang 4',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban3','Tang 4',null,null,null,null,null,'Con trong');
insert into datban(ID_Ban,Tang, Tenkh, Sdt,SLNguoi, Ngaydat ,Giodat,Trangthai) values('Ban4','Tang 4',null,null,null,null,null,'Con trong');
