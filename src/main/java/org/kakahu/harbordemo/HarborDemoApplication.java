package org.kakahu.harbordemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HarborDemoApplication {

	public static void main(String[] args) {

	    System.out.println("tail -f "+""+System.getProperty("user.dir")+"/*.log");
	    SpringApplication.run(HarborDemoApplication.class, args);
	}

}
