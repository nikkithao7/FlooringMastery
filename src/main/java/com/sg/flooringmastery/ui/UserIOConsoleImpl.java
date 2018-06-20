/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author nthao
 */
public class UserIOConsoleImpl implements UserIO {

    Scanner inputReader = new Scanner(System.in);
    Boolean error = false;
    String userInp = "";


    
    @Override
    public Boolean isType(String testStr, String type) {
            
        try {
            if (type.equalsIgnoreCase("float")) {
                Float.parseFloat(testStr);
            } else if (type.equalsIgnoreCase("int")) {
                Integer.parseInt(testStr);
            } else if (type.equalsIgnoreCase("double")) {
                Double.parseDouble(testStr);
            } else if (type.equals("MM-dd-yyyy")) {
                LocalDate.parse(testStr, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void printBlankLine() {
        System.out.println();
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        do {
            System.out.println(prompt);
            userInp = inputReader.nextLine();
            if (!this.isType(userInp, "double")) {
                error = true;
                System.out.println("Error: Must be a number.");
            } else {
                error = false;
            }
        } while (error == true);
        return Double.parseDouble(userInp);

    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        System.out.println(prompt);
        double number = inputReader.nextDouble();
        inputReader.nextLine();

        while (number < min || number > max) {
            System.out.println(prompt);
            number = inputReader.nextDouble();
            inputReader.nextLine();
        }
        return number;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        float number = inputReader.nextFloat();
        inputReader.nextLine();
        return number;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        System.out.println(prompt);
        float number = inputReader.nextFloat();
        inputReader.nextLine();

        while (number < min || number > max) {
            System.out.println(prompt);
            number = inputReader.nextFloat();
            inputReader.nextLine();
        }
        return number;
    }

    @Override
    public int readInt(String prompt) {
        do {
            System.out.println(prompt);
            userInp = inputReader.nextLine();
            if (!this.isType(userInp, "int")) {
                error = true;
            } else {
                error = false;
            }
        } while (error == true);
        return Integer.parseInt(userInp);
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.println(prompt);
        int number = inputReader.nextInt();
        inputReader.nextLine();

        while (number < min || number > max) {
            System.out.println(prompt);
            number = inputReader.nextInt();
            inputReader.nextLine();
        }
        return number;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        long number = inputReader.nextLong();
        inputReader.nextLine();
        return number;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        System.out.println(prompt);
        long number = inputReader.nextLong();
        inputReader.nextLine();

        while (number < min || number > max) {
            System.out.println(prompt);
            number = inputReader.nextLong();
            inputReader.nextLine();
        }
        return number;
    }

    @Override
    public String readLocalDateToString(String prompt) {
        do {
            System.out.println(prompt);
            userInp = inputReader.nextLine();
            if (userInp.isEmpty()) {
                error = true;
            } else if (!this.isType(userInp, "MM-dd-yyyy")) {
                error = true;
            } else 
                error = false;
        } while (error == true);
        LocalDate ld = LocalDate.parse(userInp, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        return ld.format(DateTimeFormatter.ofPattern("MMddyyyy"));
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        System.out.println(prompt);
        BigDecimal bd = inputReader.nextBigDecimal();
        inputReader.nextLine();
        return bd;
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        String input = inputReader.nextLine();
        return input;
    }

}
