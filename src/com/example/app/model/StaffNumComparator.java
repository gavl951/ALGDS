
package com.example.app.model;

import java.util.Comparator;

public class StaffNumComparator implements Comparator<Customer>{ //comparator interface

    @Override
    public int compare(Customer c1, Customer c2) { //compare two customer objects
       return (int)(c1.getStaffNum() - c2.getStaffNum()); //comparing 
                                                          
    }
    
}
