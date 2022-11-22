package com.apps.pipeline.processors;

import  org.springframework.stereotype.Component;
import 	org.springframework.beans.factory.annotation.Value;

import  java.util.ArrayList;
import  java.util.List;

import  org.apache.camel.Exchange;
import  org.apache.camel.Processor;

import 	lombok.NoArgsConstructor;

import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;

import	com.apps.pipeline.services.domain.Employee;

@Component
@NoArgsConstructor
public class EmployeeEventProcessor implements Processor {
    private static Logger logger = LoggerFactory.getLogger(EmployeeEventProcessor.class);

    public void process(Exchange exchange) throws Exception {
        try {
            Employee anEmployee = Employee.createEmployee();
            String asJson = anEmployee.toJson();            	
                
            logger.info("Employee {},{} is being dispatched: ", anEmployee.getLastName(), anEmployee.getFirstName());
            
            exchange.getIn().setBody(asJson.getBytes());
        }
        catch(Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}
