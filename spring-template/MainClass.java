import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

public class MainClass {
	private ApplicationContext applicationContext;

	public static void main(String[] args){
		MainClass mainClass = new MainClass();
		mainClass.validateArgs();
		mainClass.run(args);
	}

	private void validateArgs(String... args){
		Properties properties = System.getProperties();
		// Store args in properties
	}

	private void run(String... args){
		applicationContext = new ClassPathXmlApplicationContext("application-schema.xml");
		ApplicationDriver applicationDriver = applicationContext.getBean(ApplicationDriver.class);
		applicationDriver.init(applicationContext, args);
		applicationDriver.process();
	}
}
