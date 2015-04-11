package com.example.app.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    private static Model instance = null;

    public static synchronized Model getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    private CustomerTableGateway gateway;
    private List<Customer> Customers;
    private BranchTableGateway Branchgateway;
    private List<Branch> Branches;

    private Model() throws DataAccessException { //model connecting to DB
        try {
            Connection conn = DBConnection.getInstance(); 
            this.gateway = new CustomerTableGateway(conn);
            this.Branchgateway = new BranchTableGateway(conn);

            this.Customers = this.gateway.getCustomers();
            this.Branches = this.Branchgateway.getBranches();
        } catch (ClassNotFoundException ex) {
           throw new DataAccessException("Exception initialising Model Object: " +ex.getMessage()); //rethrow, error message.
        } catch (SQLException ex) {
            throw new DataAccessException("Exception initialising Model Object: " +ex.getMessage());
        }
    }

    public List<Customer> getCustomers() {
        return new ArrayList<Customer>(this.Customers);
    }

    public List<Customer> getCustomersByBranchNumber(int BranchNo) {
        List<Customer> list = new ArrayList<Customer>();
        for (Customer c : this.Customers){
            if (c.getBranchNo() == BranchNo){
                list.add(c);
            }
        }
        return list;
    }
    public boolean addCustomer(Customer c) throws SQLException {
        boolean result = false;
        int id;
        id = this.gateway.insertCustomer( //returns an ID, values specified for parameters
                c.getName(),
                c.getEmail(),
                c.getMobile(),
                c.getAddress(),
                c.getStaffNum(),
                c.getBranchNo());

        if (id != -1) {
            c.setid(id);
            this.Customers.add(c);
            result = true;
        }
        return result;
    }

    public boolean removeCustomer(Customer c) throws DataAccessException {
        boolean removed = false;
        try {
            removed = this.gateway.removeCustomer(c.getid());
            if (removed) {
                removed = this.Customers.remove(c);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Exception removing customer: " +ex.getMessage());
        }
        return removed;
    }

    public Customer findCustomerByStaffNumber(int staffNum) {
        Customer c = null;
        int i = 0;
        boolean found = false;
        while (i < this.Customers.size() && !found) {
            c = this.Customers.get(i);
            if (c.getStaffNum() == staffNum) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            c = null;
        }
        return c;
    }

    boolean updateCustomer(Customer c) throws DataAccessException {
        boolean updated = false;

        try {
            updated = this.gateway.updateCustomer(c);

        } catch (SQLException ex) {
           throw new DataAccessException("Exception updating customer: " +ex.getMessage());
        }

        return updated;
    }

    Customer findCustomerByStaffNum(int StaffNum) {
        Customer c = null;
        int i = 0;
        boolean found = false;
        while (i < this.Customers.size() && !found) {
            c = this.Customers.get(i);
            if (c.getStaffNum() == (StaffNum)) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            c = null;
        }
        return c;
    }

    public List<Branch> getBranches() {
        return new ArrayList<Branch>(this.Branches);
    }

    public boolean addBranch(Branch b) throws SQLException {
        boolean result = false;
        int id;
        id = this.Branchgateway.insertBranch(
                b.getAddress(),
                b.getPhoneNumber(),
                b.getManager(),
                b.getHours(),
                b.getBranchNo());

        if (id != -1) {
            b.setid(id);
            this.Branches.add(b);
            result = true;
        }
        return result;
    }

    public boolean removeBranch(Branch b) throws DataAccessException {
        boolean removed = false;
        try {
            removed = this.Branchgateway.removeBranch(b.getid());
            if (removed) {
                removed = this.Branches.remove(b);
            }
        } catch (SQLException ex) {
           throw new DataAccessException("Exception removing branch: " +ex.getMessage());
        }
        return removed;
    }

    public Branch findBranchByBranchNumber(int BranchNo) {
        Branch b = null;
        int i = 0;
        boolean found = false;
        while (i < this.Branches.size() && !found) {
            b = this.Branches.get(i);
            if (b.getBranchNo() == BranchNo) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            b = null;
        }
        return b;
    }

    boolean updateBranch(Branch b) throws DataAccessException {
        boolean updated = false;

        try {
            updated = this.Branchgateway.updateBranch(b);

        } catch (SQLException ex) {
            throw new DataAccessException("Exception updating branch: " +ex.getMessage());
        }

        return updated;
    }

    Branch findBranchbyBranchNum(int BranchNo) {
        Branch b = null;
        int i = 0;
        boolean found = false;
        while (i < this.Branches.size() && !found) {
            b = this.Branches.get(i);
            if (b.getBranchNo() == (BranchNo)) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            b = null;
        }
        return b;
    }

}
