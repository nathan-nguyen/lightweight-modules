import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Scanner;
import java.util.List;

public class MainClass {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		SelectQuery selectQuery = (SelectQuery) context.getBean("selectQuery");

		String queryText = in.nextLine();
		System.out.println("\nQuery: " + queryText + "\n");
		selectQuery.getData(queryText);
	}
}
