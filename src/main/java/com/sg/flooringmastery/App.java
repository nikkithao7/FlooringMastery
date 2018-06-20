/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringController;
import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.service.FlooringInvalidDataException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author nthao
 */
public class App {

    public static void main(String[] args) throws FlooringPersistenceException, FlooringInvalidDataException {

        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringController controller
                = ctx.getBean("controller", FlooringController.class);
        controller.run();
    }

}

