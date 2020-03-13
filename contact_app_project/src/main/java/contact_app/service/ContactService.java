package contact_app.service;

import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import contact_app.model.Address;
import contact_app.model.Contact;
import contact_app.service.AddressService.AddressServiceHolder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class ContactService {
	private ObservableList<Contact> contacts;
	private ObservableList<Contact> contactsDataBase;
	
	protected ContactService() {
		contactsDataBase = FXCollections.observableArrayList();
		contactsDataBase.addAll(getAllContactsFromDatabase());
		contactsDataBase.addListener(new ContactsListener());
		contacts = FXCollections.observableArrayList();
		for(Contact c : contactsDataBase)
			contacts.add(c);
	}
	
	protected static class ContactServiceHolder {
		private static ContactService INSTANCE = new ContactService();
	}
	
	/**
	 * Copie la liste de la database pour l'afficher
	 * @param list
	 */
	protected static void CopyDataBaseToShow(List<Contact> list)
	{
		ContactServiceHolder.INSTANCE.contacts.clear();
		for(int i = 0; i < list.size(); i++)
		{
			ContactServiceHolder.INSTANCE.contacts.add(list.get(i));
			Address a = AddressService.findAddressById(list.get(i).getIdAddress());
			ContactServiceHolder.INSTANCE.contacts.get(i).setAddress(a);
		}
	}
	
	/**
	 * Permet d'obtenir les contacts de la bdd
	 * @return
	 */
	public static ObservableList<Contact> getContacts() {
		CopyDataBaseToShow(ContactServiceHolder.INSTANCE.contactsDataBase);
		return ContactServiceHolder.INSTANCE.contacts;
	}
	
	/**
	 * Permet de filtrer les contacts
	 * @param name
	 * @return
	 */
	public static ObservableList<Contact> getContactsConteningName(String name) {
		CopyDataBaseToShow(getContactsFromDatabaseConteningName(name));
		return ContactServiceHolder.INSTANCE.contacts;
	}
	
	/**
	 * Permet d'ajouter un contact
	 * @param contact
	 */
	public static void addContact(Contact contact) {
		System.out.println("add contact");
		ContactServiceHolder.INSTANCE.contactsDataBase.add(contact);
		CopyDataBaseToShow(ContactServiceHolder.INSTANCE.contactsDataBase);
	}
	
	/**
	 * Permet de supprimer un contact
	 * @param contact
	 */
	public static void deleteContact(Contact contact) {
		ContactServiceHolder.INSTANCE.contactsDataBase.remove(contact);
		CopyDataBaseToShow(ContactServiceHolder.INSTANCE.contactsDataBase);
	}
	
	/**
	 * Permet de mettre à jour les informations d'un contact 
	 * @param newInfo
	 * @param id
	 */
	public static void updateContact(Contact newInfo, int id) {
		Contact contact = findById(id);
		if(contact == null)
			return;
		if(contact.getFirstname() != newInfo.getFirstname())
			contact.setFirstname(newInfo.getFirstname());
		if(contact.getLastname() != newInfo.getLastname())
			contact.setLastname(newInfo.getLastname());
		if(contact.getNickname() != newInfo.getNickname())
			contact.setNickname(newInfo.getNickname());
		if(contact.getPhone_number() != newInfo.getPhone_number())
			contact.setPhone_number(newInfo.getPhone_number());
		if(contact.getBirth_date() != newInfo.getBirth_date())
			contact.setBirth_date(newInfo.getBirth_date());
		if(contact.getEmail_address() != newInfo.getEmail_address())
			contact.setEmail_address(newInfo.getEmail_address());
		updateContactToDatabase(contact);
		CopyDataBaseToShow(ContactServiceHolder.INSTANCE.contactsDataBase);
	}
	
	/**
	 * Permet d'obtenir un contact par son ID 
	 * @param idperson
	 * @return
	 */
	private static Contact findById(int idperson) {
		for(Contact c : ContactServiceHolder.INSTANCE.contactsDataBase)
			if(c.getIdperson() == idperson)
				return c;
		return null;
	}
	
	/**
	 * Permet de se connecter et d'obtenir les infos de la bdd
	 * @return
	 */
	private static MysqlDataSource prepareDataSource()
	{
		MysqlDataSource mysqlDataSource = new MysqlDataSource();
		mysqlDataSource.setServerName("localhost");
		mysqlDataSource.setPort(3306);
		mysqlDataSource.setDatabaseName("java_project");
		mysqlDataSource.setUser("root");
		mysqlDataSource.setPassword("");
		return mysqlDataSource;
	}
	
	/**
	 * Permet d'obtenir dans une liste tout les contacts de la bdd. 
	 * @return
	 */
	private static List<Contact> getAllContactsFromDatabase()
	{
		List<Contact> contactList = new ArrayList<>();
		try (Connection connection = ContactService.prepareDataSource().getConnection()) {
			try (Statement statement = connection.createStatement()) {
				try (ResultSet results = statement.executeQuery("select * from person")) {
					while (results.next()) {
						Contact contact = new Contact(
							 results.getString("lastname"),
							 results.getString("firstname"),
							 results.getString("nickname"),
							 results.getString("phone_number"),
							 new Address(),
							 results.getString("email_address"),
							 results.getDate("birth_date").toLocalDate());

						contact.getAddress().setIdAddress(Integer.parseInt(results.getString("idaddress")));
						contact.setIdperson(results.getInt("idperson"));
						contactList.add(contact);
					}
				}
			}
		 } catch (SQLException e) {
		 // Manage Exception
		 e.printStackTrace();
		 }
		 return contactList;
	}
	
	/**
	 * Vérifie si la liste de contact est nulle et la vide si non. 
	 */
	public static void clearContacts() {
		if(ContactServiceHolder.INSTANCE.contacts != null) ContactServiceHolder.INSTANCE.contacts.clear();
	}
	
	/**
	 * Permet de filtrer la liste de contact en affichant celle qui contient ce que l'on souhaite
	 * @param name
	 * @return
	 */
	private static List<Contact> getContactsFromDatabaseConteningName(String name)
	{
		List<Contact> contactList = new ArrayList<>();
		try (Connection connection = ContactService.prepareDataSource().getConnection()) {
			String sqlQuery = "select * from person where lastname like ? or firstname like ? or nickname like ?";
			try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, "%" + name + "%");
				statement.setString(2, "%" + name + "%");
				statement.setString(3, "%" + name + "%");
				try (ResultSet results = statement.executeQuery()) {
					while (results.next()) {
						Contact contact = new Contact(
							 results.getString("lastname"),
							 results.getString("firstname"),
							 results.getString("nickname"),
							 results.getString("phone_number"),
							 new Address(),
							 results.getString("email_address"),
							 results.getDate("birth_date").toLocalDate());

						contact.setIdperson(results.getInt("idperson"));
						contact.getAddress().setIdAddress(Integer.parseInt(results.getString("idaddress")));
						contactList.add(contact);
					}
				}
			}
		 } catch (SQLException e) {
		 // Manage Exception
		 e.printStackTrace();
		 }
		 return contactList;
	}
	
	/**
	 * Ajoute un contact à la bdd
	 * @param contact
	 */
	private static void addContactToDatabase(Contact contact)
	{
		try (Connection connection = ContactService.prepareDataSource().getConnection()) {
			
			 String sqlQuery = "insert into person(lastname, firstname, nickname, phone_number, idaddress, email_address, birth_date) " + "VALUES(?,?,?,?,?,?,?)";
			 try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				 statement.setString(1, contact.getLastname());
				 statement.setString(2, contact.getFirstname());
				 statement.setString(3, contact.getNickname());
				 statement.setString(4, contact.getPhone_number());
				 statement.setInt(5, contact.getIdAddress());
				 statement.setString(6, contact.getEmail_address());
				 statement.setDate(7, Date.valueOf(contact.getBirth_date()));
				 statement.executeUpdate();
				 ResultSet ids = statement.getGeneratedKeys();
				 
				 if (ids.next()) {
					 contact.setIdperson(ids.getInt(1));
					 return;
				 }
			 }
		} catch (SQLException e) {
			 e.printStackTrace();
		}
		return;
	}
	
	/**
	 * Permet de mettre à jour les données d'un contact dans la bdd
	 * @param contact
	 */
	private static void updateContactToDatabase(Contact contact)
	{
		try (Connection connection = ContactService.prepareDataSource().getConnection()) {
			
			 String sqlQuery = "update person set lastname=?, firstname=?, nickname=?, phone_number=?, idaddress=?, email_address=?, birth_date=? where idperson=?";
			 try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				 statement.setString(1, contact.getLastname());
				 statement.setString(2, contact.getFirstname());
				 statement.setString(3, contact.getNickname());
				 statement.setString(4, contact.getPhone_number());
				 statement.setInt(5, contact.getIdAddress());
				 statement.setString(6, contact.getEmail_address());
				 statement.setDate(7, Date.valueOf(contact.getBirth_date()));
				 statement.setInt(8, contact.getIdperson());
				 statement.executeUpdate();
				 ResultSet ids = statement.getGeneratedKeys();
			 }
		} catch (SQLException e) {
			 e.printStackTrace();
		}
		return;
	}
	
	/**
	 * Permet de supprimer un contact par son id
	 * @param idperson
	 */
	public void delete(Integer idperson) {
		 try (Connection connection = ContactService.prepareDataSource().getConnection()) {
			 try (PreparedStatement statement = connection.prepareStatement("delete from person where idperson=?")) {
				 statement.setInt(1, idperson);
				 statement.executeUpdate();
			 }
		 }catch (SQLException e) {
			 e.printStackTrace();
		 }
	}
	public static void clearDatabase()
	{
		ContactServiceHolder.INSTANCE.contacts.clear();
		ContactServiceHolder.INSTANCE.contactsDataBase.clear();
		try (Connection connection = ContactService.prepareDataSource().getConnection()) {
			 try (PreparedStatement statement = connection.prepareStatement("delete from person")) {
				 statement.executeUpdate();
			 }
		 }catch (SQLException e) {
			 e.printStackTrace();
		 }
	}
	
	/**
	 * Permet de réagir en fonction des actions de l'utilisateur.
	 *
	 */
	class ContactsListener implements ListChangeListener<Contact> {
		@Override
		public void onChanged(Change<? extends Contact> changedContact) {
			Platform.runLater(() -> {
				changedContact.reset();
				while(changedContact.next())
				{
					if(changedContact.wasPermutated())
					{

					}
					else if(changedContact.wasUpdated())
					{
						for(int i = changedContact.getFrom(); i < changedContact.getTo(); i++)
							ContactService.updateContactToDatabase(changedContact.getList().get(i));
					}
					else 
					{
						 for (Contact contactToRemove : changedContact.getRemoved()) {
							delete(contactToRemove.getIdperson());
	                     }
	                     for (Contact contactToAdd : changedContact.getAddedSubList()) {
	                    	 ContactService.addContactToDatabase(contactToAdd);
	                     }
					}
				}
			});
		}
	}
}
