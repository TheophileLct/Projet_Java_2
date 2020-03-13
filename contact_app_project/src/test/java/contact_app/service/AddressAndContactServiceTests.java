package contact_app.service;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.Suite;
import org.junit.experimental.runners.Enclosed;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import contact_app.model.Address;
import contact_app.model.Contact;
import contact_app.service.ContactService;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import junit.framework.TestCase;

@RunWith(Enclosed.class)
public class AddressAndContactServiceTests {
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
	private static void waitForRunLater() throws InterruptedException {
	    Semaphore semaphore = new Semaphore(0);
	    Platform.runLater(() -> semaphore.release());
	    semaphore.acquire();
	}
	static JFXPanel fxPanel;
	@BeforeClass
	static public void initJavaFX()
	{
		fxPanel = new JFXPanel();
	}
	public static class ClearDatabaseTests
	{
		static JFXPanel fxPanel;
		@BeforeClass
		static public void initJavaFX()
		{
			fxPanel = new JFXPanel();
		}
		@Test
		public void shouldClearContactsDatabase() throws Exception { 
			
			//Method tested
			ContactService.clearDatabase();
			
			waitForRunLater();
			List<Contact> contactList = new ArrayList<>();
			try (Connection connection = prepareDataSource().getConnection()) {
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
			
			if(contactList == null)
				fail("The contact list is a null object");
			if(contactList.size() > 0)
				fail("There are still objects after a clear");
		}

		@Test
		public void shouldAlsoClearContactsDatabase() throws Exception { 
			LocalDate d = LocalDate.now();
			Address a = new Address("17b", "rue des cochons", "St Michiou", "France");
			Contact c = new Contact("lastname", "firstname", "nickname", "0606060606", a, "email@address.com", d);
			
			try (Connection connection = prepareDataSource().getConnection()) {
				
				 String sqlQuery = "insert into person(lastname, firstname, nickname, phone_number, idaddress, email_address, birth_date) " + "VALUES(?,?,?,?,?,?,?)";
				 try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
					 statement.setString(1, c.getLastname());
					 statement.setString(2, c.getFirstname());
					 statement.setString(3, c.getNickname());
					 statement.setString(4, c.getPhone_number());
					 statement.setInt(5, c.getIdAddress());
					 statement.setString(6, c.getEmail_address());
					 statement.setDate(7, Date.valueOf(c.getBirth_date()));
					 statement.executeUpdate();
					 statement.getGeneratedKeys();
				 }
			} catch (SQLException e) {
				 throw e;
			}
			
			//Method tested
			ContactService.clearDatabase();
			
			waitForRunLater();
			List<Contact> contactList = new ArrayList<>();
			try (Connection connection = prepareDataSource().getConnection()) {
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
				 throw e;
			 }
			
			if(contactList == null)
				fail("The contact list is a null object");
			if(contactList.size() > 0)
				fail("There are still objects after a clear");
		}

		@Test
		public void shouldClearAddressesDatabase() throws Exception { 
			
			//Method tested
			AddressService.clearDatabase();
			
			waitForRunLater();
			List<Address> AddressList = new ArrayList<>();
			try (Connection connection = prepareDataSource().getConnection()) {
				try (Statement statement = connection.createStatement()) {
					try (ResultSet results = statement.executeQuery("select * from address")) {
						while (results.next()) {
							Address address = new Address(
								 results.getString("number"),
								 results.getString("street"),
								 results.getString("town"),
								 results.getString("pays"));
							address.setIdAddress(results.getInt("idaddress"));
							AddressList.add(address);
						}
					}
				}
			 } catch (SQLException e) {
				 throw e;
			 }
			
			if(AddressList == null)
				fail("The address list is a null object");
			if(AddressList.size() > 0)
				fail("There are still objects after a clear");
		}

		@Test
		public void shouldAlsoClearAddressesDatabase() throws Exception { 
			Address a = new Address("17b", "rue des cochons", "St Michiou", "France");
			
			try (Connection connection = prepareDataSource().getConnection()) {
				
				 String sqlQuery = "insert into address(number, street, town, pays) " + "VALUES(?,?,?,?)";
				 try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
					 statement.setString(1, a.getNumero());
					 statement.setString(2, a.getRue());
					 statement.setString(3, a.getVille());
					 statement.setString(4, a.getPays());
					 statement.executeUpdate();
					 statement.getGeneratedKeys();
				 }
			} catch (SQLException e) {
				throw e;
			}
			
			//Method tested
			AddressService.clearDatabase();
			
			waitForRunLater();
			List<Address> AddressList = new ArrayList<>();
			try (Connection connection = prepareDataSource().getConnection()) {
				try (Statement statement = connection.createStatement()) {
					try (ResultSet results = statement.executeQuery("select * from address")) {
						while (results.next()) {
							Address address = new Address(
								 results.getString("number"),
								 results.getString("street"),
								 results.getString("town"),
								 results.getString("region"));
							address.setIdAddress(results.getInt("idaddress"));
							AddressList.add(address);
						}
					}
				}
			 } catch (SQLException e) {
				 throw e;
			 }
			
			if(AddressList == null)
				fail("The address list is a null object");
			if(AddressList.size() > 0)
				fail("There are still objects after a clear");
		}

	}
	public static class AddressServiceTests
	{
		static JFXPanel fxPanel;
		@BeforeClass
		static public void initJavaFX()
		{
			fxPanel = new JFXPanel();
		}
		@Before
		public void clearDataBase() throws SQLException
		{
			AddressService.clearDatabase();
			ContactService.clearDatabase();
		}
		@Test
		public void shouldAddOneAddress() throws Exception { 
			LocalDate d = LocalDate.now();
			Address a = new Address("17b", "rue des cochons", "St Michiou", "France");
			
			//Method tested
			AddressService.addAddress(a);
			
			waitForRunLater();
			List<Address> AddressList = new ArrayList<>();
			try (Connection connection = prepareDataSource().getConnection()) {
				try (Statement statement = connection.createStatement()) {
					try (ResultSet results = statement.executeQuery("select * from address")) {
						while (results.next()) {
							Address address = new Address(
								 results.getString("number"),
								 results.getString("street"),
								 results.getString("town"),
								 results.getString("pays"));
							address.setIdAddress(results.getInt("idaddress"));
							AddressList.add(address);
						}
					}
				}
			 } catch (SQLException e) {
				 throw e;
			 }
			
			if(AddressList == null)
				fail("The address list is a null object");
			if(AddressList.size() == 0)
				fail("The address list doesn't return an object");
			if(AddressList.size() > 1)
				fail("Multiple objects have been added instead of one");
			Assert.assertTrue("The address put in the database doesn't have the same informations as the address given.", a.equalsParameters(AddressList.get(0)));
		}
		@Test
		public void shouldReturnAnEmptyAddressList() throws Exception { 
			
			//Method tested
			ObservableList<Address> addressList = AddressService.getAddresses();
			
			waitForRunLater();
			if(addressList == null)
				fail("The contact list is a null object");
			if(addressList.size() > 0)
				fail("At least one object has been given added even if we do nothing");
		}
		@Test
		public void shouldReturnAListOfOneElement() throws Exception { 
			LocalDate d = LocalDate.now();
			Address a = new Address("17b", "rue des cochons", "St Michiou", "France");
			Contact c = new Contact("lastname", "firstname", "nickname", "0606060606", a, "email@address.com", d);

			AddressService.addAddress(a);
			ContactService.addContact(c);
			waitForRunLater();
			
			//Method tested
			ObservableList<Address> addressList = AddressService.getAddresses();

			waitForRunLater();
			if(addressList == null)
				fail("The address list is a null object");
			if(addressList.size() == 0)
				fail("The address list doesn't return an object");
			if(addressList.size() > 1)
				fail("Multiple objects have been added instead of one");
			Assert.assertTrue("The address put in the database doesn't have the same informations as the address given.", a.equalsParameters(addressList.get(0)));
		}
		@Test
		public void shouldUpdateTheAddressInDatabase() throws Exception {
			LocalDate d = LocalDate.now();
			Address a = new Address("17b", "rue des cochons", "St Michiou", "France");
			Contact c = new Contact("lastname", "firstname", "nickname", "0606060606", a, "email@address.com", d);

			AddressService.addAddress(a);
			ContactService.addContact(c);

			ObservableList<Address> addressList = AddressService.getAddresses();

			Address aprime = new Address("34", "new street", "Rome", "Italie");
			aprime.setIdAddress(addressList.get(0).getIdAddress());
			
			//Method tested
			AddressService.updateAddress(aprime, a.getIdAddress());

			
			Assert.assertFalse("The test do not show update since this is the same reference.", aprime == addressList.get(0));
			
			ObservableList<Address> addressListSecond = AddressService.getAddresses();
			
			if(addressListSecond == null)
				fail("The address list is a null object");
			if(addressListSecond.size() == 0)
				fail("The address list doesn't return an object");
			if(addressListSecond.size() > 1)
				fail("Multiple objects have been added instead of one");
			Assert.assertTrue("The address put in the database doesn't have the same informations as the address given.", aprime.equalsParameters(addressListSecond.get(0)));
		}
		@Test
		public void shouldDeleteTheAddressInDatabase() throws Exception {
			Address a = new Address("13b", "rue des poires", "St Michiou", "France");
			Address b = new Address("17b", "rue des cochons", "St Michiou", "France");

			AddressService.addAddress(a);
			AddressService.addAddress(b);
			waitForRunLater();
			
			ObservableList<Address> addressList = AddressService.getAddresses();
			List<Address> myAddressList = new ArrayList<>();
			for(Address addr : addressList)
				myAddressList.add(addr);
			//Method tested
			AddressService.deleteAddress(addressList.get(0));

			ObservableList<Address> addressListSecond = AddressService.getAddresses();
			
			if(myAddressList == null || addressListSecond == null)
				fail("One of the address list is a null object");
			if(myAddressList.size() != 2)
				fail("The addresses havn't been added to the database from the beginning");
			if(addressListSecond.size() > 2)
				fail("Some addresses have been haded instead of deleted");
			if(addressListSecond.size() == 2)
				fail("The address has not been deleted");
			if(addressListSecond.size() == 0)
				fail("Too many addresses has been deleted");
			Assert.assertTrue("The wrong address has beed deleted.", b.equalsParameters(addressListSecond.get(0)));
		}
	}
	
	
	public static class ContactServiceTests
	{
		static JFXPanel fxPanel;
		@BeforeClass
		static public void initJavaFX()
		{
			fxPanel = new JFXPanel();
		}
		@Before
		public void clearDataBase() throws SQLException
		{
			AddressService.clearDatabase();
			ContactService.clearDatabase();
		}
		@Test
		public void shouldAddOneContact() throws Exception { 
			LocalDate d = LocalDate.now();
			Address a = new Address("17b", "rue des cochons", "St Michiou", "France");
			Contact c = new Contact("lastname", "firstname", "nickname", "0606060606", a, "email@address.com", d);
			
			AddressService.addAddress(a);
			
			//Method tested
			ContactService.addContact(c);
			
			waitForRunLater();
			List<Contact> contactList = new ArrayList<>();
			try (Connection connection = prepareDataSource().getConnection()) {
				try (Statement statement = connection.createStatement()) {
					try (ResultSet results = statement.executeQuery("select * from person")) {
						while (results.next()) {
							Contact contact = new Contact(
								 results.getString("lastname"),
								 results.getString("firstname"),
								 results.getString("nickname"),
								 results.getString("phone_number"),
								 a,
								 results.getString("email_address"),
								 results.getDate("birth_date").toLocalDate());

							contact.getAddress().setIdAddress(Integer.parseInt(results.getString("idaddress")));
							contact.setIdperson(results.getInt("idperson"));
							contactList.add(contact);
						}
					}
				}
			 } catch (SQLException e) {
				 throw e;
			 }
			
			if(contactList == null)
				fail("The contact list is a null object");
			if(contactList.size() == 0)
				fail("The contact list doesn't return an object");
			if(contactList.size() > 1)
				fail("Multiple objects have been added instead of one");
			Assert.assertTrue("The contact put in the database doesn't have the same informations as the contact given.", c.equalsParameters(contactList.get(0)));
		}
		@Test
		public void shouldReturnAnEmptyContactList() throws Exception { 
			
			//Method tested
			ObservableList<Contact> contactList = ContactService.getContacts();
			
			waitForRunLater();
			if(contactList == null)
				fail("The contact list is a null object");
			if(contactList.size() > 0)
				fail("At least one object has been given added even if we do nothing");
		}
		@Test
		public void shouldReturnAListOfOneElement() throws Exception { 
			LocalDate d = LocalDate.now();
			Address a = new Address("17b", "rue des cochons", "St Michiou", "France");
			Contact c = new Contact("lastname", "firstname", "nickname", "0606060606", a, "email@address.com", d);

			AddressService.addAddress(a);
			ContactService.addContact(c);
			
			//Method tested
			ObservableList<Contact> contactList = ContactService.getContacts();

			waitForRunLater();
			if(contactList == null)
				fail("The contact list is a null object");
			if(contactList.size() == 0)
				fail("The contact list doesn't return an object");
			if(contactList.size() > 1)
				fail("Multiple objects have been added instead of one");
			Assert.assertTrue("The contact put in the database doesn't have the same informations as the contact given.", c.equalsParameters(contactList.get(0)));
		}
		@Test
		public void shouldUpdateTheContactInDatabase() throws Exception {
			LocalDate d = LocalDate.now();
			Address a = new Address("17b", "rue des cochons", "St Michiou", "France");
			Contact c = new Contact("lastname", "firstname", "nickname", "0606060606", a, "email@address.com", d);

			AddressService.addAddress(a);
			ContactService.addContact(c);

			ObservableList<Contact> contactList = ContactService.getContacts();

			Contact cprime = new Contact("New Last Name", "New First Name", "New Nickname", "New phone number", a, "New Email Address!", LocalDate.now());
			cprime.setIdperson(contactList.get(0).getIdAddress());
			
			//Method tested
			ContactService.updateContact(c, c.getIdperson());

			ObservableList<Contact> contactListSecond = ContactService.getContacts();
			
			Assert.assertFalse("The test do not show update since this is the same reference.", cprime == contactList.get(0));
			

			if(contactListSecond == null)
				fail("The contact list is a null object");
			if(contactListSecond.size() == 0)
				fail("The contact list doesn't return an object");
			if(contactListSecond.size() > 1)
				fail("Multiple objects have been added instead of one");
			Assert.assertTrue("The contact put in the database doesn't have the same informations as the contact given.", c.equalsParameters(contactListSecond.get(0)));
			
		}
		@Test
		public void shouldDeleteTheContactInDatabase() throws Exception {
			LocalDate d = LocalDate.now();
			Address a = new Address("17b", "rue des cochons", "St Michiou", "France");
			Contact c = new Contact("lastname", "firstname", "nickname", "0606060606", a, "email@address.com", d);
			LocalDate dprime = LocalDate.now();
			Address aprime = new Address("17bPrime", "rue des cochonsPrime", "St MichiouPrime", "FrancePrime");
			Contact cprime = new Contact("lastnamePrime", "firstnamePrime", "nicknamePrime", "0706060606", aprime, "emailPrime@address.com", dprime);

			AddressService.addAddress(a);
			ContactService.addContact(c);
			AddressService.addAddress(aprime);
			ContactService.addContact(cprime);
			waitForRunLater();

			ObservableList<Contact> contactList = ContactService.getContacts();
			List<Contact> myContactList = new ArrayList<>();
			for(Contact cont : contactList)
				myContactList.add(cont);

			//Method tested
			ContactService.deleteContact(contactList.get(0));

			ObservableList<Contact> contactListSecond = ContactService.getContacts();

			if(myContactList == null || contactListSecond == null)
				fail("One of the contact list is a null object");
			if(myContactList.size() != 2)
				fail("The contacts havn't been added to the database from the beginning");
			if(contactListSecond.size() > 2)
				fail("Some contacts have been haded instead of deleted");
			if(contactListSecond.size() == 2)
				fail("The contact has not been deleted");
			if(contactListSecond.size() == 0)
				fail("Too many contacts has been deleted");
			Assert.assertTrue("The wrong contact has beed deleted.", cprime.equalsParameters(contactListSecond.get(0)));
		}
	}
}
