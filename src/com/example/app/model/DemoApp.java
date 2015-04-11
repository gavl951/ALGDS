package com.example.app.model;

import java.util.List;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;

public class DemoApp {

    private static final int NAME_ORDER = 1; //interger constants
    private static final int STAFFNUM_ORDER = 2;

    public static void main(String[] args) throws SQLException {
        Scanner keyboard = new Scanner(System.in);

        Model model;

        int opt = 11; //creating user interface
        do { //do while loop
            try {
                model = Model.getInstance();
                System.out.println(" 1. Create a new customer");
                System.out.println(" 2. Delete an existing Customer");
                System.out.println(" 3. Edit a customer");
                System.out.println(" 4. View all customers");
                System.out.println(" 5. Order by staff number");
                System.out.println(" 6. View single customers");
                System.out.println();
                System.out.println(" 7. Create a new branch");
                System.out.println(" 8. Delete an existing branch");
                System.out.println(" 9. View all branches");
                System.out.println(" 10. Edit a branch");
                System.out.println();
                System.out.println(" 11. Exit");
                System.out.println();

                opt = getInt(keyboard, "Enter option: ", 11);

                System.out.println("You chose option " + opt);

                switch (opt) { //used to display messages when a choice is chosen
                    case 1: { //cases
                        System.out.println("Create Mode!");
                        Customer c = readCustomer(keyboard);
                        if (model.addCustomer(c)) {
                            System.out.println("customer added");
                        } else {
                            System.out.println("customer not added");
                        }

                        break;
                    }
                    case 2: {
                        System.out.println("Delete mode!");
                        deleteCustomer(keyboard, model);
                        break;
                    }
                    case 3: {
                        System.out.println("Edit Mode!");
                        editCustomer(keyboard, model);
                        break;
                    }
                    case 4: {
                        System.out.println("Viewing customers..");
                        viewCustomers1(model, NAME_ORDER);
                        break;
                    }
                    case 5: {
                        System.out.println("Ordering by Staff Number..");
                        viewCustomers1(model, STAFFNUM_ORDER);
                        break;
                    }
                    case 6: {
                        System.out.println("Viewing individual Customers..");
                        viewCustomer(keyboard, model);
                        break;
                    }
                    case 7: {
                        System.out.println("Create Mode!");
                        Branch b = readBranch(keyboard);
                        if (model.addBranch(b)) {
                            System.out.println("Branch added");
                        } else {
                            System.out.println("Branch not added");
                        }

                        break;
                    }
                    case 8: {
                        System.out.println("Delete Mode!");
                        deleteBranch(keyboard, model);
                        break;
                    }
                    case 9: {
                        System.out.println("Viewing all Branches..");
                        viewBranches1(keyboard, model);
                    }
                    case 10: {
                        System.out.println("Edit Mode!");
                        editBranch(keyboard, model);
                        break;
                    }

                }
            } catch (DataAccessException e) { //if exception is caught, do this.

                System.out.println();
                System.out.println(e.getMessage());
                System.out.println();

            }

        } while (opt != 11); //stops
        System.out.println("Goodbye");
    }

    private static Customer readCustomer(Scanner keyb) { //create customer
        String Name, Email, Mobile, Address;
        int id, StaffNum, BranchNo;

        Name = getString(keyb, "Enter Name: ");
        Email = getString(keyb, "Enter Email: ");
        Mobile = getString(keyb, "Enter Mobile: ");
        Address = getString(keyb, "Enter Address: ");
        StaffNum = getInt(keyb, "Enter Staff number: ", -1);
        BranchNo = getInt(keyb, "Enter Branch number: ", -1);

        Customer c
                = new Customer(Name, Email, Mobile,
                        Address, StaffNum, BranchNo);

        return c;
    }

    private static void viewCustomers1(Model model, int order) {    //view customers
        List<Customer> Customers = model.getCustomers(); //retrieve customers method
        System.out.println();
        if (Customers.isEmpty()) {
            System.out.println("There are no customers in the database"); //output if there is no customers in DB
        } else {                                                          //output customers alphabetically ordered
            if (order == NAME_ORDER) {
                Collections.sort(Customers);        //sort method with customers array list
            } else {
                Comparator<Customer> cmpt = new StaffNumComparator(); //declared
                Collections.sort(Customers, cmpt); //comparator object passed
            }
            System.out.printf("%10s %10s %16s %17s %15s %27s %14s"
                    + "s\n", "id", "Name", "Address", "Mobile", "Email", "Staff Number", "Branch");
            for (Customer pr : Customers) {
                Branch b = model.findBranchByBranchNumber(pr.getBranchNo());
                System.out.printf("%10s %11s %23s %13s %22s %9s %30s\n",
                        pr.getid(),
                        pr.getName(),
                        pr.getAddress(),
                        pr.getMobile(),
                        pr.getEmail(),
                        pr.getStaffNum(),
                        (b != null) ? b.getAddress() : "");
            }
        }
        System.out.println();
    }

    private static void viewCustomer(Scanner kb, Model model) throws DataAccessException { //view single customers
        int StaffNum = getInt(kb, "Enter the staff number of the customer to view:", -1);
        Customer c;

        c = model.findCustomerByStaffNumber(StaffNum);
        if (c != null) {
            Branch b = model.findBranchByBranchNumber(c.getBranchNo());
            System.out.println("");
            System.out.println("Name          :" + c.getName());
            System.out.println("Address       :" + c.getAddress());
            System.out.println("Mobile        :" + c.getMobile());
            System.out.println("Email         :" + c.getEmail());
            System.out.println("Staff Number  :" + c.getStaffNum());
            System.out.println("Branch        :" + ((b != null) ? b.getAddress() : "")); //cannot be null
            System.out.println("");

        } else {
            System.out.println("Customer not found");
        }
    }

    private static void deleteCustomer(Scanner kb, Model m) throws DataAccessException { //delete customer
        int StaffNum = getInt(kb, "Enter the staff number of the customer to delete:", -1);
        Customer c;

        c = m.findCustomerByStaffNumber(StaffNum);
        if (c != null) {
            if (m.removeCustomer(c)) {
                System.out.println("Customer deleted");
            } else {
                System.out.println("Customer not deleted");
            }
        } else {
            System.out.println("Customer not found");
        }
    }

    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private static void editCustomer(Scanner kb, Model m) throws DataAccessException { //edit customer
        int StaffNum = getInt(kb, "Enter the staff number of the customer to edit:", -1);
        Customer c;

        c = m.findCustomerByStaffNumber(StaffNum);
        if (c != null) {
            editCustomerDetails(kb, c);
            if (m.updateCustomer(c)) {
                System.out.println("Customer Updated");
            } else {
                System.out.println("Customer not updated");
            }
        } else {
            System.out.println("Customer not found");
        }
    }

    private static void editCustomerDetails(Scanner keyb, Customer c) {
        String Name, Email, Mobile, Address;
        int StaffNum, BranchNo;
        String line1;

        Name = getString(keyb, "Enter name [" + c.getName() + "]: ");
        Email = getString(keyb, "Enter Email [" + c.getEmail() + "]: ");
        Mobile = getString(keyb, "Enter Mobile [" + c.getMobile() + "]: ");
        Address = getString(keyb, "Enter Address [" + c.getAddress() + "]: ");
        StaffNum = getInt(keyb, "Enter Staff Number [" + c.getStaffNum() + "]: ", -1);
        BranchNo = getInt(keyb, "Enter Branch [" + c.getBranchNo() + "]: ", -1);

        if (Name.length() != 0) {
            c.setName(Name);
        }
        if (Email.length() != 0) {
            c.setEmail(Email);
        }
        if (Mobile.length() != 0) {
            c.setMobile(Mobile);
        }
        if (Address.length() != 0) {
            c.setAddress(Address);
        }
        if (StaffNum != c.getStaffNum()) {
            c.setStaffNum(StaffNum);
        }
        if (BranchNo != c.getBranchNo()) {
            c.setBranchNo(BranchNo);
        }
    }

    private static Branch readBranch(Scanner keyb) { //create branch
        String Address, Manager;
        int id, PhoneNumber, BranchNo, Hours;
        String Line;

        Address = getString(keyb, "Enter Address: ");
        PhoneNumber = getInt(keyb, "Enter Phone Number: ", -1);
        Manager = getString(keyb, "Enter Manager: ");
        Hours = getInt(keyb, "Enter Hours: ", -1);
        BranchNo = getInt(keyb, "Enter Branch Number: ", -1);

        Branch b
                = new Branch(Address, PhoneNumber, Manager,
                        Hours, BranchNo);

        return b;
    }

    private static void deleteBranch(Scanner kb, Model m) throws DataAccessException { //delete branch
        int BranchNo = getInt(kb, "Enter the Branch Number of the Branch you want to delete:", -1);
        Branch b;

        b = m.findBranchByBranchNumber(BranchNo);
        if (b != null) {
            if (m.removeBranch(b)) {
                System.out.println("Branch deleted");
            } else {
                System.out.println("Branch not deleted");
            }
        } else {
            System.out.println("Branch not found");
        }
    }

    private static void viewBranches1(Scanner keyboard, Model model) {
        {
            List<Branch> Branches = model.getBranches();
            System.out.println();
            if (Branches.isEmpty()) {
                System.out.println("There are no Branches in the database");
            } else {
                System.out.printf("%10s %10s %32s %15s %15s %19"
                        + "s\n", "id", "Address", "Phone Number", "Manager", "Hours", "Branch Number");
                for (Branch pr : Branches) {
                    System.out.printf("%10s %26s %13s %21s %10s %16s\n",
                            pr.getid(),
                            pr.getAddress(),
                            pr.getPhoneNumber(),
                            pr.getManager(),
                            pr.getHours(),
                            pr.getBranchNo());
                }
            }
            System.out.println();
        }
    }

    private static String getString2(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private static void editBranch(Scanner kb, Model m) throws DataAccessException, SQLException { //edit branch
        int BranchNo = getInt(kb, "Enter the Branch Number of the Branch you want to edit:", -1);
        Branch b;

        b = m.findBranchByBranchNumber(BranchNo);
        if (b != null) {
            editBranchDetails(kb, b);
            if (m.updateBranch(b)) {
                System.out.println("Branch Updated");
            } else {
                System.out.println("Branch not updated");
            }
        } else {
            System.out.println("Branch not found");
        }
    }

    private static void editBranchDetails(Scanner keyb, Branch b) {
        String Address, Manager;
        int PhoneNumber, Hours, BranchNo;

        Address = getString(keyb, "Enter Address [" + b.getAddress() + "]: ");
        PhoneNumber = getInt(keyb, "Enter Phone Number [" + b.getPhoneNumber() + "]: ", -1);
        Manager = getString(keyb, "Enter Manager [" + b.getManager() + "]: ");
        Hours = getInt(keyb, "Enter Hours [" + b.getHours() + "]: ", -1);
        BranchNo = getInt(keyb, "Enter Branch Number [" + b.getBranchNo() + "]: ", -1);

        if (Address.length() != 0) {
            b.setAddress(Address);
        }
        if (PhoneNumber != b.getPhoneNumber()) {
            b.setPhoneNumber(PhoneNumber);
        }
        if (Manager.length() != 0) {
            b.setManager(Manager);
        }
        if (Hours != b.getHours()) {
            b.setHours(Hours);
        }
        if (BranchNo != b.getBranchNo()) {
            b.setBranchNo(BranchNo);
        }
    }

    private static int getInt(Scanner keyb, String prompt, int defaultValue) { //handling exception
        int opt = defaultValue; //value that will be returned
        boolean finished = false;

        do { //do loop
            try {
                System.out.print(prompt); //print out prompt
                String line = keyb.nextLine();  //read value
                if (line.length() > 0) {
                    opt = Integer.parseInt(line);
                }
                finished = true;
            } catch (NumberFormatException e) { //when exception is caught, do this
                System.out.println("Exception: " + e.getMessage());
            }
        } while (!finished);

        return opt; //return value
    }
}
