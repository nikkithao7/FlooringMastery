/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringDao;
import com.sg.flooringmastery.dao.FlooringDaoImpl;
import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Tax;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nthao
 */
public class FlooringServiceImplTest {

    private FlooringServiceImpl service;
    List<Order> orderList = new ArrayList();
    List<Tax> taxList = new ArrayList();
    String orderId = "06062018_2";
    String date = "06062018";

    public FlooringServiceImplTest() {
        FlooringDao dao = new FlooringDaoImpl();
        service = new FlooringServiceImpl(dao);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws FlooringPersistenceException {
        orderList = service.getAllOrdersByDate(date);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createOrderId method, of class FlooringServiceImpl.
     */
    @Test
    public void testCreateOrderId() throws Exception {
        assertEquals(3, service.createOrderId(orderList));
    }

    /**
     * Test of validateOrderId method, of class FlooringServiceImpl.
     */
    @Test
    public void testValidateOrderId() throws Exception {
        assertTrue(service.validateOrderId(orderId, date));
    }

    /**
     * Test of calculateMaterialTotalCost method, of class FlooringServiceImpl.
     */
    @Test
    public void testCalculateMaterialTotalCost() throws Exception {
        Order order = orderList.get(1);

        assertEquals("225.00", service.calculateMaterialTotalCost(order));
    }

    /**
     * Test of calculateLaborTotalCost method, of class FlooringServiceImpl.
     */
    @Test
    public void testCalculateLaborTotalCost() throws Exception {
        Order order = orderList.get(1);

        assertEquals("210.00", service.calculateLaborTotalCost(order));
    }

    /**
     * Test of calculateSubTotal method, of class FlooringServiceImpl.
     */
    @Test
    public void testCalculateSubTotal() throws Exception {
        Order order = orderList.get(1);

        assertEquals("435.00", service.calculateSubTotal(order));
    }

    /**
     * Test of calculateTaxTotal method, of class FlooringServiceImpl.
     */
    @Test
    public void testCalculateTaxTotal() throws Exception {
        taxList = service.getAllTaxes();
        Order order = orderList.get(1);
        Tax states = taxList.get(0);

        assertEquals("27.19", service.calculateTaxTotal(order, states));
    }

    /**
     * Test of calculateTotalOrderCost method, of class FlooringServiceImpl.
     */
    @Test
    public void testCalculateTotalOrderCost() throws Exception {
        taxList = service.getAllTaxes();
        Order order = orderList.get(1);
        Tax states = taxList.get(0);

        assertEquals("462.19", service.calculateTotalOrderCost(order, states));
    }

}
