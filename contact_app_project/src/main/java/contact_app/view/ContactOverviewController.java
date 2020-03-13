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
	
	// partie pour taper le nom de la personne 
	@FXML
	TextField filterTextField;
	
	// partie pour valider et faire le filtrage dans la liste. 
	@FXML
	Button filterButton;

	Contact currentContact;
	
	private boolean newContact;
	private boolean onUpdateMode = false;
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		contactTable.setItems(ContactService.getContacts());
		nicknameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("nickname"));
		nameColumn.setCellValueFactory(contact -> new ReadOnlyStringWrapper(contact.getValue().getFirstname() + " " + contact.getValue().getLastname()));
		isEdibleTextField(false);
	}
	
	/**
	 * Cette méthode permet de définir l'action du bouton ajouter, 
	 * On le créer, l'ajoute à la table et update la liste afficher pour qu'il apparait. 
	 */
	@FXML
	public void handleAddButton() {
		this.newContact = true;
		onUpdateMode = true;
		this.handleUpdateButton();
	}
	
	/**
	 * Cette méthode permet de définir l'action du bouton update
	 */
	@FXML
	public void handleUpdateButton() {
		onUpdateMode = true;
		this.updatingContact(true);
		this.disableGUI(false, true);
	}
		
	/**
	 * Cette méthode permet de définir l'action du bouton supprimer, il affiche un message pour demander si on est sur de vouloir 
	 * supprimer le contact.
	 */
	@FXML
	public void handleDeleteButton() {
		Contact contact = this.contactTable.getSelectionModel().getSelectedItem();
	    if (contact != null) {
	    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.initOwner(StageService.getPrimaryStage());
			alert.setTitle("Alert :");
			alert.setHeaderText("Are you sure you want to delete this contact ?");
			Optional<ButtonType> option = alert.showAndWait();
			if (option.get() == ButtonType.OK) {
				AddressService.deleteAddress(contact.getAddress());
				ContactService.deleteContact(contact);
		        this.contactTable.getSelectionModel().clearSelection();
		        this.disableGUI(true, false);
		        onUpdateMode = false;
		        refreshTable();
	    	}
	    }
	}
	
	
	/**
	 *  Cette méthode va enclencher la sauvegarde du contact.
	 */
	@FXML
	public void handleSaveButton() {
		this.updatingContact(false);
		this.contactTable.setDisable(false);
		this.saveContactDetails();
		refreshTable();
		onUpdateMode = false;
	}
	
	
	private void updatingContact(boolean b) {}

	/** Cette méthode permet de définir l'action lorsque une cellule est selectionnée
	 * @param event
	 */
	@FXML
	public void clickItem(MouseEvent event)
	{
		if(!onUpdateMode && contactTable.getSelectionModel().getSelectedItem() != null)
		{
			this.lastNameField.setText(contactTable.getSelectionModel().getSelectedItem().getLastname());
			this.firstNameField.setText(contactTable.getSelectionModel().getSelectedItem().getFirstname());
			this.nicknameField.setText(contactTable.getSelectionModel().getSelectedItem().getNickname());
			this.phoneNumberField.setText(contactTable.getSelectionModel().getSelectedItem().getPhone_number());
			this.streetAddressField.setText(contactTable.getSelectionModel().getSelectedItem().getAddress().getRue());
			this.numberField.setText(contactTable.getSelectionModel().getSelectedItem().getAddress().getNumero());
			this.cityAddressField.setText(contactTable.getSelectionModel().getSelectedItem().getAddress().getVille());
			this.countryAddressField.setText(contactTable.getSelectionModel().getSelectedItem().getAddress().getPays());
			this.emailAddressField.setText(contactTable.getSelectionModel().getSelectedItem().getEmail_address());
			this.birthDatePicker.setValue(contactTable.getSelectionModel().getSelectedItem().getBirth_date());

			this.updateButton.setDisable(false);
			this.deleteButton.setDisable(false);
		}
	}
	
	/**
	 * Cette méthode permet de sauvegarder les informations des contacts.
	 */
	public void saveContactDetails() {
		if(this.newContact)
		{
			Address address = new Address(numberField.getText(),
					streetAddressField.getText(), cityAddressField.getText(),
					countryAddressField.getText());
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
					countryAddressField.getText());
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

	
	/**
	 * Cette méthode permet de rafraîchir la table de contacts. 
	 */
	protected void refreshTable()
	{
		AddressService.getAddresses();
		ContactService.getContacts();
        this.contactTable.refresh();
	}
	
	/**
	 * @param none
	 * @param disable
	 */
	protected void disableGUI(boolean none, boolean disable) {
		disableButton(none, disable);
		isEdibleTextField(disable);
	}
	
	/** Permet de désactiver les boutons quand on ne doit pas ou peut pas les utiliser. 
	 * @param none
	 * @param disable
	 */
	protected void disableButton(boolean none, boolean disable) {
		this.addButton.setDisable(disable);
		this.updateButton.setDisable(none ? !disable : disable);
		this.deleteButton.setDisable(none ? !disable : disable);
		this.filterButton.setDisable(disable);
		this.saveButton.setDisable(!disable);
	}
	

	/** permet de voir si le texte est éditable (cad si on est en train de modifier un contact par exemple)
	 * @param isEditable
	 */
	protected void isEdibleTextField(boolean isEditable) {
		this.lastNameField.setEditable(isEditable);
		this.firstNameField.setEditable(isEditable);
		this.nicknameField.setEditable(isEditable);
		this.phoneNumberField.setEditable(isEditable);
		this.streetAddressField.setEditable(isEditable);
		this.numberField.setEditable(isEditable);
		this.cityAddressField.setEditable(isEditable);
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
			this.countryAddressField.clear();
			this.emailAddressField.clear();
			this.birthDatePicker.getEditor().clear();
		}
	}
	
	/**
	 * Cette méthode permet de faire recherche par nom.
	 */
	public void handleFilterButton() {
		ContactService.getContactsConteningName(filterTextField.getText());
        this.contactTable.refresh();
	}
}

