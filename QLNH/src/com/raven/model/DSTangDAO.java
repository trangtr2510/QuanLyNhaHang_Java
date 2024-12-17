
package com.raven.model;

import java.util.ArrayList;
import java.util.List;

public class DSTangDAO {
    ArrayList<DanhSachTang> lsDST = new ArrayList<>();

    public DSTangDAO() {
        lsDST.add(new DanhSachTang("1", "Tầng 1"));
        lsDST.add(new DanhSachTang("2", "Tầng 2"));
        lsDST.add(new DanhSachTang("3", "Tầng 3"));
        lsDST.add(new DanhSachTang("4", "Tầng 4"));
    }
    public int add(DanhSachTang ds){
        lsDST.add(ds);
        return 1;
    }
    public List<DanhSachTang> getAllDSTang(){
        return lsDST;
    }

}
