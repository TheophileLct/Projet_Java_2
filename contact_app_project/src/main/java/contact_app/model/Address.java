package contact_app.model;

public class Address {
	private int idAddress;
	private String numero; // String car par ex 10b.
	private String rue;
	private String ville;
	private String pays;

	public Address() {
		numero = "";
		rue = "";
		ville = "";
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
		address = getNumero() + " " + getRue() + " " + getVille() + ", " + getPays();
		return address;
	}
	public final String toVCard()
	{
		String VCard = "\"" + getNumero() + " " + getRue() + getVille() + getPays() + "\"" + ":;;" + getNumero() + " " + getRue() + ";" + getVille() + ";;;" + getPays() + "\n";
		return VCard;
	}
	public boolean equalsParameters(Address a)
	{
		if(numero.equals(a.getNumero()) && rue.equals(a.getRue()) &&
		ville.equals(a.getVille()) && pays.equals(a.getPays()))
			return true;
		return false;
	}
}
