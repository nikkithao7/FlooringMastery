/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.util.List;

/**
 *
 * @author nthao
 */
public interface FlooringDao {
    
    public boolean getMode() throws FlooringPersistenceException;
    
    public List<Order> getAllOrders(String userDate) throws FlooringPersistenceException;
    
    public Order getAnOrder(String id, String date) throws FlooringPersistenceException;
   
    public void addOrder(String id, Order newOrder) throws FlooringPersistenceException;
    
    public void editOrder(String id, String date, Order order) throws FlooringPersistenceException; 
    
    public void removeOder(String id, String date) throws FlooringPersistenceException;
    
    public List<Product> getAllProducts() throws FlooringPersistenceException;
    
    public List<Tax> getAllTaxes() throws FlooringPersistenceException;
    
    public String getProductCostPerSqFt(String productType) throws FlooringPersistenceException;
    
    public String getLaborCostPerSqFr(String productType) throws FlooringPersistenceException;
    
    public String getState(String state) throws FlooringPersistenceException;
    
    public String getStateTaxRate(String state) throws FlooringPersistenceException;
}
