/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.math.BigDecimal;

/**
 *
 * @author nthao
 */
public interface UserIO {

    Boolean isType(String testStr, String type);
    
    void printBlankLine();
    
    void print(String message);
    
    double readDouble(String prompt);
    
    double readDouble(String prompt, double min, double max);
    
    float readFloat(String prompt);
    
    float readFloat(String prompt, float min, float max);
    
    int readInt(String prompt);
    
    int readInt(String prompt, int min, int max);
    
    long readLong(String prompt);
    
    long readLong(String prompt, long min, long max);
    
    String readLocalDateToString(String prompt);
    
    BigDecimal readBigDecimal(String prompt);
    
    String readString(String prompt);
    
}
