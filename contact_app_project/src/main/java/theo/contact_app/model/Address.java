package theo.contact_app.model;

public class Address {
	private String numero; //String car par ex 10b.
	private String rue; 
	private String ville;
	private String pays; 
	private String région;
	private int codePostal;
	
	public Address() {
		numero ="";
		rue="";
		ville="";
		région="";
		pays="";
		codePostal=-1;
	}
	
	public Address(String numero, String rue, String ville, String pays, String région, int codePostal) {
		this.numero=numero;
		this.rue=rue;
		this.ville=ville;
		this.pays=pays;
		this.région=région;
		this.codePostal=codePostal;
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
	 * @return the région
	 */
	public String getRégion() {
		return région;
	}

	/**
	 * @param région the région to set
	 */
	public void setRégion(String région) {
		this.région = région;
	}

	/**
	 * @return the codePostal
	 */
	public int getCodePostal() {
		return codePostal;
	}

	/**
	 * @param codePostal the codePostal to set
	 */
	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}
	
	@Override
	public final String toString() {
		String address="";
		address = getNumero()+" "+ getRue()+" "+ getCodePostal()+ " "+ getVille()+ " "+ getRégion()+" "+getPays();
		return address;
	}
}
