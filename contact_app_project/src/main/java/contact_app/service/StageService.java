package contact_app.service;

import contact_app.service.StageService;
import contact_app.service.ViewService;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StageService {

	private StageService() {
		mainLayout = ViewService.getView("MainLayout");

	}

	private static class StageServiceHolder {
		private static final StageService INSTANCE = new StageService();
	}

	private Stage primaryStage;

	private BorderPane mainLayout;

	public static Stage getPrimaryStage() {
		return StageServiceHolder.INSTANCE.primaryStage;
	}

	
	/**
	 * @param primaryStage
	 */
	public static void initPrimaryStage(Stage primaryStage) {
		primaryStage.setTitle("Contact App");
		primaryStage.setScene(new Scene(StageServiceHolder.INSTANCE.mainLayout));
		
		primaryStage.show();

		StageServiceHolder.INSTANCE.primaryStage = primaryStage;
	}

	
	/** Affiche la vue
	 * @param rootElement
	 */
	public static void showView(Node rootElement) {
		StageServiceHolder.INSTANCE.mainLayout.setCenter(rootElement);
	}

	/**
	 *  Methode qui permet de fermer la Stage.
	 */
	public static void closeStage() {
		StageServiceHolder.INSTANCE.primaryStage
				.fireEvent(new WindowEvent(StageServiceHolder.INSTANCE.primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
}