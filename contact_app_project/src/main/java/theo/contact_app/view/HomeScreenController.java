package theo.contact_app.view;

import theo.contact_app.service.StageService;
import theo.contact_app.service.ViewService;
import javafx.fxml.FXML;

public class HomeScreenController {
	@FXML
	public void handleLaunchButton() throws Exception {
		StageService.showView(ViewService.getView("ContactOverview"));
	}
	
	
}
