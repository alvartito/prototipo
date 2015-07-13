package proyecto.utad.mapony.prototipo.bean;

import util.constantes.MaponyCte;

/**
 * @author Álvaro Sánchez Blasco
 *
 */
public class RawDataBeanPrototipo {

	/** Photo/Video Identifier */
	private String identifier;
	/** Date Taken */
	private String dateTaken;
	/** Capture Device */
	private String captureDevice;
	/** Title */
	private String title;
	/** Description */
	private String description;
	/** User Tags (Comma-Separated) */
	private String userTags;
	/** Machine Tags (Comma-Separated) */
	private String machineTags;
	/** Longitude */
	private String longitude;
	/** Latitude */
	private String latitude;
	/** Photo/Video Download Url */
	private String downloadUrl;

	private String geoHash;

	private String geoHashCiudad;
	private String pais;
	private String ciudad;
	private String continente;

	public RawDataBeanPrototipo() {
		set("", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		return result;
	}

	/**
	 * @param identifier
	 * @param dateTaken
	 * @param captureDevice
	 * @param title
	 * @param description
	 * @param userTags
	 * @param machineTags
	 * @param longitude
	 * @param latitude
	 * @param downloadUrl
	 * @param geoHash
	 * @param geoHashCiudad
	 * @param continente
	 * @param pais
	 * @param ciudad
	 */
	public RawDataBeanPrototipo(String[] valores) {
		set(valores[0], valores[1], valores[2], valores[3], valores[4], valores[5], valores[6], valores[7], valores[8], valores[9], "", "", valores[10], valores[11], valores[12]);
	}
	
	public RawDataBeanPrototipo(String identifier, String dateTaken, String captureDevice, String title, String description, String userTags,
			String machineTags, String longitude, String latitude, String downloadUrl, String geoHash, String geoHashCiudad,
			String continente, String pais, String ciudad) {
		set(identifier, dateTaken, captureDevice, title, description, userTags, machineTags, longitude, latitude, downloadUrl, geoHash, geoHashCiudad, continente, pais, ciudad);
	}
	
	public String getString(){
		StringBuilder valorString = new StringBuilder();
		valorString.append(getIdentifier()).append(MaponyCte.PIPE);
		valorString.append(getDateTaken()).append(MaponyCte.PIPE);
		valorString.append(getCaptureDevice()).append(MaponyCte.PIPE);
		valorString.append(getTitle()).append(MaponyCte.PIPE);
		valorString.append(getDescription()).append(MaponyCte.PIPE);
		valorString.append(getUserTags()).append(MaponyCte.PIPE);
		valorString.append(getMachineTags()).append(MaponyCte.PIPE);
		valorString.append(getLongitude()).append(MaponyCte.PIPE);
		valorString.append(getLatitude()).append(MaponyCte.PIPE);
		valorString.append(getDownloadUrl()).append(MaponyCte.PIPE);
		valorString.append(getContinente()).append(MaponyCte.PIPE);
		valorString.append(getPais()).append(MaponyCte.PIPE);
		valorString.append(getCiudad());
		return valorString.toString();
	}
	
	/**
	 * @param identifier
	 * @param dateTaken
	 * @param captureDevice
	 * @param title
	 * @param description
	 * @param userTags
	 * @param machineTags
	 * @param longitude
	 * @param latitude
	 * @param downloadUrl
	 * @param geoHash
	 * @param geoHashCiudad
	 * @param continente
	 * @param pais
	 * @param ciudad
	 */
	public void set(String identifier, String dateTaken, String captureDevice, String title, String description, String userTags,
			String machineTags, String longitude, String latitude, String downloadUrl, String geoHash, String geoHashCiudad,
			String continente, String pais, String ciudad) {
		this.identifier = identifier;
		this.dateTaken = dateTaken;
		this.captureDevice = captureDevice;
		this.title = title;
		this.description = description;
		this.userTags = userTags;
		this.machineTags = machineTags;
		this.longitude = longitude;
		this.latitude = latitude;
		this.downloadUrl = downloadUrl;
		this.geoHash = geoHash;
		this.geoHashCiudad = geoHashCiudad;
		this.continente = continente;
		this.pais = pais;
		this.ciudad = ciudad;
	}

	public RawDataBeanPrototipo(RawDataBeanPrototipo rdw) {
		this.identifier = rdw.getIdentifier();
		this.dateTaken = rdw.getDateTaken();
		this.captureDevice = rdw.getCaptureDevice();
		this.title = rdw.getTitle();
		this.description = rdw.getDescription();
		this.userTags = rdw.getUserTags();
		this.machineTags = rdw.getMachineTags();
		this.longitude = rdw.getLongitude();
		this.latitude = rdw.getLatitude();
		this.downloadUrl = rdw.getDownloadUrl();
		this.geoHash = rdw.getGeoHash();
		this.geoHashCiudad = rdw.getGeoHashCiudad();
		this.continente = rdw.getContinente();
		this.pais = rdw.getPais();
		this.ciudad = rdw.getCiudad();
	}

	public int compareTo(RawDataBeanPrototipo o) {
		return identifier.compareTo(o.identifier);
	}

	public boolean equals(RawDataBeanPrototipo o) {
		return identifier.equals(o.identifier);
	}

	/**
	 * @return the identifier
	 */
	public final String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public final void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the dateTaken
	 */
	public final String getDateTaken() {
		return dateTaken;
	}

	/**
	 * @param dateTaken
	 *            the dateTaken to set
	 */
	public final void setDateTaken(String dateTaken) {
		this.dateTaken = dateTaken;
	}

	/**
	 * @return the captureDevice
	 */
	public final String getCaptureDevice() {
		return captureDevice;
	}

	/**
	 * @param captureDevice
	 *            the captureDevice to set
	 */
	public final void setCaptureDevice(String captureDevice) {
		this.captureDevice = captureDevice;
	}

	/**
	 * @return the title
	 */
	public final String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public final void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public final void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the userTags
	 */
	public final String getUserTags() {
		return userTags;
	}

	/**
	 * @param userTags
	 *            the userTags to set
	 */
	public final void setUserTags(String userTags) {
		this.userTags = userTags;
	}

	/**
	 * @return the machineTags
	 */
	public final String getMachineTags() {
		return machineTags;
	}

	/**
	 * @param machineTags
	 *            the machineTags to set
	 */
	public final void setMachineTags(String machineTags) {
		this.machineTags = machineTags;
	}

	/**
	 * @return the longitude
	 */
	public final String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public final void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public final String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public final void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the downloadUrl
	 */
	public final String getDownloadUrl() {
		return downloadUrl;
	}

	/**
	 * @param downloadUrl
	 *            the downloadUrl to set
	 */
	public final void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	/**
	 * @return the geoHash
	 */
	public final String getGeoHash() {
		return geoHash;
	}

	/**
	 * @param geoHash
	 *            the geoHash to set
	 */
	public final void setGeoHash(String geoHash) {
		this.geoHash = geoHash;
	}

	/**
	 * @return the geoHashCiudad
	 */
	public final String getGeoHashCiudad() {
		return geoHashCiudad;
	}

	/**
	 * @param geoHashCiudad
	 *            the geoHashCiudad to set
	 */
	public final void setGeoHashCiudad(String geoHashCiudad) {
		this.geoHashCiudad = geoHashCiudad;
	}

	/**
	 * @return the pais
	 */
	public final String getPais() {
		return pais;
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public final void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @return the ciudad
	 */
	public final String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad
	 *            the ciudad to set
	 */
	public final void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return the continente
	 */
	public final String getContinente() {
		return continente;
	}

	/**
	 * @param continente
	 *            the continente to set
	 */
	public final void setContinente(String continente) {
		this.continente = continente;
	}

}
