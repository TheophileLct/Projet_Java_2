package theo.contact_app.view;

import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import theo.contact_app.model.Contact;
import theo.contact_app.service.ContactService;
import theo.contact_app.service.StageService;
import theo.contact_app.service.ViewService;

public class ContactOverviewController {
	
	@FXML
	TableView<Contact> contactTable;

	@FXML
	TableColumn<Contact, String> nicknameColumn;
	
	@FXML
	TableColumn<Contact, String> nameColumn;

	@FXML
	TextField lastNameField;

	@FXML
	TextField firstNameField;

	@FXML
	TextField nicknameField;

	@FXML
	TextField phoneNumberField;
	
	@FXML
	TextField streetAddressField;
	
	@FXML
	TextField numberField;
	
	@FXML
	TextField cityAddressField;
	
	@FXML
	TextField regionAddressField;
	
	@FXML
	TextField countryAddressField;

	@FXML
	TextField emailAddressField;
	
	@FXML
	DatePicker birthDatePicker;
	
	@FXML
	Button addButton;
	
	@FXML
	Button updateButton;
	
	@FXML
	Button deleteButton;
	
	@FXML
	Button saveButton;
	
	@FXML
	Button cancelButton;
	
	//ça en gros c'est la partie pour taper le nom de la personne 
	@FXML
	TextField filterTextField;
	
	//ça c'est la partie pour valider et faire le filtrage dans la liste. 
	@FXML
	Button filterButton;

	Contact currentContact;
	
	private boolean newContact;
	
	//Cette métgode permet de définir l'action du bouton ajouter, 
	//On le créer, l'ajoute à la table et update la liste afficher pour qu'il apparait. 
	@FXML
	public void handleAddButton() {
		System.out.println("Add bouton");
		Contact contact = new Contact();
		ContactService.addContact(contact);
		this.contactTable.getSelectionModel().select(contact);  
		this.newContact = true;
		this.handleUpdateButton();
	}
	
	//Cette méthode permet de définir l'action du bouton update
	@FXML
	public void handleUpdateButton() {
		System.out.println("Update bouton");
		this.updatingContact(true);
		this.disableButton(false, true);
	}
	
	//Cette méthode permet de définir l'action du bouton supprimer, il affiche un message pour demander si on est sur de vouloir 
	//supprimer le contact. 
	@FXML
	public void handleDeleteButton() {
		System.out.println("Delete bouton");
		Contact contact = this.contactTable.getSelectionModel().getSelectedItem();
	    if (contact != null) {
	    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.initOwner(StageService.getPrimaryStage());
			alert.setTitle("Alert :");
			alert.setHeaderText("Are you sure you want to delete this contact ?");
			Optional<ButtonType> option = alert.showAndWait();
			if (option.get() == ButtonType.OK) {
				ContactService.deleteContact(contact);
		        this.contactTable.getSelectionModel().clearSelection();
		        this.disableButton(true, false);
		        this.contactTable.refresh();
	    	}
	    }
	}
	
	//Cette méthode permet de définir l'action du bouton sauvegarder
	@FXML
	public void handleSaveButton() {
		System.out.println("Save bouton");
		this.updatingContact(false);
		this.disableButton(false, false);
		this.contactTable.setDisable(false);
		this.saveContactDetails();
		this.contactTable.refresh();
	}
	
	
	public void saveContactDetails() {
		// TODO Auto-generated method stub
		
	}

	public void updatingContact(boolean b) {
		// TODO Auto-generated method stub
		
	}

	//Permet de désactiver les boutons quand on ne doit pas ou peut pas les utiliser. 
	public void disableButton(boolean none, boolean disable) {
		this.addButton.setDisable(disable);
		this.updateButton.setDisable(none ? !disable : disable);
		this.deleteButton.setDisable(none ? !disable : disable);
		this.filterButton.setDisable(disable);
		this.saveButton.setDisable(!disable);
		this.cancelButton.setDisable(!disable);
	}
	

	public void handleFilterButton() {
		
	}
	
	public void changePhoto() {
		
	}
}

