/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import com.sg.flooringmastery.service.FlooringInvalidDataException;
import com.sg.flooringmastery.service.FlooringServiceImpl;
import com.sg.flooringmastery.ui.FlooringView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nthao
 */
public class FlooringController {

    private FlooringView view;
    private FlooringServiceImpl service;

    private Order newOrder = new Order();
    private Product newProduct = new Product();
    private Tax newTax = new Tax();

    public FlooringController(FlooringView view, FlooringServiceImpl service) {
        this.service = service;
        this.view = view;
    }

    public void run() throws FlooringPersistenceException, FlooringInvalidDataException {
        boolean keepGoing = true;

        int menuSelection = 0;

        while (keepGoing) {

            try {
                menuSelection = getMenuSelection();
                switch (menuSelection) {
                    case 1:
                        listAllOrdersByDate();

                        break;
                    case 2:
                        createOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        keepGoing = false;
                }
            } catch (FlooringPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
        view.displayExitMessage();
    }

    private int getMenuSelection() {
        return view.displayAndGetMenuOptions();
    }

    private void listAllOrdersByDate() throws FlooringPersistenceException, FlooringInvalidDataException {
        String userDate = view.getOrderDate();
        List<Order> orders = service.getAllOrdersByDate(userDate);
        if (orders.isEmpty() == true) {
            view.displayInvalidDateError();
        } else {
            view.displayOrderList(orders);
        }

    }

    private void createOrder() throws FlooringPersistenceException {
        String orderDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy"));
        List<Order> orders = service.getAllOrdersByDate(orderDate);
        List<Product> products = service.getAllProducts();
        List<Tax> states = service.getAllTaxes();
        boolean productionMode = service.getProductionMode();

        String customerName = view.getOrderName();
        String customerState = view.getOrderState(states);
        newTax.setState(customerState);
        String customerProduct = view.getProductType(products);
        newProduct.setProductType(customerProduct);
        double customerArea = view.getOrderArea();
        newProduct.setArea(customerArea);

        String taxRate = service.getStateTaxRate(customerState);
        newTax.setTaxRate(taxRate);
        String materialCostSqFt = service.getProductCostPerSqFt(customerProduct);
        String laborCostSqFt = service.getLaborCostPerSqFr(customerState);

        int orderId = service.createOrderId(orders);
        String thisOrderId = (orderDate + "_" + orderId);
        newOrder.setOrderId(thisOrderId);
        newOrder.setCustomerName(customerName);
        newOrder.setState(customerState);
        newOrder.setTaxRate(taxRate);
        newOrder.setProductType(customerProduct);
        newOrder.setArea(customerArea);
        newOrder.setMaterialCostPerSqFt(materialCostSqFt);
        newOrder.setLaborCostPerSqFt(laborCostSqFt);

        String materialTotalCost = service.calculateMaterialTotalCost(newOrder);
        String laborTotalCost = service.calculateLaborTotalCost(newOrder);
        String subTotal = service.calculateSubTotal(newOrder);
        String taxTotal = service.calculateTaxTotal(newOrder, newTax);
        String finalCost = service.calculateTotalOrderCost(newOrder, newTax);

        newOrder.setMaterialTotalCost(materialTotalCost);
        newOrder.setLaborTotalCost(laborTotalCost);
        newOrder.setSubTotal(subTotal);
        newOrder.setTaxTotal(taxTotal);
        newOrder.setFinalCost(finalCost);

        String confirm = view.displayAndGetAddConfirmation(newOrder);
        if (confirm.equalsIgnoreCase("Y")) {
            if (!productionMode) {
            } else {
                service.createOrder(thisOrderId, newOrder);
            }
            view.displayAddOrderSuccessBanner();
        } else {
            view.displayAddOrderNotAdded();
        }

    }

    private void editOrder() throws FlooringPersistenceException, FlooringInvalidDataException {
        String userDate = view.getOrderDate();
        List<Product> products = service.getAllProducts();
        List<Tax> states = service.getAllTaxes();
        List<Order> orders = service.getAllOrdersByDate(userDate);
        boolean productionMode = service.getProductionMode();
        boolean validOrderId = false;

        if (orders.isEmpty() == true) {
            view.displayInvalidDateError();
        } else {

            while (!validOrderId) {
                String userOrderId = view.getOrderId();
                validOrderId = service.validateOrderId(userOrderId, userDate);

                if (validOrderId) {
                    Order eachOrder = service.getAnOrder(userOrderId, userDate);
                    if (eachOrder.getOrderId().equals(userOrderId)) {
                        String newName = view.displayAndEditName(eachOrder);
                        if (newName.isEmpty()) {
                        } else {
                            eachOrder.setCustomerName(newName);
                        }
                        String newState = view.displayAndEditState(eachOrder, states);
                        if (newState.isEmpty()) {
                            newTax.setState(eachOrder.getState());
                            newTax.setTaxRate(eachOrder.getTaxRate());
                        } else {
                            String state = service.getState(newState);
                            String taxRate = service.getStateTaxRate(newState);
                            eachOrder.setState(state);
                            eachOrder.setTaxRate(taxRate);
                            newTax.setState(state);
                            newTax.setTaxRate(taxRate);
                        }
                        String newProductType = view.displayAndEditProductType(eachOrder, products);
                        if (newProductType.isEmpty()) {
                        } else {
                            eachOrder.setProductType(newProductType);
                            String materialCostSqFt = service.getProductCostPerSqFt(newProductType);
                            eachOrder.setMaterialCostPerSqFt(materialCostSqFt);
                            String laborCostSqFt = service.getLaborCostPerSqFr(newProductType);
                            eachOrder.setLaborCostPerSqFt(laborCostSqFt);
                        }

                        String newArea = view.displayAndEditArea(eachOrder);
                        if (newArea.isEmpty()) {
                        } else {
                            eachOrder.setArea(Double.parseDouble(newArea));
                            String materialTotalCost = service.calculateMaterialTotalCost(eachOrder);
                            eachOrder.setMaterialTotalCost(materialTotalCost);
                            String laborTotalCost = service.calculateLaborTotalCost(eachOrder);
                            eachOrder.setLaborTotalCost(laborTotalCost);
                            String subTotal = service.calculateSubTotal(eachOrder);
                            eachOrder.setSubTotal(subTotal);
                            String taxTotal = service.calculateTaxTotal(eachOrder, newTax);
                            eachOrder.setTaxTotal(taxTotal);
                            String orderTotal = service.calculateTotalOrderCost(eachOrder, newTax);
                            eachOrder.setFinalCost(orderTotal);

                        }
                        if (!productionMode) {
                        } else {
                            service.editOrder(userOrderId, userDate, eachOrder);

                        }
                        view.displayEditOrderSuccessBanner();
                    }
                } else {
                    view.displayInvalidOrderId();

                }
            }
        }

    }

    private void removeOrder() throws FlooringPersistenceException, FlooringInvalidDataException {
        String userDate = view.getOrderDate();
        List<Order> orders = service.getAllOrdersByDate(userDate);
        boolean productionMode = service.getProductionMode();
        boolean validOrderId = false;

        if (orders.isEmpty() == true) {
            view.displayInvalidDateError();
        } else {
            while (!validOrderId) {
                String userOrderId = view.getOrderId();

                validOrderId = service.validateOrderId(userOrderId, userDate);

                if (validOrderId) {

                    Order eachOrder = service.getAnOrder(userOrderId, userDate);
                    if (eachOrder.getOrderId().equals(userOrderId)) {
                        String confirm = view.getRemoveConfirmation(eachOrder);
                        if (confirm.equalsIgnoreCase("Y")) {
                            if (!productionMode) {
                            } else {
                                service.removeOrder(userOrderId, userDate);
                                view.displayRemoveOrderSuccessBanner();
                            }
                        }
                    } else {
                        view.displayRemoveOrderNotRemoved();
                    }
                } else {
                    view.displayInvalidOrderId();
                }
            }
        }
    }

    private void displayExitMessage() {
        view.displayExitMessage();
    }

}
