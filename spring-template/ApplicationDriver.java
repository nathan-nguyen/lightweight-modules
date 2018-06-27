import org.springframework.context.ApplicationContext;
import java.io.Serializable;

public class ApplicationDriver implements Serializable{
	private static final long serialVersionUID = 0L;

	private ApplicationContext applicationContext;

	// Initialized from schema
	private ApplicationParams applicationParams;

	public void init(ApplicationContext applicationContext, String... args){
		// Store args in applicationParams since there are many arguments and we don't want them to be Application's attributes
		setApplicationContext(applicationContext);
	}

	public void process() {
		// Process your task here!
		String processConf = applicationContext.getBean("processConf", String.class);
		System.out.println(processConf);
	}

	private void setApplicationContext(ApplicationContext applicationContext){
		this.applicationContext = applicationContext;
	}

	// Important method when creating bean
	public void setApplicationParams(ApplicationParams applicationParams){
		this.applicationParams = applicationParams;
	}
}
