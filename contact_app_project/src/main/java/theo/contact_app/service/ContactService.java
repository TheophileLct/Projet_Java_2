package theo.contact_app.service;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import theo.contact_app.model.Address;
import theo.contact_app.model.Contact;

public class ContactService {
	private ObservableList<Contact> contacts;
	private ObservableList<Contact> contactsDataBase; //Quand on aura fait la bdd 
	
	private ContactService() {
		contactsDataBase = FXCollections.observableArrayList();
		contacts=FXCollections.observableArrayList();
		contacts.add(new Contact(1, "lastname1", "firstname1", "nickname1", "0665487562", new Address("", "", "address1", "", "", -1), "email_address_1", LocalDate.now()));
	}
	
	private static class ContactServiceHolder {
		private static ContactService INSTANCE;
	}
	
	public static ObservableList<Contact> getContacts() {
		return ContactServiceHolder.INSTANCE.contacts;
	}
	
	public static void addContact(Contact contact) {
		ContactServiceHolder.INSTANCE.contactsDataBase.add(contact);
		ContactServiceHolder.INSTANCE.contacts.add(contact);
	}
	
	public static void deleteContact(Contact contact) {
		ContactServiceHolder.INSTANCE.contactsDataBase.remove(contact);
		ContactServiceHolder.INSTANCE.contacts.remove(contact);
	}
	
}
