package com.example.app.model;

public class Customer implements Comparable<Customer>{ //comparable interface

    private int id;             //attributes and values initialised and declared
    private String Name;        //private so only visible to the class its in, also security reasons
    private String Email;
    private String Mobile;
    private String Address;
    private int StaffNum;
    private int BranchNo;

    public Customer(int id, String n, String e, String m, String a, int sn, int br) { //constructor takes ID
        this.id = id;
        this.Name = n;
        this.Email = e;
        this.Mobile = m;
        this.Address = a;
        this.StaffNum = sn;
        this.BranchNo = br;
    }

    public Customer(String n, String e, String m, String a, int sn, int br) { //constructor doesnt take ID
        this(-1, n, e, m, a, sn, br);

    }

    public int getid() { //get and set methods
        return id;       //will make private variables public
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public int getStaffNum() {
        return StaffNum;
    }

    public void setStaffNum(int StaffNum) {
        this.StaffNum = StaffNum;
    }
    public int getBranchNo() {
        return BranchNo;
    }

    public void setBranchNo(int BranchNo) {
        this.BranchNo = BranchNo;
    }

    @Override               //method invoked on customer object
    public int compareTo(Customer that) {   
        String myName = this.getName(); //strings are compared based on name
        String yourName = that.getName();
        
        return myName.compareTo(yourName);  
        
    }
}
