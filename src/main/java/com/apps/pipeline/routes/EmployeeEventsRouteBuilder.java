package com.apps.pipeline.routes;

import  org.springframework.beans.factory.annotation.Autowired;
import 	org.springframework.beans.factory.annotation.Value;
import  org.springframework.stereotype.Component;

import  org.apache.camel.builder.RouteBuilder;
import	org.apache.camel.model.dataformat.JsonLibrary;

import	lombok.NoArgsConstructor;

import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;

import  com.apps.pipeline.exceptions.ValidationException;
import	com.apps.pipeline.processors.EmployeeEventProcessor;
import	com.apps.pipeline.services.domain.Employee;

@Component
@NoArgsConstructor
public class EmployeeEventsRouteBuilder extends RouteBuilder {
    private static Logger logger = LoggerFactory.getLogger(EmployeeEventsRouteBuilder.class);

	@Value("${quartz2.initialDelay}")
	private int initialDelay;

	@Value("${quartz2.repetionCount}")
	private int repetionCount;

	@Autowired
	private EmployeeEventProcessor empEventProcessor;

    @Override
    public void configure() throws Exception {
		String timerExpression = String.format("timer://empeventstimer?delay=%d&repeatCount=%d",
					initialDelay,
					repetionCount);
		
        onException(ValidationException.class)
        .log("Observed validation exception")
        .markRollbackOnly()
        .useOriginalMessage()
        .logStackTrace(true)        
        .end();

        onException(Exception.class)
        .log("Observed exception")
        .markRollbackOnly()
        .useOriginalMessage()
        .logStackTrace(true)
        .end();
        
		from(timerExpression)
		.routeId("Producer.Route")
		.process(empEventProcessor)
		.log("Content: ${body}")
		.end();
    }
}