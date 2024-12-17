
package com.raven.model;

import javax.swing.Icon;


public class Model_Cart {
    Icon icon;
    String title;
    String valuesString;

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValuesString() {
        return valuesString;
    }

    public void setValuesString(String valuesString) {
        this.valuesString = valuesString;
    }

    public Model_Cart() {
    }

    public Model_Cart(Icon icon, String title, String valuesString) {
        this.icon = icon;
        this.title = title;
        this.valuesString = valuesString;
    }
    
    
}
