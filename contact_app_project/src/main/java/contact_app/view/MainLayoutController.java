package contact_app.view;

import contact_app.service.StageService;
import contact_app.service.ViewService;
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
		StageService.showView((Node)ViewService.getView("ContactOverview"));
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
	public void gotoAboutPage() {
		StageService.showView((Node)ViewService.getView("About"));
	}
}
