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
public class Product {
    
    private String productType;
    private double area;
    private String materialCostPerSqFt;
    private String laborCostPerSqFt;
    private String materialTotalCost;
    private String laborTotalCost;

    public Product() {

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
    

}
