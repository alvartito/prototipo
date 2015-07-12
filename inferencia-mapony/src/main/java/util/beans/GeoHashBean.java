package util.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.hsr.geohash.GeoHash;
import util.constantes.MaponyCte;


/** @author Álvaro Sánchez Blasco
 *
 */
public class GeoHashBean {

	private final Logger logger = LoggerFactory.getLogger(GeoHashBean.class);
	
	private String name;
	private double latitude;
	private double longitude;
	private String timezone;
	private String pais;
	private String continente;
	private String geoHash;
	
	public GeoHashBean() {
	}


	/**
	 * @param geonameid
	 * @param name
	 * @param asciiname
	 * @param alternatenames
	 * @param latitude
	 * @param longitude
	 * @param featureclass
	 * @param featurecode
	 * @param countrycode
	 * @param cc2
	 * @param admin1code
	 * @param admin2code
	 * @param admin3code
	 * @param admin4code
	 * @param population
	 * @param elevation
	 * @param dem
	 * @param timezone
	 * @param modificationdate
	 */
	public GeoHashBean(final String geonameid, final String name, final String asciiname, final String alternatenames, final String latitude,
			final String longitude, String featureclass, final String featurecode, final String countrycode, final String cc2,
			final String admin1code, final String admin2code, final String admin3code, String admin4code, final String population,
			final String elevation, final String dem, final String timezone, final String modificationdate) {
		setName(name);
		setLatitude(new Double(latitude));
		setLongitude(new Double(longitude));
		setTimezone(timezone);
		String[] continentePais = timezone.split("/");
		setContinente(continentePais[0]);
		setPais(continentePais[1]);
		setGeoHash(GeoHash.geoHashStringWithCharacterPrecision(getLatitude(), getLongitude(), MaponyCte.precisionGeoHashCiudad));
	}

	public GeoHashBean(final String[] datos) {
		setName(datos[1]);
		setLatitude(new Double(datos[4]));
		setLongitude(new Double(datos[5]));
		setTimezone(datos[17]);
		if (null != getTimezone() && getTimezone().compareTo(MaponyCte.VACIO) != 0) {
			try {
				String[] continentePais = getTimezone().split("/");
				if (null != continentePais[0] && null != continentePais[1]) {
					setContinente(continentePais[0]);
					setPais(continentePais[1]);
					setGeoHash(GeoHash.geoHashStringWithCharacterPrecision(getLatitude(), getLongitude(), MaponyCte.precisionGeoHashCiudad));
				}
			} catch (Exception e) {
				getLogger().error(toStringError());
			}
		}
	}
	
	
	
	/**
	 * @see java.lang.Object#toString()
	 * @return getGeoHash() + "|" + getName() + "|" + getPais() + "|" + getContinente();
	 */
	@Override
	public String toString() {
		return "getGeoHash()" + "|" + "getName()" + "|" + "getPais()" + "|" + "getContinente() "+getGeoHash() + "|" + getName() + "|" + getPais() + "|" + getContinente();
	}

	/**
	 * @see java.lang.Object#toString()
	 * @return getGeoHash() + "|" + getName() + "|" + getPais() + "|" + getContinente();
	 */
	public String toStringError() {
		return "getTimezone()"+"|"+"getGeoHash()" + "|" + "getName()" + "|" + "getPais()" + "|" + "getContinente() '"+getTimezone()+"'|"+getGeoHash() + "|" + getName() + "|" + getPais() + "|" + getContinente();
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	private final void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the timezone
	 */
	private final String getTimezone() {
		return timezone;
	}


	/**
	 * @param timezone the timezone to set
	 */
	private final void setTimezone(final String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the pais
	 */
	public final String getPais() {
		return pais;
	}


	/**
	 * @param pais the pais to set
	 */
	private final void setPais(final String pais) {
		this.pais = pais;
	}


	/**
	 * @return the continente
	 */
	public final String getContinente() {
		return continente;
	}


	/**
	 * @param continente the continente to set
	 */
	private final void setContinente(final String continente) {
		this.continente = continente;
	}


	/**
	 * @return the latitude
	 */
	private final double getLatitude() {
		return latitude;
	}


	/**
	 * @param latitude the latitude to set
	 */
	private final void setLatitude(final double latitude) {
		this.latitude = latitude;
	}


	/**
	 * @return the longitude
	 */
	private final double getLongitude() {
		return longitude;
	}


	/**
	 * @param longitude the longitude to set
	 */
	private final void setLongitude(final double longitude) {
		this.longitude = longitude;
	}


	/**
	 * @return the geoHash
	 */
	public final String getGeoHash() {
		return geoHash;
	}


	/**
	 * @param geoHash the geoHash to set
	 */
	private final void setGeoHash(final String geoHash) {
		this.geoHash = geoHash;
	}


	/**
	 * @return the logger
	 */
	private final Logger getLogger() {
		return logger;
	}

//	public void write(DataOutput out) throws IOException {
//		Text.writeString(out, identifier);
//		Text.writeString(out, userNsid);
//		Text.writeString(out, userNickname);
//		Text.writeString(out, dateTaken);
//		Text.writeString(out, dateUploaded);
//		Text.writeString(out, captureDevice);
//		Text.writeString(out, title);
//		Text.writeString(out, description);
//		Text.writeString(out, userTags);
//		Text.writeString(out, machineTags);
//		Text.writeString(out, longitude);
//		Text.writeString(out, latitude);
//		Text.writeString(out, accuracy);
//		Text.writeString(out, pageUrl);
//		Text.writeString(out, downloadUrl);
//		Text.writeString(out, licenseName);
//		Text.writeString(out, licenseUrl);
//		Text.writeString(out, serverIdentifier);
//		Text.writeString(out, farmIdentifier);
//		Text.writeString(out, secret);
//		Text.writeString(out, secretOriginal);
//		Text.writeString(out, extension);
//		Text.writeString(out, marker);
//	}
//
//	public void readFields(DataInput in) throws IOException {
//		identifier = Text.readString(in);
//		userNsid = Text.readString(in);
//		userNickname = Text.readString(in);
//		dateTaken = Text.readString(in);
//		dateUploaded = Text.readString(in);
//		captureDevice = Text.readString(in);
//		title = Text.readString(in);
//		description = Text.readString(in);
//		userTags = Text.readString(in);
//		machineTags = Text.readString(in);
//		longitude = Text.readString(in);
//		latitude = Text.readString(in);
//		accuracy = Text.readString(in);
//		pageUrl = Text.readString(in);
//		downloadUrl = Text.readString(in);
//		licenseName = Text.readString(in);
//		licenseUrl = Text.readString(in);
//		serverIdentifier = Text.readString(in);
//		farmIdentifier = Text.readString(in);
//		secret = Text.readString(in);
//		secretOriginal = Text.readString(in);
//		extension = Text.readString(in);
//		marker = Text.readString(in);
//	}

}
