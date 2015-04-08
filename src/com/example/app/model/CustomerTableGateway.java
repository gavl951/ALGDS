package com.example.app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerTableGateway {

    private static final String TABLE_NAME = "customers";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_MOBILE = "Mobile";
    private static final String COLUMN_ADDRESS = "Address";
    private static final String COLUMN_STAFFNUM = "StaffNum";
    private static final String COLUMN_BRANCHNO = "BranchNo";

    private Connection mConnection;

    public CustomerTableGateway(Connection connection) {
        mConnection = connection;
    }

    public List<Customer> getCustomers() throws SQLException {
        String query;       // the SQL query to execute
        Statement stmt;     // the java.sql.Statement object used to execute the
        // SQL query
        ResultSet rs;       // the java.sql.ResultSet representing the result of
        // SQL query 
        List<Customer> Customers;   // the java.util.List containing the Customer objects
        // created for each row in the result of the query
        int id;             // the id of a customer
        String Name, Email, Mobile, Address;
        int StaffNum, BranchNo;
        Customer c;       // a customer object created from a row in the result of
        // the query

        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

        // iterate through the result set, extracting the data from each row
        // and storing it in a Customer object, which is inserted into an initially
        // empty ArrayList
        Customers = new ArrayList<Customer>();
        while (rs.next()) {
            id = rs.getInt(COLUMN_ID);
            Name = rs.getString(COLUMN_NAME);
            Email = rs.getString(COLUMN_EMAIL);
            Mobile = rs.getString(COLUMN_MOBILE);
            Address = rs.getString(COLUMN_ADDRESS);
            StaffNum = rs.getInt(COLUMN_STAFFNUM);
            BranchNo = rs.getInt(COLUMN_BRANCHNO);

            c = new Customer(id, Name, Email, Mobile, Address, StaffNum, BranchNo);
            Customers.add(c);
        }

        return Customers;
    }

    public boolean removeCustomer(int id) throws SQLException {

        PreparedStatement stmt;
        int numRowsAffected;
        String query;
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";

        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);

        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }

    public int insertCustomer(String Name, String Email, String Mobile, String Address, int StaffNum, int BranchNo) throws SQLException {
        int id = -1;
        PreparedStatement stmt;
        int numRowsAffected;
        String query;
        query = "INSERT INTO " + TABLE_NAME + " ("
                + COLUMN_NAME + ", "
                + COLUMN_EMAIL + ", "
                + COLUMN_MOBILE + ", "
                + COLUMN_ADDRESS + ", "
                + COLUMN_STAFFNUM + ", "
                + COLUMN_BRANCHNO + 
                 ")VALUES(?,?,?,?,?,?)";

        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, Name);
        stmt.setString(2, Email);
        stmt.setString(3, Mobile);
        stmt.setString(4, Address);
        stmt.setInt(5, StaffNum);
        stmt.setInt(6, BranchNo);

        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            id = keys.getInt(1);
        }
        return id;
    }

    boolean updateCustomer(Customer c) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;

        query = " UPDATE " + TABLE_NAME + " SET "
                + COLUMN_NAME + " = ?, "
                + COLUMN_EMAIL + " = ?, "
                + COLUMN_MOBILE + " = ?, "
                + COLUMN_ADDRESS + " = ?, "
                + COLUMN_STAFFNUM + " = ?, "
                + COLUMN_BRANCHNO + " = ? "
                + " WHERE " + COLUMN_ID + " = ?";

        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, c.getName());
        stmt.setString(2, c.getEmail());
        stmt.setString(3, c.getMobile());
        stmt.setString(4, c.getAddress());
        stmt.setInt(5, c.getStaffNum());
        stmt.setInt(6, c.getBranchNo());
        stmt.setInt(7, c.getid());

        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }
}
