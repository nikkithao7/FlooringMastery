/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;


/**
 *
 * @author nthao
 */
public class Order {

    private String orderId;
    private String customerName;
    private String state;
    private String taxRate;
    private String productType;
    private double area;
    private String materialCostPerSqFt;
    private String laborCostPerSqFt;
    private String materialTotalCost;
    private String laborTotalCost;
    private String subTotal;
    private String taxTotal;
    private String finalCost;

    public Order() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getMaterialCostPerSqFt() {
        return materialCostPerSqFt;
    }

    public void setMaterialCostPerSqFt(String materialCostPerSqFt) {
        this.materialCostPerSqFt = materialCostPerSqFt;
    }

    public String getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    public void setLaborCostPerSqFt(String laborCostPerSqFt) {
        this.laborCostPerSqFt = laborCostPerSqFt;
    }

    public String getMaterialTotalCost() {
        return materialTotalCost;
    }

    public void setMaterialTotalCost(String materialTotalCost) {
        this.materialTotalCost = materialTotalCost;
    }

    public String getLaborTotalCost() {
        return laborTotalCost;
    }

    public void setLaborTotalCost(String laborTotalCost) {
        this.laborTotalCost = laborTotalCost;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(String taxTotal) {
        this.taxTotal = taxTotal;
    }

    public String getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(String finalCost) {
        this.finalCost = finalCost;
    }

}
