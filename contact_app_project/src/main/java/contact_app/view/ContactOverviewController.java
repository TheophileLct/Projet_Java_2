package contact_app.view;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import contact_app.model.Address;
import contact_app.model.Contact;
import contact_app.service.AddressService;
import contact_app.service.ContactService;
import contact_app.service.StageService;
import contact_app.service.ViewService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class ContactOverviewController implements Initializable {
	
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
	private boolean onUpdateMode = false;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		contactTable.setItems(ContactService.getContacts());
		nicknameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("nickname"));
		nameColumn.setCellValueFactory(contact -> new ReadOnlyStringWrapper(contact.getValue().getFirstname() + " " + contact.getValue().getLastname()));
		isEdibleTextField(false);
	}
	
	//Cette méthode permet de définir l'action du bouton ajouter, 
	//On le créer, l'ajoute à la table et update la liste afficher pour qu'il apparait. 
	@FXML
	public void handleAddButton() {
		System.out.println("Add bouton");
		this.newContact = true;
		onUpdateMode = true;
		this.handleUpdateButton();
	}
	
	//Cette méthode permet de définir l'action du bouton update
	@FXML
	public void handleUpdateButton() {
		System.out.println("Update bouton");
		onUpdateMode = true;
		this.updatingContact(true);
		this.disableGUI(false, true);
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
		        this.disableGUI(true, false);
		        onUpdateMode = false;
		        refreshTable();
	    	}
	    }
	}
	
	//Cette méthode permet de définir l'action du bouton sauvegarder
	@FXML
	public void handleSaveButton() {
		System.out.println("Save bouton");
		this.updatingContact(false);
		this.contactTable.setDisable(false);
		this.saveContactDetails();
		this.disableGUI(false, false);
		refreshTable();
		onUpdateMode = false;
	}
	//Cette méthode permet de définir l'action lorsque une cellule est selectionnée
	@FXML
	public void clickItem(MouseEvent event)
	{
		if(!onUpdateMode)
		{
			this.lastNameField.setText(contactTable.getSelectionModel().getSelectedItem().getLastname());
			this.firstNameField.setText(contactTable.getSelectionModel().getSelectedItem().getFirstname());
			this.nicknameField.setText(contactTable.getSelectionModel().getSelectedItem().getNickname());
			this.phoneNumberField.setText(contactTable.getSelectionModel().getSelectedItem().getPhone_number());
			this.streetAddressField.setText(contactTable.getSelectionModel().getSelectedItem().getAddress().getRue());
			this.numberField.setText(contactTable.getSelectionModel().getSelectedItem().getAddress().getNumero());
			this.cityAddressField.setText(contactTable.getSelectionModel().getSelectedItem().getAddress().getVille());
			if(this.regionAddressField != null)
				this.regionAddressField.setText(contactTable.getSelectionModel().getSelectedItem().getAddress().getRégion());
			this.countryAddressField.setText(contactTable.getSelectionModel().getSelectedItem().getAddress().getPays());
			this.emailAddressField.setText(contactTable.getSelectionModel().getSelectedItem().getEmail_address());
			this.birthDatePicker.setValue(contactTable.getSelectionModel().getSelectedItem().getBirth_date());

			this.updateButton.setDisable(false);
			this.deleteButton.setDisable(false);
		}
	}
	
	public void saveContactDetails() {
		if(this.newContact)
		{
			Address address = new Address(numberField.getText(),
					streetAddressField.getText(), cityAddressField.getText(),
					countryAddressField.getText(), "region",
					44444);
			AddressService.addAddress(address);
			
			Contact contact = new Contact(lastNameField.getText(),
					firstNameField.getText(), nicknameField.getText(),
					phoneNumberField.getText(), address,
					emailAddressField.getText(), birthDatePicker.getValue());
			ContactService.addContact(contact);

			this.contactTable.getSelectionModel().select(contact);  
		}
		else
		{
			Address a = new Address(contactTable.getSelectionModel().getSelectedItem().getAddress());
			Address newAddressInfo = new Address(numberField.getText(),
					streetAddressField.getText(), cityAddressField.getText(),
					countryAddressField.getText(), "region",
					44444);
			Contact c = new Contact(contactTable.getSelectionModel().getSelectedItem());
			Contact newContactInfo = new Contact(lastNameField.getText(),
					firstNameField.getText(), nicknameField.getText(),
					phoneNumberField.getText(), null,
					emailAddressField.getText(), birthDatePicker.getValue());
			AddressService.updateAddress(newAddressInfo, a.getIdAddress());
			ContactService.updateContact(newContactInfo, c.getIdperson());
		}
		this.newContact = false;
	}

	protected void refreshTable()
	{
		AddressService.getAddresses();
		ContactService.getContacts();
        this.contactTable.refresh();
	}
	
	public void updatingContact(boolean b) {
		
		
	}

	protected void disableGUI(boolean none, boolean disable) {
		disableButton(none, disable);
		isEdibleTextField(disable);
	}
	
	//Permet de désactiver les boutons quand on ne doit pas ou peut pas les utiliser. 
	protected void disableButton(boolean none, boolean disable) {
		this.addButton.setDisable(disable);
		this.updateButton.setDisable(none ? !disable : disable);
		this.deleteButton.setDisable(none ? !disable : disable);
		this.filterButton.setDisable(disable);
		this.saveButton.setDisable(!disable);
		this.cancelButton.setDisable(!disable);
	}
	protected void isEdibleTextField(boolean isEditable) {
		this.lastNameField.setEditable(isEditable);
		this.firstNameField.setEditable(isEditable);
		this.nicknameField.setEditable(isEditable);
		this.phoneNumberField.setEditable(isEditable);
		this.streetAddressField.setEditable(isEditable);
		this.numberField.setEditable(isEditable);
		this.cityAddressField.setEditable(isEditable);
		if(this.regionAddressField != null)
			this.regionAddressField.setEditable(isEditable);
		this.countryAddressField.setEditable(isEditable);
		this.emailAddressField.setEditable(isEditable);
		this.birthDatePicker.setEditable(isEditable);
		if(this.newContact)
		{
			this.lastNameField.clear();
			this.firstNameField.clear();
			this.nicknameField.clear();
			this.phoneNumberField.clear();
			this.streetAddressField.clear();
			this.numberField.clear();
			this.cityAddressField.clear();
			if(this.regionAddressField != null)
				this.regionAddressField.clear();
			this.countryAddressField.clear();
			this.emailAddressField.clear();
			this.birthDatePicker.getEditor().clear();
		}
	}
	

	public void handleFilterButton() {
		ContactService.getContactsConteningName(filterTextField.getText());
        this.contactTable.refresh();
	}
	
	public void changePhoto() {
		
	}
}

