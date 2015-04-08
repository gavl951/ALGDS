package com.example.app.model;

public class Customer {

    private int id;
    private String Name;
    private String Email;
    private String Mobile;
    private String Address;
    private int StaffNum;
    private int BranchNo;

    public Customer(int id, String n, String e, String m, String a, int sn, int br) {
        this.id = id;
        this.Name = n;
        this.Email = e;
        this.Mobile = m;
        this.Address = a;
        this.StaffNum = sn;
        this.BranchNo = br;
    }

    public Customer(String n, String e, String m, String a, int sn, int br) {
        this(-1, n, e, m, a, sn, br);

    }

    public int getid() {
        return id;
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
}
