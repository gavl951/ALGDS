
package com.example.app.model;

import java.util.Comparator;

public class StaffNumComparator implements Comparator<Customer>{

    @Override
    public int compare(Customer c1, Customer c2) {
       return (int)(c1.getStaffNum() - c2.getStaffNum());
    }
    
}
