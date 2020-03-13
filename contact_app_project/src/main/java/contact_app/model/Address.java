package contact_app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Address {
	private int idAddress;
	private String numero; // String car par ex 10b.
	private String rue;
	private String ville;
	private String pays;
	private String région;

	public Address() {
		numero = "";
		rue = "";
		ville = "";
		région = "";
		pays = "";
		idAddress = -1;
	}
	
	public Address(String numero, String rue, String ville, String pays) {
		this.idAddress = -1;
		this.numero = numero;
		this.rue = rue;
		this.ville = ville;
		this.pays = pays;
	}
	
	public Address(Address a)
	{
		this.idAddress = a.idAddress;
		this.numero = a.numero;
		this.rue = a.rue;
		this.ville = a.ville;
		this.pays = a.pays;
	}

	/**
	 * @return the idAddress
	 */
	public int getIdAddress() {
		return idAddress;
	}

	/**
	 * @param idAddress the idAddress to set
	 */
	public void setIdAddress(int idAddress) {
		this.idAddress = idAddress;
	}
	
	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the rue
	 */
	public String getRue() {
		return rue;
	}

	/**
	 * @param rue the rue to set
	 */
	public void setRue(String rue) {
		this.rue = rue;
	}

	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}

	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}

	/**
	 * @return the pays
	 */
	public String getPays() {
		return pays;
	}

	/**
	 * @param pays the pays to set
	 */
	public void setPays(String pays) {
		this.pays = pays;
	}

	/**
	 * On affiche l'adresse complète
	 */
	@Override
	public final String toString() {
		String address = "";
		address = getNumero() + " " + getRue() + " " + getVille() + " "+ getPays();
		System.out.println(address);
		return address;
	}
}
