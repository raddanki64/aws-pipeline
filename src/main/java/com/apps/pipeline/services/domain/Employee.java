package	com.apps.pipeline.services.domain;

import 	com.github.javafaker.Faker;

import 	lombok.NoArgsConstructor;
import 	lombok.AllArgsConstructor;
import 	lombok.Getter;
import	lombok.Setter;

import 	com.fasterxml.jackson.databind.ObjectMapper;

import  java.util.Random;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {	
	private static int MAXIMUM_CEILING = 2000;
	private static Random random = new Random();
	private static ObjectMapper mapper = new ObjectMapper();
	
	private int		id;
    private String  firstName;
    private String  lastName;
    
    public static Employee createEmployee() {
        Faker faker = new Faker();
    	return new Employee(random.nextInt(MAXIMUM_CEILING),
    					faker.name().firstName(), 
    					faker.name().lastName());
    }
    
    public String toJson() {
    	try {
    	return mapper.writeValueAsString(this);
    	}
    	catch(Exception e) {
    		return "Observed failure to generate json";
    	}
    }

    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("id: ")
    	  .append(id)
    	  .append(", firstName: ")
    	  .append(firstName)
    	  .append(", lastName: ")
    	  .append(lastName)
    	  ;
    	
    	return sb.toString();
    }
}