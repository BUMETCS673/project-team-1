package googleAuth.googleAuth;
import main.Expense;
import main.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
//@ComponentScan(basePackages = "main")

public class GoogleAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(GoogleAuthApplication.class, args);
	}
	/*
	@Component
	public class PinnyWise {

		@Autowired
		public PinnyWise(User user1, User user2, Expense expense) {

			// add the users as observers for the expenses
			expense.addObserver(user1);
			expense.addObserver(user2);
			// Update the expense, and user will be notified
			expense.updateExpense(250.0);

		}

	}*/
}