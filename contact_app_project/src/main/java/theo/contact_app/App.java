package theo.contact_app;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import theo.contact_app.service.StageService;
import theo.contact_app.service.ViewService;

public class App extends Application{
	
	public App() {

	}

	@Override
	public void start(Stage primaryStage) {
		StageService.initPrimaryStage(primaryStage);
		StageService.showView(ViewService.getView("HomeScreen"));
	}

	public static void main(String[] args) throws IOException {
		launch(args);
	}
}
