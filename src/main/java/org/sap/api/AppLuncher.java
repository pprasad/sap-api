package org.sap.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/*
 * @Auth prasad
 * @Date 09/12/2021
 * @Desc Trigger entry point application 
 */
@SpringBootApplication
@EnableScheduling
public class AppLuncher {
   /*static{
	  System.setProperty("java.library.path","bin");
   }*/
   public static void main(String args[]){
	   SpringApplication.run(AppLuncher.class, args);
   }
}
