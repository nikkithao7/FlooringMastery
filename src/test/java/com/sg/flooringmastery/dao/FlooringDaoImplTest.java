/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
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
public class FlooringDaoImplTest {
    
    private FlooringDao dao = new FlooringDaoImpl();
    private List<Order> orderList = new ArrayList<>();
    private List<Tax> taxList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
    private boolean productionMode = false;
    String date = "06071991";
    
    
    public FlooringDaoImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FlooringPersistenceException {
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getMode method, of class FlooringDaoImpl.
     */
    @Test
    public void testGetMode() throws Exception {
        assertTrue("Production", dao.getMode());
    }

    /**
     * Test of getAllOrders method, of class FlooringDaoImpl.
     */
    @Test
    public void testGetAllOrders() throws Exception {
        orderList = dao.getAllOrders(date);
        assertEquals(4, orderList.size());
    }

    /**
     * Test of addOrder method, of class FlooringDaoImpl.
     */
    @Test
    public void testAddOrder() throws Exception {
        orderList = dao.getAllOrders(date);
        
        String id = "06071991_5";
        Order newOrder = new Order();
        newOrder.setOrderId(id);
        newOrder.setCustomerName("Sam");
        newOrder.setState("OH");
        newOrder.setTaxRate("6.25");
        newOrder.setProductType("Wood");
        newOrder.setArea(100);
        newOrder.setMaterialCostPerSqFt("5.15");
        newOrder.setLaborCostPerSqFt("4.75");
        newOrder.setMaterialTotalCost("515");
        newOrder.setLaborTotalCost("475");
        newOrder.setTaxTotal("61.88");
        newOrder.setFinalCost("1051.88");
        
        dao.addOrder(id, newOrder);
        
        Order fromDao = dao.getAnOrder(id, date);
        
        assertEquals(newOrder, fromDao);
    }

    /**
     * Test of getAnOrder method, of class FlooringDaoImpl.
     */
    @Test
    public void testGetAnOrder() throws Exception {
        String id = "06071991_4";
        orderList = dao.getAllOrders(date);
        Order fromDao = dao.getAnOrder(id, date);
        assertEquals("06071991_4", fromDao.getOrderId());
    }

    /**
     * Test of editOrder method, of class FlooringDaoImpl.
     */
    @Test
    public void testEditOrder() throws Exception {
        String editDate = "06081991";
        String id = "06081991_1";
        orderList = dao.getAllOrders(editDate);
        Order order = dao.getAnOrder(id, editDate);
        order.setCustomerName("Jennifer");
        dao.editOrder(id, editDate, order);
        
        assertEquals(1, orderList.size());
        assertEquals("Jennifer", order.getCustomerName());
        
    }

    /**
     * Test of removeOder method, of class FlooringDaoImpl.
     */
    @Test
    public void testRemoveOder() throws Exception {
        String id = "06061991_1";
        String removeDate = "06061991";
        orderList = dao.getAllOrders(removeDate);
        dao.removeOder(id, removeDate);
        String remainingId = "06061991_2";
        Order fromDao = dao.getAnOrder(remainingId, removeDate);
        
        assertEquals("06061991_2",fromDao.getOrderId());
    }

    /**
     * Test of getAllProducts method, of class FlooringDaoImpl.
     */
    @Test
    public void testGetAllProducts() throws Exception {
        assertEquals(4, dao.getAllProducts().size());
    }

    /**
     * Test of getAllTaxes method, of class FlooringDaoImpl.
     */
    @Test
    public void testGetAllTaxes() throws Exception {
        assertEquals(4, dao.getAllProducts().size());
    }

    /**
     * Test of getProductCostPerSqFt method, of class FlooringDaoImpl.
     */
    @Test
    public void testGetProductCostPerSqFt() throws Exception {
        productList = dao.getAllProducts();
        String productType = "Carpet";
        assertEquals("2.25",dao.getProductCostPerSqFt(productType));
    }

    /**
     * Test of getLaborCostPerSqFr method, of class FlooringDaoImpl.
     */
    @Test
    public void testGetLaborCostPerSqFr() throws Exception {
        productList = dao.getAllProducts();
        String productType = "Carpet";
        assertEquals("2.10",dao.getLaborCostPerSqFr(productType));
    }

    /**
     * Test of getState method, of class FlooringDaoImpl.
     */
    @Test
    public void testGetState() throws Exception {
        taxList = dao.getAllTaxes();
        String state = "OH";
        assertEquals("OH", dao.getState(state));
    }

    /**
     * Test of getStateTaxRate method, of class FlooringDaoImpl.
     */
    @Test
    public void testGetStateTaxRate() throws Exception {
        taxList = dao.getAllTaxes();
        String state = "OH";
        assertEquals("6.25", dao.getStateTaxRate(state));
    }
    
}
