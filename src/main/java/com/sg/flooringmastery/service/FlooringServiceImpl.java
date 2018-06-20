/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringDao;
import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author nthao
 */
public class FlooringServiceImpl {

    private FlooringDao dao;

    public FlooringServiceImpl(FlooringDao dao) {
        this.dao = dao;
    }

    public boolean getProductionMode() throws FlooringPersistenceException {
        return dao.getMode();
    }

    public List<Order> getAllOrdersByDate(String userDate) throws FlooringPersistenceException {
        return dao.getAllOrders(userDate);
    }

    public Order getAnOrder(String id, String date) throws FlooringPersistenceException {
        return dao.getAnOrder(id, date);
    }

    public void createOrder(String id, Order newOrder) throws FlooringPersistenceException {
        dao.addOrder(id, newOrder);
    }
    
    public void editOrder(String id, String userDate, Order order) throws FlooringPersistenceException {
        dao.editOrder(id, userDate, order);
    }

    public void removeOrder(String id, String userDate) throws FlooringPersistenceException {
        dao.removeOder(id, userDate);
    }

    public List<Product> getAllProducts() throws FlooringPersistenceException {
        return dao.getAllProducts();
    }

    public List<Tax> getAllTaxes() throws FlooringPersistenceException {
        return dao.getAllTaxes();
    }

    public String getProductCostPerSqFt(String productType) throws FlooringPersistenceException {
        return dao.getProductCostPerSqFt(productType);
    }

    public String getLaborCostPerSqFr(String productType) throws FlooringPersistenceException {
        return dao.getLaborCostPerSqFr(productType);
    }

    public String getState(String state) throws FlooringPersistenceException {
        return dao.getState(state);
    }

    public String getStateTaxRate(String state) throws FlooringPersistenceException {
        return dao.getStateTaxRate(state);
    }

    public int createOrderId(List<Order> orderList) throws FlooringPersistenceException {
        boolean match = false;
        int orderId = 0;
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy"));
        orderList = dao.getAllOrders(date);
        orderId = orderList.size() + 1;
        String order = (date + "_" + orderId);
        do {
            for (Order o : orderList) {
                if (o.getOrderId().equals(order)) {
                    orderId = orderId + 1;
                }
            }
            match = true;

        } while (match == false);
        return orderId;
    }

    public boolean validateOrderId(String orderNumber, String userDate) throws FlooringPersistenceException {
        List<Order> orderList = dao.getAllOrders(userDate);
        for (Order o : orderList) {
            if (o.getOrderId().equals(orderNumber)) {
                return true;
            }
        }
        return false;
    }

    public String calculateMaterialTotalCost(Order order) throws FlooringPersistenceException {
        BigDecimal materialTotalCost = new BigDecimal(order.getMaterialCostPerSqFt()).multiply(new BigDecimal(order.getArea()));
        return materialTotalCost.setScale(2, RoundingMode.HALF_UP).toString();
    }

    public String calculateLaborTotalCost(Order order) throws FlooringPersistenceException {
        BigDecimal laborTotalCost = new BigDecimal(order.getLaborCostPerSqFt()).multiply(new BigDecimal(order.getArea()));
        return laborTotalCost.setScale(2, RoundingMode.HALF_UP).toString();
    }

    public String calculateSubTotal(Order order) throws FlooringPersistenceException {
        order.setMaterialTotalCost(calculateMaterialTotalCost(order));
        order.setLaborTotalCost(calculateLaborTotalCost(order));
        BigDecimal subTotal = new BigDecimal(order.getMaterialTotalCost()).add(new BigDecimal(order.getLaborTotalCost()));
        return subTotal.setScale(2, RoundingMode.HALF_UP).toString();
    }

    public String calculateTaxTotal(Order order, Tax states) throws FlooringPersistenceException {
        order.setSubTotal(calculateSubTotal(order));
        order.setTaxRate(states.getTaxRate());
        BigDecimal subTotal = new BigDecimal(order.getSubTotal());
        BigDecimal taxToMultiply = new BigDecimal(order.getTaxRate()).divide(new BigDecimal("100.00"));
        BigDecimal taxTotal = subTotal.multiply(taxToMultiply);
        return taxTotal.setScale(2, RoundingMode.HALF_UP).toString();
    }

    public String calculateTotalOrderCost(Order order, Tax states) throws FlooringPersistenceException {
        order.setSubTotal(calculateSubTotal(order));
        order.setTaxTotal(calculateTaxTotal(order, states));
        BigDecimal subTotal = new BigDecimal(order.getSubTotal());
        BigDecimal taxTotal = new BigDecimal(order.getTaxTotal());
        BigDecimal totalOrderCost = subTotal.add(taxTotal);
        return totalOrderCost.setScale(2, RoundingMode.HALF_UP).toString();
    }

}
