package theo.contact_app.view;

import theo.contact_app.service.StageService;
import theo.contact_app.service.ViewService;
import javafx.scene.Node;

public class MainLayoutController {
	
	/**
	 * Method allowing us to go back to the home screen
	 */
	public void gotoHome() {
		Node root = ViewService.getView("HomeScreen");
		StageService.showView(root);
	}

	/**
	 * Method allowing us to go to the Contact view
	 */
	public void gotoContactOverview() {
		StageService.showView(ViewService.getView("ContactOverview"));
	}
	
	/**
	 * Method allowing to quit the application
	 */
	public void gotoCloseApplication() {
		StageService.closeStage();
	}
	
	/**
	 * Method allowing to reach the help page
	 */
	public void gotoHelpPage() {
		StageService.showView(ViewService.getView("ContactOverview"));
	}
}
