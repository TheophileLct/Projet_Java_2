package contact_app.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;

import contact_app.model.Address;

public class Contact {
	private int idperson;
	private String lastname;
	private String firstname;
	private String nickname;
	private String phone_number;
	private Address address;
	private String email_address;
	private LocalDate birth_date;

	public Contact(String lastname, String firstname, String nickname, String phone_number, Address address, String email_address, LocalDate birth_date) {
		this.idperson = -1;
		this.lastname = lastname;
		this.firstname = firstname;
		this.nickname = nickname;
		this.phone_number = phone_number;
		this.address = address;
		this.email_address = email_address;
		this.birth_date = birth_date;
		if(this.birth_date == null)
			this.birth_date = LocalDate.now();
	}
	public Contact(Contact c)
	{
		this.idperson = c.idperson;
		this.lastname = c.lastname;
		this.firstname = c.firstname;
		this.nickname = c.nickname;
		this.phone_number = c.phone_number;
		this.address = new Address(c.address);
		this.email_address = c.email_address;
		this.birth_date = c.birth_date;
	}

	
	/**
	 * @return the idAddress
	 */
	public int getIdAddress() {
		if(this.address == null)
			return -1;
		return this.address.getIdAddress();
	}
	
	/**
	 * @return the idperson
	 */
	public int getIdperson() {
		return idperson;
	}

	/**
	 * @param idperson the idperson to set
	 */
	public void setIdperson(int idperson) {
		this.idperson = idperson;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the phone_number
	 */
	public String getPhone_number() {
		return phone_number;
	}

	/**
	 * @param phone_number the phone_number to set
	 */
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the email_address
	 */
	public String getEmail_address() {
		return email_address;
	}

	/**
	 * @param email_address the email_address to set
	 */
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

	/**
	 * @return the birth_date
	 */
	public LocalDate getBirth_date() {
		return birth_date;
	}

	/**
	 * @param birth_date the birth_date to set
	 */
	public void setBirth_date(LocalDate birth_date) {
		this.birth_date = birth_date;
	}
	
	@Override
	public String toString()
	{
		String string = "\n";
		string += "BEGIN:VCARD" + "\n";
		string += "VERSION:4.0" + "\n";
		string += "UID:" + getIdperson() + "\n";
		string += "N:" + getLastname() + ";" + getFirstname() + ";" + "" + ";" + "" +  ";" + "" + "\n";
		string += "FN:" + getFirstname() + " " + getLastname() + "\n";
		string += "NICKNAME:" + getNickname() + "\n";
		string += "TEL;" + "TYPE=home,voice;" + "VALUE=uri:tel:" + getPhone_number() + "\n";
		string += "EMAIL:" + getEmail_address() + "\n";
		string += "ADR;" + "TYPE=HOME;" + "LABEL=" + getAddress();
		string += "BDAY:" + getBirth_date() + "\n";
		string += "END:VCARD";
		return string;
	}
	
	public final void export(File directory)
	{
		File file = new File(directory, getFirstname() + "_" + getLastname() + ".vcf");
		try(Writer writer = new OutputStreamWriter(new FileOutputStream(file.toString()), "UTF-8"))
		{
			String string = "\n";
			string += "BEGIN:VCARD" + "\n";
			string += "VERSION:4.0" + "\n";
			string += "UID:" + getIdperson() + "\n";
			string += "N:" + getLastname() + ";" + getFirstname() + ";" + "" + ";" + "" +  ";" + "" + "\n";
			string += "FN:" + getFirstname() + " " + getLastname() + "\n";
			string += "NICKNAME:" + getNickname() + "\n";
			string += "TEL;" + "TYPE=home,voice;" + "VALUE=uri:tel:" + getPhone_number() + "\n";
			string += "EMAIL:" + getEmail_address() + "\n";
			string += "ADR;" + "TYPE=HOME;" + "LABEL=" + getAddress();
			string += "BDAY:" + getBirth_date() + "\n";
			string += "END:VCARD";
			writer.write(string);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//Import de contact à faire. ToString a retoucher certainement qd on aura + d'info. 
	
}
