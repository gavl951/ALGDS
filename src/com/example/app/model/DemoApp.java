package com.example.app.model;

import java.util.List;
import java.sql.SQLException;
import java.util.Scanner;

public class DemoApp {

    public static void main(String[] args) throws SQLException {
        Scanner keyboard = new Scanner(System.in);

        Model model;

        int opt = 9; //creating user interface
        do { //do while loop
            try{
                model = Model.getInstance();
            System.out.println("1. Create new Customer");
            System.out.println("2. Delete existing Customer");
            System.out.println("3. View all Customers");
            System.out.println("4. Edit a Customer");
            System.out.println();
            System.out.println("5. Create new Branch");
            System.out.println("6. Delete existing Branch");
            System.out.println("7. View all Branch");
            System.out.println("8. Edit a Branch");
            System.out.println();
            System.out.println("9. Exit");
            System.out.println();

            opt = getInt(keyboard, "Enter option: ", 9);

            System.out.println("You chose option " + opt);

            switch (opt) { //used to display messages when a choice is chosen
                case 1: {
                    System.out.println("Creating customer");
                    Customer c = readCustomer(keyboard);
                    if (model.addCustomer(c)) {
                        System.out.println("customer added");
                    } else {
                        System.out.println("customer not added");
                    }

                    break;
                }
                case 2: {
                    System.out.println("Deleting customer");
                    deleteCustomer(keyboard, model);
                    break;
                }
                case 3: {
                    System.out.println("Viewing all customers");
                    List<Customer> Customers = model.getCustomers();
                    System.out.println();
                    if (Customers.isEmpty()) {
                        System.out.println("There are no customers in the database");
                    } else {
                        System.out.printf("%10s %10s %15s %20s %15s %25"
                                + "s\n", "id", "Name", "Email", "Mobile", "Address", "StaffNum");
                        for (Customer pr : Customers) {
                            System.out.printf("%10s %11s %25s %13s %20s %10s\n",
                                    pr.getid(),
                                    pr.getName(),
                                    pr.getEmail(),
                                    pr.getMobile(),
                                    pr.getAddress(),
                                    pr.getStaffNum());
                        }
                    }
                    System.out.println();
                    break;
                }
                case 4: {
                    System.out.println("Edit Mode");
                    editCustomer(keyboard, model);
                    break;
                }

                case 5: {
                    System.out.println("Creating Branch");
                    Branch b = readBranch(keyboard);
                    if (model.addBranch(b)) {
                        System.out.println("Branch added");
                    } else {
                        System.out.println("Branch not added");
                    }

                    break;
                }
                case 6: {
                    System.out.println("Deleting Branches");
                    deleteBranch(keyboard, model);
                    break;
                }
                case 7: {
                    System.out.println("Viewing all Branches");
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
                    break;
                }
                case 8: {
                    System.out.println("Edit Mode");
                    editBranch(keyboard, model);
                    break;
                }

            }
            }
            catch(DataAccessException e){
                
                System.out.println();
                System.out.println(e.getMessage());
                System.out.println();
                
                
            }
        
        } 
        
        while (opt != 9); //stops
        System.out.println("Goodbye");
    }

    private static Customer readCustomer(Scanner keyb) {
        String Name, Email, Mobile, Address;
        int id, StaffNum;

        Name = getString(keyb, "Enter Name: ");
        Email = getString(keyb, "Enter Email: ");
        Mobile = getString(keyb, "Enter Mobile: ");
        Address = getString(keyb, "Enter Address: ");
        StaffNum = getInt(keyb, "Enter staff number: ", -1);


        Customer c
                = new Customer(Name, Email, Mobile,
                        Address, StaffNum);

        return c;
    }

    private static void deleteCustomer(Scanner kb, Model m) throws DataAccessException {
        int StaffNum = getInt(kb,"Enter the staff number of the customer to delete:", -1);
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

    private static void viewCustomers(Model model) {
        List<Customer> customers = model.getCustomers();
        for (Customer pr : customers) {
            System.out.println("Name: " + pr.getName());
        }
    }

    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private static void editCustomer(Scanner kb, Model m) throws DataAccessException {
        int StaffNum = getInt(kb,"Enter the staff number of the customer to edit:", -1);
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
        int StaffNum;
        String line1;

        Name = getString(keyb, "Enter name [" + c.getName() + "]: ");
        Email = getString(keyb, "Enter Email [" + c.getEmail() + "]: ");
        Mobile = getString(keyb, "Enter Mobile [" + c.getMobile() + "]: ");
        Address = getString(keyb, "Enter Address [" + c.getAddress() + "]: ");
        StaffNum = getInt(keyb, "Enter Staff Number [" + c.getStaffNum() + "]: ", -1);

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
            c.setName(Address);
        }
        if (StaffNum != c.getStaffNum()) {
            c.setStaffNum(StaffNum);
        }
    }

    private static Branch readBranch(Scanner keyb) {
        String Address, Manager;
        int id, PhoneNumber, BranchNo, Hours;
        String Line;

        Address = getString(keyb, "Enter Address: ");
        PhoneNumber = getInt(keyb, "Enter Phone Number: ", -1);
        Manager = getString(keyb, "Enter Manager: ");
        Hours = getInt(keyb, "Enter Hours: " , -1);
        BranchNo = getInt(keyb, "Enter Branch Number: ", -1);


        Branch b
                = new Branch(Address, PhoneNumber, Manager,
                        Hours, BranchNo);

        return b;
    }

    private static void deleteBranch(Scanner kb, Model m) throws DataAccessException {
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

    private static void viewBranches(Model model) {
        List<Branch> branches = model.getBranches();
        for (Branch pr : branches) {
            System.out.println("Address: " + pr.getAddress());
        }
    }

    private static String getString2(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private static void editBranch(Scanner kb, Model m) throws DataAccessException {
        int BranchNo = getInt(kb, "Enter the Branch Number of the Branch you want to edit:", -1); 
        Branch b;

        b = m.findBranchbyBranchNum(BranchNo);
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
        int opt = defaultValue;
        boolean finished=false;
        
        do{
            try{
        System.out.print(prompt);
        String line = keyb.nextLine();
        if(line.length() > 0){
        opt = Integer.parseInt(line);
        }
        finished = true;
            }
            catch (NumberFormatException e){
                System.out.println("Exception: " +e.getMessage());
            }
        }
        while (!finished);
            
            return opt;
    }
}
