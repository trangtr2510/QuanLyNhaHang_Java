
package com.raven.model;

import com.raven.form.*;


public class DanhSachTang {
    private String id;
    private String Name;
    
    public DanhSachTang() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    public DanhSachTang(String id, String Name) {
        this.id = id;
        this.Name = Name;
    }
    
    @Override
    public String toString() {
        return this.Name; 
    }
}
