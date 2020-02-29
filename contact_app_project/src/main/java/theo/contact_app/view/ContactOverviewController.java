package theo.contact_app.view;

import javafx.scene.Node;
import theo.contact_app.service.StageService;
import theo.contact_app.service.ViewService;

public class ContactOverviewController {

	
	/**
	 * Method allowing us to go back to the home screen
	 */
	public void gotoHome() {
		Node root = ViewService.getView("HomeScreen");
		StageService.showView(root);
	}
	
	/**
	 * Method allowing to quit the application
	 */
	public void gotoCloseApplication() {
		StageService.closeStage();
	}
}

