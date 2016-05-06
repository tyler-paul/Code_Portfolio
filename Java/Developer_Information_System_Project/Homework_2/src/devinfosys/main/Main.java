package devinfosys.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import devinfosys.view.Startable;

public class Main {
	public static void main(String[] args) {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("beans.xml");
		Startable systemMenu = (Startable)context.getBean("systemMenu");
		while (true)
			systemMenu.start();
	}
}
