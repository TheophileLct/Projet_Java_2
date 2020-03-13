package contact_app.view;

import contact_app.service.StageService;
import contact_app.service.ViewService;
import javafx.fxml.FXML;
import javafx.scene.Node;


public class HomeScreenController {
	
	/**
	 * @throws Exception
	 */
	@FXML
	public void handleLaunchButton() throws Exception {
		StageService.showView((Node)ViewService.getView("ContactOverview"));
	}
	
	
}
