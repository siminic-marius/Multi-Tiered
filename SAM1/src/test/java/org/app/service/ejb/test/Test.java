package org.app.service.ejb.test;

import java.util.Date;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Date startDate = new Date();
//		Long interval = 12l * 30 * /*zile*/  24 /*ore*/ * 60 /*min*/ * 60 /*sec*/ * 1000 /*milisec*/;
//		System.out.println("----------------------------------------------------------------------------------------");
//		System.out.println(new Date(startDate.getTime() + 0 * interval));
//		System.out.println("----------------------------------------------------------------------------------------");
//		System.out.println(new Date(startDate.getTime() + 1 * interval));
//		System.out.println("----------------------------------------------------------------------------------------");
//		System.out.println(new Date(startDate.getTime() + 2 * interval));
//		System.out.println("----------------------------------------------------------------------------------------");
//		System.out.println(new Date(startDate.getTime() + 3 * interval));
//		System.out.println("----------------------------------------------------------------------------------------");
//		System.out.println(new Date(startDate.getTime() + 4 * interval));
//		System.out.println("----------------------------------------------------------------------------------------");
		
		int num1, num2;
		try {
			
	         /* We suspect that this block of statement can throw 
	          * exception so we handled it by placing these statements
	          * inside try and handled the exception in catch block
	          */
	         num1 = 0;
	         num2 = 62 / num1;
	         System.out.println(num2);
	         System.out.println("Hey I'm at the end of try block");
	      }
	      catch (ArithmeticException e) { 
	         /* This block will only execute if any Arithmetic exception 
	          * occurs in try block
	          */
	         System.out.println("You should not divide a number by zero");
	      }
	      catch (Exception e) {
	         /* This is a generic Exception handler which means it can handle
	          * all the exceptions. This will execute if the exception is not
	          * handled by previous catch blocks.
	          */
	         System.out.println("Exception occurred");
	      }
	      System.out.println("I'm out of try-catch block in Java.");
	}

}
