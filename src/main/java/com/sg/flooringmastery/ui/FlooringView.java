/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import com.sg.flooringmastery.service.FlooringInvalidDataException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nthao
 */
public class FlooringView {

    private final UserIO io;
    private List<Order> orderList = new ArrayList();
    private List<Product> productList = new ArrayList();
    private List<Tax> taxList = new ArrayList();
    private Tax tax;
    private Product product;
    private boolean error = true;
    private String userInput;

    public FlooringView(UserIO io) {
        this.io = io;
    }

    public void displayProgramBanner() {
        io.print("*** Welcome to the Flooring Orders Program ***");
    }

    public int displayAndGetMenuOptions() {
        io.printBlankLine();
        io.print("=== Flooring Program Options ===");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Exit Program");

        return io.readInt("Please select from the options above: ", 1, 5);
    }

    public String getOrderDate() throws FlooringInvalidDataException {
        return io.readLocalDateToString("Please input the order date (MM-dd-yyyy): ");
    }

    public void displayInvalidDateError() {
        io.print("There are no orders for the date entered. ");
    }

    public String getOrderId() {
        do {
            userInput = io.readString("Please input the order ID number: ");
            if (userInput.isEmpty()) {
                error = true;
            } else {
                error = false;
            }
        } while (error == true);
        return userInput;
    }

    public void displayInvalidOrderId() {
        io.print("There are no orders with the Order ID you entered. ");
    }

    public String displayAndGetAddConfirmation(Order newOrder) {
        io.printBlankLine();
        io.print(" === Order Summary ===");
        io.print("Order ID: " + newOrder.getOrderId());
        io.print("Customer Name: " + newOrder.getCustomerName());
        io.print("State: " + newOrder.getState());
        io.print("Product Type: " + newOrder.getProductType());
        io.print("Area: " + newOrder.getArea());
        io.printBlankLine();

        do {
            userInput = io.readString("Would you like to move forward with the order? (Y/N)");
            if (userInput.isEmpty()) {
                error = true;
            } else {
                error = false;
            }
        } while (error == true);
        return userInput;
    }

    public void displayOrderList(List<Order> orderList) {
        for (Order currentOrder : orderList) {
            io.printBlankLine();
            io.print("Order ID: " + currentOrder.getOrderId());
            io.print("Customer Name: " + currentOrder.getCustomerName());
            io.print("State: " + currentOrder.getState());
            io.print("Tax Rate: " + currentOrder.getTaxRate());
            io.print("Product Type: " + currentOrder.getProductType());
            io.print("Area: " + currentOrder.getArea());
            io.print("Product Cost Per Sq Foot: " + currentOrder.getMaterialCostPerSqFt());
            io.print("Labor Cost Per Sq Foot: " + currentOrder.getLaborCostPerSqFt());
            io.print("Product Total Cost: " + currentOrder.getMaterialTotalCost());
            io.print("Labor Total Cost: " + currentOrder.getLaborTotalCost());
            io.print("Tax: " + currentOrder.getTaxTotal());
            io.print("Total Cost: " + currentOrder.getFinalCost());
        }
    }

    public String getOrderName() {
        io.print(" === ADD ORDER ===");
        do {
            userInput = io.readString("Please enter the customer's name: ");
            if (userInput.isEmpty()) {
                error = true;
            } else {
                error = false;
            }
        } while (error == true);
        return userInput;
    }

    public double getOrderArea() {
        double areaOfRoom = io.readDouble("What is the area of the room? ");
        return areaOfRoom;
    }

    public String getOrderState(List<Tax> taxList) {
        io.print("  ***Available States for Flooring Services***");
        for (int i = 0; i <= taxList.size() - 1; i++) {
            Tax state = taxList.get(i);
            io.print("          " + state.getState());
        }
        do {
            userInput = io.readString("Please enter the State you'd like to have the flooring services completed in: ");

            if (userInput.isEmpty()) {
                error = true;
            } else {
                for (Tax thisState : taxList) {
                    if (userInput.equalsIgnoreCase(thisState.getState())) {
                        error = false;
                        return userInput;
                    } else {
                        error = true;
                    }
                }

            }
            io.print("Sorry, that is not a state we service.");
        } while (error == true);
        return null;
    }

    public String getProductType(List<Product> type) {
        io.print("  ***Flooring Types Available***");
        for (int i = 0; i <= type.size() - 1; i++) {
            Product productType = type.get(i);
            io.print("          " + productType.getProductType());
        }
        do {
            userInput = io.readString("Please enter the flooring type you'd like from the choices above: ");

            if (userInput.isEmpty()) {
                error = true;
            } else {
                for (Product thisType : type) {
                    if (userInput.equalsIgnoreCase(thisType.getProductType())) {
                        error = false;
                        return userInput;
                    } else {
                        error = true;
                    }
                }
            }
            io.print("Sorry, that is not a flooring type we produce.");
        } while (error == true);
        return null;
    }

    public String displayAndEditName(Order editOrder) {
        io.printBlankLine();
        io.print(" === EDIT ORDER ===");
        io.print("***NOTE: If no edit is needed for a field, hit enter to skip.***");
        io.printBlankLine();
        String editCustomerName = io.readString("Enter the customer name you'd like to edit order to (Currently: " + editOrder.getCustomerName() + ") :");
        return editCustomerName;
    }

    public String displayAndEditState(Order editOrder, List<Tax> taxList) {
        io.print("  ***Available States for Flooring Services***");
        for (int i = 0; i <= taxList.size() - 1; i++) {
            Tax state = taxList.get(i);
            io.print("          " + state.getState());
        }
        do {
            userInput = io.readString("Enter the state you'd like to edit order to (Currently: " + editOrder.getState() + "):");
            for (Tax thisState : taxList) {
                if (userInput.equalsIgnoreCase(thisState.getState()) || userInput.isEmpty()) {
                    error = false;
                    return userInput;

                } else {
                    error = true;
                }
            }
            io.print("Sorry, that is not a state we service");
        } while (error == true);
        return null;
    }

    public String displayAndEditProductType(Order editOrder, List<Product> productList) {
        io.print("  ***Flooring Types Available***");
        for (int i = 0; i <= productList.size() - 1; i++) {
            Product productType = productList.get(i);
            io.print("          " + productType.getProductType());
        }
        do {
            userInput = io.readString("Enter the product type you'd like to edit order to (Currently: " + editOrder.getProductType() + "):");
            for (Product thisProduct : productList) {
                if (userInput.equalsIgnoreCase(thisProduct.getProductType()) || userInput.isEmpty()) {
                    error = false;
                    return userInput;

                } else {
                    error = true;
                }
            }
            io.print("Sorry, that is not a flooring type we produce.");
        } while (error == true);
        return null;
    }

    public String displayAndEditArea(Order editOrder) {
        String editArea = io.readString("Enter the area you'd like to edit order to (Currently: " + editOrder.getArea() + "):");
        return editArea;
    }

    public String getRemoveConfirmation(Order removedOrder) {
        io.printBlankLine();
        io.print(" === Order Summary ===");
        io.print("Order ID: " + removedOrder.getOrderId());
        io.print("Customer Name: " + removedOrder.getCustomerName());
        io.print("State: " + removedOrder.getState());
        io.print("Tax Rate: " + removedOrder.getTaxRate());
        io.print("Product Type: " + removedOrder.getProductType());
        io.print("Area: " + removedOrder.getArea());
        io.print("Product Cost Per Sq Foot: " + removedOrder.getMaterialCostPerSqFt());
        io.print("Labor Cost Per Sq Foot: " + removedOrder.getLaborCostPerSqFt());
        io.print("Product Total Cost: " + removedOrder.getMaterialTotalCost());
        io.print("Labor Total Cost: " + removedOrder.getLaborTotalCost());
        io.print("Tax: " + removedOrder.getTaxTotal());
        io.print("Total Cost: " + removedOrder.getFinalCost());
        io.printBlankLine();

        do {
            userInput = io.readString("Are you sure you want to delete this order? (Y/N)");
            if (userInput.isEmpty()) {
                error = true;
            } else {
                error = false;
            }
        } while (error == true);
        return userInput;
    }

    public void displayAddOrderSuccessBanner() {
        io.print("Your order has been successfully added.");
    }

    public void displayEditOrderSuccessBanner() {
        io.print("Your order has been successfully edited.");
    }

    public void displayRemoveOrderSuccessBanner() {
        io.print("Your order has been successfully removed.");
    }

    public void displayAddOrderNotAdded() {
        io.print("Your order was not created.");
    }

    public void displayRemoveOrderNotRemoved() {
        io.print("Your order was not removed.");
    }

    public void displayErrorMessage(String prompt) {
        io.print(prompt);
        io.printBlankLine();
    }

    public void displayExitMessage() {
        io.printBlankLine();
        io.print("Thanks for using the Flooring Mastery Program!");
    }

}
