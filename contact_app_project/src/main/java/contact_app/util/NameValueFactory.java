package contact_app.util;

import contact_app.model.Contact;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class NameValueFactory implements Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>> {
	@Override
	public ObservableValue<String> call(CellDataFeatures<Contact, String> cellData) {
		return new SimpleStringProperty(cellData.getValue().getLastname() + " " + cellData.getValue().getFirstname());
	}
}
