/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

/**
 *
 * @author nthao
 */
public class FlooringInvalidDataException extends Exception{

    public FlooringInvalidDataException() {
        
    }
    
    public FlooringInvalidDataException(String message) {
        super(message);
    }

    public FlooringInvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
