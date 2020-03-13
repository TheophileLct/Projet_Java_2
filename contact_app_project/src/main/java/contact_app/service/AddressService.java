package contact_app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import contact_app.model.Address;
import contact_app.model.Contact;
import contact_app.service.ContactService.ContactServiceHolder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;

public class AddressService {
	private ObservableList<Address> Addresses;
	private ObservableList<Address> AddressesDataBase;

	protected AddressService() {
		AddressesDataBase = FXCollections.observableArrayList();
		AddressesDataBase.addAll(getAllAddressesFromDatabase());
		AddressesDataBase.addListener(new AddressesListener());
		Addresses = FXCollections.observableArrayList();
		for(Address a : AddressesDataBase)
			Addresses.add(a);
	}

	protected static class AddressServiceHolder {
		private static AddressService INSTANCE = new AddressService();
	}
	
	/**
	 * Copie la liste de la database pour l'afficher dans notre liste sur l'application
	 * @param list
	 */
	protected static void CopyDataBaseToShow(List<Address> list)
	{
		AddressServiceHolder.INSTANCE.Addresses.clear();
		for(Address a : list)
			AddressServiceHolder.INSTANCE.Addresses.add(a);
	}
	
	/**
	 * Permet d'obtenir une adresse de la liste
	 * @return
	 */
	public static ObservableList<Address> getAddresses() {
		CopyDataBaseToShow(AddressServiceHolder.INSTANCE.AddressesDataBase);
		return AddressServiceHolder.INSTANCE.Addresses;
	}
	
	/**
	 * Permet d'ajouter une adresse dans la bdd
	 * @param address
	 */
	public static void addAddress(Address address) {
		AddressServiceHolder.INSTANCE.AddressesDataBase.add(address);
		CopyDataBaseToShow(AddressServiceHolder.INSTANCE.AddressesDataBase);
	}
	
	/**
	 * Permet de supprimer une adresse dans la bdd
	 * @param address
	 */
	public static void deleteAddress(Address address) {
		AddressServiceHolder.INSTANCE.AddressesDataBase.remove(address);
		CopyDataBaseToShow(AddressServiceHolder.INSTANCE.AddressesDataBase);
	}
	
	/**
	 * Permet de mettre à jour les informations de l'adresse. 
	 * @param newInfo
	 * @param id
	 */
	public static void updateAddress(Address newInfo, int id) {
		Address address = findById(id);
		if(address == null)
			return;
		if(address.getNumero() != newInfo.getNumero())
			address.setNumero(newInfo.getNumero());
		if(address.getRue() != newInfo.getRue())
			address.setRue(newInfo.getRue());
		if(address.getVille() != newInfo.getVille())
			address.setVille(newInfo.getVille());
		if(address.getRégion() != newInfo.getRégion())
			address.setRégion(newInfo.getRégion());
		if(address.getCodePostal() != newInfo.getCodePostal())
			address.setCodePostal(newInfo.getCodePostal());
		if(address.getPays() != newInfo.getPays())
			address.setPays(newInfo.getPays());
		updateAddressToDatabase(address);
		CopyDataBaseToShow(AddressServiceHolder.INSTANCE.AddressesDataBase);
	}
	
	/**
	 * Pour obtenir l'adresse avec son identifiant dans la bdd
	 * @param idaddress
	 * @return
	 */
	private static Address findById(int idaddress) {
		for(Address a : AddressServiceHolder.INSTANCE.AddressesDataBase)
			if(a.getIdAddress() == idaddress)
				return a;
		return null;
	}
	
	/**
	 * Ici par les adresses. 
	 * @param idaddress
	 * @return
	 */
	public static Address findAddressById(int idaddress) {
		for(Address a : AddressServiceHolder.INSTANCE.Addresses)
			if(a.getIdAddress() == idaddress)
				return a;
		return null;
	}
	
	/**
	 * Pour se connecter et obtenir les infos de la bdd
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
	 * Permet de retourner une liste avec toutes les adresses de la bdd
	 * @return
	 */
	private static List<Address> getAllAddressesFromDatabase()
	{
		List<Address> AddressList = new ArrayList<>();
		try (Connection connection = AddressService.prepareDataSource().getConnection()) {
			try (Statement statement = connection.createStatement()) {
				try (ResultSet results = statement.executeQuery("select * from address")) {
					while (results.next()) {
						Address address = new Address(
							 results.getString("number"),
							 results.getString("street"),
							 results.getString("town"),
							 results.getString("region"),
							 results.getString("pays"),
							 results.getInt("postCode"));
						address.setIdAddress(results.getInt("idaddress"));
						AddressList.add(address);
					}
				}
			}
		 } catch (SQLException e) {
		 // Manage Exception
		 e.printStackTrace();
		 }
		 return AddressList;
	}
	
	/**
	 * Permet d'ajouter les adresses dans la bdd
	 * @param address
	 * @return
	 */
	private static int addAddressToDatabase(Address address)
	{
		try (Connection connection = AddressService.prepareDataSource().getConnection()) {
			
			 String sqlQuery = "insert into address(number, street, town, postCode, region, pays) " + "VALUES(?,?,?,?,?,?)";
			 try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				 statement.setString(1, address.getNumero());
				 statement.setString(2, address.getRue());
				 statement.setString(3, address.getVille());
				 statement.setInt(4, address.getCodePostal());
				 statement.setString(5, address.getRégion());
				 statement.setString(6, address.getPays());
				 statement.executeUpdate();
				 ResultSet ids = statement.getGeneratedKeys();
				 
				 if (ids.next()) {
					 address.setIdAddress(ids.getInt(1));
					 return address.getIdAddress();
				 }
			 }
		} catch (SQLException e) {
			 e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Permet de mettre à jour les adresses dans la bdd
	 * @param address
	 * @return
	 */
	private static int updateAddressToDatabase(Address address)
	{
		try (Connection connection = AddressService.prepareDataSource().getConnection()) {
			
			 String sqlQuery = "update address set number=?, street=?, town=?, postCode=?, region=?, pays=? where idaddress=?";
			 try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				 statement.setString(1, address.getNumero());
				 statement.setString(2, address.getRue());
				 statement.setString(3, address.getVille());
				 statement.setInt(4, address.getCodePostal());
				 statement.setString(5, address.getRégion());
				 statement.setString(6, address.getPays());
				 statement.setInt(7, address.getIdAddress());
				 statement.executeUpdate();
				 ResultSet ids = statement.getGeneratedKeys();
			 }
		} catch (SQLException e) {
			 e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Permet de supprimer une adresse de la bdd
	 * @param idaddress
	 */
	public void delete(Integer idaddress) {
		 try (Connection connection = AddressService.prepareDataSource().getConnection()) {
			 try (PreparedStatement statement = connection.prepareStatement("delete from address where idaddress=?")) {
				 statement.setInt(1, idaddress);
				 statement.executeUpdate();
			 }
		 }catch (SQLException e) {
			 e.printStackTrace();
		 }
	}
	/**
	 * Dès qu'on ajoute un contact, cela va réagir avec une adresse selon les actions que nous voulons faire. 
	 *
	 */
	class AddressesListener implements ListChangeListener<Address> {
		@Override
		public void onChanged(Change<? extends Address> changedAddress) {
			Platform.runLater(() -> {
				changedAddress.reset();
				while(changedAddress.next())
				{
					if(changedAddress.wasPermutated())
					{
						
					}
					else if(changedAddress.wasUpdated())
					{
						System.out.println("Update");
						for(int i = changedAddress.getFrom(); i < changedAddress.getTo(); i++)
							AddressService.updateAddressToDatabase(changedAddress.getList().get(i));
					}
					else 
					{
						for (Address AddressToRemove : changedAddress.getRemoved()) {
							delete(AddressToRemove.getIdAddress());
	                     }
	                     for (Address AddressToAdd : changedAddress.getAddedSubList()) {
	                    	 AddressService.addAddressToDatabase(AddressToAdd);
	                     }
					}
				}
			});
		}
	}
}
