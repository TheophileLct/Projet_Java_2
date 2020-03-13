package contact_app.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

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
	
	/**
	 * Affiche les contacts sous forme de vCard
	 */
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
	
	/**
	 * Permet de créer un fichier qui contenir nos contacts sous forme de vCard. 
	 * @param directory
	 */
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
	
	public static final Contact importFile(File file) throws IOException
	{
		List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		Iterator<String> line = lines.iterator();
		
		if(!line.hasNext())
		{
			new IOException("The file " + file + " is empty.");
		}
		
		String lastname = null;
		String firstname = null;
		String nickname = null;
		String phone_number = null;
		Address address = null;
		String email_address = null;
		LocalDate birth_date = null;
		while(line.hasNext())
		{
			String data = line.next();
			if(data.equals("BEGIN:VCARD") || data.regionMatches(0, "FN:", 0, 3))
			{
				continue;
			}
			else if(data.equals("END:VCARD"))
			{
				break;
			}
			
			else
			{
				String[] dataSeperate = data.split(":");
				switch (dataSeperate[0])
				{
					case "VERSION":
						if(!dataSeperate[1].equals("4.0"))
						{
							new IOException("The file " + file + " is in a wrong version.");
						}
						break;
					case "UID":
					break;
					case "N":
						dataSeperate = dataSeperate[1].split(";");
						lastname = dataSeperate[0];
						firstname = dataSeperate[1];
						break;
					case "NICKNAME":
						nickname = dataSeperate[1];
						break;
					case "EMAIL":
						email_address = dataSeperate[1];
						break;
					case "BDAY":
						birth_date = LocalDate.parse(dataSeperate[1]);
						break;
				}
				
				dataSeperate = data.split(";");
				switch (dataSeperate[0])
				{
					case "TEL":
						phone_number = dataSeperate[2].substring("VALUE=uri:tel:".length());
						break;
					case "ADR":
						String pays = "";
						String région = "";
						String ville = "";
						String rue = "";
						String numero = "";
						int codePostal =4444;
						switch(dataSeperate.length)
						{
							case 9:
								pays = dataSeperate[8];
							case 7:
								région = dataSeperate[6];
							case 6:
								ville = dataSeperate[5];
							case 5:
								rue = dataSeperate[4];
							case 4:
								numero = dataSeperate[3];
						}
						address = new Address(numero,rue, ville,pays,région, codePostal);
						break;
				}
			}
		}
		return new Contact(lastname, firstname, nickname, phone_number, address, email_address, birth_date);
	}
	
}
