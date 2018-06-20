/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author nthao
 */
public class FlooringDaoImpl implements FlooringDao {

    private Map<String, Order> orderList = new HashMap<>();
    private List<Product> products = new ArrayList<>();
    private List<Tax> taxes = new ArrayList<>();
    private boolean productionMode = false;

    public static final String CONFIG_FILE = "config.txt";
    public static final String PRODUCT_FILE = "product.txt";
    public static final String TAX_FILE = "taxes.txt";
    public final String ORDER_PATH = "./orders/";
    public final String ORDER_FILE = "orders_";
    LocalDate date = LocalDate.now();
    public String fileDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
    public final String TXT = ".txt";

    public static final String DELIMITER = "::";

    private void checkFileDate(String userDate) throws FlooringPersistenceException {
        try {
            File file = new File(ORDER_PATH + ORDER_FILE + userDate + TXT);
            boolean dateFile = file.createNewFile();
            if (dateFile) {
                //File did not exist before. New file would be created.
            } else {
                //File already exists.
            }
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not look for file. Error happened.", e);
        }
    }

    private void loadOrderFile(String userDate) throws FlooringPersistenceException {
        checkFileDate(userDate);

        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(new FileReader(ORDER_PATH + ORDER_FILE + userDate + TXT)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not load order file into memory");
        }

        String currentLine;

        String[] currentTokens;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);

            if (currentTokens.length == 12) {
                /*
                adding the currentTokens length check to make sure it does not read blank files that 
                are created for the day without having any actual data.
                 */
                Order currentOrder = new Order();
                currentOrder.setOrderId(currentTokens[0]);
                currentOrder.setCustomerName(currentTokens[1]);
                currentOrder.setState(currentTokens[2]);
                currentOrder.setTaxRate(currentTokens[3]);
                currentOrder.setProductType(currentTokens[4]);
                currentOrder.setArea(Double.parseDouble(currentTokens[5]));
                currentOrder.setMaterialCostPerSqFt(currentTokens[6]);
                currentOrder.setLaborCostPerSqFt(currentTokens[7]);
                currentOrder.setMaterialTotalCost(currentTokens[8]);
                currentOrder.setLaborTotalCost(currentTokens[9]);
                currentOrder.setTaxTotal(currentTokens[10]);
                currentOrder.setFinalCost(currentTokens[11]);

                orderList.put(currentOrder.getOrderId(), currentOrder);
            }
        }
        scanner.close();
    }

    private void writeAddOrderFile() throws FlooringPersistenceException {
        checkFileDate(fileDate);

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ORDER_PATH + ORDER_FILE + fileDate + TXT));
        } catch (IOException e) {
            throw new FlooringPersistenceException("File could not be saved with new order information.");
        }

        Collection<Order> orders = orderList.values();
        for (Order currentOrder : orders) {
            out.println(currentOrder.getOrderId() + DELIMITER
                    + currentOrder.getCustomerName() + DELIMITER
                    + currentOrder.getState() + DELIMITER
                    + currentOrder.getTaxRate() + DELIMITER
                    + currentOrder.getProductType() + DELIMITER
                    + currentOrder.getArea() + DELIMITER
                    + currentOrder.getMaterialCostPerSqFt() + DELIMITER
                    + currentOrder.getLaborCostPerSqFt() + DELIMITER
                    + currentOrder.getMaterialTotalCost() + DELIMITER
                    + currentOrder.getLaborTotalCost() + DELIMITER
                    + currentOrder.getTaxTotal() + DELIMITER
                    + currentOrder.getFinalCost());

            out.flush();
        }
        out.close();
    }

    private void writeRemoveAndEditOrderFile(String userDate) throws FlooringPersistenceException {
        checkFileDate(userDate);

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ORDER_PATH + ORDER_FILE + userDate + TXT));
        } catch (IOException e) {
            throw new FlooringPersistenceException("File could not be saved with new order information.");
        }

        Collection<Order> orders = orderList.values();
        for (Order currentOrder : orders) {
            out.println(currentOrder.getOrderId() + DELIMITER
                    + currentOrder.getCustomerName() + DELIMITER
                    + currentOrder.getState() + DELIMITER
                    + currentOrder.getTaxRate() + DELIMITER
                    + currentOrder.getProductType() + DELIMITER
                    + currentOrder.getArea() + DELIMITER
                    + currentOrder.getMaterialCostPerSqFt() + DELIMITER
                    + currentOrder.getLaborCostPerSqFt() + DELIMITER
                    + currentOrder.getMaterialTotalCost() + DELIMITER
                    + currentOrder.getLaborTotalCost() + DELIMITER
                    + currentOrder.getTaxTotal() + DELIMITER
                    + currentOrder.getFinalCost());

            out.flush();
        }
        out.close();
    }

    private void loadConfigFile() throws FlooringPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(CONFIG_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not load product list into memory.", e);
        }

        String currentLine = scanner.nextLine();

        if (currentLine.equalsIgnoreCase("Production")) {
            productionMode = true;
        }
        scanner.close();
    }

    private void loadProductFile() throws FlooringPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not load product list into memory.", e);
        }

        String currentLine;
        String[] currentTokens;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Product currentProduct = new Product();
            currentProduct.setProductType(currentTokens[0]);
            currentProduct.setMaterialCostPerSqFt(currentTokens[1]);
            currentProduct.setLaborCostPerSqFt(currentTokens[2]);

            products.add(currentProduct);
        }
        scanner.close();
    }

    private void loadTaxesFile() throws FlooringPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not load tax list into memory.", e);
        }

        String currentLine;
        String[] currentTokens;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Tax currentTax = new Tax();
            currentTax.setState(currentTokens[0]);
            currentTax.setTaxRate(currentTokens[1]);

            taxes.add(currentTax);
        }
        scanner.close();
    }

    @Override
    public boolean getMode() throws FlooringPersistenceException {
        loadConfigFile();
        return productionMode;
    }

    @Override
    public List<Order> getAllOrders(String userDate) throws FlooringPersistenceException {
        orderList.clear();
        loadOrderFile(userDate);
        return new ArrayList<Order>(orderList.values());
    }

    @Override
    public void addOrder(String id, Order order) throws FlooringPersistenceException {
        loadOrderFile(fileDate);
        orderList.put(id, order);
        writeAddOrderFile();
    }

    @Override
    public Order getAnOrder(String id, String date) throws FlooringPersistenceException {
        loadOrderFile(date);
        return orderList.get(id);
    }

    @Override
    public void editOrder(String id, String date, Order order) throws FlooringPersistenceException {
        loadOrderFile(date);
        orderList.remove(id);
        orderList.put(id, order);
        writeRemoveAndEditOrderFile(date);
    }

    @Override
    public void removeOder(String id, String date) throws FlooringPersistenceException {
        loadOrderFile(date);
        orderList.remove(id);
        writeRemoveAndEditOrderFile(date);

    }

    @Override
    public List<Product> getAllProducts() throws FlooringPersistenceException {
        products.clear();
        loadProductFile();
        return products;
    }

    @Override
    public List<Tax> getAllTaxes() throws FlooringPersistenceException {
        taxes.clear();
        loadTaxesFile();
        return taxes;
    }

    @Override
    public String getProductCostPerSqFt(String productType) throws FlooringPersistenceException {
        for (Product type : products) {
            return type.getMaterialCostPerSqFt();
        }
        return null;
    }

    @Override
    public String getLaborCostPerSqFr(String productType) throws FlooringPersistenceException {
        for (Product type : products) {
            return type.getLaborCostPerSqFt();
        }
        return null;
    }

    @Override
    public String getState(String state) throws FlooringPersistenceException {
        for (Tax st : taxes) {
            if (st.getState().equalsIgnoreCase(state)) {
                return st.getState();
            }
        }
        return null;
    }

    @Override
    public String getStateTaxRate(String state) throws FlooringPersistenceException {
        for (Tax st : taxes) {
            return st.getTaxRate();
        }
        return null;
    }

}
