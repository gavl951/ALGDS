package com.example.app.model;

public class Branch {

    private int id;
    private String Address;
    private int PhoneNumber;
    private String Manager;
    private int Hours;
    private int BranchNo;

    public Branch(int id, String a, int pn, String m, int h, int bno) {
        this.id = id;
        this.Address = a;
        this.PhoneNumber = pn;
        this.Manager = m;
        this.Hours = h;
        this.BranchNo = bno;
    }

    public Branch(String a, int pn, String m, int h, int bno) {
        this(-1, a, pn, m, h, bno);
    }

    public int getid() {
        return id;
    }

    public String getAddress() {
        return Address;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public String getManager() {
        return Manager;
    }

    public void setid(int id) {
        this.id = id;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setHours(int Hours) {
        this.Hours = Hours;
    }

    public int getHours() {
        return Hours;
    }

    public void setPhoneNumber(int PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public void setManager(String Manager) {
        this.Manager = Manager;
    }

    public void setBranchNo(int BranchNo) {
        this.BranchNo = BranchNo;
    }

    public int getBranchNo() {
        return BranchNo;
    }

}
