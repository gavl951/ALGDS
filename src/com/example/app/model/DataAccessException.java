
package com.example.app.model;

public class DataAccessException extends Exception{ 
    public DataAccessException(String msg){ //constructor, error message
        super(msg); //pass to constructor
    }
}
