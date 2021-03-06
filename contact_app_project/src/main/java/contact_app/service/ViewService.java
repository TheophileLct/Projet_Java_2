package contact_app.service;

import java.io.IOException;

import contact_app.App;
import javafx.fxml.FXMLLoader;

public class ViewService {
	public static <T> T getView(String id) {
		return getLoader(id).getRoot();
	}

	/**
	 * @param id
	 * @return 
	 */
	private static FXMLLoader getLoader(String id) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/" + id + ".fxml"));
			loader.load();
			return loader;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}