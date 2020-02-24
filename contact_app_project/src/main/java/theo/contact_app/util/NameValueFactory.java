package theo.contact_app.util;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import theo.contact_app.model.Contact;

public class NameValueFactory implements Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>> {
	//Il manquera un Override ici. 
	public ObservableValue<String> call(CellDataFeatures<Contact, String> cellData) {
		return new SimpleStringProperty(cellData.getValue().getLastname() + " " + cellData.getValue().getFirstname());
	}
}
