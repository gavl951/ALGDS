package com.example.app.model;

import com.example.app.model.Branch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BranchTableGateway {

    private Connection mConnection;

    private static final String TABLE_NAME = "branches";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ADDRESS = "Address";
    private static final String COLUMN_PHONENUMBER = "PhoneNumber";
    private static final String COLUMN_MANAGER = "Manager";
    private static final String COLUMN_HOURS = "Hours";
    private static final String COLUMN_BRANCHNO = "BranchNo";

    public BranchTableGateway(Connection connection) {
        mConnection = connection;
    }

    public int insertBranch(String a, int pn, String m, int h, int bno) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;
        int id = -1;

        // the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "INSERT INTO " + TABLE_NAME + " ("
                + COLUMN_ADDRESS + ", "
                + COLUMN_PHONENUMBER + ", "
                + COLUMN_MANAGER + ", "
                + COLUMN_HOURS + ", "
                + COLUMN_BRANCHNO
                + ") VALUES (?, ?, ?, ?, ?)";

        // create a PreparedStatement object to execute the query and insert the values into the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, a);
        stmt.setInt(2, pn);
        stmt.setString(3, m);
        stmt.setInt(4, h);
        stmt.setInt(5, bno);

        // execute the query and make sure that one and only one row was inserted into the database
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            // if one row was inserted, retrieve the id assigned to that row
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();

            id = keys.getInt(1);
        }

        // return the id assigned to the row in the database
        return id;
    }

    public boolean removeBranch(int id) throws SQLException {

        PreparedStatement stmt;
        int numRowsAffected;
        String query;
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";

        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);

        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }

    public boolean deleteBranch(int id) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;

        // the required SQL DELETE statement with place holders for the id of the row to be remove from the database
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";

        // create a PreparedStatement object to execute the query and insert the id into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);

        // execute the query
        numRowsAffected = stmt.executeUpdate();

        // return the true if one and only one row was deleted from the database
        return (numRowsAffected == 1);
    }

    public List<Branch> getBranches() throws SQLException {
        String query;                   // the SQL query to execute
        Statement stmt;                 // the java.sql.Statement object used to execute the SQL query
        ResultSet rs;                   // the java.sql.ResultSet representing the result of SQL query
        List<Branch> branches;   // the java.util.List containing the Branch objects created for each row
        // in the result of the query the id of a branch

        String Address, Manager;
        int id, Hours, BranchNo, PhoneNumber;

        Branch b;                   // a Branch object created from a row in the result of the query

        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

        // iterate through the result set, extracting the data from each row
        // and storing it in a Branch object, which is inserted into an initially
        // empty ArrayList
        branches = new ArrayList<Branch>();
        while (rs.next()) {
            id = rs.getInt(COLUMN_ID);
            Address = rs.getString(COLUMN_ADDRESS);
            PhoneNumber = rs.getInt(COLUMN_PHONENUMBER);
            Manager = rs.getString(COLUMN_MANAGER);
            Hours = rs.getInt(COLUMN_HOURS);
            BranchNo = rs.getInt(COLUMN_BRANCHNO);

            b = new Branch(id, Address, PhoneNumber, Manager, Hours, BranchNo);
            branches.add(b);
        }

        // return the list of Branch objects retrieved
        return branches;
    }

    boolean updateBranch(Branch b) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;

        query = "UPDATE " + TABLE_NAME + " SET "
                + COLUMN_ADDRESS + " = ?, "
                + COLUMN_PHONENUMBER + " = ?, "
                + COLUMN_MANAGER + " = ?, "
                + COLUMN_HOURS + " = ?, "
                + COLUMN_BRANCHNO + " = ? "
                + " WHERE " + COLUMN_ID + " = ?";

        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, b.getAddress());
        stmt.setInt(2, b.getPhoneNumber());
        stmt.setString(3, b.getManager());
        stmt.setInt(4, b.getHours());
        stmt.setInt(5, b.getBranchNo());
        stmt.setInt(6, b.getid());

        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }
}
